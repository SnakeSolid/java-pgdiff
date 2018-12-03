package ru.snake.util.pgdiff.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.esotericsoftware.yamlbeans.YamlConfig;
import com.esotericsoftware.yamlbeans.YamlReader;

/**
 * Utility class to read configuration from file.
 *
 * @author snake
 *
 */
public class ConfigurationReader {

	/**
	 * Try to read configuration from given file.
	 *
	 * @param configFile
	 *            path to file
	 * @return configuration
	 * @throws ConfigNotFoundException
	 *             if file does not exists
	 * @throws ReadConfigException
	 *             if file can't be read
	 */
	public static Configuration read(File configFile) throws ConfigNotFoundException, ReadConfigException {
		YamlConfig config = new YamlConfig();
		config.setAllowDuplicates(false);
		config.setBeanProperties(false);
		config.setPrivateFields(true);

		try (FileReader configReader = new FileReader(configFile)) {
			YamlReader reader = new YamlReader(configReader, config);
			Configuration result = reader.read(Configuration.class);

			return result;
		} catch (FileNotFoundException e) {
			throw new ConfigNotFoundException(e);
		} catch (IOException e) {
			throw new ReadConfigException(e);
		}
	}

}
