import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.Timer;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.ThermometerPlot;
import org.jfree.data.general.DefaultValueDataset;
import org.snmp4j.smi.OID;

public class CpuTempRunnable extends JPanel implements Runnable
{
	public ChartPanel chartPanel;

	private static final int W = 200;
	private static final int H = 2 * W;

	private Timer timer;

	DefaultValueDataset dataset;

	SNMPCommIF CommIF;

	public String lmTempSensors2;

	OID oid_lmTempSensors2 = new OID(".1.3.6.1.4.1.2021.13.16.2.1.3.2");

	public CpuTempRunnable(SNMPCommIF CommIF)
	{
		this.CommIF = CommIF;

		timer = new Timer(500, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				try
				{
					lmTempSensors2 = CommIF.getAsString(oid_lmTempSensors2);
				}

				catch (IOException e)
				{
					e.printStackTrace();
				}
				dataset.setValue(Double.parseDouble(lmTempSensors2) / 1000);
			}
		});

		dataset = new DefaultValueDataset(0);
		ThermometerPlot plot = new ThermometerPlot(dataset);
		plot.setSubrangePaint(0, Color.green.darker());
		plot.setSubrangePaint(1, Color.orange);
		plot.setSubrangePaint(2, Color.red.darker());
		JFreeChart chart = new JFreeChart("Cpu Temperature", JFreeChart.DEFAULT_TITLE_FONT, plot,
				true);
		chart.setBackgroundPaint(Color.LIGHT_GRAY);

		chartPanel = new ChartPanel(chart, W, H, W, H, W, H, false, true, true, true, true, true);
	}

	@Override
	public void run()
	{
		timer.start();
	}

	public ChartPanel getChartPanel()
	{
		return chartPanel;
	}
}
