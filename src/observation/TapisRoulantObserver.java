package observation;

import modele.Tas;

public interface TapisRoulantObserver extends IObserver
{
	void onWaitTick();
	void onCreateTas(Tas tas);
	void onDeleteTas(Tas tas);
	void onClearCarpet();
	void onEndGererCommande();
}
