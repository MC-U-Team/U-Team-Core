package info.u_team.u_team_core.data;

import java.io.IOException;
import java.nio.file.Path;

import com.google.gson.JsonElement;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;

public interface CommonDataProvider<V> {
	
	GenerationData getGenerationData();
	
	default String modid() {
		return getGenerationData().modid();
	};
	
	void register(V data);
	
	static void saveData(CachedOutput cachedOutput, JsonElement json, Path path, String errorMessage) {
		try {
			DataProvider.saveStable(cachedOutput, json, path);
		} catch (final IOException ex) {
			throw new RuntimeException(errorMessage + ". Path: " + path, ex);
		}
	}
	
	interface NoParam extends CommonDataProvider<Void> {
		
		@Override
		default void register(Void data) {
			register();
		}
		
		void register();
	}
}
