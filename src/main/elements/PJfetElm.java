package main.elements;
public class PJfetElm extends JfetElm {
	public PJfetElm(int xx, int yy) {
		super(xx, yy, true);
	}

	@Override
	public Class<JfetElm> getDumpClass() {
		return JfetElm.class;
	}
}
