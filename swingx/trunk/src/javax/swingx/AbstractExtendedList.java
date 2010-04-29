package javax.swingx;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import javax.swing.ListModel;

/**
 * This class is basis for FreeList and EnumList. It's used to provide a basic
 * functionality to connect a output string to a free object for selection.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
abstract public class AbstractExtendedList extends List {

    private static final long serialVersionUID = 34435300495300631L;

    private final Map<Object, Object> listData = new Hashtable<Object, Object>();

    public AbstractExtendedList() {
	super();
    }

    public AbstractExtendedList(ListModel listModel) {
	super(listModel);
    }

    protected void setListData(ArrayList<Object> displayItems,
	    ArrayList<Object> assignedItems) {
	if (displayItems.size() != assignedItems.size()) {
	    throw new IllegalArgumentException(
		    "The length of displayItems is unequal to assignedItems!");
	}
	Map<Object, Object> listData = new Hashtable<Object, Object>();
	for (int index = 0; index < displayItems.size(); index++) {
	    listData.put(displayItems.get(index), assignedItems.get(index));
	}
	setListData(listData);
    }

    protected void setListData(Map<Object, Object> listData) {
	removeAll();
	if (listData != null) {
	    this.listData.putAll(listData);
	    setListData(new Vector<Object>(listData.keySet()));
	}
    }

    public void setSelectedValue(Object value, boolean scroll) {
	for (Object key : listData.keySet()) {
	    if (listData.get(key).equals(value) || key.equals(value)) {
		super.setSelectedValue(key, scroll);
	    }
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

    public void removeAll() {
	super.removeAll();
	listData.clear();
    }
}
