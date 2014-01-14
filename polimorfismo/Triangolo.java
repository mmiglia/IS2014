package polimorfismo;

public class Triangolo
extends Poligono
{
	double[] lati;

	public Triangolo(double l1, double l2, double l3) throws InstantiationException
	{
		if(l1 > (l2+l3) || l2 > (l1+l3) || l3 > (l1+l2))
		{
			throw new InstantiationException("Un triangolo con un lato maggiore della somma degli altri due non esiste");
		}

		lati = new double[3];
		lati[0] = l1;
		lati[1] = l2;
		lati[2] = l3;
	}

	public double perimetro()
	{
		return lati[0]+lati[1]+lati[2];
	}

	public double area()
	{
		double sp = (lati[0]+lati[1]+lati[2])/2;
		return Math.sqrt(sp*(sp-lati[0])*(sp-lati[1])*(sp-lati[2]));
	}

	public String toString()
	{
		return super.toString() + ", nel dettaglio sono un triangolo";
	}
}
