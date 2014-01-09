public class UsaString
{
	public static void main(String[] argv)
	{
		final String costante = "pippo";

		System.out.println(argv[0].hashCode() + " " + argv[0]);
		argv[0] = argv[0].trim();
		System.out.println(argv[0].hashCode() + " " + argv[0]);

		String a = argv[0].trim();
		System.out.println("<" + a + "> <" + argv[0] + ">");

		System.out.println(argv[0] + " : " + costante);
		//if(argv[0] == costante)
		if(argv[0].equals(costante))
		{
			System.out.println(argv[0] + " e uguale a " + costante);
		}
		else
		{
			System.out.println(argv[0] + " e diverso da " + costante);
		}
	}
}
