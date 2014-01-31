package proxy;

public class UsaProxy
implements Runnable
{
	Proxy p = null;

	public static void main(String[] argv)
	{
		(new UsaProxy()).exec(argv);
	}

	void exec(String[] argv)
	{
		p = new Proxy();

		int i = 0;
		while(i < argv.length)
		{
			if(argv[i].equals("esegui"))
			{
				int num = Integer.parseInt(argv[++i]);
				for(int j = 0;j<num;j++)
				{
					(new Thread(this)).start();
				}
			}
			else if(argv[i].equals("aspetta"))
			{
				long num = Long.parseLong(argv[++i]);
				try
				{
					System.out.println("Nessuna richiesta per " + num + " ms");
					Thread.sleep(num);
				}
				catch(InterruptedException ie)
				{
					ie.printStackTrace();
				}
			}
			else
			{
				System.out.println("Non capisco <" + argv[i] + ">");
			}
			i++;
		}
	}

	public void run()
	{
		System.out.println(Thread.currentThread().getName() + " Chiedo servizio");
		p.mul(2 ,2 );
		System.out.println(Thread.currentThread().getName() + " Ottenuto servizio");
	}
}
