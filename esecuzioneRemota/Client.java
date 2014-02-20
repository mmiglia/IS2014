package esecuzioneRemota;

import java.rmi.*;

public class Client
{
	public static void main(String[] argv) throws RemoteException, ResultUnavailableException, NotBoundException, java.net.MalformedURLException
	{
		String key = "pippo";
		if(argv.length>0)
		{
			key = argv[0];
		}

		RemExec re = (RemExec)Naming.lookup(key);

		Task t = new Task();
		System.out.println("execute");
		re.execute(t);
		t = new Task();
		System.out.println("submit " + t);
		int id1 = re.submit(t);
		t = new Task();
		System.out.println("submit " + t);
		int id2 = re.submit(t);
		System.out.println("Retrieve " + id2);
		System.out.println(re.retrieve(id2));
		System.out.println("Retrieve " + id1);
		System.out.println(re.retrieve(id1));

		t = new Task();
		System.out.println("Submit " + t);
		int id3 = re.submit(t);
		while(!re.isReady(id3))
		{
			System.out.println("Risultato non pronto...");
			try
			{
				Thread.sleep(1000);
			}
			catch(InterruptedException ie)
			{
				ie.printStackTrace();
			}
		}
		System.out.println("Retrieve " + id3);
		System.out.println(re.retrieve(id3));
	}
}
