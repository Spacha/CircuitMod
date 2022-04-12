package main;
class PushSwitchElm extends SwitchElm {
	public PushSwitchElm(int xx, int yy) {
		super(xx, yy, true);
	}

	@Override
	Class<SwitchElm> getDumpClass() {
		return SwitchElm.class;
	}
}
