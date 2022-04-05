import java.awt.Dimension;
//import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
//import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * Code by Miika Sikala, Federico García.
 * 05/04/2022.
 *
 * Frame that shows an error dialog.
 */

public class ErrorFrame extends JDialog implements WindowListener {
	private static final long serialVersionUID = 1L;
	private final String TITLE = "CircuitMod Error!";
	private final String ERROR_HELP = "CircuitMod encountered error and stopped.";

	public ErrorFrame(String message) {

		// Set frame properties.
		setTitle(TITLE);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);  //javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE
		addWindowListener(this);

		// Create main panel.
		JPanel panelMain = new JPanel();
		final int border = 16;
		panelMain.setBorder(BorderFactory.createEmptyBorder(border, border,
				border, border));
		panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));

		// We use HTML for new lines.
		JLabel labelErrorHelp = new JLabel("<html><h4>" + ERROR_HELP + "</h4></html>");
		panelMain.add(labelErrorHelp);
		
		JLabel labelError = new JLabel(message);
		panelMain.add(labelError);

		// Create panel with button
		JPanel panelButton = new JPanel();
		JButton button = new JButton("   Ok   ");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				close();
			}
		});

		panelButton.add(button);
		panelMain.add(panelButton);

		// Add main panel to frame.
		add(panelMain);

		// Show frame.
		pack();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(((int) d.getWidth() - getWidth()) / 2,
				((int) d.getHeight() - getHeight()) / 2);
		setVisible(true);
	}

	private void close() {
		dispose();
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		close();
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
	}
}
