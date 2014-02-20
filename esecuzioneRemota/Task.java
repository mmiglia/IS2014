package esecuzioneRemota;

import java.util.Random;

public class Task
implements java.io.Serializable, Runnable
{
	boolean complete = false;
	Result r = null;
	Random rand = new Random(System.currentTimeMillis());

	public void run()
	{
		System.out.println("Thread <" + Thread.currentThread().getName() + "> esegue task <" + toString() + ">");
		try
		{
			Thread.sleep(1000 + rand.nextInt(4001));
		}
		catch(InterruptedException ie)
		{
			ie.printStackTrace();
		}

		System.out.println("Thread <" + Thread.currentThread().getName() + "> eseguito task <" + toString() + ">");
	}
}
