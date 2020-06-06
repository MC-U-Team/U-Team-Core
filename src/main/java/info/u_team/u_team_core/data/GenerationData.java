package info.u_team.u_team_core.data;

import java.util.function.Function;

import net.minecraft.data.*;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public class GenerationData {
	
	private final String modid;
	private final DataGenerator generator;
	private final ExistingFileHelper existingFileHelper;
	
	public GenerationData(String modid, GatherDataEvent event) {
		this(modid, event.getGenerator(), event.getExistingFileHelper());
	}
	
	public GenerationData(String modid, DataGenerator generator, ExistingFileHelper existingFileHelper) {
		this.modid = modid;
		this.generator = generator;
		this.existingFileHelper = existingFileHelper;
	}
	
	public String getModid() {
		return modid;
	}
	
	public DataGenerator getGenerator() {
		return generator;
	}
	
	public ExistingFileHelper getExistingFileHelper() {
		return existingFileHelper;
	}
	
	public void addProvider(Function<GenerationData, IDataProvider> function) {
		generator.addProvider(function.apply(this));
	}
	
}
