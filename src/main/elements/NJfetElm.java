package main.elements;
public class NJfetElm extends JfetElm {
	public NJfetElm(int xx, int yy) {
		super(xx, yy, false);
	}

	@Override
	public Class<JfetElm> getDumpClass() {
		return JfetElm.class;
	}
}
