package net;


import java.util.*;
import java.io.*;

public class UsaProxy2
implements Runnable
{
	Proxy p = null;
	Executable e = null;
	Random rand = new Random(System.currentTimeMillis());
	int t;

	public static void main(String[] argv) throws Exception
	{
		(new UsaProxy2()).exec(argv);
	}

	void exec(String[] argv) throws Exception
	{
		t = rand.nextInt(100);
		System.out.println(t);
		p = new Proxy();
		e = new Esempio3();

		for(int i = 0;i<argv.length;i++)
		{
			(new Thread(this)).start();
		}
	}

	public void run()
	{
		List<Serializable> params = new LinkedList<Serializable>();
		params.add(Thread.currentThread().getName() + " " + t);
		try
		{
			System.out.println(Thread.currentThread().getName() + " -> " + p.doIt(e, params));
		}
		catch(IOException | ClassNotFoundException | UnconnectedProxyServerException ups)
		{
			ups.printStackTrace();
		}
	}
}
