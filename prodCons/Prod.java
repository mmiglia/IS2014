package prodCons;

public class Prod
implements Runnable
{
	Buffer2<String> b = new Buffer2<String>();

	public static void main(String[] argv)
	{
		(new Prod()).exec(argv);

	}

	void exec(String[] argv)
	{
		(new Thread(this)).start();//faccio partire il consumatore

		//agisco da produttore
		for(int i=0;i<argv.length;i++)
		{
			b.put(argv[i]);
		}
	}

	public void run()
	{
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
}
