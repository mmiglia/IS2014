package osservato;

public class OsservatoreImpl
implements Osservatore
{
	String name;

	public OsservatoreImpl(String s)
	{
		name = s;
	}

	public void callBack(Evento e)
	{
		System.out.println("Sono l'osservatore " + name + " e osservo " + e);
	}
}
