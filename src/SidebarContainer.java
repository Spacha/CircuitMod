import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;

import java.awt.Component;

public class SidebarContainer extends Panel {
	private static final long serialVersionUID = 1L;
	private static final String SEPARATOR_TEXT = "......................................................................................................";

	CirSim pg;
	GridBagConstraints constraints;

	SidebarContainer(CirSim p) {
		pg = p;
		
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.weightx = 1;
		constraints.weighty = 0;
		
		setLayout(new SidebarLayout());
		//setBackground(Color.blue);
	}

	public void addSeparator() {
		Font f = new Font("SansSerif", Font.PLAIN, 8);
		Label s = new Label(SEPARATOR_TEXT, Label.CENTER);
		s.setForeground(Color.lightGray);
		s.setFont(f);
		add(s);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(200, 500);
	}

	@Override
	public Component add(Component c) {
		//c.setBackground(Color.red);
		super.add(c, constraints);
		return c;
	}

	public void addSpacer() {
		constraints.weighty = 1;
		Label label = new Label("");
		add(label, constraints);
		constraints.weighty = 0;
	}
}
