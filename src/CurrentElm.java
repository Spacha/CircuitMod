import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.StringTokenizer;

class CurrentElm extends CircuitElm {
	double currentValue;

	public CurrentElm(int xx, int yy) {
		super(xx, yy);
		currentValue = .01;
	}

	public CurrentElm(int xa, int ya, int xb, int yb, int f,
			StringTokenizer st) {
		super(xa, ya, xb, yb, f);
		try {
			currentValue = new Double(st.nextToken()).doubleValue();
		} catch (Exception e) {
			currentValue = .01;
		}
	}

	@Override
	String dump() {
		return super.dump() + " " + currentValue;
	}

	@Override
	int getDumpType() {
		return 'i';
	}

	@Override
	boolean needsShortcut() {
		return getClass() == CurrentElm.class;
	}

	Polygon arrow;
	Point ashaft1, ashaft2, center;

	@Override
	void setPoints() {
		super.setPoints();
		calcLeads(26);
		ashaft1 = interpPoint(lead1, lead2, .25);
		ashaft2 = interpPoint(lead1, lead2, .6);
		center = interpPoint(lead1, lead2, .5);
		Point p2 = interpPoint(lead1, lead2, .75);
		arrow = calcArrow(center, p2, 4, 4);
	}

	@Override
	void draw(Graphics g) {
		int cr = 12;
		draw2Leads(g);
		setVoltageColor(g, (volts[0] + volts[1]) / 2);
		setPowerColor(g, false);

		drawThickCircle(g, center.x, center.y, cr);
		drawThickLine(g, ashaft1, ashaft2);

		g.fillPolygon(arrow);
		setBbox(point1, point2, cr);
		doDots(g);
		if (sim.showValuesCheckItem.getState()) {
			String s = getShortUnitText(currentValue, "A");
			if (dx == 0 || dy == 0)
				drawValues(g, s, cr);
		}
		drawPosts(g);
	}

	@Override
	void stamp() {
		current = currentValue;
		sim.stampCurrentSource(nodes[0], nodes[1], current);
	}

	@Override
	public EditInfo getEditInfo(int n) {
		if (n == 0)
			return new EditInfo("Current (A)", currentValue, 0, .1);
		return null;
	}

	@Override
	public void setEditValue(int n, EditInfo ei) {
		currentValue = ei.value;
	}

	@Override
	void getInfo(String arr[]) {
		arr[0] = "current source";
		getBasicInfo(arr);
	}

	@Override
	double getVoltageDiff() {
		return volts[1] - volts[0];
	}
}
