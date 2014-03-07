package barrier;

public class UsaBarrierSyncProxy
implements Runnable
{
	BarrierSyncProxy bsp = null;
	public static void main(String[] argv) throws BarrierSyncException, InterruptedException
	{
		UsaBarrierSyncProxy ubsp = new UsaBarrierSyncProxy();
		ubsp.bsp = new BarrierSyncProxy();

		for(int i = 0;i<argv.length;i++)
		{
			(new Thread(ubsp, argv[i])).start();
		}

		System.out.println("Aspetto che si registrino");
		Thread.sleep(5000);

		ubsp.bsp.init();
		System.out.println("Inizializzata la barriera, io me ne vado");
	}

	public void run()
	{
		java.util.Random r = new java.util.Random(System.currentTimeMillis());
		String name = Thread.currentThread().getName();
		try
		{
			bsp.register();

			Thread.sleep(6000);

			while(true)
			{
				System.out.println(name + " aspetto");
				Thread.sleep(1000 + r.nextInt(2000));
				bsp.barrierSync();
				System.out.println(name + " sincronizzato");
				Thread.sleep(1000 + r.nextInt(2000));
			}

		}
		catch(BarrierSyncException | InterruptedException bse)
		{
			bse.printStackTrace();
			System.out.println(name + " termina");
		}
	}
}
