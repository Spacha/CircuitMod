package main;
import java.awt.TextField;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JScrollBar;

public class EditInfo {
	public EditInfo(String n, double val, double mn, double mx) {
		name = n;
		value = val;
		if (mn == 0 && mx == 0 && val > 0) {
			minval = 1e10;
			while (minval > val / 100)
				minval /= 10.;
			maxval = minval * 1000;
		} else {
			minval = mn;
			maxval = mx;
		}
		forceLargeM = name.indexOf("(ohms)") > 0 || name.indexOf("(Hz)") > 0;
		dimensionless = false;
	}	

	public EditInfo setDimensionless() {
		dimensionless = true;
		return this;
	}
	
	public void setText(String s) {
		text = s;
	}
	
	public void setCheckbox(JCheckBox cb) {
		checkbox = cb;
	}
	
	public void setChoice(JComboBox<?> c, int selectedIndex) {
		choice = c;
		choice.setSelectedIndex(selectedIndex);
	}
	
	public int getChoice() {
		return choice.getSelectedIndex();
	}
	
	public boolean isChecked() {
		return checkbox.isSelected();
	}
	
	public boolean setNewDialog(boolean b) {
		return newDialog = b;
	}
	
	public String getName() { return name; }
	public double getValue() { return value; }
	public String getText() { return textf.getText(); }

	String name, text;
	double value, minval, maxval;
	TextField textf;
	JScrollBar bar;
	JComboBox<?> choice;
	JCheckBox checkbox;
	boolean newDialog;
	boolean forceLargeM;
	boolean dimensionless;
}
