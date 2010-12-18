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

/**
 * This is the main class for the I18N tools included within this package. The
 * main purpose is the mapping to the different progs included.
 *
 * @author Rick-Rainer Ludwig
 */
public class I18N4Java {

    private static void showUsage() {
        System.out.println("Usage: i18n4java <tool> <params>");
        System.out.println();
        System.out.println("available tools:");
        System.out.println("\tupdate\tI18NUpdate to read source files to" +
                "build i18n files");
        System.out.println("\trelease\tconvert i18n files to tr files to" +
                "be used in java applications");
        System.out.println();
        System.out.println("All tools understand the --help argument to" +
                "show a help screen.");
    }

    public static void main(String args[]) {
        if (args.length == 0) {
            showUsage();
            return;
        }
        String toolArgs[] = new String[args.length - 1];
        for (int index = 1; index < args.length; index++) {
            toolArgs[index - 1] = args[index];
        }
        if (args[0].equals("update")) {
            I18NUpdateApp.main(toolArgs);
        } else if (args[0].equals("release")) {
            I18NReleaseApp.main(toolArgs);
        } else if (args[0].equals("--help")) {
            showUsage();
        } else {
            System.err.println("Unknown tool '" + args[0] + "'!");
            showUsage();
        }
    }
}
