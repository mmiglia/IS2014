package macchine;

import java.util.*;

public class Macchina2
implements Runnable
{

	List<Prodotto> in;
	List<Prodotto>[] out;


	public Macchina2(List<Prodotto> i, List<Prodotto>[] o)
	{
		in = i;
		out = o;

		(new Thread(this)).start();
	}

	public void run()
	{
		String name = Thread.currentThread().getName();
		while(true)
		{
			Prodotto p = null;
			synchronized(in)
			{
				while(in.isEmpty())
				{
					try
					{
						in.wait();
					}
					catch(InterruptedException ie)
					{
						ie.printStackTrace();
					}
				}
				p = in.remove(0);
			}

			Operazione op = p.takeNext();
			//assumiamo che sia giusta per la macchina corrente, non facciamo controlli
			try
			{
				System.out.println("Macchina " + name + " in uso per " + op.getDurata() + " ms");
				Thread.sleep(op.getDurata());
			}
			catch(InterruptedException ie)
			{
				ie.printStackTrace();
			}

			op = p.getNext();
			if(op == null)
			{
				System.out.println("Prodotto " + p + " finito");
			}
			else
			{
				List<Prodotto> tmp = out[op.getTipo()];
				System.out.println("Accodo prodotto " + p + " per macchine " + op.getTipo());
				synchronized(tmp)
				{
					tmp.add(p);
					tmp.notify();
				}
			}
		}
	}
}
