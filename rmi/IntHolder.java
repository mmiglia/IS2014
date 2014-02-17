package rmi;

public class IntHolder
implements java.io.Serializable
{
	int val;

	public String toString()
	{
		return super.toString() + " e valgo " + val;
	}
}
