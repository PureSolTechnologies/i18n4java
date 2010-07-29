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

package apps;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.i18n4j.I18NUpdate;
import javax.i18n4j.Translator;

/**
 * This applications reads files and directories given by command line and
 * search the found files for strings to be translated. This tools is similar to
 * Trolltech's lupdate.
 * 
 * @author Rick-Rainer Ludwig
 */
public class I18NUpdateApp {

	private static final Translator translator = Translator
			.getTranslator(I18NUpdateApp.class);

	private static void showUsage() {
		System.out.println("==========");
		System.out.println("I18NUpdate");
		System.out.println("==========");
		System.out.println();
		System.out.println(translator
				.i18n("usage: I18NUpdate <project directory>"));
		System.out.println(translator
				.i18n("   or  I18NUpdate <source directory> <i18n directory>"));
		System.out.println();
		System.out
				.println(translator
						.i18n("This application reads a source directory (<project directory>/src)\n"
								+ "and looks for all Java files (<source directory>/**/*.java).\n"
								+ "These files are scanned for i18n strings and the i18n files are\n"
								+ "stored classwise in a i18n directory  (<project directory>/res).\n"));
	}

	static public void main(String args[]) {
		if (args.length != 1) {
			showUsage();
			return;
		}
		try {
			new I18NUpdate(new File(args[0])).update();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
