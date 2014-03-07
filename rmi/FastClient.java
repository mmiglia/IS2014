package rmi;

import java.rmi.*;

public class FastClient
implements Runnable
{
	public static void main(String[] argv) throws Exception
	{
		(new FastClient()).ex();
	}

	void ex()
	{
		(new Thread(this)).start();
		(new Thread(this)).start();
	}

	public void run()
	{
		String name = Thread.currentThread().getName();
		try
		{
			FastResponse fs = (FastResponse)Naming.lookup("fast");
			while(true)
			{
				Thread.sleep(1000);
				System.out.println(name + ": " + fs.fast());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
