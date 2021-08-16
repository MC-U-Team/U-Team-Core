package info.u_team.u_team_core.util;

import java.io.IOException;
import java.io.Writer;

import com.google.gson.Gson;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;

public class GsonUtil {

	public static JsonWriter createTabWriter(Gson gson, Writer writer) throws IOException {
		final var jsonWriter = gson.newJsonWriter(Streams.writerForAppendable(writer));
		jsonWriter.setIndent("	"); // Use tab instead of two spaces
		return jsonWriter;
	}

}
