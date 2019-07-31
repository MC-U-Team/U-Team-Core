package info.u_team.u_team_core.util;

import java.io.*;
import java.nio.file.*;
import java.util.function.Function;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

public class ConfigUtil {
	
	public static <T> T loadConfig(Path directory, String name, Gson gson, Function<JsonWriter, T> write, Function<BufferedReader, T> read) throws IOException {
		final Path path = directory.resolve(name + ".json");
		if (Files.exists(path) && Files.isReadable(path) && Files.isReadable(path)) {
			try (BufferedReader reader = Files.newBufferedReader(path)) {
				return read.apply(reader);
			}
		} else {
			Files.deleteIfExists(path);
			Files.createDirectories(directory);
			Files.createFile(path);
			try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path); JsonWriter writer = GsonUtil.createTabWriter(gson, bufferedWriter)) {
				return write.apply(writer);
			}
		}
	}
}
