package esecuzioneRemota;

import java.rmi.*;

public interface RemExec
extends Remote
{
	Result execute(Task t) throws RemoteException;
	int submit(Task t) throws RemoteException;
	Result retrieve(int id) throws RemoteException, ResultUnavailableException;
	boolean isReady(int id) throws RemoteException, ResultUnavailableException;
}
