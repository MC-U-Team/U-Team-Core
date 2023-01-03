package info.u_team.u_team_core.data;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonElement;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;

public interface CommonDataProvider<V> extends DataProvider {
	
	GenerationData getGenerationData();
	
	default String modid() {
		return getGenerationData().modid();
	}
	
	void register(V param);
	
	static CompletableFuture<?> saveData(CachedOutput cachedOutput, JsonElement json, Path path) {
		return DataProvider.saveStable(cachedOutput, json, path);
	}
	
	interface NoParam extends CommonDataProvider<Void> {
		
		@Override
		default void register(Void data) {
			register();
		}
		
		void register();
	}
}
