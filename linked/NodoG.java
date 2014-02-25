package linked;

public class NodoG<T>
{
	T valore;
	NodoG<T> prev;
	NodoG<T> next;

	public NodoG(T val)
	{
		valore = val;
		next = prev = null;
	}

	public T getValore()
	{
		return valore;
	}

	public NodoG<T> getNext()
	{
		return next;
	}

	public NodoG<T> getPrev()
	{
		return prev;
	}

	public void setNext(NodoG<T> n)
	{
		next = n;
	}

	public void setPrev(NodoG<T> p)
	{
		prev = p;
	}

	public String toString()
	{
		return String.valueOf(valore);
	}

	public boolean equals(Object o)
	{
		assert o instanceof NodoG;

		if(! (o instanceof NodoG))
			return false;

		if(valore == null)
		{
			return (((NodoG<T>)o).valore == null);
		}
		return valore.equals(((NodoG<T>)o).valore);
	}
}
