package linked;

public class UsaListaG
{
	public static void main(String[] argv)
	{
		ListaG<Object> l = new ListaG<Object>();
		for(int i = 0;i<argv.length;i++)
		{
			l.addFirst(argv[i]);
			l.addLast(new Integer(Integer.parseInt(argv[i])));
		}

		System.out.println(l);
		while(!l.isEmpty())
		{
			Object tmp = l.removeFirst();
			System.out.println(tmp.getClass().getName() + " " + tmp);
		}
	}
}
