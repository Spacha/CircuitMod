package main.elements;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.StringTokenizer;

import main.EditInfo;

public class InverterElm extends CircuitElm {
	double slewRate; // V/ns

	public InverterElm(int xx, int yy) {
		super(xx, yy);
		noDiagonal = true;
		slewRate = .5;
	}

	public InverterElm(int xa, int ya, int xb, int yb, int f,
			StringTokenizer st) {
		super(xa, ya, xb, yb, f);
		noDiagonal = true;
		try {
			slewRate = Double.valueOf(st.nextToken()).doubleValue();
		} catch (Exception e) {
			slewRate = .5;
		}
	}

	@Override
	public String dump() {
		return super.dump() + " " + slewRate;
	}

	@Override
	public int getDumpType() {
		return 'I';
	}

	@Override
	public boolean needsShortcut() {
		return getClass() == InverterElm.class;
	}

	@Override
	public void draw(Graphics g) {
		drawPosts(g);
		draw2Leads(g);
		g.setColor(needsHighlight() ? selectColor : lightGrayColor);
		drawThickPolygon(g, gatePoly);
		drawThickCircle(g, pcircle.x, pcircle.y, 3);
		curcount = updateDotCount(current, curcount);
		drawDots(g, lead2, point2, curcount);
	}

	Polygon gatePoly;
	Point pcircle;

	@Override
	public void setPoints() {
		super.setPoints();
		int hs = 16;
		int ww = 16;
		if (ww > dn / 2)
			ww = (int) (dn / 2);
		lead1 = interpPoint(point1, point2, .5 - ww / dn);
		lead2 = interpPoint(point1, point2, .5 + (ww + 2) / dn);
		pcircle = interpPoint(point1, point2, .5 + (ww - 2) / dn);
		Point triPoints[] = newPointArray(3);
		interpPoint2(lead1, lead2, triPoints[0], triPoints[1], 0, hs);
		triPoints[2] = interpPoint(point1, point2, .5 + (ww - 5) / dn);
		gatePoly = createPolygon(triPoints);
		setBbox(point1, point2, hs);
	}

	@Override
	public int getVoltageSourceCount() {
		return 1;
	}

	@Override
	public void stamp() {
		sim.stampVoltageSource(0, nodes[1], voltSource);
	}

	@Override
	public void doStep() {
		double v0 = volts[1];
		double out = volts[0] > 2.5 ? 0 : 5;
		double maxStep = slewRate * sim.getTimeStep() * 1e9;
		out = Math.max(Math.min(v0 + maxStep, out), v0 - maxStep);
		sim.updateVoltageSource(0, nodes[1], voltSource, out);
	}

	@Override
	public double getVoltageDiff() {
		return volts[0];
	}

	@Override
	public void getInfo(String arr[]) {
		arr[0] = "inverter";
		arr[1] = "Vi = " + getVoltageText(volts[0]);
		arr[2] = "Vo = " + getVoltageText(volts[1]);
	}

	@Override
	public EditInfo getEditInfo(int n) {
		if (n == 0)
			return new EditInfo("Slew Rate (V/ns)", slewRate, 0, 0);
		return null;
	}

	@Override
	public void setEditValue(int n, EditInfo ei) {
		slewRate = ei.getValue();
	}

	// there is no current path through the inverter input, but there
	// is an indirect path through the output to ground.
	@Override
	public boolean getConnection(int n1, int n2) {
		return false;
	}

	@Override
	public boolean hasGroundConnection(int n1) {
		return (n1 == 1);
	}
}
