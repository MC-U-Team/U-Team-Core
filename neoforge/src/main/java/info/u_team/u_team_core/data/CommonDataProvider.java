package info.u_team.u_team_core.data;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;

public interface CommonDataProvider<V> extends DataProvider {
	
	GenerationData getGenerationData();
	
	default String modid() {
		return getGenerationData().modid();
	}
	
	default String nameSuffix() {
		final StringBuilder builder = new StringBuilder(modid());
		if (!getGenerationData().nameSuffix().isEmpty()) {
			builder.append(" (");
			builder.append(getGenerationData().nameSuffix());
			builder.append(")");
		}
		return builder.toString();
	}
	
	default <U> CompletableFuture<U> withRegistries(Function<HolderLookup.Provider, ? extends CompletionStage<U>> function) {
		return getGenerationData().registriesFuture().thenCompose(function);
	}
	
	void register(V param);
	
	default <T> CompletableFuture<?> saveData(CachedOutput cachedOutput, HolderLookup.Provider registries, Codec<T> codec, T value, Path path) {
		return DataProvider.saveStable(cachedOutput, registries, codec, value, path);
	}
	
	default CompletableFuture<?> saveData(CachedOutput cachedOutput, JsonElement json, Path path) {
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
