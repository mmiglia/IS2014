package rmi;

import java.rmi.*;
import java.rmi.server.*;

public class Test1Impl
extends UnicastRemoteObject
implements Test1
{
	int count = 0;

	public Test1Impl() throws RemoteException
	{
		super();
	}

	public void metodo1() throws java.rmi.RemoteException
	{
		System.out.println(Thread.currentThread().getName());
		int tmp = count;
		try
		{
			Thread.sleep(10000);
		}
		catch(InterruptedException ie)
		{
			ie.printStackTrace();
		}
		System.out.println("Chiamato metodo1. " + tmp + "esima chiamata");
		tmp++;
		count = tmp;
	}

	public int contaChiamate() throws java.rmi.RemoteException
	{
		int tmp = count;
		System.out.println("Chiamato contaChiamate. " + tmp + "esima chiamata");
		tmp++;
		count = tmp;
		return count;
	}

	public void incrementa(IntHolder ih) throws java.rmi.RemoteException
	{
		int tmp = count;
		System.out.println("Chiamato incerementa. " + tmp + "esima chiamata");
		System.out.println(ih);
		tmp+= ih.val;
		ih.val = tmp;
		count = tmp;
	}

	public int nonFunzionera() throws RemoteException
	{
		throw new RuntimeException();
	}

	public static void main(String[] argv)
	{
		String name = "pippo";
		if(argv.length > 0)
		{
			name = argv[0];
		}

		try
		{
			Test1Impl t1 = new Test1Impl();

			java.rmi.Naming.rebind(name, t1);
			java.rmi.Naming.rebind("fast", new Fast());
		}
		catch(RemoteException | java.net.MalformedURLException re)
		{
			re.printStackTrace();
		}

		System.out.println("Il main termina");
	}
}
