public class Figlia
extends Madre
{
	int a = 10;

	public static void main(String[] argv)
	{
		Madre m;
		Figlia f;

		m = f = new Figlia();
		System.out.println(m.a);
		System.out.println(f.a);
		System.out.println(f + " " + m);
		Class clazz = f.getClass();
		System.out.println(clazz);
	}

	/*
	public Figlia()
	{
		//c'e' sempre, anche se non e' esplicitato
		super();
		System.out.println("Costruttore default Figlia");
	}
	*/

	public String toString()
	{
		return super.toString() + " anche se sono la figlia!";
	}
}
