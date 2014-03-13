package macchine;

import java.util.*;

public class Main
{
	public static void main(String[] argv)
	{
		Random rand = new Random(System.currentTimeMillis());
		List<Prodotto>[] code = (List<Prodotto>[])(new List[Prodotto.NUM_TIPI]);
		List<Prodotto> stadioIniziale = new LinkedList<Prodotto>();
		for(int i=0;i<code.length;i++)
		{
			code[i] = new LinkedList<Prodotto>();
			int tmp = rand.nextInt(5);
			for(int j=0;j<(tmp+1);j++)
			{
				new Macchina(code[i], stadioIniziale);
			}
		}
		new Smistatore(stadioIniziale, code);

		int k = 20;
		while(k-- > 0)
		{
			Prodotto p = new Prodotto();
			synchronized(stadioIniziale)
			{
				stadioIniziale.add(p);
				stadioIniziale.notify();
			}
		}
	}
}
