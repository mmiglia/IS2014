public class BloccoInit
{
	public static void main(String[] argv)
	{
		for(int i = 0;i<10;i++)
		{
			new BloccoInit();
		}
	}

	public BloccoInit()
	{
		System.out.println("Salve, sono il costruttore di default");
	}

	static
	{
		System.out.println("Buongiorno, sono il blocco di inizializzazione");
	}
}
