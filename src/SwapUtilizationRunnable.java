import java.io.IOException;

import org.snmp4j.smi.OID;

public class SwapUtilizationRunnable implements Runnable
{
	SNMPCommIF CommIF;
	GraphicalDisplay GD;

	public String swapUtilization;

	public String memTotalSwap;
	public String memAvailSwap;

	OID oid_memTotalSwap = new OID(".1.3.6.1.4.1.2021.4.3.0");
	OID oid_memAvailSwap = new OID(".1.3.6.1.4.1.2021.4.4.0");

	public SwapUtilizationRunnable(SNMPCommIF CommIF)
	{
		this.CommIF = CommIF;

		try
		{
			memTotalSwap = this.CommIF.getAsString(oid_memTotalSwap);
		}

		catch (IOException e)
		{
			e.printStackTrace();
		}

		GD = new GraphicalDisplay("Swap Utilization");
	}

	public void run()
	{
		try
		{
			while (true)
			{
				memAvailSwap = CommIF.getAsString(oid_memAvailSwap);
				swapUtilization = String
						.valueOf(100 - (Integer.parseInt(memAvailSwap) * 100 / Integer
								.parseInt(memTotalSwap)));
				GD.addData(swapUtilization);
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
