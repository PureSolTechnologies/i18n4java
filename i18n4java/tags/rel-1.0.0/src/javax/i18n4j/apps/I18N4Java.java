/***************************************************************************
 *
 *   I18N4Java.java
 *   -------------------
 *   copyright            : (c) 2009 by Rick-Rainer Ludwig
 *   author               : Rick-Rainer Ludwig
 *   email                : rl719236@sourceforge.net
 *
 ***************************************************************************/

/***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package javax.i18n4j.apps;

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
            I18NUpdate.main(toolArgs);
        } else if (args[0].equals("release")) {
            I18NRelease.main(toolArgs);
        } else if (args[0].equals("--help")) {
            showUsage();
        } else {
            System.err.println("Unknown tool '" + args[0] + "'!");
            showUsage();
        }
    }
}
