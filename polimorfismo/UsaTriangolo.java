package polimorfismo;

public class UsaTriangolo
{
	public static void main(String[] argv)
	{
		Triangolo t = null;
		try
		{
			t = new Triangolo(1.0, 1.0, 1.0);
		}
		catch(InstantiationException ie)
		{
			ie.printStackTrace();
			System.exit(1);
		}
		Poligono p = t;
		Object o = t;

		System.out.println("L'area vale: " + t.area());
		System.out.println("Il perimetro di " + p.toString() + " vale: " + t.perimetro());
		System.out.println("Il perimetro di " + o.toString() + " vale: " + p.perimetro());
	}
}
