package mizurin.shieldmod;

import mizurin.shieldmod.blocks.RinBlocks;
import mizurin.shieldmod.entities.*;
import mizurin.shieldmod.item.Shields;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.render.colorizer.Colorizers;
import net.minecraft.client.render.entity.SnowballRenderer;
import net.minecraft.core.block.Block;
import net.minecraft.core.crafting.LookupFuelFurnace;
import net.minecraft.core.enums.ArtType;
import net.minecraft.core.item.Item;
import net.minecraft.core.net.entity.NetEntityHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import turniplabs.halplibe.util.ConfigHandler;
import turniplabs.halplibe.util.GameStartEntrypoint;

import java.util.Properties;

public class ShieldMod implements ModInitializer, GameStartEntrypoint, ClientStartEntrypoint{
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
		new Shields().initializeItems();
		new RinBlocks().initializeBlocks();
		Colorizers.registerColorizers();
    }

	@Override
	public void beforeGameStart() {
		paintingSeal = new ArtType("paintingSeal", "The Orb", "Rin", "shieldmod:art/seal", 32, 32);
		paintingRice = new ArtType("paintingRice", "Lunch", "Rin", "shieldmod:art/onigiri", 32, 32);
		EntityHelper.createEntity(EntityShield.class, entityID, "ammoShield", () -> new SnowballRenderer(Shields.ammotearShield));
		EntityHelper.createEntity(EntityPB.class, ++entityID, "poisonBottle", () -> new SnowballRenderer(Shields.poisonBottle));
		EntityHelper.createEntity(EntityRock.class, ++entityID, "pebbleShield", () -> new SnowballRenderer(Item.ammoPebble));
		EntityHelper.createEntity(EntityFire.class, ++entityID, "entityFire", () -> new SnowballRenderer(Block.fire.asItem()));
		EntityHelper.createEntity(EntityWeb.class, ++entityID, "entityWeb", () -> new SnowballRenderer(Block.cobweb.asItem()));
		EntityHelper.createEntity(EntityIceBall.class, ++entityID, "ammoSnow", () -> new SnowballRenderer(Item.ammoSnowball));

		NetEntityHandler.registerNetworkEntry(new NetShieldEntry(), 8000);
		NetEntityHandler.registerNetworkEntry(new NetPotionEntry(), 8001);
		NetEntityHandler.registerNetworkEntry(new NetFireEntry(), 8002);
		NetEntityHandler.registerNetworkEntry(new NetWebEntry(), 8003);
	}

	@Override
	public void afterGameStart() {
		LookupFuelFurnace.instance.addFuelEntry(Shields.woodenShield.id, 600);
		new Recipes().initializeRecipe();
		LOGGER.info("RF initialized");
	}

	@Override
	public void beforeClientStart() {
	}

	@Override
	public void afterClientStart() {
	}



}
