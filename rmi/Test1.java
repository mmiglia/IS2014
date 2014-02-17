package rmi;

public interface Test1
extends java.rmi.Remote
{
	void metodo1() throws java.rmi.RemoteException;
	int contaChiamate() throws java.rmi.RemoteException;
	void incrementa(IntHolder ih) throws java.rmi.RemoteException;
}
