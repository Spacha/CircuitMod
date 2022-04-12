package main.elements;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JCheckBox;

import main.EditInfo;

public class TextElm extends CircuitElm {
	String text;
	Vector<String> lines;
	int size;
	final int FLAG_CENTER = 1;
	final int FLAG_BAR = 2;

	public TextElm(int xx, int yy) {
		super(xx, yy);
		text = "text";
		lines = new Vector<String>();
		lines.add(text);
		size = 24;
	}

	public TextElm(int xa, int ya, int xb, int yb, int f, StringTokenizer st) {
		super(xa, ya, xb, yb, f);
		size = Integer.valueOf(st.nextToken()).intValue();
		text = st.nextToken();
		while (st.hasMoreTokens())
			text += ' ' + st.nextToken();
		split();
	}

	void split() {
		int i;
		lines = new Vector<String>();
		StringBuffer sb = new StringBuffer(text);
		for (i = 0; i < sb.length(); i++) {
			char c = sb.charAt(i);
			if (c == '\\') {
				sb.deleteCharAt(i);
				c = sb.charAt(i);
				if (c == 'n') {
					lines.add(sb.substring(0, i));
					sb.delete(0, i + 1);
					i = -1;
					continue;
				}
			}
		}
		lines.add(sb.toString());
	}

	@Override
	public String dump() {
		return super.dump() + " " + size + " " + text;
	}

	@Override
	public int getDumpType() {
		return 'x';
	}

	@Override
	public
	boolean canViewInScope() {
		return false;
	}

	@Override
	public boolean needsShortcut() {
		return getClass() == TextElm.class;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(needsHighlight() ? selectColor : lightGrayColor);
		Font f = new Font("SansSerif", 0, size);
		g.setFont(f);
		FontMetrics fm = g.getFontMetrics();
		int i;
		int maxw = -1;
		for (i = 0; i != lines.size(); i++) {
			int w = fm.stringWidth((lines.elementAt(i)));
			if (w > maxw)
				maxw = w;
		}
		int cury = y;
		setBbox(x, y, x, y);
		for (i = 0; i != lines.size(); i++) {
			String s = (lines.elementAt(i));
			if ((flags & FLAG_CENTER) != 0)
				x = (sim.getWindowWidth() - fm.stringWidth(s)) / 2;
			g.drawString(s, x, cury);
			if ((flags & FLAG_BAR) != 0) {
				int by = cury - fm.getAscent();
				g.drawLine(x, by, x + fm.stringWidth(s) - 1, by);
			}
			adjustBbox(x, cury - fm.getAscent(), x + fm.stringWidth(s),
					cury + fm.getDescent());
			cury += fm.getHeight();
		}
		x2 = boundingBox.x + boundingBox.width;
		y2 = boundingBox.y + boundingBox.height;
	}

	@Override
	public EditInfo getEditInfo(int n) {
		if (n == 0) {
			EditInfo ei = new EditInfo("Text", 0, -1, -1);
			ei.setText(text);
			return ei;
		}
		if (n == 1)
			return new EditInfo("Size", size, 5, 100);
		if (n == 2) {
			EditInfo ei = new EditInfo("", 0, -1, -1);
			ei.setCheckbox(new JCheckBox("Center", (flags & FLAG_CENTER) != 0));
			return ei;
		}
		if (n == 3) {
			EditInfo ei = new EditInfo("", 0, -1, -1);
			ei.setCheckbox(new JCheckBox("Draw Bar On Top", (flags & FLAG_BAR) != 0));
			return ei;
		}
		return null;
	}

	@Override
	public void setEditValue(int n, EditInfo ei) {
		if (n == 0) {
			text = ei.getText();
			split();
		}
		if (n == 1)
			size = (int) ei.getValue();
		if (n == 3) {
			if (ei.isChecked())
				flags |= FLAG_BAR;
			else
				flags &= ~FLAG_BAR;
		}
		if (n == 2) {
			if (ei.isChecked())
				flags |= FLAG_CENTER;
			else
				flags &= ~FLAG_CENTER;
		}
	}

	@Override
	public boolean isCenteredText() {
		return (flags & FLAG_CENTER) != 0;
	}

	@Override
	public void getInfo(String arr[]) {
		arr[0] = text;
	}

	@Override
	public int getPostCount() {
		return 0;
	}
}
