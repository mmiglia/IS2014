package osservato;

public class OsservatoreImpl2
implements Osservatore
{
	String name;

	public OsservatoreImpl2(String s)
	{
		name = s;
	}

	public void callBack(Evento e)
	{
		System.out.println("Sono l'osservatore " + name + " e osservo " + e);
		try
		{
			Thread.sleep(5000);
		}
		catch(InterruptedException ie)
		{
		}
	}
}
