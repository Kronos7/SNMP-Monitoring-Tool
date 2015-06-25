import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.Timer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

public class GraphicalDisplay implements ActionListener
{
	public ChartPanel chartPanel;

	private TimeSeries series;
	private double lastValue;
	private String title;
	private Timer timer = new Timer(250, this);

	public GraphicalDisplay(final String title)
	{
		this.title = title;
		this.series = new TimeSeries("Dataset");

		final TimeSeriesCollection dataset = new TimeSeriesCollection(this.series);
		final JFreeChart chart = createChart(dataset);
		chart.setBackgroundPaint(new Color(214, 217, 223));

		timer.setInitialDelay(1000);

		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(400, 400));
		chartPanel.setBorder(BorderFactory.createLineBorder(new Color(146, 151, 161), 1, false));

		timer.start();
	}

	private JFreeChart createChart(final XYDataset dataset)
	{
		final JFreeChart result = ChartFactory.createTimeSeriesChart(title, "Time", "Value",
				dataset, false, true, false);

		final XYPlot plot = result.getXYPlot();

		plot.setBackgroundPaint(new Color(0xffffe0));
		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.lightGray);
		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.lightGray);

		ValueAxis xaxis = plot.getDomainAxis();
		xaxis.setAutoRange(true);

		xaxis.setFixedAutoRange(60000.0);
		// xaxis.setVerticalTickLabels(true);
		xaxis.setVisible(false);

		// ValueAxis yaxis = plot.getRangeAxis();
		// yaxis.setLabel(null);
		// yaxis.setAutoRange(true);

		NumberAxis yaxis = (NumberAxis) plot.getRangeAxis();
		yaxis.setLabel(null);
		yaxis.setLabelFont(new Font("Dialog", Font.PLAIN, 18));
		yaxis.setAutoRange(true);
		yaxis.setNumberFormatOverride(new DecimalFormat("#,###"));

		Font font = new Font("Dialog", Font.PLAIN, 18);
		yaxis.setTickLabelFont(font);

		return result;
	}

	public ChartPanel getChartPanel()
	{
		return chartPanel;
	}

	public void addData(String newData)
	{
		this.lastValue = Double.parseDouble(newData);
	}

	public void actionPerformed(final ActionEvent e)
	{
		this.series.addOrUpdate(new Millisecond(), this.lastValue);
	}

}