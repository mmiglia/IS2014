package net;

import java.io.*;
import java.net.*;
import java.util.*;

public class ObjClient
{
	public static void main(String[] argv) throws ClassNotFoundException
	{
		int port = 5678;
		if(argv.length > 0)
		{
			port = Integer.parseInt(argv[0]);
		}

		try
		{
			Socket s = new Socket("localhost", port);
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());

			StringHolder sh = new StringHolder("");
			String line = null;
			while( (line = stdin.readLine()) != null)
			{
				System.out.println("Scrivo al server");
				sh.s = line;
				oos.writeObject(sh);
				oos.flush();

				System.out.println("Leggo dal server");
				line = (String)ois.readObject();
				System.out.println("Il server mi ha risposto <" + line + ">");
			}
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
}
