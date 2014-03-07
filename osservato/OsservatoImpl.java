package osservato;

import java.util.*;
import java.util.concurrent.atomic.*;

public class OsservatoImpl
implements Osservato
{
	List<Box> l;
	AtomicInteger ai;

	public OsservatoImpl()
	{
		l = new LinkedList<Box>();
		ai = new AtomicInteger(0);
	}

	public int registra(Osservatore o)
	{
		int retval = ai.getAndIncrement();
		synchronized(l)
		{
			l.add(new Box(retval, o);
		}
		return retval;
	}

	public void rimuovi(int id)
	{
		synchronized(l)
		{
			ListIterator<Box> li = l.listIterator();
			while(li.hasNext())
			{
				if(li.next().id == id)
				{
					li.remove();
					break;
				}
			}
		}
	}

	public void nuovoEvento(Evento e)
	{
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
	}
}
