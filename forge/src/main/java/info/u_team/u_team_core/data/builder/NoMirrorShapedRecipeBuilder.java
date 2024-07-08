package info.u_team.u_team_core.data.builder;

import com.google.gson.JsonElement;

import info.u_team.u_team_core.intern.init.UCoreRecipeSerializers;
import net.minecraft.advancements.Advancement.Builder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class NoMirrorShapedRecipeBuilder extends ShapedRecipeBuilder {
	
	public NoMirrorShapedRecipeBuilder(RecipeCategory cateogry, ItemLike item, int count) {
		super(cateogry, item, count);
	}
	
	public static NoMirrorShapedRecipeBuilder noMirrorShapedRecipe(RecipeCategory cateogry, ItemLike item) {
		return noMirrorShapedRecipe(cateogry, item, 1);
	}
	
	public static NoMirrorShapedRecipeBuilder noMirrorShapedRecipe(RecipeCategory cateogry, ItemLike item, int count) {
		return new NoMirrorShapedRecipeBuilder(cateogry, item, count);
	}
	
	@Override
	public void save(RecipeOutput output, ResourceLocation location) {
		super.save(new RecipeOutput() {
			
			@Override
			public Builder advancement() {
				return output.advancement();
			}
			
			@Override
			public void accept(ResourceLocation id, Recipe<?> recipe, ResourceLocation advancementId, JsonElement advancement) {
				// TODO maybe rework
				output.accept(id, new Recipe<>() {
					
					@Override
					public boolean matches(RecipeInput input, Level level) {
						return false;
					}
					
					@Override
					public ItemStack assemble(RecipeInput input, HolderLookup.Provider registries) {
						return null;
					}
					
					@Override
					public boolean canCraftInDimensions(int width, int height) {
						return false;
					}
					
					@Override
					public ItemStack getResultItem(HolderLookup.Provider registries) {
						return null;
					}
					
					@Override
					public RecipeSerializer<?> getSerializer() {
						return (RecipeSerializer<?>) UCoreRecipeSerializers.NO_MIRROR_SHAPED;
					}
					
					@Override
					public RecipeType<?> getType() {
						return null;
					}
					
				}, advancementId, advancement);
			}
			
			@Override
			public HolderLookup.Provider registry() {
				return output.registry();
			}
		}, location);
	}
}
