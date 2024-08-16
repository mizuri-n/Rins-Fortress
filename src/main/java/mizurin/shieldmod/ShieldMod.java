package mizurin.shieldmod;

import mizurin.shieldmod.item.EntityShield;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.SnowballRenderer;
import net.minecraft.core.crafting.LookupFuelFurnace;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.entity.projectile.EntitySnowball;
import net.minecraft.core.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.AchievementHelper;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import turniplabs.halplibe.util.ConfigHandler;
import turniplabs.halplibe.util.GameStartEntrypoint;
import mizurin.shieldmod.item.Shields;
import turniplabs.halplibe.util.achievements.AchievementPage;

import java.util.Properties;
import java.util.function.Supplier;

public class ShieldMod implements ModInitializer, GameStartEntrypoint, ClientStartEntrypoint {
    public static final String MOD_ID = "shieldmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static int itemID;
	public static int entityID;
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
		EntityHelper.createEntity(EntityShield.class, entityID, "ammoShield", () -> new SnowballRenderer(Shields.ammotearShield));
	}

	@Override
	public void afterClientStart() {
	}
}
