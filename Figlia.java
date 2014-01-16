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
	}

	public String toString()
	{
		return super.toString() + " anche se sono la figlia!";
	}
}
