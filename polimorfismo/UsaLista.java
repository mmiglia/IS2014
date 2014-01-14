package polimorfismo;

public class UsaLista
{
	public static void main(String[] argv)
	{
		java.util.List l = new java.util.LinkedList();

		for(int i = 0;i<argv.length;i++)
		{
			l.add(argv[i]);
		}

		System.out.println(l);
		l.clear();

		for(int i = 0;i<argv.length;i++)
		{
			l.add(Integer.parseInt(argv[i]));
		}

		Integer k = new Integer(0);
		Integer m = new Integer(0);

		if(k == m)
			System.out.println("Sono uguali");
		else
			System.out.println("Sono diversi");


		if(k.equals(m))
			System.out.println("Sono uguali");
		else
			System.out.println("Sono diversi");

		System.out.println(l);
		java.util.ListIterator li = l.listIterator();
		Integer j = 0;
		System.out.println(j.hashCode());
		while(li.hasNext())
		{
			j += (Integer)li.next();
			System.out.println(j.hashCode());
		}
		System.out.println(j);
	}
}
