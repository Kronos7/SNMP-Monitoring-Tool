import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import org.snmp4j.smi.OID;
import org.jfree.ui.RefineryUtilities;

public class MainController
{
	JTextField ip_addr;

	JFrame frame = new JFrame("Monitoring Tool");
	JTabbedPane tabbedpane = new JTabbedPane();
	JButton adding_button;
	JButton closing_button;
	JPanel message_panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JLabel label = new JLabel("Add network device");

	private void display()
	{
		tabbedpane.setVisible(true);

		label.setFont(label.getFont().deriveFont(48.0f));
		label.setVisible(true);

		message_panel.add(label);

		frame.add(tabbedpane, BorderLayout.NORTH);
		frame.add(message_panel, BorderLayout.NORTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));

		adding_button = new JButton(new AbstractAction("Add Network Device")
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					DeviceMonitoring DM = new DeviceMonitoring(ip_addr.getText());

					tabbedpane.add(DM.getDeviceName(), DM.getContent());

					frame.add(tabbedpane, BorderLayout.CENTER);
					message_panel.setVisible(false);
					frame.pack();
					RefineryUtilities.centerFrameOnScreen(frame);
				}

				catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		});

		closing_button = new JButton(new AbstractAction("Close Current Tab")
		{
			public void actionPerformed(ActionEvent e)
			{
				int remIndex = tabbedpane.getSelectedIndex();
				if (remIndex < 0)
				{
					JOptionPane.showMessageDialog(frame, "No tab available to close");
				} else
				{
					tabbedpane.remove(remIndex);
				}

				if (tabbedpane.getTabCount() < 1)
				{
					message_panel.setVisible(true);
					frame.pack();
					RefineryUtilities.centerFrameOnScreen(frame);
				}
			}

		});

		ip_addr = new JTextField(15);

		p.add(ip_addr);
		p.add(adding_button);
		p.add(new JLabel("  \u2666\u2666\u2666  "));
		p.add(closing_button);

		frame.add(p, BorderLayout.SOUTH);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		RefineryUtilities.centerFrameOnScreen(frame);
	}

	public static void main(String args[])
	{
		try
		{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e)
		{
		}

		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new MainController().display();
			}
		});
	}
}