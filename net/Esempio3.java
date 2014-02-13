package net;


public class Esempio3
implements Executable
{
	public java.io.Serializable execute(java.util.List<java.io.Serializable> params)
	{
		System.out.println("Esempio di executable con parametri " + params);
		try
		{
			Thread.sleep(10000);
		}
		catch(InterruptedException ie)
		{
		}
		return "Ciao " + params.get(0) + " again: in space";
	}
}
