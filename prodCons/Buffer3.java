package prodCons;

import java.util.*;

public class Buffer3<E>
{
	protected E[] storage;
	protected int currSize;
	protected int head, tail;
	protected List<Thread> elencoAttesaProduttori, elencoAttesaConsumatori;

	public Buffer3(int max)
	{
		storage = (E[])(new Object[max]);
		head = tail = currSize = 0;
		elencoAttesaProduttori = new LinkedList<Thread>();
		elencoAttesaConsumatori = new LinkedList<Thread>();
	}

	public boolean isFull()
	{
		return currSize == storage.length;
	}

	public boolean isEmpty()
	{
		return currSize == 0;
	}

	public int size()
	{
		return currSize;
	}

	public void put(E elem)
	{
		while(true)
		{
			Thread t = Thread.currentThread();
			synchronized(t)
			{
				boolean attesa = false;
				synchronized(storage)
				{
					if(isFull())
					{
						//poi vediamo
						attesa = true;
						elencoAttesaProduttori.add(t);
					}
					else
					{
						storage[tail] = elem;
						currSize++;
						tail = (tail + 1)%storage.length;
						if(size() == 1 && !elencoAttesaConsumatori.isEmpty())
						{
							Thread tc = elencoAttesaConsumatori.remove(0);
							synchronized(tc)
							{
								tc.notify();
							}
						}
					}
				}
				if(attesa)
				{
					synchronized(t)
					{
						try
						{
							t.wait();
						}
						catch(InterruptedException ie)
						{
							ie.printStackTrace();
						}
					}
				}
				else
				{
					return;
				}
			}
		}
	}

	public E remove()
	{
		E retval = null;
		while(true)
		{
			boolean attesa = false;
			Thread t = Thread.currentThread();
			synchronized(t)
			{
				synchronized(storage)
				{
					if(isEmpty())
					{
						//poi vediamo
						elencoAttesaConsumatori.add(t);
						attesa = true;
					}
					else
					{
						retval = storage[head];
						currSize--;
						head = (head + 1)%storage.length;

						if(size() == storage.length-1 && !elencoAttesaProduttori.isEmpty())
						{
							Thread tc = elencoAttesaProduttori.remove(0);
							synchronized(tc)
							{
								tc.notify();
							}
						}
					}
				}
				if(attesa)
				{
					try
					{
						t.wait();
					}
					catch(InterruptedException ie)
					{
						ie.printStackTrace();
					}
				}
				else
				{
					break;
				}
			}
		}
		return retval;
	}

	public String toString()
	{
		String retval = "[";
		int i = 0;
		for(i=0;i<size()-1;i++)
		{
			retval = retval.concat(storage[(i+head)%storage.length].toString() + ", ");
		}
		retval = retval.concat(storage[(i+head)%storage.length].toString());
		return retval + "]";
	}
}
