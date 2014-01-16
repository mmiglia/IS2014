public class ClasseEsempio
{
	String p = "PIPPO";
	public static void main(String[] argv)
	{
		ClasseEsempio ce;
		if(argv.length > 0)
		{
			ce = new ClasseEsempio(argv[0]);
		}
		else
		{
			ce = new ClasseEsempio();
		}
		System.out.println(ce);
		System.out.println(ce.p);
	}

	public ClasseEsempio(String s)
	{
		System.out.println("Costruttore master");
		p = s;
	}

	public ClasseEsempio()
	{
		this("PIPPO");
		System.out.println("Costruttore default");
	}
}
