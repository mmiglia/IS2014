package net;

import java.net.*;
import java.io.*;

public class ObjServer
{
	public static void main(String[] argv) throws ClassNotFoundException
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
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());

				Object line = null;
				while( (line = ois.readObject()) != null)
				{
					System.out.println("Mi hanno detto <" + line + ">");
					oos.writeObject(new String("Mi hai detto <" + line + ">"));
					oos.flush();
				}
				ois.close();
				oos.close();
				s.close();
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
			}
		}
	}
}
