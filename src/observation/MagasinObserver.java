package observation;

public interface MagasinObserver extends IObserver
{
	void onEject(int nombre, String code);
}
