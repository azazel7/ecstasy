package observation;

public class MagasinObservable extends BetterObservable
{
	public void notifyEject(int nombre, String code, int position)
	{
		callWithObservers("onEject", nombre, code, position);
	}
	
	public void notifyOutOfStock(String code, int position)
	{
		callWithObservers("onOutOfStock", code, position);
	}
	

}
