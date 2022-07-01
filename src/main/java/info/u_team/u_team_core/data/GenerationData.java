package info.u_team.u_team_core.data;

import java.util.function.Function;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

public record GenerationData(String modid, DataGenerator generator, ExistingFileHelper existingFileHelper) {
	
	public GenerationData(String modid, GatherDataEvent event) {
		this(modid, event.getGenerator(), event.getExistingFileHelper());
	}
	
	public void addProvider(boolean shouldRun, Function<GenerationData, DataProvider> function) {
		final DataProvider provider = function.apply(this);
		generator.addProvider(shouldRun, provider);
	}
}
