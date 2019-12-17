package info.u_team.u_team_core.data;

import java.io.*;
import java.nio.file.*;
import java.util.Objects;

import org.apache.logging.log4j.*;

import com.google.gson.*;
import com.google.gson.stream.JsonWriter;

import info.u_team.u_team_core.util.GsonUtil;
import net.minecraft.data.*;
import net.minecraft.util.ResourceLocation;

public abstract class CommonProvider implements IDataProvider {
	
	public static final Logger LOGGER = LogManager.getLogger("DataGenerator");
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
	
	protected final Marker marker;
	
	protected final GenerationData data;
	protected final String modid;
	protected final DataGenerator generator;
	protected final Path path;
	
	public CommonProvider(GenerationData data) {
		this.data = data;
		generator = data.getGenerator();
		modid = data.getModid();
		path = generator.getOutputFolder();
		marker = MarkerManager.getMarker(getName());
	}
	
	public ResourceLocation modLoc(String path) {
		return new ResourceLocation(modid, path);
	}
	
	public ResourceLocation mcLoc(String path) {
		return new ResourceLocation(path);
	}
	
	public Path resolveModData() {
		return path.resolve("data").resolve(modid);
	}
	
	public Path resolveModAssets() {
		return path.resolve("assets").resolve(modid);
	}
	
	public Path resolveData(ResourceLocation location) {
		return path.resolve("data").resolve(location.getNamespace());
	}
	
	public Path resolveAssets(ResourceLocation location) {
		return path.resolve("assets").resolve(location.getNamespace());
	}
	
	public static void write(DirectoryCache cache, JsonElement element, Path path) throws IOException {
		write(cache, element, path, GSON);
	}
	
	public static void write(DirectoryCache cache, JsonElement element, Path path, Gson gson) throws IOException {
		try (final StringWriter writer = new StringWriter(); //
				final JsonWriter jsonWriter = GsonUtil.createTabWriter(gson, writer)) {
			GSON.toJson(element, jsonWriter);
			write(cache, writer.toString(), path);
		} catch (IOException ex) {
			throw new JsonIOException(ex);
		}
	}
	
	public static void write(DirectoryCache cache, String string, Path path) throws IOException {
		final String hash = HASH_FUNCTION.hashUnencodedChars(string).toString();
		if (!Objects.equals(cache.getPreviousHash(path), hash) || !Files.exists(path)) {
			Files.createDirectories(path.getParent());
			try (final BufferedWriter writer = Files.newBufferedWriter(path)) {
				writer.write(string);
			}
		}
		cache.recordHash(path, hash);
	}
}
