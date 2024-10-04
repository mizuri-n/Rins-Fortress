package mizurin.shieldmod.item;
import net.minecraft.core.entity.Entity;

public interface ParryInterface {
	int shieldmod$getParryTicks();
	void shieldmod$Parry(int parryTicks);
}
