package main.elements;
import java.awt.Label;
import java.awt.Scrollbar;
import java.util.StringTokenizer;

import main.CirSim;
import main.EditInfo;

public class VarRailElm extends RailElm {
	Scrollbar slider;
	Label label;
	String sliderText;

	public VarRailElm(int xx, int yy) {
		super(xx, yy, WF_VAR);
		sliderText = "Voltage";
		frequency = maxVoltage;
		createSlider();
	}

	public VarRailElm(int xa, int ya, int xb, int yb, int f, StringTokenizer st) {
		super(xa, ya, xb, yb, f, st);
		sliderText = st.nextToken();
		while (st.hasMoreTokens())
			sliderText += ' ' + st.nextToken();
		createSlider();
	}

	@Override
	public String dump() {
		return super.dump() + " " + sliderText;
	}

	@Override
	public int getDumpType() {
		return 172;
	}

	void createSlider() {
		waveform = WF_VAR;
		int value = (int) ((frequency - bias) * 100 / (maxVoltage - bias));
		CirSim.addSidebarComponent(label = new Label(sliderText, Label.CENTER));
		CirSim.addSidebarComponent(slider = new Scrollbar(Scrollbar.HORIZONTAL, value, 1, 0, 101));
		CirSim.updateSidebar();
	}

	@Override
	public double getVoltage() {
		frequency = slider.getValue() * (maxVoltage - bias) / 100. + bias;
		return frequency;
	}

	@Override
	public void delete() {
		CirSim.removeSidebarComponent(label);
		CirSim.removeSidebarComponent(slider);
		CirSim.updateSidebar();
	}

	@Override
	public EditInfo getEditInfo(int n) {
		if (n == 0)
			return new EditInfo("Min Voltage", bias, -20, 20);
		if (n == 1)
			return new EditInfo("Max Voltage", maxVoltage, -20, 20);
		if (n == 2) {
			EditInfo ei = new EditInfo("Slider Text", 0, -1, -1);
			ei.setText(sliderText);
			return ei;
		}
		return null;
	}

	@Override
	public void setEditValue(int n, EditInfo ei) {
		if (n == 0)
			bias = ei.getValue();
		if (n == 1)
			maxVoltage = ei.getValue();
		if (n == 2) {
			sliderText = ei.getText();
			label.setText(sliderText);
		}
	}
}
