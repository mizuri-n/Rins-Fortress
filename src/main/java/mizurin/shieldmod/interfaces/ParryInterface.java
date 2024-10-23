package mizurin.shieldmod.interfaces;

public interface ParryInterface {
	public int shieldmod$getParryTicks();
	public void shieldmod$Parry(int parryTicks);

	public int shieldmod$getBlockTicks();
	public void shieldmod$Block(int blockTicks);

	public boolean shieldmod$getIsBlock();
	public void shieldmod$setIsBlock(boolean bool);

	public int shieldmod$getCounterTicks();
	public void shieldmod$Counter(int counterTicks);

	public int shieldmod$getFireTicks();
	public void shieldmod$Fire(int fireTicks);
}
