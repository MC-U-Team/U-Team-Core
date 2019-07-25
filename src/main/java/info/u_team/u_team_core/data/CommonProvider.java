package info.u_team.u_team_core.data;

import java.io.*;
import java.nio.file.*;
import java.util.Objects;

import org.apache.logging.log4j.*;

import com.google.gson.*;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;

import net.minecraft.data.*;

public abstract class CommonProvider implements IDataProvider {
	
	protected static final Logger LOGGER = LogManager.getLogger("DataGenerator");
	protected static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	
	protected final Marker marker;
	
	private final String name;
	protected final DataGenerator generator;
	protected final Path path;
	
	public CommonProvider(String name, DataGenerator generator) {
		this.generator = generator;
		this.name = name;
		this.path = resolvePath(generator.getOutputFolder());
		marker = MarkerManager.getMarker(name);
	}
	
	protected abstract Path resolvePath(Path outputFolder);
	
	protected Path resolveAssets(Path outputFolder, String modid) {
		return outputFolder.resolve("assets").resolve(modid);
	}
	
	protected Path resolveData(Path outputFolder, String modid) {
		return outputFolder.resolve("data").resolve(modid);
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	protected void write(DirectoryCache cache, JsonElement element, Path path) throws IOException {
		final StringWriter writer = new StringWriter();
		try {
			final JsonWriter jsonWriter = GSON.newJsonWriter(Streams.writerForAppendable(writer));
			jsonWriter.setIndent("	"); // Use tab instead of two spaces
			GSON.toJson(element, jsonWriter);
		} catch (IOException e) {
			throw new JsonIOException(e);
		}
		write(cache, writer.toString(), path);
	}
	
	protected void write(DirectoryCache cache, String string, Path path) throws IOException {
		final String hash = HASH_FUNCTION.hashUnencodedChars(string).toString();
		if (!Objects.equals(cache.getPreviousHash(path), hash) || !Files.exists(path)) {
			Files.createDirectories(path.getParent());
			try (final BufferedWriter writer = Files.newBufferedWriter(path)) {
				writer.write(string);
			}
		}
		cache.func_208316_a(path, hash);
	}
}
