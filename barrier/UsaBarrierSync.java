package barrier;

import java.util.*;

public class UsaBarrierSync
implements Runnable
{
	Random r;
	BarrierSync bs;

	public UsaBarrierSync()
	{
		r = new Random(System.currentTimeMillis());
		bs = new BarrierSyncImplementation();
	}

	public static void main(String[] argv)
	{
		UsaBarrierSync ubs = new UsaBarrierSync();

		for(int i=0;i<argv.length;i++)
		{
			(new Thread(ubs, argv[i])).start();
		}
		System.out.println("main: lanciati Thread, aspetto che si registrino");

		try
		{
			Thread.sleep(2000);
		}
		catch(InterruptedException ie)
		{
			ie.printStackTrace();
		}
		try
		{
			ubs.bs.register();
		}
		catch(BarrierSyncException bse)
		{
			bse.printStackTrace();
		}
		System.out.println("main esegui init su BarrierSync");
		try
		{
			ubs.bs.init();
		}
		catch(BarrierSyncException bse)
		{
			bse.printStackTrace();
		}

		System.out.println("main: Provo a registrarmi...");
		try
		{
			ubs.bs.register();
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
		System.out.println("Main: termina");
	}

	public void run()
	{
		String name = Thread.currentThread().getName();

		System.out.println("Thread <" + name + "> partito, mi registro");
		try
		{
			bs.register();
		}
		catch(BarrierSyncException bse)
		{
			bse.printStackTrace();
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
		System.out.println("Thread <" + name + "> aspetto un po' prima di fare la barrierSync");
		try
		{
			Thread.sleep(1000+r.nextInt(5000));
		}
		catch(InterruptedException ie)
		{
			ie.printStackTrace();
		}
		System.out.println("Thread <" + name + "> eseguo la barrierSync");
		try
		{
			bs.barrierSync();
		}
		catch(BarrierSyncException bse)
		{
			bse.printStackTrace();
		}
		System.out.println("Thread <" + name + "> liberi tutti");
	}
}
