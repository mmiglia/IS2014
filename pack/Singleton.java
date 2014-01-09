package pack;

public class Singleton
{
	private static Singleton s = null;
	String name = "il mio nome e nessuno";

	private Singleton()
	{
		//voglio impedire l'accesso dall'esterno al costruttore di default
	}

	public static Singleton getSingletonInstance()
	{
		if(s == null)
		{
			s = new Singleton();
			System.out.println(s.name);
		}
		return s;
	}

	public static void main(String[] argv)
	{
		Singleton s = Singleton.getSingletonInstance();

		System.out.println(argv[0].equals(argv[1]));
	}
}
