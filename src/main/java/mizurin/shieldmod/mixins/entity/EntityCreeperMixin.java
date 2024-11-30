package mizurin.shieldmod.mixins.entity;

import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.entity.monster.EntityCreeper;
import net.minecraft.core.entity.monster.EntityMonster;
import net.minecraft.core.item.Item;
import net.minecraft.core.world.World;
import net.minecraft.core.world.weather.Weather;
import org.spongepowered.asm.mixin.Mixin;

import static mizurin.shieldmod.ShieldMod.expertMode;

@Mixin(value = EntityCreeper.class, remap = false)
public class EntityCreeperMixin extends EntityMonster {
	public EntityCreeperMixin(World world) {
		super(world);
	}
	public void spawnInit() {
		super.init();
		if (expertMode && (this.world.getCurrentWeather() == Weather.overworldStorm) && random.nextInt(50) == 0){
			this.entityData.set(17, (byte)1);
		}
		if(expertMode){
			this.mobDrops.add(new WeightedRandomLootObject(Item.seedsWheat.getDefaultStack(), 0, 1));
			this.mobDrops.add(new WeightedRandomLootObject(Item.sugarcane.getDefaultStack(), 0, 1));
		}
	}
}
