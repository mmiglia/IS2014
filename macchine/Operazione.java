package macchine;

public class Operazione
{
	int tipo;
	int durata;

	public Operazione(int t, int d)
	{
		tipo = t;
		durata = d;
	}

	public int getTipo()
	{
		return tipo;
	}

	public int getDurata()
	{
		return durata;
	}

	public String toString()
	{
		return "tipo: " + tipo + "; durata: " + durata;
	}
}
