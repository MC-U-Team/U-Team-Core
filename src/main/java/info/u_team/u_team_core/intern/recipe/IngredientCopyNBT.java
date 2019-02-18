package info.u_team.u_team_core.intern.recipe;

import java.util.stream.Stream;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.crafting.*;

public class IngredientCopyNBT extends Ingredient {
	
	private final ItemStack stack;
	
	protected IngredientCopyNBT(ItemStack stack) {
		super(Stream.of(new Ingredient.SingleItemList(stack)));
		this.stack = stack;
	}
	
	@Override
	public boolean test(@Nullable ItemStack input) {
		if (input == null)
			return false;
		return this.stack.getItem() == input.getItem();
	}
	
	@Override
	public boolean isSimple() {
		return false;
	}
	
	public static class Serializer implements IIngredientSerializer<IngredientCopyNBT> {
		
		@Override
		public IngredientCopyNBT parse(PacketBuffer buffer) {
			return new IngredientCopyNBT(buffer.readItemStack());
		}
		
		@Override
		public IngredientCopyNBT parse(JsonObject json) {
			return new IngredientCopyNBT(CraftingHelper.getItemStack(json, true));
		}
		
		@Override
		public void write(PacketBuffer buffer, IngredientCopyNBT ingredient) {
			buffer.writeItemStack(ingredient.stack);
		}
	}
}