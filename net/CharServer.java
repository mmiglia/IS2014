package net;

import java.net.*;
import java.io.*;

public class CharServer
{
	public static void main(String[] argv)
	{
		int port = 5678;
		if(argv.length > 0)
		{
			port = Integer.parseInt(argv[0]);
		}

		ServerSocket ss = null;
		try
		{
			ss = new ServerSocket(port);
			// me lo hanno tolto... tanto non funzionava mai...
			//ss.listen(1);
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			System.exit(1);
		}

		while(true)
		{
			try
			{
				System.out.println("Accetto connessioni...");
				Socket s = ss.accept();
				System.out.println("Ricevuta connessione");
				BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				PrintStream ps = new PrintStream(s.getOutputStream());

				String line = null;
				while( (line = br.readLine()) != null)
				{
					System.out.println("Mi hanno detto <" + line + ">");
					ps.println("Mi hai detto <" + line + ">");
				}
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
