package javax.swingx.data;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.text.DateFormat;
import java.util.Date;

import javax.swingx.Table;

public class ClipboardCopy {

	static public void copy(ClipboardOwner sender, Table table) {
		Clipboard systemClipboard = Toolkit.getDefaultToolkit()
				.getSystemClipboard();
		StringBuffer content = new StringBuffer();
		for (int row = 0; row < table.getRowCount(); row++) {
			for (int col = 0; col < table.getColumnCount(); col++) {
				Object value = table.getValueAt(row, col);
				if (String.class.isAssignableFrom(value.getClass())) {
					String str = (String) table.getValueAt(row, col);
					str = str.replaceAll("\"", "''");
					content.append("\"");
					content.append(str);
					content.append("\"\t");
				} else if (Date.class.isAssignableFrom(value.getClass())) {
					DateFormat form = DateFormat.getDateTimeInstance();
					content.append("\"");
					content.append(form.format(value));
					content.append("\"\t");
				} else {
					content.append(value);
					content.append("\t");
				}
			}
			content.append("\n");
		}
		systemClipboard.setContents(new StringSelection(content.toString()),
				sender);
	}
}
