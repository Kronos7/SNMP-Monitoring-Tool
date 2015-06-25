import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JPanel;

import org.snmp4j.smi.OID;

public class DeviceMonitoring
{
	// SNMPCommIF CIF = new SNMPCommIF("udp:127.0.0.1/161");
	private SNMPCommIF CIF;
	private String ip_addr;
	public JPanel content;

	public DeviceMonitoring(String ip_addr) throws IOException
	{
		this.ip_addr = ip_addr;
		this.CIF = new SNMPCommIF("udp:" + this.ip_addr + "/161");

		CpuUtilizationRunnable CpuUtilization = new CpuUtilizationRunnable(CIF);
		MemUsageRunnable MemUsage = new MemUsageRunnable(CIF);
		MemUtilizationRunnable MemUtilization = new MemUtilizationRunnable(CIF);
		DiskUtilizationRunnable DiskUtilization = new DiskUtilizationRunnable(CIF);
		DiskUsageRunnable DiskUsage = new DiskUsageRunnable(CIF);
		SwapUtilizationRunnable SwapUtilization = new SwapUtilizationRunnable(CIF);

		this.content = new JPanel(new GridLayout(2, 3));

		content.add(CpuUtilization.getGraphicalDisplay().getChartPanel());
		content.add(MemUtilization.getGraphicalDisplay().getChartPanel());
		content.add(MemUsage.getGraphicalDisplay().getChartPanel());
		content.add(DiskUtilization.getGraphicalDisplay().getChartPanel());
		content.add(DiskUsage.getGraphicalDisplay().getChartPanel());
		content.add(SwapUtilization.getGraphicalDisplay().getChartPanel());

		Thread T_CpuUtilization = new Thread(CpuUtilization);
		Thread T_MemUsage = new Thread(MemUsage);
		Thread T_MemUtilization = new Thread(MemUtilization);
		Thread T_DiskUtilization = new Thread(DiskUtilization);
		Thread T_DiskUsage = new Thread(DiskUsage);
		Thread T_SwapUtilization = new Thread(SwapUtilization);

		T_CpuUtilization.start();
		T_MemUsage.start();
		T_MemUtilization.start();
		T_DiskUtilization.start();
		T_DiskUsage.start();
		T_SwapUtilization.start();
	}

	public JPanel getContent()
	{
		return content;
	}

	public String getDeviceName() throws IOException
	{
		return CIF.getAsString(new OID(".1.3.6.1.2.1.1.5.0"));
	}
}
