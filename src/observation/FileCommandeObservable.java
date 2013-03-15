package observation;

public class FileCommandeObservable extends BetterObservable
{
	public void notifyAddCommande()
	{
		callWithObservers("onAddCommande");
	}
	
	public void notifyDeleteCommande()
	{
		callWithObservers("onDeleteCommande");
	}
}
