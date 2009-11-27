package javax.swingx;

import java.util.Hashtable;
import java.util.Vector;

import javax.swing.ListModel;

public class FreeList extends List {

	private static final long serialVersionUID = 210162776409051253L;

	private Hashtable<Object, Object> listData = new Hashtable<Object, Object>();

	public FreeList() {
		super();
	}

	public FreeList(ListModel listModel) {
		super(listModel);
	}

	public void setListData(Hashtable<Object, Object> listData) {
		this.listData = listData;
		if (listData == null) {
			this.removeAll();
		} else if (listData.size() == 0) {
			this.removeAll();
		} else {
			setListData(new Vector<Object>(listData.keySet()));
		}
	}

	public Object getSelectedValue() {
		Object value = super.getSelectedValue();
		if (value != null) {
			value = listData.get(super.getSelectedValue());
		}
		return value;
	}

	public Object[] getSelectedValues() {
		Object[] values = super.getSelectedValues();
		for (int index = 0; index < values.length; index++) {
			values[index] = listData.get(values[index]);
		}
		return values;
	}

}
