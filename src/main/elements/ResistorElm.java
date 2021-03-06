package main.elements;
import java.awt.Graphics;
import java.awt.Point;
import java.util.StringTokenizer;

import main.CirSim;
import main.EditInfo;

public class ResistorElm extends CircuitElm {
	double resistance;
	
	public double getResistance() { return resistance; }

	public ResistorElm(int xx, int yy) {
		super(xx, yy);
		resistance = 100;
	}

	public ResistorElm(int xa, int ya, int xb, int yb, int f, StringTokenizer st) {
		super(xa, ya, xb, yb, f);
		resistance = Double.valueOf(st.nextToken()).doubleValue();
	}

	@Override
	public int getDumpType() {
		return 'r';
	}

	@Override
	public String dump() {
		return super.dump() + " " + resistance;
	}

	Point ps3, ps4;

	@Override
	public void setPoints() {
		super.setPoints();
		calcLeads(32);
		ps3 = new Point();
		ps4 = new Point();
	}

	@Override
	public void draw(Graphics g) {
		int segments = 16;
		int i;
		int ox = 0;
		int hs = sim.isUsingEuroResistors() ? 6 : 8;
		double v1 = volts[0];
		double v2 = volts[1];
		setBbox(point1, point2, hs);
		draw2Leads(g);
		setPowerColor(g, true);
		double segf = 1. / segments;
		if (!sim.isUsingEuroResistors()) {
			// draw zigzag
			for (i = 0; i != segments; i++) {
				int nx = 0;
				switch (i & 3) {
				case 0:
					nx = 1;
					break;
				case 2:
					nx = -1;
					break;
				default:
					nx = 0;
					break;
				}
				double v = v1 + (v2 - v1) * i / segments;
				setVoltageColor(g, v);
				interpPoint(lead1, lead2, ps1, i * segf, hs * ox);
				interpPoint(lead1, lead2, ps2, (i + 1) * segf, hs * nx);
				drawThickLine(g, ps1, ps2);
				ox = nx;
			}
		} else {
			// draw rectangle
			setVoltageColor(g, v1);
			interpPoint2(lead1, lead2, ps1, ps2, 0, hs);
			drawThickLine(g, ps1, ps2);
			for (i = 0; i != segments; i++) {
				double v = v1 + (v2 - v1) * i / segments;
				setVoltageColor(g, v);
				interpPoint2(lead1, lead2, ps1, ps2, i * segf, hs);
				interpPoint2(lead1, lead2, ps3, ps4, (i + 1) * segf, hs);
				drawThickLine(g, ps1, ps3);
				drawThickLine(g, ps2, ps4);
			}
			interpPoint2(lead1, lead2, ps1, ps2, 1, hs);
			drawThickLine(g, ps1, ps2);
		}
		if (sim.isShowingValues()) {
			String s = getShortUnitText(resistance, "");
			drawValues(g, s, hs, 0);
		}
		doDots(g);
		drawPosts(g);
	}

	@Override
	void calculateCurrent() {
		current = (volts[0] - volts[1]) / resistance;
		// System.out.print(this + " res current set to " + current + "\n");
	}

	@Override
	public void stamp() {
		sim.stampResistor(nodes[0], nodes[1], resistance);
	}

	@Override
	public void getInfo(String arr[]) {
		arr[0] = "resistor";
		getBasicInfo(arr);
		arr[3] = "R = " + getUnitText(resistance, CirSim.OHM_STR);
		arr[4] = "P = " + getUnitText(getPower(), "W");
	}

	@Override
	public EditInfo getEditInfo(int n) {
		// ohmString doesn't work here on linux
		if (n == 0)
			return new EditInfo("Resistance (ohms)", resistance, 0, 0);
		return null;
	}

	@Override
	public void setEditValue(int n, EditInfo ei) {
		if (ei.getValue() > 0)
			resistance = ei.getValue();
	}

	@Override
	public boolean needsShortcut() {
		return true;
	}
}
