package mizurin.shieldmod;

import mizurin.shieldmod.entities.EntityPB;
import mizurin.shieldmod.entities.EntityRock;
import mizurin.shieldmod.entities.EntityShield;
import mizurin.shieldmod.entities.NetShieldEntry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.render.colorizer.Colorizers;
import net.minecraft.client.render.entity.ArmoredZombieRenderer;
import net.minecraft.client.render.entity.SnowballRenderer;
import net.minecraft.client.render.model.ModelZombie;
import net.minecraft.core.crafting.LookupFuelFurnace;
import net.minecraft.core.entity.SpawnListEntry;
import net.minecraft.core.enums.ArtType;
import net.minecraft.core.enums.EnumCreatureType;
import net.minecraft.core.item.Item;
import net.minecraft.core.net.entity.NetEntityHandler;
import net.minecraft.core.net.entity.entries.ArrowNetEntry;
import net.minecraft.core.world.biome.Biomes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.AchievementHelper;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import turniplabs.halplibe.util.ConfigHandler;
import turniplabs.halplibe.util.GameStartEntrypoint;
import mizurin.shieldmod.item.Shields;

import java.util.Properties;

public class ShieldMod implements ModInitializer, GameStartEntrypoint, ClientStartEntrypoint{
    public static final String MOD_ID = "shieldmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static int playerArmorRenderOffset = 0;

	public static int itemID;
	public static int entityID;
	public static ArtType paintingSeal;
	public static ArtType paintingRice;
	static {
		Properties prop = new Properties();
		prop.setProperty("starting_item_id", "21000");
		prop.setProperty("starting_entity_id", "100");
		ConfigHandler config = new ConfigHandler(ShieldMod.MOD_ID, prop);
		itemID = config.getInt("starting_item_id");
		entityID = config.getInt("starting_entity_id");
		config.updateConfig();
	}


    @Override
    public void onInitialize() {
        LOGGER.info("Better with Defense has been initialized!");
		new Shields().initializeItems();
		Colorizers.registerColorizers();
    }

	@Override
	public void beforeGameStart() {
		paintingSeal = new ArtType("paintingSeal", "The Orb", "Rin", "shieldmod:art/seal", 32, 32);
		paintingRice = new ArtType("paintingRice", "Lunch", "Rin", "shieldmod:art/onigiri", 32, 32);
		EntityHelper.createEntity(EntityShield.class, entityID, "ammoShield", () -> new SnowballRenderer(Shields.ammotearShield));
		EntityHelper.createEntity(EntityPB.class, ++entityID, "poisonBottle", () -> new SnowballRenderer(Shields.poisonBottle));
		EntityHelper.createEntity(EntityRock.class, ++entityID, "pebbleShield", () -> new SnowballRenderer(Item.ammoPebble));
		//AchievementPage SHIELDACHIEVEMENTS;
		//SHIELDACHIEVEMENTS = new ShieldAchievements();
		AchievementHelper.addPage(new ShieldAchievements());

		NetEntityHandler.registerNetworkEntry(new NetShieldEntry(), 8000);
	}

	@Override
	public void afterGameStart() {
		LookupFuelFurnace.instance.addFuelEntry(Shields.woodenShield.id, 600);
		new recipes().initializeRecipe();
		LOGGER.info("BWD initialized");
	}

	@Override
	public void beforeClientStart() {
	}

	@Override
	public void afterClientStart() {
	}
}
