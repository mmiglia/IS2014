package prodCons;

public class Buffer2<E>
{
	protected E storage;
	protected Thread t;

	public void put(E elem)
	{
		synchronized(this)
		{
			//questo e' compatibile con un numero qualunque di produttori
			while(storage != null)
			{
				try
				{
					this.wait();
				}
				catch(InterruptedException ie)
				{
					ie.printStackTrace();
				}
			}
			//adesso c'e' spazio
			storage = elem;
			this.notify();
		}
	}

	public E remove()
	{
		E retval = null;

		synchronized(this)
		{
			//questo e' corretto se e solo se c'e' un solo consumatore
			if(storage == null)
			{
				try
				{
					this.wait();
				}
				catch(InterruptedException ie)
				{
					ie.printStackTrace();
				}
			}
			retval = storage;
			storage = null;
			this.notify();
		}
		return retval;
	}

	public boolean isEmpty()
	{
		return storage == null;
	}

	public String toString()
	{
		return String.valueOf(storage);
	}
}
