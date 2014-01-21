public class MyRunnable
implements Runnable
{
	public static void main(String[] argv)
	{
		System.out.println("Sono il main");
		MyRunnable mt = new MyRunnable();
		mt.run();
		System.out.println("Sono ancora il main");
		Thread t = new Thread(mt);
		t.start();
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