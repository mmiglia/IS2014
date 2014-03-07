package barrier;

import java.util.*;
import java.rmi.*;

public class UsaBarrierSync2
implements Runnable
{
	Random r;
	BarrierSync2 bs;

	public UsaBarrierSync2() throws RemoteException, NotBoundException, java.net.MalformedURLException, ClassNotFoundException
	{
		r = new Random(System.currentTimeMillis());
		bs = (BarrierSync2)Naming.lookup("barrier");
	}

	public static void main(String[] argv) throws RemoteException, NotBoundException, java.net.MalformedURLException, ClassNotFoundException
	{
		UsaBarrierSync2 ubs = new UsaBarrierSync2();

		for(int i=0;i<argv.length;i++)
		{
			(new Thread(ubs, argv[i])).start();
		}
		System.out.println("main: lanciati Thread, aspetto che si registrino");

		try
		{
			Thread.sleep(5000);
		}
		catch(InterruptedException ie)
		{
			ie.printStackTrace();
		}

		int mainId = -1;

		/*
		System.out.println("main: Provo a registrarmi...");
		try
		{
			mainId = ubs.bs.register();
		}
		catch(BarrierSyncException bse)
		{
			bse.printStackTrace();
		}
		*/

		System.out.println("main esegui init su BarrierSync");
		try
		{
			ubs.bs.init();
		}
		catch(BarrierSyncException bse)
		{
			bse.printStackTrace();
		}
		try
		{
			Thread.sleep(10000);
		}
		catch(InterruptedException ie)
		{
			ie.printStackTrace();
		}

		System.out.println("main: barrier sync con id fasullo...");
		try
		{
			ubs.bs.barrierSync(0);
		}
		catch(BarrierSyncException bse)
		{
			bse.printStackTrace();
		}
		System.out.println("Main: termina");
	}

	public void run()
	{
		String name = Thread.currentThread().getName();
		int id = -1;

		try
		{
			System.out.println("Thread <" + name + "> partito, mi registro");
			try
			{
				id = bs.register();
			}
			catch(BarrierSyncException bse)
			{
				bse.printStackTrace();
				System.out.println("Thread <" + name + "> termina");
				return;
			}
			System.out.println("Thread <" + name + "> registrato, aspetto che main esegua init");
			try
			{
				Thread.sleep(5000);
			}
			catch(InterruptedException ie)
			{
				ie.printStackTrace();
			}
			while(true)
			{
				System.out.println("Thread <" + name + "> aspetto un po' prima di fare la barrierSync");
				try
				{
					Thread.sleep(r.nextInt(5000));
				}
				catch(InterruptedException ie)
				{
					ie.printStackTrace();
				}
				System.out.println("Thread <" + name + "> eseguo la barrierSync");
				try
				{
					bs.barrierSync(id);
				}
				catch(BarrierSyncException bse)
				{
					bse.printStackTrace();
					System.out.println("Thread <" + name + "> termina");
					return;
				}
				System.out.println("Thread <" + name + "> liberi tutti");
			}
		}
		catch(RemoteException re)
		{
			re.printStackTrace();
		}
	}
}
