package main;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.Dimension;
//import java.awt.Event;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

class ImportDialog extends Dialog implements ActionListener, WindowListener {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	CirSim cframe;
	Button importButton, closeButton;
	TextArea text;
	boolean isURL;

	ImportDialog(CirSim f, String str, boolean url) {
		super(f, (str.length() > 0) ? "Export" : "Import", false);
		isURL = url;
		cframe = f;
		setLayout(new ImportDialogLayout());
		add(text = new TextArea(str, 10, 60, TextArea.SCROLLBARS_BOTH));
		importButton = new Button("Import");
		if (!isURL)
			add(importButton);
		importButton.addActionListener(this);
		add(closeButton = new Button("Close"));
		closeButton.addActionListener(this);
		Point x = CirSim.main.getLocationOnScreen();
		setSize(400, 300);
		Dimension d = getSize();
		setLocation(x.x + (cframe.winSize.width - d.width) / 2, x.y + (cframe.winSize.height - d.height) / 2);
		setVisible(true);
		if (str.length() > 0)
			text.selectAll();
		addWindowListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if (src == importButton) {
			cframe.readSetup(text.getText());
			setVisible(false);
		}
		if (src == closeButton)
			setVisible(false);
	}

	// @Override
	// public boolean handleEvent(Event ev) {
	// if (ev.id == Event.WINDOW_DESTROY) {
	// CirSim.main.requestFocus();
	// setVisible(false);
	// CirSim.impDialog = null;
	// return true;
	// }
	// return super.handleEvent(ev);
	// }
	public void windowClosed(WindowEvent event) {
	}

	public void windowDeiconified(WindowEvent event) {
	}

	public void windowIconified(WindowEvent event) {
	}

	public void windowActivated(WindowEvent event) {
	}

	public void windowDeactivated(WindowEvent event) {
	}

	public void windowOpened(WindowEvent event) {
	}

	public void windowClosing(WindowEvent event) {
		CirSim.main.requestFocus();
		setVisible(false);
		CirSim.impDialog = null;
	}
}
