import java.io.IOException;

import org.snmp4j.smi.OID;

public class DiskUtilizationRunnable implements Runnable
{
	SNMPCommIF CommIF;
	GraphicalDisplay GD;

	public String dskPercent;

	OID oid_dskPercent = new OID(".1.3.6.1.4.1.2021.9.1.9.1");

	public DiskUtilizationRunnable(SNMPCommIF CommIF)
	{
		this.CommIF = CommIF;

		GD = new GraphicalDisplay("Disk Utilization");
	}

	public void run()
	{
		try
		{
			while (true)
			{
				dskPercent = CommIF.getAsString(oid_dskPercent);
				GD.addData(dskPercent);
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
