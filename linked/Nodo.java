package linked;

public class Nodo
{
	Object valore;
	Nodo prev;
	Nodo next;

	public Nodo(Object val)
	{
		valore = val;
		next = prev = null;
	}

	public Object getValore()
	{
		return valore;
	}

	public Nodo getNext()
	{
		return next;
	}

	public Nodo getPrev()
	{
		return prev;
	}

	public void setNext(Nodo n)
	{
		next = n;
	}

	public void setPrev(Nodo p)
	{
		prev = p;
	}

	public String toString()
	{
		return String.valueOf(valore);
	}

	public boolean equals(Object o)
	{
		assert o instanceof Nodo;

		if(! (o instanceof Nodo))
			return false;

		if(valore == null)
		{
			return (((Nodo)o).valore == null);
		}
		return valore.equals(((Nodo)o).valore);
	}
}
