import java.io.IOException;

import org.snmp4j.smi.OID;

public class CpuUtilizationRunnable implements Runnable
{
	SNMPCommIF CommIF;
	GraphicalDisplay GD;

	public String ssCpuUser;

	OID oid_ssCpuUser = new OID(".1.3.6.1.4.1.2021.11.9.0");

	public CpuUtilizationRunnable(SNMPCommIF CommIF)
	{
		this.CommIF = CommIF;

		GD = new GraphicalDisplay("CPU Utilization");
		// GD.pack();
		// RefineryUtilities.centerFrameOnScreen(GD);
		// GD.setVisible(true);
	}

	public void run()
	{
		try
		{
			while (true)
			{
				ssCpuUser = CommIF.getAsString(oid_ssCpuUser);
				GD.addData(ssCpuUser);
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
