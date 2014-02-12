package net;


import java.util.*;
import java.io.*;

public class UsaProxy
{
	public static void main(String[] argv) throws Exception
	{
		Proxy p = new Proxy();
		List<Serializable> params = new LinkedList<Serializable>();
		for(int i = 0;i<argv.length;i++)
		{
			params.add(argv[i]);
		}

		Executable e = new Esempio();

		for(int i = 0;i<argv.length;i++)
		{
			System.out.println(p.doIt(e, params));
		}
	}
}
