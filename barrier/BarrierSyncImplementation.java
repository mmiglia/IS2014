package barrier;

import java.util.*;

public class BarrierSyncImplementation
implements BarrierSync, Runnable
{

	Set<Thread> participants = new HashSet<Thread>();
	boolean running = false;
	int counter = 0;
	int counter2 = 0;
	boolean primaFaseCompleta = false;
	boolean secondaFaseCompleta = false;

	/**
	* Metodo per registrare un partecipante al gruppo. Il partecipante e' il chiamante, non si possono registrare altri.
	* Lancia una eccezione BarrierSyncException nel caso in cui l'oggetto BarrierSync non sia piu' nello stato di registrazione partecipanti.
	* Lancia una eccezione BarrierSyncException nel caso in cui sia chiamata piu' di una volta dallo stesso chiamante.
	*/
	public synchronized void register() throws BarrierSyncException
	{
		if(running)
		{
			throw new BarrierSyncException("Non posso registrare partecipanti una volta partita la BarrierSync");
		}
		Thread p = Thread.currentThread();
		if(participants.contains(p))
		{
			throw new BarrierSyncException("Non posso registrarmi piu' di una volta");
		}
		participants.add(p);
	}

	/**
	* Forza la transizione dallo stato di registrazione allo stato di esecuzione della sincronizzazione a barriera.
	* Lancia un eccezione BarrierSyncException se chiamata piu' di una volta.
	*/
	public synchronized void init() throws BarrierSyncException
	{
		if(running)
		{
			throw new BarrierSyncException("L'oggetto BarrierSync non puo' essere inizializzato piu' di una volta");
		}
		running = true;
		(new Thread(this)).start();
	}

	/**
	* Metodo per eseguire la sincronizzazione a barriera. Quando viene chiamato blocca il chiamante sino a che tutti gli elementi del gruppo non hanno chiamato lo stesso metodo qulla stessa istanza.
	* Lancia BarrierSyncException nel caso in cui sia eseguito da qualcuno non parte del gruppo (non preventivamente registrato).
	* Lancia BarrierSyncException nel caso in cui la BarrierSync sia ancora nello stato di registrazione partecipanti.
	*/
	public synchronized void barrierSync() throws BarrierSyncException
	{
		if(!running)
		{
			throw new BarrierSyncException("L'oggetto BarrierSync non e' ancora stato inizializzato, sono ancora in registrazione");
		}
		Thread p = Thread.currentThread();
		if(!participants.contains(p))
		{
			throw new BarrierSyncException("Il Thread <" + p + "> non e' registrato in questa BarrierSync.");
		}
		counter++;
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
}
