import java.io.IOException;

import org.snmp4j.smi.OID;

public class MemUtilizationRunnable implements Runnable
{
	SNMPCommIF CommIF;
	GraphicalDisplay GD;

	public String memUtilization;

	public String memTotalReal;
	public String memCached;
	public String memShared;

	OID oid_memTotalReal = new OID(".1.3.6.1.4.1.2021.4.5.0");
	OID oid_memCached = new OID(".1.3.6.1.4.1.2021.4.15.0");
	OID oid_memShared = new OID(".1.3.6.1.4.1.2021.4.13.0");

	public MemUtilizationRunnable(SNMPCommIF CommIF)
	{
		this.CommIF = CommIF;

		try
		{
			memTotalReal = this.CommIF.getAsString(oid_memTotalReal);
		}

		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		GD = new GraphicalDisplay("Memory Utilization");
	}

	public void run()
	{
		try
		{
			while (true)
			{
				memCached = CommIF.getAsString(oid_memCached);
				memShared = CommIF.getAsString(oid_memShared);
				memUtilization = String.valueOf((Integer.parseInt(memCached) + Integer
						.parseInt(memShared)) * 100 / Integer.parseInt(memTotalReal));

				GD.addData(memUtilization);
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
