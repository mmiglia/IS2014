import java.io.*;

public class ApriFile
{
	public static void main(String[] argv) throws IOException, MiaException
	{
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = null;
		while(true)
		{
			System.out.print("Nome file ? > ");
			String line = null;
			try
			{
				line = stdin.readLine();
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
				throw ioe;
			}
	
			try
			{
				br = new BufferedReader(new FileReader(line));
				break;
			}
			catch(FileNotFoundException fnfe)
			{
				fnfe.printStackTrace();
				continue;
			}
		}

		try
		{
			String line2 = null;
			while( (line2 = br.readLine()) != null)
			{
				if(line2.startsWith("pub"))
				{
					throw new IOException("Sto barando");
				}
				System.out.println(line2);
			}

		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			MiaException me = new MiaException("Perche' si'");
			me.initCause(ioe);
			throw me;
		}
		finally
		{
			try
			{
				br.close();
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
				System.exit(1);
			}
		}
	}
}
