public class UsaArray
{
	public static void main(String[] argv)
	{
		int[] a;
		String[] b;

		a = new int[10];
		int i = 0;
		for(i=0;i<a.length;i++)
		{
			System.out.println(a[i]);
		}

		b = new String[3];
		for(i=0;i<b.length;i++)
		{
			System.out.println(b[i]);
		}

		for(i=0;i<argv.length;i++)
		{
			b[i] = argv[i];
		}

		for(i=0;i<b.length;i++)
		{
			System.out.println(b[i]);
		}

		int[][] pippo = new int[3][];
		for(i=0;i<pippo.length;i++)
		{
			pippo[i] = new int[i+1];
		}

		int j = 0;
		for(i=0;i<pippo.length;i++)
		{
			for(j=0;j<pippo[i].length;j++)
			{
				pippo[i][j] = i+j;
			}
		}

		for(i=0;i<pippo.length;i++)
		{
			for(j=0;j<pippo[i].length;j++)
			{
				System.out.print(pippo[i][j] + " ");
			}
			System.out.println();
		}
	}
}
