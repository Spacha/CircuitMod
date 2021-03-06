package main.elements;
import java.awt.Graphics;
import java.util.StringTokenizer;

import javax.swing.JCheckBox;

import main.EditInfo;
import main.Inductor;

public class InductorElm extends CircuitElm {
	Inductor ind;
	double inductance;
	
	public double getInductance() { return inductance; }

	public InductorElm(int xx, int yy) {
		super(xx, yy);
		ind = new Inductor(sim);
		inductance = 1;
		ind.setup(inductance, current, flags);
	}

	public InductorElm(int xa, int ya, int xb, int yb, int f,
			StringTokenizer st) {
		super(xa, ya, xb, yb, f);
		ind = new Inductor(sim);
		inductance = Double.valueOf(st.nextToken()).doubleValue();
		current = Double.valueOf(st.nextToken()).doubleValue();
		ind.setup(inductance, current, flags);
	}

	@Override
	public int getDumpType() {
		return 'l';
	}

	@Override
	public boolean needsShortcut() {
		return getClass() == InductorElm.class;
	}

	@Override
	public String dump() {
		return super.dump() + " " + inductance + " " + current;
	}

	@Override
	public void setPoints() {
		super.setPoints();
		calcLeads(32);
	}

	@Override
	public void draw(Graphics g) {
		double v1 = volts[0];
		double v2 = volts[1];
		//int i;
		int hs = 8;
		setBbox(point1, point2, hs);
		draw2Leads(g);
		setPowerColor(g, false);
		drawCoil(g, 8, lead1, lead2, v1, v2);
		if (sim.isShowingValues()) {
			String s = getShortUnitText(inductance, "H");
			drawValues(g, s, hs, 0);
		}
		doDots(g);
		drawPosts(g);
	}

	@Override
	public void reset() {
		current = volts[0] = volts[1] = curcount = 0;
		ind.reset();
	}

	@Override
	public void stamp() {
		ind.stamp(nodes[0], nodes[1]);
	}

	@Override
	public void startIteration() {
		ind.startIteration(volts[0] - volts[1]);
	}

	@Override
	public boolean nonLinear() {
		return ind.nonLinear();
	}

	@Override
	void calculateCurrent() {
		double voltdiff = volts[0] - volts[1];
		current = ind.calculateCurrent(voltdiff);
	}

	@Override
	public void doStep() {
		double voltdiff = volts[0] - volts[1];
		ind.doStep(voltdiff);
	}

	@Override
	public void getInfo(String arr[]) {
		arr[0] = "inductor";
		getBasicInfo(arr);
		arr[3] = "L = " + getUnitText(inductance, "H");
		arr[4] = "P = " + getUnitText(getPower(), "W");
	}

	@Override
	public EditInfo getEditInfo(int n) {
		if (n == 0)
			return new EditInfo("Inductance (H)", inductance, 0, 0);
		if (n == 1) {
			EditInfo ei = new EditInfo("", 0, -1, -1);
			ei.setCheckbox(new JCheckBox("Trapezoidal Approximation", ind.isTrapezoidal()));
			return ei;
		}
		return null;
	}

	@Override
	public void setEditValue(int n, EditInfo ei) {
		if (n == 0)
			inductance = ei.getValue();
		if (n == 1) {
			if (ei.isChecked())
				flags &= ~Inductor.FLAG_BACK_EULER;
			else
				flags |= Inductor.FLAG_BACK_EULER;
		}
		ind.setup(inductance, current, flags);
	}
}
