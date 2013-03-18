package observation;

public interface MagasinObserver extends IObserver
{
	void onEject(int nombre, String code);
	//void onRecharge(int nombre, String code);
}
