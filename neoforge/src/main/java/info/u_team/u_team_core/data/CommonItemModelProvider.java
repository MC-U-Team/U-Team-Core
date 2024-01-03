package info.u_team.u_team_core.data;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.common.collect.Streams;

import info.u_team.u_team_core.util.RegistryUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile.UncheckedModelFile;

public abstract class CommonItemModelProvider extends ItemModelProvider implements CommonDataProvider.NoParam {
	
	private final GenerationData generationData;
	
	public CommonItemModelProvider(GenerationData generationData) {
		super(generationData.output(), generationData.modid(), generationData.existingFileHelper());
		this.generationData = generationData;
	}
	
	@Override
	public GenerationData getGenerationData() {
		return generationData;
	}
	
	@Override
	protected final void registerModels() {
		register(null);
	}
	
	@Override
	public String getName() {
		return "Item-Model";
	}
	
	// Item model methods
	
	protected void simpleGenerated(ItemLike provider) {
		simpleParent(provider, "item/generated");
	}
	
	protected void simpleHandheld(ItemLike provider) {
		simpleParent(provider, "item/handheld");
	}
	
	protected void spawnEgg(ItemLike provider) {
		withExistingParent(RegistryUtil.getBuiltInRegistry(Registries.ITEM).getKey(provider.asItem()).getPath(), "item/template_spawn_egg");
	}
	
	protected void simpleParent(ItemLike provider, final String parent) {
		final String registryPath = RegistryUtil.getBuiltInRegistry(Registries.ITEM).getKey(provider.asItem()).getPath();
		getBuilder(registryPath).parent(new UncheckedModelFile(parent)).texture("layer0", "item/" + registryPath);
	}
	
	protected void simpleBlock(Block block) {
		final ResourceLocation registryName = RegistryUtil.getBuiltInRegistry(Registries.BLOCK).getKey(block);
		getBuilder(registryName.getPath()).parent(new UncheckedModelFile(new ResourceLocation(registryName.getNamespace(), "block/" + registryName.getPath())));
	}
	
	// Utility methods
	protected void iterateItems(Iterable<? extends Supplier<? extends Item>> iterable, Consumer<ItemLike> item) {
		Streams.stream(iterable).map(Supplier::get).forEach(item);
	}
	
	protected void iterateBlocks(Iterable<? extends Supplier<? extends Block>> iterable, Consumer<ItemLike> item) {
		Streams.stream(iterable).map(Supplier::get).forEach(item);
	}
	
	protected String getPath(ItemLike provider) {
		return RegistryUtil.getBuiltInRegistry(Registries.ITEM).getKey(provider.asItem()).getPath();
	}
	
}
