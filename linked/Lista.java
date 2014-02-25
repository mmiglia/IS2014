package linked;

public class Lista
{
	Nodo head, tail;
	int dim;

	public Object get(int index)
	{
		if(index >= dim || index < 0)
		{
			throw new java.util.NoSuchElementException("non ho un elemento in posizione <" + index + ">");
		}

		Nodo tmp = head;
		for(int i=0;i<index;i++)
		{
			tmp = tmp.getNext();
		}
		return tmp.getValore();
	}

	public void addFirst(Object o)
	{
		Nodo tmp = new Nodo(o);
		if(dim == 0)
		{
			head = tail = tmp;
			dim++;
			return;
		}

		tmp.setNext(head);
		head.setPrev(tmp);
		head = head.getPrev();// == head = tmp;
		dim++;
	}

	public Object remove(int index)
	{
		if(index < 0 || index >= dim)
		{
			throw new java.util.NoSuchElementException("non ho un elemento in posizione <" + index + ">");
		}

		Object retval;

		if(dim == 1)
		{
			retval = head.getValore();
			clear();
		}
		else if(index == 0)
		{
			retval = head.getValore();
			head = head.getNext();
			head.setPrev(null);
			dim--;
		}
		else if(index == dim-1)
		{
			retval = tail.getValore();
			tail = tail.getPrev();
			tail.setNext(null);
			dim--;
		}
		else
		{
			//sono in mezzo, caso generale
			//ottimizzo partendo da testa o coda

			Nodo tmp;
			if(index > dim/2)
			{
				tmp = tail;
				for(int i=dim-1;i>index;i--)
				{
					tmp = tmp.getPrev();
				}
			}
			else
			{
				tmp = head;
				for(int i=0;i<index;i++)
				{
					tmp = tmp.getNext();
				}
			}
			retval = tmp.getValore();
			tmp.getNext().setPrev(tmp.getPrev());
			tmp.getPrev().setNext(tmp.getNext());
			dim--;
		}

		return retval;
	}

	public Object removeLast()
	{
		return remove(size()-1);
	}

	public Object removeFirst()
	{
		return remove(0);
	}

	public void addLast(Object o)
	{
		Nodo tmp = new Nodo(o);
		if(dim == 0)
		{
			head = tail = tmp;
			dim++;
			return;
		}

		tail.setNext(tmp);
		tmp.setPrev(tail);
		tail = tail.getNext();// == tail = tmp;
		dim++;
	}

	public void clear()
	{
		dim = 0;
		head = tail = null;
	}

	public String toString()
	{
		String retval = "[";
		Nodo tmp = head;
		for(int i=0;i<dim-1;i++)
		{
			retval = retval.concat(String.valueOf(tmp.getValore()) + ", ");
			tmp = tmp.getNext();
		}
		if(dim > 0)
		{
			retval = retval.concat(String.valueOf(tmp.getValore()));
		}
		retval = retval.concat("]");
		return retval;
	}

	public int size()
	{
		return dim;
	}

	public boolean isEmpty()
	{
		return size() == 0;
	}

	public static void main(String[] argv)
	{
		Lista l = new Lista();
		for(int i = 0;i<argv.length;i++)
		{
			System.out.println(l.size());
			l.addLast(Integer.parseInt(argv[i]));
			l.addLast(argv[i]);
			System.out.println(l);
			System.out.println(l.size());
		}

		int accu = 0;
		for(int i = 0;i<l.size();i++)
		{
			System.out.println(l.get(i));
			Integer integer = (Integer)l.get(i);
			accu += integer.intValue();
		}
		System.out.println(accu);

		System.out.println(l);

		//while(l.size() > 0)
		while(!l.isEmpty())
		{
			//System.out.println(l.removeLast());
			System.out.println(l.removeFirst());
			System.out.println(l);
		}

		/*
		System.out.println(l.remove(l.size()-2));
		System.out.println(l);
		*/


		/*
		l.clear();
		for(int i = 0;i<argv.length;i++)
		{
			System.out.println(l.size());
			l.addFirst(argv[i]);
			System.out.println(l);
			System.out.println(l.size());
		}

		for(int i = 0;i<l.size();i++)
		{
			System.out.println(l.get(i));
		}

		System.out.println(l);
		*/

	}
}
