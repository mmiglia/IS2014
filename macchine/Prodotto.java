package macchine;

import java.util.*;

public class Prodotto
{
	// parte comune a tutti i prodotti
	public static final int MAX_NUM_OP = 10;
	public static final int NUM_TIPI = 5;
	public static final int MAX_DURATION = 2000;
	static Random rand = new Random(System.currentTimeMillis());

	//parte specifica della singola istanza
	List<Operazione> seq;

	public Prodotto()
	{
		int numOp = rand.nextInt(MAX_NUM_OP)+1;
		seq = new LinkedList<Operazione>();
		for(int i=0;i<numOp;i++)
		{
			seq.add(new Operazione(rand.nextInt(NUM_TIPI), rand.nextInt(MAX_DURATION)));
		}
	}

	public Operazione takeNext() //null se e' finito
	{
		if(seq.isEmpty())
		{
			return null;
		}
		return seq.remove(0);
	}

	public Operazione getNext() //null se e' finito
	{
		if(seq.isEmpty())
		{
			return null;
		}
		return seq.get(0);
	}

	public String toString()
	{
		return seq.toString();
	}
}
