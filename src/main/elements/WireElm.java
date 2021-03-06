package main.elements;
import java.awt.Graphics;
import java.util.StringTokenizer;

import javax.swing.JCheckBox;

import main.EditInfo;

public class WireElm extends CircuitElm {
	public WireElm(int xx, int yy) {
		super(xx, yy);
	}

	public WireElm(int xa, int ya, int xb, int yb, int f,
			@SuppressWarnings("unused") StringTokenizer st) {
		super(xa, ya, xb, yb, f);
	}

	static final int FLAG_SHOWCURRENT = 1;
	static final int FLAG_SHOWVOLTAGE = 2;

	@Override
	public void draw(Graphics g) {
		setVoltageColor(g, volts[0]);
		drawThickLine(g, point1, point2);
		doDots(g);
		setBbox(point1, point2, 3);

		if (mustShowCurrent())
			drawCurrentValue(g, getShortUnitText(Math.abs(getCurrent()), "A"));
		if (mustShowVoltage())
			drawVoltageValue(g, getShortUnitText(volts[0], "V"));

		drawPosts(g);
	}

	void drawVoltageValue(Graphics g, String s) {
		drawValues(g, s, 4, mustShowBoth() ? 1 : 0);
	}

	void drawCurrentValue(Graphics g, String s) {
		drawValues(g, s, 4, mustShowBoth() ? -1 : 0);
	}

	@Override
	public void stamp() {
		sim.stampVoltageSource(nodes[0], nodes[1], voltSource, 0);
	}

	boolean mustShowBoth() {
		int both = (FLAG_SHOWVOLTAGE | FLAG_SHOWCURRENT);
		return (flags & both) == both;
	}

	boolean mustShowCurrent() {
		return (flags & FLAG_SHOWCURRENT) != 0;
	}

	boolean mustShowVoltage() {
		return (flags & FLAG_SHOWVOLTAGE) != 0;
	}

	@Override
	public int getVoltageSourceCount() {
		return 1;
	}

	@Override
	public void getInfo(String arr[]) {
		arr[0] = "wire";
		arr[1] = "I = " + getCurrentDText(getCurrent());
		arr[2] = "V = " + getVoltageText(volts[0]);
	}

	@Override
	public int getDumpType() {
		return 'w';
	}

	@Override
	double getPower() {
		return 0;
	}

	@Override
	public double getVoltageDiff() {
		return volts[0];
	}

	@Override
	public boolean isWire() {
		return true;
	}

	@Override
	public EditInfo getEditInfo(int n) {
		if (n == 0) {
			EditInfo ei = new EditInfo("", 0, -1, -1);
			ei.setCheckbox(new JCheckBox("Show Current", mustShowCurrent()));
			return ei;
		}
		if (n == 1) {
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
				flags |= FLAG_SHOWCURRENT;
			else
				flags &= ~FLAG_SHOWCURRENT;
		}
		if (n == 1) {
			if (ei.isChecked())
				flags |= FLAG_SHOWVOLTAGE;
			else
				flags &= ~FLAG_SHOWVOLTAGE;
		}
	}

	@Override
	public boolean needsShortcut() {
		return true;
	}
}
