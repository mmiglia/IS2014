package esecuzioneRemota;

import java.rmi.*;
import java.net.*;

public class Server
{
	public static void main(String[] argv)
	{
		String key = "pippo";

		if(argv.length>0)
		{
			key = argv[0];
		}

		try
		{
			RemExec re = new RemExecImpl();
			Naming.bind(key, re);
		}
		catch(RemoteException | AlreadyBoundException | MalformedURLException re)
		{
			re.printStackTrace();
		}
	}
}
