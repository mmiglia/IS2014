package prodCons;

public class Buffer<E>
{
	protected E storage;

	public void put(E elem)
	{
		while(true)
		{
			synchronized(this)
			{
				if(storage == null)
				{
					//adesso c'e' spazio
					storage = elem;
					break;
				}
			}
			System.out.println("put sleep");
			try
			{
				Thread.sleep(5000);
			}
			catch(InterruptedException ie)
			{
				ie.printStackTrace();
			}
		}
	}

	public E remove()
	{
		E retval = null;

		while(true)
		{
			synchronized(this)
			{
				if(storage != null)
				{
					retval = storage;
					storage = null;
					break;
				}
			}
			System.out.println("remove sleep");
			try
			{
				Thread.sleep(5000);
			}
			catch(InterruptedException ie)
			{
				ie.printStackTrace();
			}
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
