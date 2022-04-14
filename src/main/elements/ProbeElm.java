package main.elements;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.StringTokenizer;

import javax.swing.JCheckBox;

import main.EditInfo;

public class ProbeElm extends CircuitElm {
	static final int FLAG_SHOWVOLTAGE = 1;

	public ProbeElm(int xx, int yy) {
		super(xx, yy);
	}

	public ProbeElm(int xa, int ya, int xb, int yb, int f,
			@SuppressWarnings("unused") StringTokenizer st) {
		super(xa, ya, xb, yb, f);
	}

	@Override
	public int getDumpType() {
		return 'p';
	}

	@Override
	public boolean needsShortcut() {
		return getClass() == ProbeElm.class;
	}

	Point center;

	@Override
	public void setPoints() {
		super.setPoints();
		// swap points so that we subtract higher from lower
		if (point2.y < point1.y) {
			Point x = point1;
			point1 = point2;
			point2 = x;
		}
		center = interpPoint(point1, point2, .5);
	}

	@Override
	public void draw(Graphics g) {
		int hs = 8;
		setBbox(point1, point2, hs);
		boolean selected = (needsHighlight() || sim.getPlotYElm() == this);
		double len = (selected || sim.dragElm == this) ? 16 : dn - 32;
		calcLeads((int) len);
		setVoltageColor(g, volts[0]);
		if (selected)
			g.setColor(selectColor);
		drawThickLine(g, point1, lead1);
		setVoltageColor(g, volts[1]);
		if (selected)
			g.setColor(selectColor);
		drawThickLine(g, lead2, point2);
		Font f = new Font("SansSerif", Font.BOLD, 14);
		g.setFont(f);
		if (this == sim.getPlotXElm())
			drawCenteredText(g, "X", center.x, center.y, true);
		if (this == sim.getPlotYElm())
			drawCenteredText(g, "Y", center.x, center.y, true);
		if (mustShowVoltage()) {
			String s = getShortUnitText(volts[0], "V");
			drawValues(g, s, 4, 0);
		}
		drawPosts(g);
	}

	boolean mustShowVoltage() {
		return (flags & FLAG_SHOWVOLTAGE) != 0;
	}

	@Override
	public void getInfo(String arr[]) {
		arr[0] = "scope probe";
		arr[1] = "Vd = " + getVoltageText(getVoltageDiff());
	}

	@Override
	public boolean getConnection(int n1, int n2) {
		return false;
	}

	@Override
	public EditInfo getEditInfo(int n) {
		if (n == 0) {
			EditInfo ei = new EditInfo("", 0, -1, -1);
			ei.setCheckbox(new JCheckBox("Show Voltage", mustShowVoltage()));
			return ei;
		}
		return null;
	}

	@Override
	public void setEditValue(int n, EditInfo ei) {
		if (n == 0) {
			if (ei.isChecked())
				flags = FLAG_SHOWVOLTAGE;
			else
				flags &= ~FLAG_SHOWVOLTAGE;
		}
	}
}
