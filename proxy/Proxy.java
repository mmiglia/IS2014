package proxy;

import java.util.*;

public class Proxy
implements Runnable
{
	List<RichiestaDiMoltiplicazione> coda = null;
	List<Servant> listaServant = null;

	static Random rand = new Random(System.currentTimeMillis());
	final int MINIMO_SINDACALE = 3;

	public Proxy()
	{
		listaServant = new LinkedList<Servant>();
		coda = new LinkedList<RichiestaDiMoltiplicazione>();
		(new Thread(this)).start();
		for(int i = 0;i<MINIMO_SINDACALE;i++)
		{
			Servant s = new Servant(coda);
			listaServant.add(s);
			(new Thread(s)).start();
		}
	}

	public void run()
	{
		while(true)
		{
			System.out.println("Il becchino si addormenta...");
			try
			{
				Thread.sleep(1000*30);
			}
			catch(InterruptedException ie)
			{
				ie.printStackTrace();
			}

			System.out.println("Il becchino si sveglia...");
			synchronized(coda)
			{
				//controllo la coda per aumentare o diminuire il numero di Servant
				if(coda.size() == 0)
				{
					//riduco
					if(listaServant.size() > MINIMO_SINDACALE)
					{
						Servant s = listaServant.remove(0);
						System.out.println("Il becchino fa fuori " + s.toString());
						s.die();
					}
				}
			}
		}
	}

	/*
	public Risposta eseguiServizio(Parametri p)
	{
	}
	*/

	public double mul(double a, double b)
	{
		RichiestaDiMoltiplicazione rdm = new RichiestaDiMoltiplicazione(a, b);

		synchronized(coda)
		{
			//controllo la coda per aumentare o diminuire il numero di Servant
			if(coda.size() == 0)
			{
				//riduco
				if(listaServant.size() > MINIMO_SINDACALE)
				{
					Servant s = listaServant.remove(0);
					s.die();
				}
			}
			else
			{
				if(coda.size() >= MINIMO_SINDACALE)
				{
					Servant s = new Servant(coda);
					listaServant.add(s);
					(new Thread(s)).start();
				}
			}
			coda.add(rdm);
			coda.notify();
		}

		synchronized(rdm)
		{
			try
			{
				while(!rdm.isValid())
				{
					rdm.wait();
				}
			}
			catch(InterruptedException ie)
			{
				ie.printStackTrace();
			}
		}
		return rdm.getResult();
	}

	class RichiestaDiMoltiplicazione
	{
		double m1, m2;
		double result;
		boolean valid = false;

		RichiestaDiMoltiplicazione(double a, double b)
		{
			m1 = a;
			m2 = b;
		}

		boolean isValid()
		{
			return valid;
		}

		void setValid(boolean b)
		{
			valid = b;
		}

		double getResult()
		{
			return result;
		}

		void setResult(double r)
		{
			result = r;
		}

		double getM1()
		{
			return m1;
		}

		double getM2()
		{
			return m2;
		}
	}

	class Servant
	implements Runnable
	{
		boolean go = true;
		List<RichiestaDiMoltiplicazione> q;
		Thread t;

		Servant(List<RichiestaDiMoltiplicazione> theQ)
		{
			q = theQ;
		}

		public void run()
		{
			t = Thread.currentThread();
			while(go)
			{
				RichiestaDiMoltiplicazione rdm = null;
				synchronized(q)
				{
					while(q.isEmpty())
					{
						try
						{
							q.wait();
						}
						catch(InterruptedException ie)
						{
							ie.printStackTrace();
							if(!go)
							{
								break;
							}
						}
					}
					rdm = q.remove(0);
				}

				try
				{
					System.out.println("Servant " + Thread.currentThread().getName() + " eseguo servizio");
					Thread.sleep(1000 + rand.nextInt(1000));
					System.out.println("Servant " + Thread.currentThread().getName() + " eseguito servizio");
				}
				catch(InterruptedException ie)
				{
					ie.printStackTrace();
				}

				synchronized(rdm)
				{
					rdm.setResult(rdm.getM1() * rdm.getM2());
					rdm.setValid(true);
					rdm.notify();
				}
			}
			System.out.println(Thread.currentThread().getName() + " e stato bello fino a che e durato....");
		}

		void die()
		{
			go = false;
			t.interrupt();
		}
	}
}
