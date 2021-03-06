package net;


import java.net.*;
import java.io.*;
import java.util.*;

public class ProxyServer
implements Runnable
{
	final int NUM_DEM = 3;
	java.util.concurrent.atomic.AtomicInteger counter = new java.util.concurrent.atomic.AtomicInteger(0);

	ServerSocket ss;
	List<Socket> queue;
	List<Request> rq;

	public ProxyServer(int port) throws IOException
	{
		ss = new ServerSocket(port);
		queue = new LinkedList<Socket>();
		rq = new LinkedList<Request>();
		for(int i = 0;i<NUM_DEM;i++)
		{
			Servant s = new Servant(rq);
			Thread t = new Thread(s);
			t.setDaemon(true);
			t.start();
		}
	}

	public ProxyServer() throws IOException
	{
		this(5678);
	}

	public static void main(String[] argv)
	{
		ProxyServer ps = null;
		try
		{
			if(argv.length > 0)
			{
				ps = new ProxyServer(Integer.parseInt(argv[0]));
			}
			else
			{
				ps = new ProxyServer();
			}
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			System.exit(1);
		}

		ps.exec();
	}

	void exec()
	{
		try
		{
			while(true)
			{
				Socket s = ss.accept();
				synchronized(queue)
				{
					queue.add(s);
				}
				(new Thread(this)).start();
			}
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}

	public void run()
	{
		System.out.println("Servo il Proxy # " + counter.incrementAndGet());
		Socket s = null;
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		synchronized(queue)
		{
			s = queue.remove(0);
		}

		try
		{
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());

			while(true)
			{
				/* Pre multiplexaggio canale da e verso client
				Executable ex = (Executable)ois.readObject();
				List<Serializable> l = (List<Serializable>)ois.readObject();
				*/
				Request r = (Request)ois.readObject();
				r.oos = oos;

				synchronized(rq)
				{
					rq.add(r);
					rq.notify();
				}
			}
		}
		catch(IOException ioe)
		{
			try
			{
				oos.close();
				ois.close();
				s.close();
			}
			catch(IOException ioe2)
			{
				ioe2.printStackTrace();
			}
		}
		catch(ClassNotFoundException cnfe)
		{
			try
			{
				oos.close();
				ois.close();
				s.close();
			}
			catch(IOException ioe2)
			{
				ioe2.printStackTrace();
			}
		}
		finally
		{
			System.out.println("Chiudo una connessione, adesso servo solo " + counter.decrementAndGet() + " proxy");
		}
	}

	public class Servant
	implements Runnable
	{
		List<Request> q;

		Servant(List<Request> theQ)
		{
			q = theQ;
		}

		public void run()
		{
			while(true)
			{
				Request r = null;
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
					r = q.remove(0);
				}

				r.result = r.ex.execute(r.p);
				try
				{
					ObjectOutputStream myOos = r.oos;
					r.oos = null;
					synchronized(myOos)
					{
						myOos.writeObject(r);
						myOos.flush();
					}
				}
				catch(IOException ioe)
				{
					ioe.printStackTrace();
				}
			}
		}
	}
}
