package main.elements;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.StringTokenizer;

import javax.swing.JComboBox;

import main.EditInfo;

public class VoltageElm extends CircuitElm {
	static final int FLAG_COS = 2;
	int waveform;
	static final int WF_DC = 0;
	static final int WF_AC = 1;
	static final int WF_SQUARE = 2;
	static final int WF_TRIANGLE = 3;
	static final int WF_SAWTOOTH = 4;
	static final int WF_PULSE = 5;
	static final int WF_VAR = 6;
	double frequency, maxVoltage, freqTimeZero, bias, phaseShift, dutyCycle;

	VoltageElm(int xx, int yy, int wf) {
		super(xx, yy);
		waveform = wf;
		maxVoltage = 5;
		frequency = 40;
		dutyCycle = .5;
		reset();
	}

	public VoltageElm(int xa, int ya, int xb, int yb, int f,
			StringTokenizer st) {
		super(xa, ya, xb, yb, f);
		maxVoltage = 5;
		frequency = 40;
		waveform = WF_DC;
		dutyCycle = .5;
		try {
			waveform = Integer.parseInt(st.nextToken());
			frequency = Double.parseDouble(st.nextToken());
			maxVoltage = Double.parseDouble(st.nextToken());
			bias = Double.parseDouble(st.nextToken());
			phaseShift = Double.parseDouble(st.nextToken());
			dutyCycle = Double.parseDouble(st.nextToken());
		} catch (Exception ignore) {}

		if ((flags & FLAG_COS) != 0) {
			flags &= ~FLAG_COS;
			phaseShift = pi / 2;
		}
		reset();
	}

	@Override
	public int getDumpType() {
		return 'v';
	}

	@Override
	public String dump() {
		return super.dump() + " " + waveform + " " + frequency + " "
				+ maxVoltage + " " + bias + " " + phaseShift + " " + dutyCycle;
	}
	/*
	 * void setCurrent(double c) { current = c; System.out.print(
	 * "v current set to " + c + "\n"); }
	 */

	@Override
	public void reset() {
		freqTimeZero = 0;
		curcount = 0;
	}

	double triangleFunc(double x) {
		if (x < pi)
			return x * (2 / pi) - 1;
		return 1 - (x - pi) * (2 / pi);
	}

	@Override
	public void stamp() {
		if (waveform == WF_DC)
			sim.stampVoltageSource(nodes[0], nodes[1], voltSource,
					getVoltage());
		else
			sim.stampVoltageSource(nodes[0], nodes[1], voltSource);
	}

	@Override
	public void doStep() {
		if (waveform != WF_DC)
			sim.updateVoltageSource(nodes[0], nodes[1], voltSource,
					getVoltage());
	}

	public double getVoltage() {
		double w = 2 * pi * (sim.getTime() - freqTimeZero) * frequency + phaseShift;
		switch (waveform) {
		case WF_DC:
			return maxVoltage + bias;
		case WF_AC:
			return Math.sin(w) * maxVoltage + bias;
		case WF_SQUARE:
			return bias + ((w % (2 * pi) > (2 * pi * dutyCycle)) ? -maxVoltage
					: maxVoltage);
		case WF_TRIANGLE:
			return bias + triangleFunc(w % (2 * pi)) * maxVoltage;
		case WF_SAWTOOTH:
			return bias + (w % (2 * pi)) * (maxVoltage / pi) - maxVoltage;
		case WF_PULSE:
			return ((w % (2 * pi)) < 1) ? maxVoltage + bias : bias;
		default:
			return 0;
		}
	}

	final int circleSize = 17;

	@Override
	public void setPoints() {
		super.setPoints();
		calcLeads(
				(waveform == WF_DC || waveform == WF_VAR) ? 8 : circleSize * 2);
	}

