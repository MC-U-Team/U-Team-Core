package info.u_team.u_team_core.data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.stream.JsonWriter;

import info.u_team.u_team_core.util.GsonUtil;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;

public abstract class CommonProvider implements DataProvider {
	
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
	
	public static void write(CachedOutput cache, JsonElement element, Path path) throws IOException {
		write(cache, element, path, GSON);
	}
	
	public static void write(CachedOutput cache, JsonElement element, Path path, Gson gson) throws IOException {
		try (final StringWriter writer = new StringWriter(); //
				final JsonWriter jsonWriter = GsonUtil.createTabWriter(gson, writer)) {
			GSON.toJson(element, jsonWriter);
			write(cache, writer.toString(), path);
		} catch (final IOException ex) {
			throw new JsonIOException(ex);
		}
	}
	
	// TODO 1.19 cleanup: rewrite data stuff to match vanilla better
	@SuppressWarnings("deprecation")
	public static void write(CachedOutput cache, String string, Path path) throws IOException {
		try (final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); //
				final HashingOutputStream hashingOutputStream = new HashingOutputStream(Hashing.sha1(), byteArrayOutputStream); //
				final Writer writer = new OutputStreamWriter(hashingOutputStream, StandardCharsets.UTF_8)) {
			writer.write(string);
			cache.writeIfNeeded(path, byteArrayOutputStream.toByteArray(), hashingOutputStream.hash());
		}
	}
}
