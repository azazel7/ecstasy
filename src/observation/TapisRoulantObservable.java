package observation;

public class TapisRoulantObservable extends BetterObservable
{
	public void notifyWaitTick()
	{
		callWithObservers("onWaitTick");
	}
}
