package lock;

import java.util.concurrent.atomic.*;

public class TryLock2
{
	private Thread locker = null;
	
	public synchronized boolean tryLock()
	{
		boolean retval = false;
		synchronized(this)
		{
			if(locker == null)
			{
				locker = Thread.currentThread();
				retval = true;
			}
		}
		return retval;
	}

	public synchronized boolean unlock()
	{
		if(Thread.currentThread() == locker)
		{
			//io ho il lock
			locker = null;
			return true;
		}
		return false;
	}
}
