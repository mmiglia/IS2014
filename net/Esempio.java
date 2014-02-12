package net;


public class Esempio
implements Executable
{
	public java.io.Serializable execute(java.util.List<java.io.Serializable> params)
	{
		System.out.println("Esempio di executable con parametri " + params);
		return "Ciao ciccio";
	}
}
