package rmi;

import java.rmi.*;

public class Client1
{
	public static void main(String[] argv) throws Exception
	{
		Test1 t1 = (Test1)Naming.lookup("pippo");

		IntHolder ih = new IntHolder();
		ih.val = 12;

		t1.metodo1();
		System.out.println(t1.contaChiamate());
		t1.metodo1();
		System.out.println(t1.contaChiamate());
		t1.metodo1();
		System.out.println(t1.contaChiamate());
		t1.metodo1();
		System.out.println(t1.contaChiamate());
		t1.incrementa(ih);
		System.out.println(ih);
		ih.val = 2;
		System.out.println(t1.contaChiamate());
		t1.incrementa(ih);
		System.out.println(ih);
		System.out.println(t1.contaChiamate());

		t1.nonFunzionera();
	}
}
