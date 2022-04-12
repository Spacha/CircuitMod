package main.elements;
public class PushSwitchElm extends SwitchElm {
	public PushSwitchElm(int xx, int yy) {
		super(xx, yy, true);
	}

	@Override
	Class<SwitchElm> getDumpClass() {
		return SwitchElm.class;
	}
}
