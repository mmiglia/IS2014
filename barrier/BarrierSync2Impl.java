package barrier;

import java.util.*;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.atomic.*;

public class BarrierSync2Impl
extends UnicastRemoteObject
implements BarrierSync2//, Runnable
{
	private AtomicInteger idCount = null;

	Set<Integer> participants = new HashSet<Integer>();
	boolean running = false;
	int counter = 0;
	int counter2 = 0;
	boolean primaFaseCompleta = false;
	boolean secondaFaseCompleta = false;

	public BarrierSync2Impl() throws RemoteException
	{
		super();
		idCount = new AtomicInteger(0);
	}

	public static void main(String[] argv) throws RemoteException, java.net.MalformedURLException
	{
		BarrierSync2Impl bs2 = new BarrierSync2Impl();
		Naming.rebind("barrier", bs2);
	}

	/**
	* Metodo per registrare un partecipante al gruppo. Il partecipante e' il chiamante, non si possono registrare altri.
	* Lancia una eccezione BarrierSyncException nel caso in cui l'oggetto BarrierSync non sia piu' nello stato di registrazione partecipanti.
	* Lancia una eccezione BarrierSyncException nel caso in cui sia chiamata piu' di una volta dallo stesso chiamante.
	*/
	public synchronized int register() throws BarrierSyncException, RemoteException
	{
		if(running)
		{
			throw new BarrierSyncException("Non posso registrare partecipanti una volta partita la BarrierSync");
		}
		int retval = idCount.getAndIncrement();
		System.out.println("Registro id <" + retval + ">");

		if(participants.contains(retval))
		{
			throw new BarrierSyncException("Non posso registrarmi piu' di una volta");
		}
		participants.add(retval);
		return retval;
	}

	/**
	* Forza la transizione dallo stato di registrazione allo stato di esecuzione della sincronizzazione a barriera.
	* Lancia un eccezione BarrierSyncException se chiamata piu' di una volta.
	*/
	public synchronized void init() throws BarrierSyncException, RemoteException
	{
		if(running)
		{
			throw new BarrierSyncException("L'oggetto BarrierSync non puo' essere inizializzato piu' di una volta");
		}
		running = true;
		System.out.println("Initialized");

		// con i thread su altre jvm non ho piu' controllo
		//(new Thread(this)).start();
	}

	/**
	* Metodo per eseguire la sincronizzazione a barriera. Quando viene chiamato blocca il chiamante sino a che tutti gli elementi del gruppo non hanno chiamato lo stesso metodo qulla stessa istanza.
	* Lancia BarrierSyncException nel caso in cui sia eseguito da qualcuno non parte del gruppo (non preventivamente registrato).
	* Lancia BarrierSyncException nel caso in cui la BarrierSync sia ancora nello stato di registrazione partecipanti.
	*/
	public synchronized void barrierSync(int id) throws BarrierSyncException, RemoteException
	{
		if(!running)
		{
			throw new BarrierSyncException("L'oggetto BarrierSync non e' ancora stato inizializzato, sono ancora in registrazione");
		}
		if(!participants.contains(id))
		{
			throw new BarrierSyncException("Il Client <" + id + "> non e' registrato in questa BarrierSync.");
		}
		counter++;

		/* I thread sono in client remoti, non ha senso il controllo
		Iterator<Thread> iter = participants.iterator();
		while(iter.hasNext())
		{
			Thread t = iter.next();
			if(!t.isAlive())
			{
				System.out.println("Thread <" + t.getName() + "> e' terminato, lo rimuovo");
				iter.remove();
			}
		}
		*/

		while(counter < participants.size() && !primaFaseCompleta)
		{
			try
			{
				wait();
			}
			catch(InterruptedException ie)
			{
				ie.printStackTrace();
			}
		}
		primaFaseCompleta = true;
		counter--;
		notifyAll();
		secondaFaseCompleta = false;

		counter2++;
		while(counter2 < participants.size() && !secondaFaseCompleta)
		{
			try
			{
				wait();
			}
			catch(InterruptedException ie)
			{
				ie.printStackTrace();
			}
		}
		secondaFaseCompleta = true;
		counter2--;
		notifyAll();
		primaFaseCompleta = false;
	}

	/* Non posso piu' controllare i Thread "vivi" perche' i clienti sono remoti
	public void run()
	{
		while(true)
		{
			try
			{
				Thread.sleep(1000);
			}
			catch(InterruptedException ie)
			{
				ie.printStackTrace();
			}

			synchronized(this)
			{
				Iterator<Thread> iter = participants.iterator();
				while(iter.hasNext())
				{
					Thread t = iter.next();
					if(!t.isAlive())
					{
						System.out.println("Whatchdog: Thread <" + t.getName() + "> e' terminato, lo rimuovo");
						iter.remove();
					}
				}
				if(counter == participants.size())
				{
					notifyAll();
					return;
				}
			}
		}
	}
	*/
}
