package polimorfismo;
import java.util.*;

public class StringHolder
{
	String value;
	//static String value; non si puo' usare due volte lo stesso simbolo

	public StringHolder(String s)
	{
		value = s;
	}

	public String toString()
	{
		return value;
	}

	public static void main(String[] argv)
	{
		List<StringHolder> l = new LinkedList<StringHolder>();

		for(int i = 0;i<argv.length;i++)
		{
			l.add(new StringHolder(argv[i]));
		}

		StringHolder sh = null;
		ListIterator<StringHolder> li = l.listIterator();
		while(li.hasNext())
		{
			StringHolder tmp = null;
			tmp = li.next();
			if(sh != null)
			{
				if(sh.equals(tmp))
				{
					System.out.println(sh + " e uguale a " + tmp);
				}
				else
				{
					System.out.println(sh + " non e uguale a " + tmp);
				}
			}
			sh = tmp;
		}
	}
}
