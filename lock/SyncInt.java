package lock;

public class SyncInt
implements Runnable
{
	Integer i = new Integer(0);

	public static void main(String[] argv)
	{
		int j;
		SyncInt si = new SyncInt();
		for(j=0;j<argv.length;j++)
		{
			(new Thread(si)).start();
		}
	}

	public void run()
	{
		while(true)
		{
			synchronized(i)
			{
				System.out.println(Thread.currentThread().getName() + " acquisito lock per la " + i.intValue() + " volta");
				i++;
				try
				{
					Thread.sleep(1000);
				}
				catch(InterruptedException ie)
				{
					ie.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + " dormito");
			}
			System.out.println(Thread.currentThread().getName() + " rilasciato lock");
		}
	}
}
