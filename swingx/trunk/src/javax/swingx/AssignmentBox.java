package javax.swingx;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Collections;
import java.util.Vector;

import javax.i18n4j.Translator;
import javax.swingx.connect.Slot;

public class AssignmentBox extends Panel {

	private static final long serialVersionUID = 1L;

	private static final Translator translator = Translator
			.getTranslator(AssignmentBox.class);

	private Vector<String> selection = null;
	private Vector<String> available = null;
	private Vector<String> reservoir = null;
	private List selectionList = null;
	private List availableList = null;
	private Button removeButton = null;
	private Button addButton = null;

	public AssignmentBox() {
		super();
		initializeUI();
		selection = new Vector<String>();
		reservoir = new Vector<String>();
	}

	public AssignmentBox(Vector<String> selection, Vector<String> reservoir) {
		this();
		setSelection(selection);
		setReservoir(reservoir);
	}

	private void initializeUI() {
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		c.ipadx = 2;
		c.ipady = 2;
		c.insets = new Insets(2, 2, 2, 2);

		Panel selectionPanel = Label.addFor(new ScrollPane(
				selectionList = new List()), translator.i18n("Assigned"),
				Label.NORTH);
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 10;
		c.weighty = 10;
		c.fill = GridBagConstraints.BOTH;
		layout.setConstraints(selectionPanel, c);
		add(selectionPanel);

		Panel buttonPanel = new Panel();
		GridLayout buttonLayout = new GridLayout(2, 1);
		buttonLayout.setVgap(2);
		buttonPanel.setLayout(buttonLayout);
		buttonPanel.add(addButton = new Button(translator.i18n("<<< add")));
		buttonPanel
				.add(removeButton = new Button(translator.i18n("remove >>>")));
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		layout.setConstraints(buttonPanel, c);
		add(buttonPanel);

		Panel reservoirPanel = Label.addFor(new ScrollPane(
				availableList = new List()), translator.i18n("Available"),
				Label.NORTH);
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = 1;
		c.weightx = 10;
		c.weighty = 10;
		c.fill = GridBagConstraints.BOTH;
		layout.setConstraints(reservoirPanel, c);
		add(reservoirPanel);

		addButton.connect("start", this, "add");
		removeButton.connect("start", this, "remove");
	}

	public void setSelection(Vector<String> selection) {
		if (selection == null) {
			this.selection = new Vector<String>();
		} else {
			this.selection = selection;
		}
		Collections.sort(this.selection);
		selectionList.setListData(this.selection);
		updateAvailability();
	}

	public Vector<String> getSelection() {
		return selection;
	}

	public void setReservoir(Vector<String> reservoir) {
		if (reservoir == null) {
			this.reservoir = new Vector<String>();
		} else {
			this.reservoir = reservoir;
		}
		updateAvailability();
	}

	private void updateAvailability() {
		available = new Vector<String>();
		for (String string : reservoir) {
			if (!selection.contains(string)) {
				available.add(string);
			}
		}
		Collections.sort(available);
		availableList.setListData(available);
	}

	@Slot
	public void add() {
		int[] indices = availableList.getSelectedIndices();
		for (int index : indices) {
			selection.add(available.get(index));
		}
		setSelection(selection);
	}

	@Slot
	public void remove() {
		int[] indices = selectionList.getSelectedIndices();
		Vector<String> save = new Vector<String>();
		for (int index : indices) {
			save.add(selection.get(index));
		}
		for (String remove : save) {
			selection.remove(remove);
		}
		setSelection(selection);
	}
}
