package mizurin.shieldmod.interfaces;

public interface IStatus {
	public void shieldmod$dazedHurt(int dazedTicks);

	public int shieldmod$getDazedHurt();

	public void shieldmod$freezeHurt(int freezeTicks);

	public int shieldmod$getFreezeHurt();

	public void shieldmod$poisonHurt(int poisonTicks);

	public int shieldmod$getPoisonHurt();
}
