import java.io.IOException;

import org.snmp4j.smi.OID;

public class DiskUsageRunnable implements Runnable
{
	SNMPCommIF CommIF;
	GraphicalDisplay GD;

	public String dskUsed;

	OID oid_dskUsed = new OID(".1.3.6.1.4.1.2021.9.1.8.1");

	public DiskUsageRunnable(SNMPCommIF CommIF)
	{
		this.CommIF = CommIF;

		GD = new GraphicalDisplay("Disk Usage");

	}

	public void run()
	{
		try
		{
			while (true)
			{
				dskUsed = CommIF.getAsString(oid_dskUsed);
				GD.addData(String.valueOf(Integer.parseInt(dskUsed) / 1024));
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
