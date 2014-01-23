package prodCons;

public class Prod2
implements Runnable
{
	Buffer2<String> b = new Buffer2<String>();

	public static void main(String[] argv)
	{
		(new Prod2()).exec(argv);

	}

	void exec(String[] argv)
	{
		int k = Integer.parseInt(argv[0]);
		for(int i = 0;i<k;i++)
		{
			(new Thread(this)).start();//faccio partire il consumatore
		}

		while(true)
		{
			String msg = b.remove();
			System.out.println(msg);
			try
			{
				Thread.sleep(1000);
			}
			catch(InterruptedException ie)
			{
				ie.printStackTrace();
			}
		}
	}

	public void run()
	{
		int i = 0;
		System.out.println("Consumatore");
		while(true)
		{
			//agisco da produttore
			b.put(Thread.currentThread().getName() + " " + (i++));
		}
	}
}
