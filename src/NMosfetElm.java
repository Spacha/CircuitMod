class NMosfetElm extends MosfetElm {
	public NMosfetElm(int xx, int yy) {
		super(xx, yy, false);
	}

	@Override
	Class<MosfetElm> getDumpClass() {
		return MosfetElm.class;
	}
}
