package net;

import java.io.*;
import java.net.*;
import java.util.*;

public class CharClient
{
	public static void main(String[] argv)
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

			Scanner br = new Scanner(s.getInputStream());
			PrintStream ps = new PrintStream(s.getOutputStream());

			String line = null;
			while( (line = stdin.readLine()) != null)
			{
				System.out.println("Scrivo al server");
				ps.println(line);

				System.out.println("Leggo dal server");
				line = br.nextLine();
				System.out.println("Il server mi ha risposto <" + line + ">");
			}
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
}
