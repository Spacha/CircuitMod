import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindowListener extends WindowAdapter {
	
	CirSim window;
	
	MainWindowListener(CirSim p) {
		window = p;
	}
	
	public void windowOpened(WindowEvent e) {
		System.out.println("opened");
	}

	public void windowClosing(WindowEvent e) {
		window.doExit();
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
	}
}
