class OpAmpSwapElm extends OpAmpElm {
	public OpAmpSwapElm(int xx, int yy) {
		super(xx, yy);
		flags |= FLAG_SWAP;
	}

	@Override
	Class<OpAmpElm> getDumpClass() {
		return OpAmpElm.class;
	}
}
