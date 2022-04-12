package main.elements;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.util.StringTokenizer;

import javax.swing.JCheckBox;

import main.EditInfo;

public class OutputElm extends CircuitElm {
	final int FLAG_VALUE = 1;

	public OutputElm(int xx, int yy) {
		super(xx, yy);
	}

	public OutputElm(int xa, int ya, int xb, int yb, int f,
			@SuppressWarnings("unused") StringTokenizer st) {
		super(xa, ya, xb, yb, f);
	}

	@Override
	int getDumpType() {
		return 'O';
	}

	@Override
	public boolean needsShortcut() {
		return getClass() == OutputElm.class;
	}

	@Override
	int getPostCount() {
		return 1;
	}

	@Override
	void setPoints() {
		super.setPoints();
		lead1 = new Point();
	}

	@Override
	void draw(Graphics g) {
		boolean selected = (needsHighlight() || sim.getPlotYElm() == this);
		Font f = new Font("SansSerif", selected ? Font.BOLD : 0, 14);
		g.setFont(f);
		g.setColor(selected ? selectColor : whiteColor);
		String s = (flags & FLAG_VALUE) != 0 ? getVoltageText(volts[0]) : "out";
		FontMetrics fm = g.getFontMetrics();
		if (this == sim.getPlotXElm())
			s = "X";
		if (this == sim.getPlotYElm())
			s = "Y";
		interpPoint(point1, point2, lead1,
				1 - (fm.stringWidth(s) / 2 + 8) / dn);
		setBbox(point1, lead1, 0);
		drawCenteredText(g, s, x2, y2, true);
		setVoltageColor(g, volts[0]);
		if (selected)
			g.setColor(selectColor);
		drawThickLine(g, point1, lead1);
		drawPosts(g);
	}

	@Override
	public double getVoltageDiff() {
		return volts[0];
	}

	@Override
	void getInfo(String arr[]) {
		arr[0] = "output";
		arr[1] = "V = " + getVoltageText(volts[0]);
	}

	@Override
	public EditInfo getEditInfo(int n) {
		if (n == 0) {
			EditInfo ei = new EditInfo("", 0, -1, -1);
			ei.setCheckbox(new JCheckBox("Show Voltage", (flags & FLAG_VALUE) != 0));
			return ei;
		}
		return null;
	}

	@Override
	public void setEditValue(int n, EditInfo ei) {
		if (n == 0)
			flags = (ei.isChecked()) ? (flags | FLAG_VALUE)
					: (flags & ~FLAG_VALUE);
	}
}
