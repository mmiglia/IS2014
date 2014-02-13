package net;

import java.net.*;
import java.io.*;
import java.util.*;

public class Proxy
implements Runnable
{

	Socket s;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	boolean invalid = false;

	Map<String, Request> mappa;

	public Proxy(String host, int port) throws UnknownHostException, IOException
	{
		s = new Socket(host, port);
		try
		{
			oos = new ObjectOutputStream(s.getOutputStream());
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			s.close();
			throw ioe;
		}
		try
		{
			ois = new ObjectInputStream(s.getInputStream());
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			oos.close();
			s.close();
			throw ioe;
		}

		mappa = new HashMap<String, Request>();
		(new Thread(this)).start();
	}

	public Proxy() throws UnknownHostException, IOException
	{
		this("localhost", 5678);
	}

	public Serializable doIt(Executable ex, List<Serializable> params) throws IOException, ClassNotFoundException, UnconnectedProxyServerException
	{
		if(invalid)
		{
			throw new UnconnectedProxyServerException();
		}

		Request r = new Request(ex, params, Thread.currentThread().getName());
		synchronized(r)
		{
			synchronized(mappa)
			{
				mappa.put(r.id, r);
			}

			synchronized(oos)
			{
				oos.writeObject(r);
				oos.flush();
			}

			try
			{
				r.wait();
			}
			catch(InterruptedException ie)
			{
				ie.printStackTrace();
			}

			return r.result;
		}
	}

	public void run()
	{
		while(true)
		{
			Request r = null;
			try
			{
				r = (Request)ois.readObject();
			}
			catch(IOException | ClassNotFoundException ioe)
			{
				ioe.printStackTrace();
				invalid = true;
				return;
			}
			/*
			catch(ClassNotFoundException cnfe)
			{
				cnfe.printStackTrace();
				invalid = true;
				return;
			}
			*/

			Request orig = null;
			synchronized(mappa)
			{
				orig = mappa.remove(r.id);
			}

			synchronized(orig)
			{
				orig.result = r.result;
				orig.notify();
			}
		}
	}
}