	@Override
	public void draw(Graphics g) {
		setBbox(x, y, x2, y2);
		draw2Leads(g);
		if (waveform == WF_DC) {
			setPowerColor(g, false);
			setVoltageColor(g, volts[0]);
			interpPoint2(lead1, lead2, ps1, ps2, 0, 10);
			drawThickLine(g, ps1, ps2);
			setVoltageColor(g, volts[1]);
			int hs = 16;
			setBbox(point1, point2, hs);
			interpPoint2(lead1, lead2, ps1, ps2, 1, hs);
			drawThickLine(g, ps1, ps2);
		} else {
			setBbox(point1, point2, circleSize);
			interpPoint(lead1, lead2, ps1, .5);
			drawWaveform(g, ps1);
		}
		updateDotCount();
		if (sim.dragElm != this) {
			if (waveform == WF_DC)
				drawDots(g, point1, point2, curcount);
			else {
				drawDots(g, point1, lead1, curcount);
				drawDots(g, point2, lead2, -curcount);
			}
		}
		drawPosts(g);
	}

	void drawWaveform(Graphics g, Point center) {
		g.setColor(needsHighlight() ? selectColor : Color.gray);
		setPowerColor(g, false);
		int xc = center.x;
		int yc = center.y;
		drawThickCircle(g, xc, yc, circleSize);
		int wl = 8;
		adjustBbox(xc - circleSize, yc - circleSize, xc + circleSize,
				yc + circleSize);
		int xc2;
		switch (waveform) {
		case WF_DC: {
			break;
		}
		case WF_SQUARE:
			xc2 = (int) (wl * 2 * dutyCycle - wl + xc);
			xc2 = max(xc - wl + 3, min(xc + wl - 3, xc2));
			drawThickLine(g, xc - wl, yc - wl, xc - wl, yc);
			drawThickLine(g, xc - wl, yc - wl, xc2, yc - wl);
			drawThickLine(g, xc2, yc - wl, xc2, yc + wl);
			drawThickLine(g, xc + wl, yc + wl, xc2, yc + wl);
			drawThickLine(g, xc + wl, yc, xc + wl, yc + wl);
			break;
		case WF_PULSE:
			yc += wl / 2;
			drawThickLine(g, xc - wl, yc - wl, xc - wl, yc);
			drawThickLine(g, xc - wl, yc - wl, xc - wl / 2, yc - wl);
			drawThickLine(g, xc - wl / 2, yc - wl, xc - wl / 2, yc);
			drawThickLine(g, xc - wl / 2, yc, xc + wl, yc);
			break;
		case WF_SAWTOOTH:
			drawThickLine(g, xc, yc - wl, xc - wl, yc);
			drawThickLine(g, xc, yc - wl, xc, yc + wl);
			drawThickLine(g, xc, yc + wl, xc + wl, yc);
			break;
		case WF_TRIANGLE: {
			int xl = 5;
			drawThickLine(g, xc - xl * 2, yc, xc - xl, yc - wl);
			drawThickLine(g, xc - xl, yc - wl, xc, yc);
			drawThickLine(g, xc, yc, xc + xl, yc + wl);
			drawThickLine(g, xc + xl, yc + wl, xc + xl * 2, yc);
			break;
		}
		case WF_AC: {
			int i;
			int xl = 10;
			int ox = -1, oy = -1;
			for (i = -xl; i <= xl; i++) {
				int yy = yc + (int) (.95 * Math.sin(i * pi / xl) * wl);
				if (ox != -1)
					drawThickLine(g, ox, oy, xc + i, yy);
				ox = xc + i;
				oy = yy;
			}
			break;
		}
		}
		if (sim.isShowingValues()) {
			String s = getShortUnitText(frequency, "Hz");
			if (dx == 0 || dy == 0)
				drawValues(g, s, circleSize, 0);
		}
	}

	@Override
	public int getVoltageSourceCount() {
		return 1;
	}

	@Override
	double getPower() {
		return -getVoltageDiff() * current;
	}

