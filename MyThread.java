public class MyThread
extends Thread
{
	public static void main(String[] argv)
	{
		System.out.println("Sono il main");
		MyThread mt = new MyThread();
		mt.run();
		System.out.println("Sono ancora il main");
		mt.start();
		System.out.println("Sono il main, adesso aspetto il thread");
		try
		{
			mt.join();
		}
		catch(InterruptedException ie)
		{
			ie.printStackTrace();
		}


		System.out.println("Sono sempre il main");
	}

	public void run()
	{
		System.out.println("Sono un Thread");
		try
		{
			Thread.sleep(2000);
		}
		catch(InterruptedException ie)
		{
			ie.printStackTrace();
		}
		System.out.println("Sono sempre lo stesso Thread");
	}
}