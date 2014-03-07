package osservato;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class OsservatoImpl
implements Osservato, Runnable
{
	List<Box> l;
	AtomicInteger ai;

	BlockingQueue<Integer> q;

	BlockingQueue<Evento> codaInterna;

	public OsservatoImpl()
	{
		l = new LinkedList<Box>();
		ai = new AtomicInteger(0);
		q = new ArrayBlockingQueue<Integer>(2);

		codaInterna = new LinkedBlockingQueue<Evento>();

		(new Thread(new SimulaHardware(q))).start();
		(new Thread(new ConsumaDati(this))).start();
		(new Thread(this)).start();
	}

	public int registra(Osservatore o)
	{
		int retval = ai.getAndIncrement();
		synchronized(l)
		{
			l.add(new Box(retval, o));
		}
		return retval;
	}

	public void rimuovi(int id)
	{
		synchronized(l)
		{
			/* se ho definito equals in Box posso usare remove direttamente
			ListIterator<Box> li = l.listIterator();
			while(li.hasNext())
			{
				if(li.next().id == id)
				{
					li.remove();
					break;
				}
			}
			*/
			l.remove(new Box(id));
		}
	}

	public void nuovoEvento(Evento e)
	{
		try
		{
			codaInterna.put(e);
		}
		catch(InterruptedException ie)
		{
			ie.printStackTrace();
		}
	}

	void smistaEvento(Evento e)
	{
		synchronized(l)
		{
			ListIterator<Box> li = l.listIterator();
			while(li.hasNext())
			{
				Box b = li.next();
				b.o.callBack(e);
			}
		}
	}

	public void run()
	{
		while(true)
		{
			try
			{
				smistaEvento(codaInterna.take());
			}
			catch(InterruptedException ie)
			{
				ie.printStackTrace();
			}
			System.out.println("La coda interna contiene " + codaInterna.size() + " eventi");
		}
	}

	class Box
	{
		int id;
		Osservatore o;

		Box(int i, Osservatore oss)
		{
			id = i;
			o = oss;
		}

		Box(int i)
		{
			this(i, null);
		}

		public boolean equals(Object o)
		{
			if(! (o instanceof Box))
			{
				return false;
			}

			Box b = (Box)o;
			return b.id == id;
		}
	}

	class SimulaHardware
	implements Runnable
	{
		Random r = new Random(System.currentTimeMillis());
		BlockingQueue<Integer> q;

		SimulaHardware(BlockingQueue<Integer> abq)
		{
			q = abq;
		}

		public void run()
		{
			while(true)
			{
				Integer temp = new Integer(-5 + r.nextInt(30));
				if(!q.offer(temp))
				{
					System.out.println("Buffer overrun! Mi perdo la temperatura " + temp);
				}
				try
				{
					Thread.sleep(1000);
				}
				catch(InterruptedException ie)
				{
					ie.printStackTrace();
				}
			}
		}
	}

	class ConsumaDati
	implements Runnable
	{
		OsservatoImpl oi;

		ConsumaDati(OsservatoImpl ossI)
		{
			oi = ossI;
		}

		public void run()
		{
			while(true)
			{
				Integer temp = null;
				try
				{
					temp = oi.q.take();
				}
				catch(InterruptedException ie)
				{
					ie.printStackTrace();
					continue;
				}
				Evento e = new Evento();
				e.contenuto = temp;
				oi.nuovoEvento(e);
			}
		}
	}

	public static void main(String[] argv)
	{
		Osservato o = new OsservatoImpl();
		int[] ids = new int[argv.length];
		Random r = new Random(System.currentTimeMillis());
		for(int i=0;i<argv.length;i++)
		{
			Osservatore oss = new OsservatoreImpl(argv[i]);
			ids[i] = o.registra(oss);
			try
			{
				Thread.sleep(1000+r.nextInt(4000));
			}
			catch(InterruptedException ie)
			{
				ie.printStackTrace();
			}
		}

		o.registra(new OsservatoreImpl2("Gambadilegno"));
		/*
		int count = 0;
		while(true)
		{
			try
			{
				Thread.sleep(1000+r.nextInt(4000));
			}
			catch(InterruptedException ie)
			{
				ie.printStackTrace();
			}
			Evento e = new Evento();
			e.contenuto = new Integer(count++);

			System.out.println("Pubblico <" + e + ">");
			o.nuovoEvento(e);
		}
		*/
	}
}
