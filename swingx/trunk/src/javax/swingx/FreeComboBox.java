package javax.swingx;

import java.util.ArrayList;
import java.util.Hashtable;

public class FreeComboBox extends AbstractExtendedComboBox {

	private static final long serialVersionUID = -1078072332476864091L;

	public FreeComboBox() {
		super();
	}

	protected void setItems(ArrayList<Object> displayItems,
			ArrayList<Object> assignedItems) {
		super.setItems(displayItems, assignedItems);
	}

	protected void setItems(Hashtable<Object, Object> listData) {
		super.setItems(listData);
	}
}
