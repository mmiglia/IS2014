public class Mem
{
	String[] strArr;

	public static void main(String[] argv)
	{
		Mem m = new Mem();

		m = m.met1(argv[0]);
	}

	public Mem met1(String s)
	{
		String s2 = s;
		return new Mem();
	}
}
