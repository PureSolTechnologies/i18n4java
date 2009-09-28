package javax.i18n4j.apps;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Vector;

import javax.i18n4j.FileSearch;
import javax.i18n4j.Translator;
import javax.swingx.config.APIConfig;
import javax.swingx.config.ConfigFile;
import javax.swingx.config.RTAParser;

import org.apache.log4j.Logger;

public class TemplateHeaderUtility {

	private static final Logger logger = Logger
			.getLogger(TemplateHeaderUtility.class);
	private static final Translator translator = Translator
			.getTranslator(TemplateHeaderUtility.class);

	private static void writeNewHeader(RandomAccessFile out, File outFile,
			File template, ConfigFile about) {
		try {
			RandomAccessFile in = new RandomAccessFile(template, "r");
			String line = in.readLine();
			while (line != null) {
				line = line.replaceAll("%SOURCEFILE%", outFile.getName());
				line = line.replaceAll("%OWNER%", about
						.read("GENERAL", "owner"));
				line = line.replaceAll("%YEARS%", about
						.read("GENERAL", "years"));
				line = line.replaceAll("%COPYRIGHT%", about.read("GENERAL",
						"copyright"));
				line = line.replaceAll("%AUTHOR%", about.read("GENERAL",
				"author"));
				line = line.replaceAll("%BUGREPORT%", about.read("GENERAL",
				"bugreport"));
				line = line.replaceAll("%VERSION%", about.read("GENERAL",
				"version"));
				out.writeBytes(line + "\n");
				line = in.readLine();
			}
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	private static void addHeaderToFile(File template, File about, File file) {
		if (!template.exists()) {
			logger.error("template '" + template.getPath()
					+ "' is not existing!");
		}
		try {
			String bakFile = file.getPath() + "~";
			file.renameTo(new File(bakFile));
			RandomAccessFile in;
			in = new RandomAccessFile(bakFile, "r");
			RandomAccessFile out = new RandomAccessFile(file, "rw");

			String line;
			line = in.readLine();
			while ((!line.contains("package ")) && (line != null)) {
				line = in.readLine();
			}

			ConfigFile aboutFile = new ConfigFile(about);
			writeNewHeader(out, file, template, aboutFile);
			aboutFile.close();

			while (line != null) {
				out.writeBytes(line + "\n");
				line = in.readLine();
			}
			out.close();
			in.close();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void main(String[] args) {
		APIConfig.setApplicationName("Template Header Utility");
		RTAParser rta = new RTAParser(args, "Template Header Utility",
				TemplateHeaderUtility.class, translator
						.i18n("adds headers to source files."));
		rta.readStandardSwitches();
		String headerTemplate = rta.getFreeParameterString(translator
				.i18n("Header template file"));
		String aboutFile = rta.getFreeParameterString(translator
				.i18n("About file"));
		String searchPattern = rta.getFreeParameterString(translator
				.i18n("Search pattern like 'src/**/*.java'"));
		if (rta.printErrorMessage() || (APIConfig.getHelpRequest() != 0)
				|| (APIConfig.getVersionRequest() != 0)) {
			if (APIConfig.getVersionRequest() != 0) {
				rta.showVersion(APIConfig.PACKAGE_VERSION,
						APIConfig.PACKAGE_YEARS, APIConfig.PACKAGE_OWNER,
						APIConfig.PACKAGE_AUTHOR);
			}
			if (APIConfig.getHelpRequest() != 0) {
				rta.showUsage(APIConfig.PACKAGE_BUGREPORT);

			}

			if ((APIConfig.getHelpRequest() == 0)
					&& (APIConfig.getVersionRequest() == 0)) {
				System.out.println("failed. wrong parameter!\n");
				System.exit(1);
			}
			System.exit(0);
		}

		logger.info("Header template is '" + headerTemplate + "'");
		Vector<File> files = FileSearch.find(searchPattern);
		for (File file : files) {
			logger.info("Processing file '" + file.getPath() + "'...");
			addHeaderToFile(new File(headerTemplate), new File(aboutFile), file);
			logger.info("done.");
		}
	}
}
