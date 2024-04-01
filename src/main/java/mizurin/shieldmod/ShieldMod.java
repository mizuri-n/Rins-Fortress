package mizurin.shieldmod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.crafting.LookupFuelFurnace;
import net.minecraft.core.data.registry.Registries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.AchievementHelper;
import turniplabs.halplibe.util.GameStartEntrypoint;
import mizurin.shieldmod.item.Shields;
import turniplabs.halplibe.util.achievements.AchievementPage;

public class ShieldMod implements ModInitializer, GameStartEntrypoint {
    public static final String MOD_ID = "shieldmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);



    @Override
    public void onInitialize() {
        LOGGER.info("Better with Defense has been initialized!");
    }

	@Override
	public void beforeGameStart() {
		new Shields().initializeItems();
		AchievementPage SHIELDACHIEVEMENTS;
		SHIELDACHIEVEMENTS = new ShieldAchievements();
		AchievementHelper.addPage(SHIELDACHIEVEMENTS);

	}

	@Override
	public void afterGameStart() {
		LookupFuelFurnace.instance.addFuelEntry(Shields.woodenShield.id, 600);
		new recipes().initializeRecipe();
		LOGGER.info("Time to Block!");
	}
}
