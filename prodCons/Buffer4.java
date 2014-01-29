package prodCons;

import java.util.*;

public class Buffer4<E>
{
	protected E[] storage;
	protected int currSize;
	protected int head, tail;
	Lock l;
	Condition empty, full;

	public Buffer4(int max)
	{
		storage = (E[])(new Object[max]);
		head = tail = currSize = 0;
		l = new ReentrantLock();
		empty = l.newCondition();
		full = l.newCondition();
	}

	public boolean isFull()
	{
		l.lock();
		try
		{
			return currSize == storage.length;
		}
		finally
		{
			l.unlock();
		}
	}

	public boolean isEmpty()
	{
		l.lock();
		try
		{
			return currSize == 0;
		}
		finally
		{
			l.unlock();
		}
	}

	public int size()
	{
		l.lock();
		try
		{
			return currSize;
		}
		finally
		{
			l.unlock();
		}
	}

	public void put(E elem)
	{
		l.lock();
		try
		{
			while(isFull())
			{
				full.awaitUninterruptibly();
			}
			if(isEmpty())
			{
				empty.signal();
			}
			storage[tail] = elem;
			currSize++;
			tail = (tail + 1)%storage.length;
		}
		finally
		{
			l.unlock();
		}
	}

	public E remove()
	{
		E retval = null;
		l.lock();
		try
		{
			while(isEmpty())
			{
				empty.awaitUninterruptibly();
			}
			retval = storage[head];
			currSize--;
			head = (head + 1)%storage.length;

			if(size() == storage.length-1)
			{
				full.signal();
			}
		}
		finally
		{
			l.unlock();
		}
		return retval;
	}

	public String toString()
	{
		String retval = "[";
		int i = 0;
		for(i=0;i<size()-1;i++)
		{
			retval = retval.concat(storage[(i+head)%storage.length].toString() + ", ");
		}
		retval = retval.concat(storage[(i+head)%storage.length].toString());
		return retval + "]";
	}
}
