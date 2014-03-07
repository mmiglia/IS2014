package barrier;

public interface BarrierSync2
extends java.rmi.Remote
{
	/**
	* Metodo per registrare un partecipante al gruppo. Il partecipante e' il chiamante, non si possono registrare altri.
	* Lancia una eccezione BarrierSyncException nel caso in cui l'oggetto BarrierSync non sia piu' nello stato di registrazione partecipanti.
	* Lancia una eccezione BarrierSyncException nel caso in cui sia chiamata piu' di una volta dallo stesso chiamante.
	*/
	int register() throws BarrierSyncException, java.rmi.RemoteException;

	/**
	* Forza la transizione dallo stato di registrazione allo stato di esecuzione della sincronizzazione a barriera.
	* Lancia un eccezione BarrierSyncException se chiamata piu' di una volta.
	*/
	void init() throws BarrierSyncException, java.rmi.RemoteException;

	/**
	* Metodo per eseguire la sincronizzazione a barriera. Quando viene chiamato blocca il chiamante sino a che tutti gli elementi del gruppo non hanno chiamato lo stesso metodo qulla stessa istanza.
	* Lancia BarrierSyncException nel caso in cui sia eseguito da qualcuno non parte del gruppo (non preventivamente registrato).
	* Lancia BarrierSyncException nel caso in cui la BarrierSync sia ancora nello stato di registrazione partecipanti.
	*/
	void barrierSync(int id) throws BarrierSyncException, java.rmi.RemoteException;
}
