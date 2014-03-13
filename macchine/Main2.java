package macchine;

import java.util.*;

public class Main2
{
	public static void main(String[] argv)
	{
		Random rand = new Random(System.currentTimeMillis());
		List<Prodotto>[] code = (List<Prodotto>[])(new List[Prodotto.NUM_TIPI]);
		List<Prodotto> stadioIniziale = new LinkedList<Prodotto>();
		for(int i=0;i<code.length;i++)
		{
			code[i] = new LinkedList<Prodotto>();
		}
		for(int i=0;i<code.length;i++)
		{
			int tmp = rand.nextInt(5);
			for(int j=0;j<(tmp+1);j++)
			{
				new Macchina2(code[i], code);
			}
		}
		//qui non c'e'
		//new Smistatore(stadioIniziale, code);

		int k = 20;
		while(k-- > 0)
		{
			Prodotto p = new Prodotto();
			System.out.println(p);
			List<Prodotto> l = (code[p.getNext().getTipo()]);
			synchronized(l)
			{
				l.add(p);
				l.notify();
			}
		}
	}
}
