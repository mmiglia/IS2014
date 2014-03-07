package barrier;

import java.rmi.*;
import java.net.MalformedURLException;
import java.util.*;

public class BarrierSyncProxy
implements BarrierSync
{

	private BarrierSync2 bs2;
	Map<Thread, Integer> mappa;

	public BarrierSyncProxy() throws BarrierSyncException
	{
		try
		{
			bs2 = (BarrierSync2)Naming.lookup("barrier");
		}
		catch(RemoteException | NotBoundException | MalformedURLException e)
		{
			BarrierSyncException bse = new BarrierSyncException();
			bse.initCause(e);
			throw bse;
		}

		mappa = Collections.synchronizedMap(new HashMap<Thread, Integer>());
	}


	/**
	* Metodo per registrare un partecipante al gruppo. Il partecipante e' il chiamante, non si possono registrare altri.
	* Lancia una eccezione BarrierSyncException nel caso in cui l'oggetto BarrierSync non sia piu' nello stato di registrazione partecipanti.
	* Lancia una eccezione BarrierSyncException nel caso in cui sia chiamata piu' di una volta dallo stesso chiamante.
	*/
	public void register() throws BarrierSyncException
	{
		try
		{
			int id = bs2.register();
			mappa.put(Thread.currentThread(), id);
		}
		catch(RemoteException re)
		{
			BarrierSyncException bse = new BarrierSyncException();
			bse.initCause(re);
			throw bse;
		}
	}

	/**
	* Forza la transizione dallo stato di registrazione allo stato di esecuzione della sincronizzazione a barriera.
	* Lancia un eccezione BarrierSyncException se chiamata piu' di una volta.
	*/
	public void init() throws BarrierSyncException
	{
		try
		{
			bs2.init();
		}
		catch(RemoteException re)
		{
			BarrierSyncException bse = new BarrierSyncException();
			bse.initCause(re);
			throw bse;
		}
	}

	/**
	* Metodo per eseguire la sincronizzazione a barriera. Quando viene chiamato blocca il chiamante sino a che tutti gli elementi del gruppo non hanno chiamato lo stesso metodo qulla stessa istanza.
	* Lancia BarrierSyncException nel caso in cui sia eseguito da qualcuno non parte del gruppo (non preventivamente registrato).
	* Lancia BarrierSyncException nel caso in cui la BarrierSync sia ancora nello stato di registrazione partecipanti.
	*/
	public void barrierSync() throws BarrierSyncException
	{
		try
		{
			int id = mappa.get(Thread.currentThread());
			bs2.barrierSync(id);
		}
		catch(NullPointerException | RemoteException re)
		{
			BarrierSyncException bse = new BarrierSyncException();
			bse.initCause(re);
			throw bse;
		}
	}
}
