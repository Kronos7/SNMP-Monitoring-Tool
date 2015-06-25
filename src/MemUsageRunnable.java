import java.io.IOException;

import org.snmp4j.smi.OID;

public class MemUsageRunnable implements Runnable
{
	SNMPCommIF CommIF;
	GraphicalDisplay GD;

	public String memUsage;

	public String memTotalReal;
	public String memCached;
	public String memShared;

	OID oid_memCached = new OID(".1.3.6.1.4.1.2021.4.15.0");
	OID oid_memShared = new OID(".1.3.6.1.4.1.2021.4.13.0");

	public MemUsageRunnable(SNMPCommIF CommIF)
	{
		this.CommIF = CommIF;

		GD = new GraphicalDisplay("Memory Usage");
	}

	public void run()
	{
		try
		{
			while (true)
			{
				memCached = CommIF.getAsString(oid_memCached);
				memShared = CommIF.getAsString(oid_memShared);
				memUsage = String.valueOf((Integer.parseInt(memCached) + Integer
						.parseInt(memShared)) / 1024);

				GD.addData(memUsage);
				Thread.sleep(250);
			}
		}

		catch (IOException | InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public GraphicalDisplay getGraphicalDisplay()
	{
		return GD;
	}
}
