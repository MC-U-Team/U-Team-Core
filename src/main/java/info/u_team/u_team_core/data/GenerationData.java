package info.u_team.u_team_core.data;

import java.util.function.BiFunction;
import java.util.function.Function;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;

public record GenerationData(String modid, DataGenerator generator, ExistingFileHelper existingFileHelper) {
	
	public GenerationData(String modid, GatherDataEvent event) {
		this(modid, event.getGenerator(), event.getExistingFileHelper());
	}
	
	public <B extends CommonBlockTagsProvider, I extends CommonItemTagsProvider> void addProvider(boolean shouldRun, Function<GenerationData, B> blockTagsFunction, BiFunction<GenerationData, B, I> itemTagsFunction) {
		final B blockTags = addProvider(shouldRun, blockTagsFunction);
		addProvider(shouldRun, data -> itemTagsFunction.apply(data, blockTags));
	}
	
	public <T extends DataProvider> T addProvider(boolean shouldRun, Function<GenerationData, T> function) {
		final T provider = function.apply(this);
		generator.addProvider(shouldRun, provider);
		return provider;
	}
}
