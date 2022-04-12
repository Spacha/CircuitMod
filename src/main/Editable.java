package main;

public interface Editable {
	public EditInfo getEditInfo(int n);

	void setEditValue(int n, EditInfo ei);
}