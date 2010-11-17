/***************************************************************************
 *
 * Copyright 2009-2010 PureSol Technologies 
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 *
 ***************************************************************************/

package javax.swingx;

import java.awt.FlowLayout;
import java.util.Locale;

import javax.i18n4java.Translator;
import javax.swingx.config.APIInformation;
import javax.swingx.config.CustomerInformation;

/**
 * This class implements the standard about box for copyright and license
 * information.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public class AboutBox extends Dialog {

	private static final long serialVersionUID = 1L;

	private static final Translator translator = Translator
			.getTranslator(AboutBox.class);

	/**
	 * This is the standard constructor for the about box. Only a parent
	 * reference is needed. All other information are standard or taken out of
	 * APIConfig and CustomerConfig.
	 * 
	 * @see com.APIInformation.api.APIConfig
	 * @see com.CustomerInformation.config.api.CustomerConfig
	 * 
	 * @param parent
	 *            is the reference to the parent Application showing this
	 *            dialog.
	 */
	public AboutBox() {
		super(translator.i18n("About"), false);
		initView();
	}

	/**
	 * This is the method for creating the complete UI.
	 */
	protected void initView() {
		setDefaultLayout();
		BorderLayoutWidget content = (BorderLayoutWidget) getContentPane();
		TabbedPane tabbedPane = new TabbedPane();
		content.setCenter(tabbedPane);
		tabbedPane.add(translator.i18n("Copyright"), new ScrollPane(
				getCopyrightPanel()));
		tabbedPane.add(translator.i18n("Vendor"), new ScrollPane(
				getVendorPanel()));
		tabbedPane.add(translator.i18n("Customer"), new ScrollPane(
				getCustomerPanel()));
		tabbedPane.add(translator.i18n("Contact"), new ScrollPane(
				getContactPanel()));
		pack();
	}

	/**
	 * This method creates the copyright panel for this about box.
	 * 
	 * @return The Panel is returned for the TabbedPane.
	 */
	private Panel getCopyrightPanel() {
		Panel panel = new Panel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		HTMLTextPane html = new HTMLTextPane();
		html.setText(APIInformation.getCopyrightMessage(Application.getInstance()
				.getClass()));
		panel.add(html);
		return panel;
	}

	/**
	 * This method creates the vendor panel for this about box.
	 * 
	 * @return The Panel is returned for the TabbedPane.
	 */
	private Panel getVendorPanel() {
		Panel panel = new Panel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		HTMLTextPane html = new HTMLTextPane();
		html.setText(APIInformation.getVendorInformation(Application.getInstance()
				.getClass()));
		panel.add(html);
		return panel;
	}

	/**
	 * This method creates the customer panel for this about box.
	 * 
	 * @return The Panel is returned for the TabbedPane.
	 */
	private Panel getCustomerPanel() {
		Panel panel = new Panel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		HTMLTextPane html = new HTMLTextPane();
		html.setText(CustomerInformation.getCustomerInformation(Application
				.getInstance().getClass()));
		panel.add(html);
		return panel;
	}

	/**
	 * This method creates the contact panel for this about box.
	 * 
	 * @return The Panel is returned for the TabbedPane.
	 */
	private Panel getContactPanel() {
		Panel panel = new Panel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		HTMLTextPane html = new HTMLTextPane();
		html.setText(APIInformation.getContactInformation(Application.getInstance()
				.getClass()));
		panel.add(html);
		return panel;
	}

	static public void about() {
		new AboutBox().run();
	}

	static public void main(String args[]) {
		Translator.setDefault(new Locale("de", "DE"));
		new AboutBox().run();
	}
}
