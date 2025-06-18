package mizurin.shieldmod;

import mizurin.shieldmod.blocks.RinBlocks;
import mizurin.shieldmod.entities.*;
import mizurin.shieldmod.item.Shields;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.entity.particle.ParticleFlame;
import net.minecraft.client.render.colorizer.Colorizers;
import net.minecraft.client.render.texture.stitcher.AtlasStitcher;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.crafting.LookupFuelFurnace;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.enums.ArtType;
import net.minecraft.core.net.entity.NetEntityHandler;
import net.minecraft.core.util.collection.NamespaceID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.helper.ParticleHelper;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import turniplabs.halplibe.util.ConfigHandler;
import turniplabs.halplibe.util.GameStartEntrypoint;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

public class ShieldMod implements ModInitializer, GameStartEntrypoint, ClientStartEntrypoint {
    public static final String MOD_ID = "shieldmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static int playerArmorRenderOffset = 0;

	public static int blockID;
	public static int itemID;
	public static int entityID;
	public static boolean hurtSound;
	public static boolean expertMode;
	public static boolean appleGenerate;
	public static ArtType paintingSeal;
	public static ArtType paintingRice;
	static {
		Properties prop = new Properties();
		prop.setProperty("starting_block_id", "7000");
		prop.setProperty("starting_item_id", "21000");
		prop.setProperty("starting_entity_id", "200");
		prop.setProperty("enable_hit_sounds", "false");
		prop.setProperty("enable_expert_mode", "false");
		prop.setProperty("enable_apple_tree_generation", "true");
		ConfigHandler config = new ConfigHandler(ShieldMod.MOD_ID, prop);
		blockID = config.getInt("starting_block_id");
		itemID = config.getInt("starting_item_id");
		entityID = config.getInt("starting_entity_id");
		hurtSound = config.getBoolean("enable_hit_sounds");
		expertMode = config.getBoolean("enable_expert_mode");
		appleGenerate = config.getBoolean("enable_apple_tree_generation");
		config.updateConfig();
	}


    @Override
    public void onInitialize() {
        LOGGER.info("Rin's Fortress has been initialized.");
    }

	@Override
	public void beforeGameStart() {
		new Shields().initializeItems();
		new RinBlocks().initializeBlocks();

		paintingSeal = new ArtType("paintingSeal", "The Orb", "Rin", "shieldmod:art/seal", 32, 32);
		paintingRice = new ArtType("paintingRice", "Lunch", "Rin", "shieldmod:art/onigiri", 32, 32);
		// TODO need to assign models with the model entrypoint and model helper now
		EntityHelper.createEntity(EntityShield.class, NamespaceID.getPermanent(MOD_ID, "ammo_shield"), null, "ammoShield", entityID);
		EntityHelper.createEntity(EntityPB.class, NamespaceID.getPermanent(MOD_ID, "poison_bottle"), null, "poisonBottle", ++entityID);
		EntityHelper.createEntity(EntityRock.class, NamespaceID.getPermanent(MOD_ID, "shield_pebble"), null, "pebbleShield", ++entityID);
		EntityHelper.createEntity(EntityFire.class, NamespaceID.getPermanent(MOD_ID, "fire"), null, "entityFire", ++entityID);
		EntityHelper.createEntity(EntityWeb.class, NamespaceID.getPermanent(MOD_ID, "web"), null, "entityWeb", ++entityID);
		EntityHelper.createEntity(EntityIceBall.class, NamespaceID.getPermanent(MOD_ID, "ammo_snow"), null, "ammoSnow", ++entityID);

		NetEntityHandler.registerNetworkEntry(new NetShieldEntry(), 8000);
		NetEntityHandler.registerNetworkEntry(new NetPotionEntry(), 8001);
		NetEntityHandler.registerNetworkEntry(new NetFireEntry(), 8002);
		NetEntityHandler.registerNetworkEntry(new NetWebEntry(), 8003);

		ParticleHelper.createParticle("purpleflame", ((world, d, e, f, g, h, i, j) -> new EntityPoisonFX(world, d, e, f, g, h, i, ParticleFlame.Type.BLUE)));

		try {
			for(AtlasStitcher stitcher : TextureRegistry.stitcherMap.values()) {
				TextureRegistry.initializeAllFiles(MOD_ID, stitcher, stitcher != TextureRegistry.artAtlas);
			}
		} catch (Exception e) {
			LOGGER.warn("Failed to fully initialize assets, some issue may occur!", e);
		}
	}

	@Override
	public void afterGameStart() {
		LookupFuelFurnace.instance.addFuelEntry(Shields.woodenShield.id, 600);
		Registries.RECIPE_TYPES.register("colored/shield", RecipeColor.class);
		LOGGER.info("RF initialized");
	}

	@Override
	public void beforeClientStart() {
		Colorizers.registerColorizers();
		for (final AtlasStitcher stitcher : TextureRegistry.stitcherMap.values()) {
			try {
				TextureRegistry.initializeAllFiles(MOD_ID, stitcher, true);
			} catch (URISyntaxException | IOException e) {
				LOGGER.error("Failed to initialize texture files!", e);
			}
		}
	}

	@Override
	public void afterClientStart() {
	}

}
