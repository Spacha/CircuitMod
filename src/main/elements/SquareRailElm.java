package main.elements;
public class SquareRailElm extends RailElm {
	public SquareRailElm(int xx, int yy) {
		super(xx, yy, WF_SQUARE);
	}

	@Override
	Class<RailElm> getDumpClass() {
		return RailElm.class;
	}
}
