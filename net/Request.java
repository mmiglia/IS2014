package net;

import java.io.*;
import java.util.*;

class Request
implements Serializable
{
	Executable ex;
	List<Serializable> p;
	String id;

	ObjectOutputStream oos;
	Serializable result;

	Request(Executable e, List<Serializable> theP, String theId)
	{
		ex = e;
		p = theP;
		id = theId;
	}
}
