package observation;

public class MagasinObservable extends BetterObservable
{
	public void notifyEject(int nombre, String code)
	{
		callWithObservers("onEject", nombre, code);
	}
	
	public void notifyOutOfStock(String code)
	{
		callWithObservers("onOutOfStock", code);
	}
}
