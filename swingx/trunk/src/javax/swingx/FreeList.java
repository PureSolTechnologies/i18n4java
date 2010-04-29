package javax.swingx;

import java.util.ArrayList;
import java.util.Map;

import javax.swing.ListModel;

public class FreeList extends AbstractExtendedList {

    private static final long serialVersionUID = 210162776409051253L;

    public FreeList() {
	super();
    }

    public FreeList(ListModel listModel) {
	super(listModel);
    }

    public void setListData(ArrayList<Object> displayItems,
	    ArrayList<Object> assignedItems) {
	super.setListData(displayItems, assignedItems);
    }

    public void setListData(Map<Object, Object> listData) {
	super.setListData(listData);
    }

    public Object getSelectedValue() {
	return super.getSelectedValue();
    }

    public Object[] getSelectedValues() {
	return super.getSelectedValues();
    }
}
