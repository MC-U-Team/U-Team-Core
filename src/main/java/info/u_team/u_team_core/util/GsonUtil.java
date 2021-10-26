package info.u_team.u_team_core.util;

import java.io.IOException;
import java.io.Writer;

import com.google.gson.Gson;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;

/**
 * Utility methods for gson
 *
 * @author HyCraftHD
 */
public class GsonUtil {
	
	/**
	 * Creates a {@link JsonWriter} with the indent set to one tab
	 *
	 * @param gson Gson instance
	 * @param writer Write
	 * @return Configured writer with one tab
	 * @throws IOException
	 */
	public static JsonWriter createTabWriter(Gson gson, Writer writer) throws IOException {
		final var jsonWriter = gson.newJsonWriter(Streams.writerForAppendable(writer));
		jsonWriter.setIndent("	"); // Use tab instead of two spaces
		return jsonWriter;
	}
	
}
