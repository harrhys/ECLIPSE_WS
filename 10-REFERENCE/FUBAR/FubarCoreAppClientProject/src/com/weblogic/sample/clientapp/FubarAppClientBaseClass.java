package com.weblogic.sample.clientapp;

//Usage: FubarAppClientBaseClass [protocol://host:port-or-filler] [iterations] [client sleep time] [client id] [async flag] [publish flag] [server sleep time]

public abstract class FubarAppClientBaseClass
{
	public final static int default_iterations = 1;
	public final static int default_serversleeptime = 100;
	public final static int default_clientsleeptime = 100;
	public final static String default_hostandport = "localhost:80";
	public final static int default_clientid = 2;
	public final static boolean default_asyncflag = false;
	public final static boolean default_publishflag = false;

	public void go(String[] args)
	{
		int max = 0;
		int sstime = 0;
		int cstime = 0;
		int cid = 0;
		boolean aflag = false;
		boolean pflag = false;
		long st = 0;
		long et = 0;
		String phporfiller = null;

		max = default_iterations;
		sstime = default_serversleeptime;
		cstime = default_clientsleeptime;
		cid = default_clientid;
		aflag = default_asyncflag;
		pflag = default_publishflag;
		
		if (args.length > 0)
		{
			phporfiller = args[0];
			if (phporfiller == null)
			{
				System.out.println("ERROR: Invalid php-or-filler: ["+args[0]+"]");
			}
		}
		
		if (args.length > 1)
		{
			try
			{
				Integer i = new Integer(args[1]);
				max = i.intValue();
			}
			catch (Throwable e)
			{
				System.out.println("ERROR: Invalid iteration count: ["+args[1]+"]");
			}
		}

		if (args.length > 2)
		{
			try
			{
				Integer i = new Integer(args[2]);
				cstime = i.intValue();
			}
			catch (Throwable e)
			{
				System.out.println("ERROR: Invalid client sleep time: ["+args[2]+"]");
			}
		}
		
		if (args.length > 3)
		{
			try
			{
				Integer i = new Integer(args[3]);
				cid = i.intValue();
			}
			catch (Throwable e)
			{
				System.out.println("ERROR: Invalid client id: ["+args[3]+"]");
			}
		}

		if (args.length > 4)
		{
			if (args[4].compareToIgnoreCase("true") == 0)
			{
				aflag = true;
			}
		}

		if (args.length > 5)
		{
			if (args[5].compareToIgnoreCase("true") == 0)
			{
				pflag = true;
			}
		}

		if (args.length > 6)
		{
			try
			{
				Integer i = new Integer(args[6]);
				sstime = i.intValue();
			}
			catch (Throwable e)
			{
				System.out.println("ERROR: Invalid server sleep time: ["+args[6]+"]");
			}
		}
		
		System.out.println("phporfiller: ["+phporfiller+"]");
		System.out.println("iterations: ["+max+"]");
		System.out.println("clientSleepTime: ["+cstime+"]");
		System.out.println("clientId: ["+cid+"]");
		System.out.println("asyncFlag: ["+aflag+"]");
		System.out.println("publishFlag: ["+pflag+"]");
		System.out.println("serverSleepTime: ["+sstime+"]");
		
		setup(phporfiller, cid, aflag, pflag, sstime);
	    
		for (int i=0; i < max; i++)
		{
			System.out.println("Doing iteration: ["+cid+aflag+pflag+sstime+i+"]");
			st = System.currentTimeMillis();
			doaniteration(phporfiller, cid, aflag, pflag, sstime, i);
			et = System.currentTimeMillis();
			System.out.println(
				"Processing time was ["+(et-st)+
				"] for iteration: ["+cid+aflag+pflag+sstime+i+"]");
			try
			{
				System.out.println("Sleeping for ["+cstime+"] millisecond(s)...");
				Thread.sleep(cstime);
			}
			catch (Throwable e)
			{
				System.out.println("Sleep interrupted");
			}
		}
	}
	
	abstract public void setup(
		String phporfiller, int clientId, boolean asyncFlag,
		boolean publishFlag, int serverSleepTime);
	
	abstract public void doaniteration(
			String phporfiller, int clientId, boolean asyncFlag,
			boolean publishFlag, int serverSleepTime,
			int requestNumber);
}
