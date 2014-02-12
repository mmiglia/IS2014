package net;

import java.net.*;
import java.io.*;
import java.util.*;

public class Proxy
{

	Socket s;
	ObjectInputStream ois;
	ObjectOutputStream oos;

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
	}

	public Proxy() throws UnknownHostException, IOException
	{
		this("localhost", 5678);
	}

	public Serializable doIt(Executable ex, List<Serializable> params) throws IOException, ClassNotFoundException
	{
		Serializable retval = null;
		//poi lo faccio
		oos.writeObject(ex);
		oos.writeObject(params);
		oos.flush();
		retval = (Serializable)ois.readObject();
		return retval;
	}
}
