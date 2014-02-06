package reflections;

import java.io.*;
import java.util.*;
import java.lang.reflect.*;

public class Carica
{
	public static void main(String[] argv) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		List<Serializable> l = null;
		File f = new File("obj.bin");
		if(f.canRead())
		{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
			try
			{
				l = (List<Serializable>)ois.readObject();
				ois.close();
			}
			catch(ClassNotFoundException cnfe)
			{
				cnfe.printStackTrace();
			}
		}

		if(l == null)
		{
			l = new LinkedList<Serializable>();
		}

		System.out.println(l);


		String line = null;
		while( (line = br.readLine())!=null)
		{
			System.out.println("Voglio la classe <" + line + ">");
			try
			{
				Class clazz = Class.forName(line);
				Object o = clazz.newInstance();
				System.out.println(o.toString());
				Constructor c = null;
				try
				{
					c = clazz.getConstructor("pippo".getClass());
					Object o1 = c.newInstance("paperino");
					l.add((Serializable)o1);
				}
				catch(InvocationTargetException ite)
				{
					ite.printStackTrace();
				}
				catch(NoSuchMethodException nsme)
				{
					nsme.printStackTrace();
				}
				l.add((Serializable)o);

				System.out.println(l);
			}
			catch(IllegalAccessException iae)
			{
				iae.printStackTrace();
			}
			catch(InstantiationException ie)
			{
				ie.printStackTrace();
			}
			catch(ClassNotFoundException cnfe)
			{
				cnfe.printStackTrace();
			}
		}

		System.out.println(l);
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("obj.bin"));
		oos.writeObject(l);
		oos.close();
	}
}
