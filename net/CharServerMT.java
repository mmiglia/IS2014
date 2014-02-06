package net;

import java.net.*;
import java.io.*;
import java.util.*;

public class CharServerMT
{
	List<Socket> l;
	int port;
	ServerSocket ss = null;
	int idleHandler = 0;

	public static void main(String[] argv)
	{
		(new CharServerMT(argv)).exec();
	}

	public CharServerMT(String[] argv)
	{
		port = 5678;
		if(argv.length > 0)
		{
			port = Integer.parseInt(argv[0]);
		}

		try
		{
			ss = new ServerSocket(port);
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			System.exit(1);
		}

		l = new LinkedList<Socket>();
	}

	void exec()
	{
		while(true)
		{
			try
			{
				System.out.println("Accetto connessioni...");
				Socket s = ss.accept();
				System.out.println("Ricevuta connessione");

				synchronized(l)
				{
					l.add(s);
					l.notify();
					if(idleHandler < l.size())
					{
						(new Thread(new Handler(l, this))).start();
					}
				}
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
			}
		}
	}

	class Handler
	implements Runnable
	{
		List<Socket> q;
		Socket s = null;
		CharServerMT csmt = null;

		Handler(List<Socket> theQ, CharServerMT theS)
		{
			System.out.println("Creo Handler");
			q = theQ;
			csmt = theS;
		}

		public void run()
		{
			long waitingTime = 10000;
			while(true)
			{
				boolean goAway = false;
				s = null;
				BufferedReader br = null;
				PrintStream ps = null;
				synchronized(q)
				{
					while(q.isEmpty())
					{
						if(goAway)
						{
							//sono andato in timeout
							System.out.println("Handler dies");
							return;
						}
						try
						{
							csmt.idleHandler++;
							q.wait(waitingTime);
							csmt.idleHandler--;
						}
						catch(InterruptedException ie)
						{
							ie.printStackTrace();
						}
						goAway = true;
					}
					s = q.remove(0);
					goAway = false;
				}

				try
				{
					br = new BufferedReader(new InputStreamReader(s.getInputStream()));
					ps = new PrintStream(s.getOutputStream());
	
					String line = null;
					while( (line = br.readLine()) != null)
					{
						System.out.println("Mi hanno detto <" + line + ">");
						ps.println("Mi hai detto <" + line + ">");
					}
				}
				catch(IOException ioe)
				{
					ioe.printStackTrace();
				}
				finally
				{
					try
					{
						br.close();
						ps.close();
						s.close();
					}
					catch(IOException ioe)
					{
						ioe.printStackTrace();
					}
				}
			}
		}
	}
}
