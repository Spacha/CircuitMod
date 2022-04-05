import java.awt.Graphics;
import java.awt.Point;
import java.util.StringTokenizer;

import javax.swing.JCheckBox;

class Switch2Elm extends SwitchElm {
	int link;
	static final int FLAG_CENTER_OFF = 1;

	public Switch2Elm(int xx, int yy) {
		super(xx, yy, false);
		noDiagonal = true;
	}

	Switch2Elm(int xx, int yy, boolean mm) {
		super(xx, yy, mm);
		noDiagonal = true;
	}

	public Switch2Elm(int xa, int ya, int xb, int yb, int f,
			StringTokenizer st) {
		super(xa, ya, xb, yb, f, st);
		link = Integer.valueOf(st.nextToken()).intValue();
		noDiagonal = true;
	}

	@Override
	int getDumpType() {
		return 'S';
	}

	@Override
	boolean needsShortcut() {
		return getClass() == Switch2Elm.class;
	}

	@Override
	String dump() {
		return super.dump() + " " + link;
	}

	final int openhs = 16;
	Point swposts[], swpoles[];

	@Override
	void setPoints() {
		super.setPoints();
		calcLeads(32);
		swposts = newPointArray(2);
		swpoles = newPointArray(3);
		interpPoint2(lead1, lead2, swpoles[0], swpoles[1], 1, openhs);
		swpoles[2] = lead2;
		interpPoint2(point1, point2, swposts[0], swposts[1], 1, openhs);
		posCount = hasCenterOff() ? 3 : 2;
	}

	@Override
	void draw(Graphics g) {
		setBbox(point1, point2, openhs);

		// draw first lead
		setVoltageColor(g, volts[0]);
		drawThickLine(g, point1, lead1);

		// draw second lead
		setVoltageColor(g, volts[1]);
		drawThickLine(g, swpoles[0], swposts[0]);

		// draw third lead
		setVoltageColor(g, volts[2]);
		drawThickLine(g, swpoles[1], swposts[1]);

		// draw switch
		if (!needsHighlight())
			g.setColor(whiteColor);
		drawThickLine(g, lead1, swpoles[position]);

		updateDotCount();
		drawDots(g, point1, lead1, curcount);
		if (position != 2)
			drawDots(g, swpoles[position], swposts[position], curcount);
		drawPosts(g);
	}

	@Override
	Point getPost(int n) {
		return (n == 0) ? point1 : swposts[n - 1];
	}

	@Override
	int getPostCount() {
		return 3;
	}

	@Override
	void calculateCurrent() {
		if (position == 2)
			current = 0;
	}

	@Override
	void stamp() {
		if (position == 2) // in center?
			return;
		sim.stampVoltageSource(nodes[0], nodes[position + 1], voltSource, 0);
	}

	@Override
	int getVoltageSourceCount() {
		return (position == 2) ? 0 : 1;
	}

	@Override
	void toggle() {
		super.toggle();
		if (link != 0) {
			int i;
			for (i = 0; i != sim.elmList.size(); i++) {
				Object o = sim.elmList.elementAt(i);
				if (o instanceof Switch2Elm) {
					Switch2Elm s2 = (Switch2Elm) o;
					if (s2.link == link)
						s2.position = position;
				}
			}
		}
	}

	@Override
	boolean getConnection(int n1, int n2) {
		if (position == 2)
			return false;
		return comparePair(n1, n2, 0, 1 + position);
	}

	@Override
	void getInfo(String arr[]) {
		arr[0] = (link == 0) ? "switch (SPDT)" : "switch (DPDT)";
		arr[1] = "I = " + getCurrentDText(getCurrent());
	}

	@Override
	public EditInfo getEditInfo(int n) {
		if (n == 1) {
			EditInfo ei = new EditInfo("", 0, -1, -1);
			ei.checkbox = new JCheckBox("Center Off", hasCenterOff());
			return ei;
		}
		return super.getEditInfo(n);
	}

	@Override
	public void setEditValue(int n, EditInfo ei) {
		if (n == 1) {
			flags &= ~FLAG_CENTER_OFF;
			if (ei.checkbox.isSelected())
				flags |= FLAG_CENTER_OFF;
			if (hasCenterOff())
				momentary = false;
			setPoints();
		} else
			super.setEditValue(n, ei);
	}

	boolean hasCenterOff() {
		return (flags & FLAG_CENTER_OFF) != 0;
	}
}
