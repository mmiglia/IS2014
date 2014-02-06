package net;

public class StringHolder
implements java.io.Serializable
{
	String s;

	public StringHolder(String theS)
	{
		s = theS;
	}

	public String toString()
	{
		return s;
	}
}
