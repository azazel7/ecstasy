package observation;

import modele.Tas;

public class TapisRoulantObservable extends BetterObservable
{
	public void notifyWaitTick()
	{
		callWithObservers("onWaitTick");
	}
	
	public void notifyCreateTas(Tas tas)
	{
		callWithObservers("onCreateTas", tas);
	}
	
	public void notifyDeleteTas(Tas tas)
	{
		callWithObservers("onDeleteTas", tas);
	}
}
