package mizurin.shieldmod;

import mizurin.shieldmod.entities.ShieldZombie;
import mizurin.shieldmod.entities.EntityShield;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.render.entity.ArmoredZombieRenderer;
import net.minecraft.client.render.entity.SnowballRenderer;
import net.minecraft.client.render.model.ModelZombie;
import net.minecraft.core.crafting.LookupFuelFurnace;
import net.minecraft.core.enums.ArtType;
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
		prop.setProperty("starting_item_id", "17000");
		prop.setProperty("starting_entity_id", "100");
		ConfigHandler config = new ConfigHandler(ShieldMod.MOD_ID, prop);
		itemID = config.getInt("starting_item_id");
		entityID = config.getInt("starting_entity_id");
		config.updateConfig();
	}


    @Override
    public void onInitialize() {
        LOGGER.info("Better with Defense has been initialized!");
    }

	@Override
	public void beforeGameStart() {
		new Shields().initializeItems();
		paintingSeal = new ArtType("paintingSeal", "The Orb", "Rin", "shieldmod:art/seal", 32, 32);
		paintingRice = new ArtType("paintingRice", "Lunch", "Rin", "shieldmod:art/onigiri", 32, 32);
		EntityHelper.createEntity(EntityShield.class, entityID, "ammoShield", () -> new SnowballRenderer(Shields.ammotearShield));
		EntityHelper.createEntity(ShieldZombie.class, ++entityID, "ShieldedZombie", () -> new ArmoredZombieRenderer(new ModelZombie(), 0.1F));
		//AchievementPage SHIELDACHIEVEMENTS;
		//SHIELDACHIEVEMENTS = new ShieldAchievements();
		AchievementHelper.addPage(new ShieldAchievements());
	}

	@Override
	public void afterGameStart() {
		LookupFuelFurnace.instance.addFuelEntry(Shields.woodenShield.id, 600);
		new recipes().initializeRecipe();
		LOGGER.info("Time to Block!");
	}

	@Override
	public void beforeClientStart() {
	}

	@Override
	public void afterClientStart() {
	}
}