	@Override
	public double getVoltageDiff() {
		return volts[1] - volts[0];
	}

	@Override
	public void getInfo(String[] arr) {
		switch (waveform) {
		case WF_DC:
		case WF_VAR:
			arr[0] = "voltage source";
			break;
		case WF_AC:
			arr[0] = "A/C source";
			break;
		case WF_SQUARE:
			arr[0] = "square wave gen";
			break;
		case WF_PULSE:
			arr[0] = "pulse gen";
			break;
		case WF_SAWTOOTH:
			arr[0] = "sawtooth gen";
			break;
		case WF_TRIANGLE:
			arr[0] = "triangle gen";
			break;
		}
		arr[1] = "I = " + getCurrentText(getCurrent());
		arr[2] = ((this instanceof RailElm) ? "V = " : "Vd = ")
				+ getVoltageText(getVoltageDiff());
		if (waveform != WF_DC && waveform != WF_VAR) {
			arr[3] = "f = " + getUnitText(frequency, "Hz");
			arr[4] = "Vmax = " + getVoltageText(maxVoltage);
			int i = 5;
			if (bias != 0)
				arr[i++] = "Voff = " + getVoltageText(bias);
			else if (frequency > 500)
				arr[i++] = "wavelength = "
						+ getUnitText(2.9979e8 / frequency, "m");
			arr[i++] = "P = " + getUnitText(getPower(), "W");
		}
	}

	@Override
	public EditInfo getEditInfo(int n) {
		if (n == 0)
			return new EditInfo(waveform == WF_DC ? "Voltage" : "Max Voltage",
					maxVoltage, -20, 20);
		if (n == 1) {
			EditInfo ei = new EditInfo("Waveform", waveform, -1, -1);
			String[] waveForms = { "DC", "AC", "Square Wave", "Triangle",
					"Sawtooth", "Pulse" };
			ei.setChoice(new JComboBox<Object>(waveForms), waveform);
			return ei;
		}
		if (waveform == WF_DC)
			return null;
		if (n == 2)
			return new EditInfo("Frequency (Hz)", frequency, 4, 500);
		if (n == 3)
			return new EditInfo("DC Offset (V)", bias, -20, 20);
		if (n == 4)
			return new EditInfo("Phase Offset (degrees)", phaseShift * 180 / pi,
					-180, 180).setDimensionless();
		if (n == 5 && waveform == WF_SQUARE)
			return new EditInfo("Duty Cycle", dutyCycle * 100, 0, 100)
					.setDimensionless();
		return null;
	}

	@Override
	public void setEditValue(int n, EditInfo ei) {
		double val = ei.getValue();

		if (n == 0)
			maxVoltage = val;
		if (n == 3)
			bias = val;
		if (n == 2) {
			// adjust time zero to maintain continuity ind the waveform
			// even though the frequency has changed.
			double oldfreq = frequency;
			frequency = val;
			double maxfreq = 1 / (8 * sim.getTimeStep());
			if (frequency > maxfreq)
				frequency = maxfreq;
			//double adj = frequency - oldfreq;
			freqTimeZero = sim.getTime() - oldfreq * (sim.getTime() - freqTimeZero) / frequency;
		}
		if (n == 1) {
			int ow = waveform;
			waveform = ei.getChoice();

			// waveform changed from non-DC to DC
			if (waveform == WF_DC && ow != WF_DC) {
				ei.setNewDialog(true);
				bias = 0;

			// waveform changed from non-DC to another non-DC
			} else if (waveform != WF_DC && ow == WF_DC) {
				ei.setNewDialog(true);
			}

			// new/old waveform is/was square
			if ((waveform == WF_SQUARE || ow == WF_SQUARE) && waveform != ow)
				ei.setNewDialog(true);
			setPoints();
		}
		if (n == 4)
			phaseShift = val * pi / 180;
		if (n == 5)
			dutyCycle = val * .01;
	}
}
