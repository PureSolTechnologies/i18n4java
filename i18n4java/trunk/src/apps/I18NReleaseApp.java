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

import javax.i18n4j.I18NRelease;
import javax.i18n4j.Translator;

/**
 * This application converts all i18n files in the the specified directory and
 * converts them into tr files for usage in internationalized applications.
 * 
 * @author Rick-Rainer Ludwig
 */
public class I18NReleaseApp {

	private static final Translator translator = Translator
			.getTranslator(I18NReleaseApp.class);

	private static void showUsage() {
		System.out.println("===========");
		System.out.println("I18NRelease");
		System.out.println("===========");
		System.out.println();
		System.out.println(translator.i18n("usage:  I18NRelease <directory>"));
		System.out.println();
		System.out
				.println(translator
						.i18n("This application converts all i18n files in the\n"
								+ "the specified directory and converts them into\n"
								+ "tr files for usage in internationalized applications."));
	}

	public static void main(String[] args) {
		if ((args.length == 0) || (args.length > 1)) {
			showUsage();
			return;
		}
		try {
			new I18NRelease(new File(args[0])).release();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
