package mizurin.shieldmod.mixins.entity;

import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.entity.monster.MobCreeper;
import net.minecraft.core.entity.monster.MobMonster;
import net.minecraft.core.item.Items;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;

import static mizurin.shieldmod.ShieldMod.expertMode;
import static net.minecraft.core.world.weather.Weathers.OVERWORLD_STORM;

@Mixin(value = MobCreeper.class, remap = false)
public class EntityCreeperMixin extends MobMonster {
	public EntityCreeperMixin(World world) {
		super(world);
	}
	public void spawnInit() {
		if (expertMode && (this.world.getCurrentWeather() == OVERWORLD_STORM) && random.nextInt(25) == 0){
			this.entityData.set(17, (byte)1);
		}
		if(expertMode){
			this.mobDrops.add(new WeightedRandomLootObject(Items.SEEDS_WHEAT.getDefaultStack(), 0, 1));
			this.mobDrops.add(new WeightedRandomLootObject(Items.SUGARCANE.getDefaultStack(), 0, 1));
		}
	}
}
