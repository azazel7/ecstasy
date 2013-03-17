package observation;

public class MagasinObservable extends BetterObservable
{
	public void notifyEject(int nombre, String code)
	{
		callWithObservers("onEject", nombre, code);
	}
	
}
