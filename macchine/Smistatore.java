package macchine;

import java.util.*;

public class Smistatore
implements Runnable
{
	List<Prodotto> in;
	List<Prodotto>[] codeMacchine;

	public Smistatore(List<Prodotto> i, List<Prodotto>[] cm)
	{
		in = i;
		codeMacchine = cm;

		(new Thread(this)).start();
	}

	public void run()
	{
		while(true)
		{
			Prodotto p = null;
			synchronized(in)
			{
				if(in.isEmpty())
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

			Operazione op = p.getNext();
			if(op == null)
			{
				System.out.println("Prodotto <" + p + "> finito");
			}
			else
			{
				System.out.println("Accodo prodotto " + p + " per macchine di tipo " + op.getTipo());
				List<Prodotto> tmp = codeMacchine[op.getTipo()];
				synchronized(tmp)
				{
					tmp.add(p);
					tmp.notify();
				}
			}
		}
	}
}
