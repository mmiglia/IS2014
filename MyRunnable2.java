public class MyRunnable2
implements Runnable
{
	int quanti = 0;

	public static void main(String[] argv)
	{
		System.out.println("Sono il main");
		MyRunnable2 mt = new MyRunnable2(Integer.parseInt(argv[0]));
		//mt.run();
		System.out.println("Sono ancora il main");
		Thread t = new Thread(mt);
		Thread t1 = new Thread(mt);
		t.start();
		t.interrupt();
		try
		{
			Thread.sleep(100);
		}
		catch(InterruptedException ie)
		{
			ie.printStackTrace();
		}
		t.interrupt();
		t1.start();
		System.out.println("Sono il main, adesso aspetto il thread");
		try
		{
			t.join();
		}
		catch(InterruptedException ie)
		{
			ie.printStackTrace();
		}


		System.out.println("Sono sempre il main");
	}

	public MyRunnable2(int q)
	{
		quanti = q;
	}

	public void run()
	{

		long now = System.currentTimeMillis();
		long benchmark = now;
		long start = now;
		long correction = 0;

		// SBAGLIATO! nel senso di NON thread-safe
		while(quanti-- > 0)
		{
			benchmark += 1000;
			try
			{
				if(Thread.interrupted())
				{
					System.out.println("Ci hai provato...");
				}
				Thread.sleep(1000-(correction));
			}
			catch(InterruptedException ie)
			{
				ie.printStackTrace();
			}
			now = System.currentTimeMillis();
			correction = now - benchmark;
			System.out.println(Thread.currentThread().getName() + " " + (now - start) + " " + correction);
		}
	}
}