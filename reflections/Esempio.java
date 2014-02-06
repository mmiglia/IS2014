package reflections;

public class Esempio
implements java.io.Serializable
{
	String messaggio = null;

	public Esempio()
	{
		this(null);
	}

	public Esempio(String msg)
	{
		System.out.println("Sono solo una classe di esempio");
		messaggio = msg;
	}

	public String toString()
	{
		return "io sono una classe di esempio e dico: " + messaggio;
	}
}
