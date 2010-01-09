package javax.swingx;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * This class is basis for FreeList and EnumList. It's used to provide a basic
 * functionality to connect a output string to a free object for selection.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
abstract public class AbstractExtendedComboBox extends ComboBox {

	private static final long serialVersionUID = 34435300495300631L;

	private Hashtable<Object, Object> listData = new Hashtable<Object, Object>();

	public AbstractExtendedComboBox() {
		super();
	}

	protected void setItems(ArrayList<Object> displayItems,
			ArrayList<Object> assignedItems) {
		removeAll();
		if (displayItems.size() != assignedItems.size()) {
			throw new IllegalArgumentException(
					"The length of displayItems is unequal to assignedItems!");
		}
		for (int index = 0; index < displayItems.size(); index++) {
			listData.put(displayItems.get(index), assignedItems.get(index));
			addItem(displayItems.get(index));
		}
	}

	protected void setItems(Hashtable<Object, Object> listData) {
		removeAll();
		this.listData = listData;
		if (listData == null) {
			return;
		}
		for (Object displayString : listData.keySet()) {
			addItem((String) displayString);
		}
	}

	public void setSelectedItem(Object item) {
		System.out.println(item.toString());
		for (Object key : listData.keySet()) {
			if (listData.get(key).equals(item) || (key.equals(item))) {
				super.setSelectedItem(key);
			}
		}
	}

	public Object getSelectedItem() {
		Object value = super.getSelectedItem();
		if (value != null) {
			value = listData.get(value);
		}
		return value;
	}
}
