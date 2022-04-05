import java.awt.Font;
import java.awt.Graphics;
import java.util.StringTokenizer;

class RailElm extends VoltageElm {
	public RailElm(int xx, int yy) {
		super(xx, yy, WF_DC);
	}

	RailElm(int xx, int yy, int wf) {
		super(xx, yy, wf);
	}

	public RailElm(int xa, int ya, int xb, int yb, int f, StringTokenizer st) {
		super(xa, ya, xb, yb, f, st);
	}

	final int FLAG_CLOCK = 1;

	@Override
	int getDumpType() {
		return 'R';
	}

	@Override
	boolean needsShortcut() {
		return getClass() == RailElm.class;
	}
	// TODO
	// @Override
	// void getInfo(String arr[]) {
	//
	// }

	@Override
	int getPostCount() {
		return 1;
	}

	@Override
	void setPoints() {
		super.setPoints();
		lead1 = interpPoint(point1, point2, 1 - circleSize / dn);
	}

	@Override
	void draw(Graphics g) {
		setBbox(point1, point2, circleSize);
		setVoltageColor(g, volts[0]);
		drawThickLine(g, point1, lead1);
		boolean clock = waveform == WF_SQUARE && (flags & FLAG_CLOCK) != 0;
		if (waveform == WF_DC || waveform == WF_VAR || clock) {
			Font f = new Font("SansSerif", 0, 12);
			g.setFont(f);
			g.setColor(needsHighlight() ? selectColor : whiteColor);
			setPowerColor(g, false);
			double v = getVoltage();
			String s = getShortUnitText(v, "V");
			if (Math.abs(v) < 1)
				s = showFormat.format(v) + "V";
			if (getVoltage() > 0)
				s = "+" + s;
			if (this instanceof AntennaElm)
				s = "Ant";
			if (clock)
				s = "CLK";
			drawCenteredText(g, s, x2, y2, true);
		} else {
			drawWaveform(g, point2);
		}
		drawPosts(g);
		curcount = updateDotCount(-current, curcount);
		if (sim.dragElm != this)
			drawDots(g, point1, lead1, curcount);
	}

	@Override
	double getVoltageDiff() {
		return volts[0];
	}

	@Override
	void stamp() {
		if (waveform == WF_DC)
			sim.stampVoltageSource(0, nodes[0], voltSource, getVoltage());
		else
			sim.stampVoltageSource(0, nodes[0], voltSource);
	}

	@Override
	void doStep() {
		if (waveform != WF_DC)
			sim.updateVoltageSource(0, nodes[0], voltSource, getVoltage());
	}

	@Override
	boolean hasGroundConnection(int n1) {
		return true;
	}
}
