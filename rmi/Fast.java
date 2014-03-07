package rmi;

import java.rmi.*;

class Fast
extends java.rmi.server.UnicastRemoteObject
implements FastResponse
{

	Fast() throws RemoteException
	{
		super();
	}

	java.util.Random r = new java.util.Random(System.currentTimeMillis());

	public String fast() throws java.rmi.RemoteException
	{
		try
		{
			Thread.sleep(r.nextInt(200));
		}
		catch(InterruptedException ie)
		{
		}
		return Thread.currentThread().getName();
	}
}
