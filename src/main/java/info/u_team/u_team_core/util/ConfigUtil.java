package info.u_team.u_team_core.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BiConsumer;
import java.util.function.Function;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

/**
 * Utility methods for loading and reading json config files with gson
 *
 * @author HyCraftHD
 */
public class ConfigUtil {
	
	/**
	 * Default extension of json files
	 */
	public static final String JSON_EXTENSION = ".json";
	
	/**
	 * Load and create a config file if not already there from a default value. Use {@link Gson} for serialization and
	 * deserialization. The extension of the file is automatically {@link #JSON_EXTENSION}
	 *
	 * @param <T> Class to load with gson
	 * @param directory Path of the directory where the file is
	 * @param name The file without the extension
	 * @param gson Gson instance with possible registered serializer
	 * @param write Function that should write the default values to the {@link JsonWriter} and return the the default
	 *        instance
	 * @param read Function that should read the values from {@link BufferedReader} and deserialize them and return a new
	 *        instance of the class with the new values
	 * @return Either the default instance if the file was created or an instance with loaded value from the config
	 * @throws IOException
	 */
	public static <T> T loadConfig(Path directory, String name, Gson gson, Function<JsonWriter, T> write, Function<BufferedReader, T> read) throws IOException {
		return loadConfig(directory, name, JSON_EXTENSION, gson, write, read);
	}
	
	/**
	 * Load and create a config file if not already there from a default value. Use {@link Gson} for serialization and
	 * deserialization.
	 *
	 * @param <T> Class to load with gson
	 * @param directory Path of the directory where the file is
	 * @param name The file without the extension
	 * @param extension Extension of the file
	 * @param gson Gson instance with possible registered serializer
	 * @param write {@link Function} that should write the default values to the {@link JsonWriter} and return the the
	 *        default instance
	 * @param read {@link Function} that should read the values from {@link BufferedReader} and deserialize them and return
	 *        a new instance of the class with the new values
	 * @return Either the default instance if the file was created or an instance with loaded value from the config
	 * @throws IOException
	 */
	public static <T> T loadConfig(Path directory, String name, String extension, Gson gson, Function<JsonWriter, T> write, Function<BufferedReader, T> read) throws IOException {
		final Path path = directory.resolve(name + extension);
		if (Files.exists(path) && Files.isReadable(path) && Files.isReadable(path)) {
			try (BufferedReader reader = Files.newBufferedReader(path)) {
				return read.apply(reader);
			}
		} else {
			Files.deleteIfExists(path);
			Files.createDirectories(directory);
			Files.createFile(path);
			try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path); //
					JsonWriter writer = GsonUtil.createTabWriter(gson, bufferedWriter)) {
				return write.apply(writer);
			}
		}
	}
	
	/**
	 * Load and create a config file if not already there from a default value. Use {@link Gson} for serialization and
	 * deserialization. The extension of the file is automatically {@link #JSON_EXTENSION}
	 *
	 * @param <T> Class to load with gson
	 * @param directory Path of the directory where the file is
	 * @param name The file without the extension
	 * @param gson Gson instance with possible registered serializer
	 * @param defaultValue The default instance of the class that should be written if no config file is present
	 * @param write {@link BiConsumer} that should write the default values to the {@link JsonWriter}
	 * @param read {@link Function} that should read the values from {@link BufferedReader} and deserialize them and return
	 *        a new instance of the class with the new values
	 * @return Either the default instance if the file was created or an instance with loaded value from the config
	 * @throws IOException
	 */
	public static <T> T loadConfig(Path directory, String name, Gson gson, T defaultValue, BiConsumer<JsonWriter, T> write, Function<BufferedReader, T> read) throws IOException {
		return loadConfig(directory, name, JSON_EXTENSION, gson, defaultValue, write, read);
	}
	
	/**
	 * Load and create a config file if not already there from a default value. Use {@link Gson} for serialization and
	 * deserialization.
	 *
	 * @param <T> Class to load with gson
	 * @param directory Path of the directory where the file is
	 * @param name The file without the extension
	 * @param extension Extension of the file
	 * @param gson Gson instance with possible registered serializer
	 * @param defaultValue The default instance of the class that should be written if no config file is present
	 * @param write {@link BiConsumer} that should write the default values to the {@link JsonWriter}
	 * @param read {@link Function} that should read the values from {@link BufferedReader} and deserialize them and return
	 *        a new instance of the class with the new values
	 * @return Either the default instance if the file was created or an instance with loaded value from the config
	 * @throws IOException
	 */
	public static <T> T loadConfig(Path directory, String name, String extension, Gson gson, T defaultValue, BiConsumer<JsonWriter, T> write, Function<BufferedReader, T> read) throws IOException {
		return loadConfig(directory, name, extension, gson, writer -> {
			write.accept(writer, defaultValue);
			return defaultValue;
		}, read);
	}
}
