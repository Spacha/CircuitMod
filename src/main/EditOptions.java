package main;

import main.elements.CircuitElm;

class EditOptions implements Editable {
	CirSim sim;

	public EditOptions(CirSim s) {
		sim = s;
	}

	@Override
	public EditInfo getEditInfo(int n) {
		if (n == 0)
			return new EditInfo("Time step size (s)", sim.timeStep, 0, 0);
		if (n == 1)
			return new EditInfo("Range for voltage color (V)", CircuitElm.getVoltageRange(), 0, 0);

		return null;
	}

	@Override
	public void setEditValue(int n, EditInfo ei) {
		if (n == 0 && ei.value > 0)
			sim.timeStep = ei.value;
		if (n == 1 && ei.value > 0)
			CircuitElm.setVoltageRange( ei.getValue() );
	}
};
