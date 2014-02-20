package esecuzioneRemota;

import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class RemExecImpl
extends UnicastRemoteObject
implements RemExec
{
	public static final int NUM_SERV = 5;

	int idCount = 0;
	List<Task> queue;
	Map<Integer, Task> mappa;

	public RemExecImpl() throws RemoteException
	{
		super();

		queue = new LinkedList<Task>();
		mappa = new HashMap<Integer, Task>();

		for(int i = 0;i<NUM_SERV;i++)
		{
			(new Thread(new Servant(queue))).start();
		}
	}

	public Result execute(Task t) throws RemoteException
	{
		synchronized(queue)
		{
			queue.add(t);
			queue.notify();
		}

		synchronized(t)
		{
			while(!t.complete)
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
			return t.r;
		}
	}

	public int submit(Task t) throws RemoteException
	{
		int retVal;
		synchronized(mappa)
		{
			retVal = idCount++;

			mappa.put(new Integer(retVal), t);
		}

		synchronized(queue)
		{
			queue.add(t);
			queue.notify();
		}
		return retVal;
	}

	public boolean isReady(int id) throws RemoteException, ResultUnavailableException
	{
		boolean retval = false;
		Task t = null;
		synchronized(mappa)
		{
			t = mappa.get(new Integer(id));
		}

		if(t == null)//non sono stato il primo.... oppure non e' mai stato submitted il task con id data
		{
			throw new ResultUnavailableException("oops...");
		}

		synchronized(t)
		{
			return t.complete;
		}
	}

	public Result retrieve(int id) throws RemoteException, ResultUnavailableException
	{
		Task t = null;
		synchronized(mappa)
		{
			t = mappa.remove(new Integer(id));
		}
		if(t == null)//non sono stato il primo.... oppure non e' mai stato submitted il task con id data
		{
			throw new ResultUnavailableException("oops...");
		}

		synchronized(t)
		{
			while(!t.complete)
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
			return t.r;
		}
	}

	class Servant
	implements Runnable
	{
		List<Task> q;

		Servant(List<Task> l)
		{
			q = l;
		}

		public void run()
		{
			while(true)
			{
				Task t = null;
				synchronized(q)
				{
					while(q.isEmpty())
					{
						try
						{
							q.wait();
						}
						catch(InterruptedException ie)
						{
							ie.printStackTrace();
						}
					}
					t = q.remove(0);
				}

				//eseguo quello che c'e' da eseguire...
				t.run();

				synchronized(t)
				{
					t.complete = true;
					t.notify();
				}
			}
		}
	}
}
