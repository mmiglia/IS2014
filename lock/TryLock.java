package lock;

import java.util.concurrent.atomic.*;

public class TryLock
{
	private AtomicInteger ai = new AtomicInteger(0);
	private Thread locker = null;
	
	public boolean tryLock()
	{
		boolean retval = ai.compareAndSet(0, 1);
		if(retval)
		{
			locker = Thread.currentThread();
		}
		return retval;
	}

	public boolean unlock()
	{
		if(Thread.currentThread() == locker)
		{
			//io ho il lock
			locker = null;
			//adesso compio l'azione atomica che mi priva del lock
			ai.decrementAndGet();
			return true;
		}
		return false;
	}
}
