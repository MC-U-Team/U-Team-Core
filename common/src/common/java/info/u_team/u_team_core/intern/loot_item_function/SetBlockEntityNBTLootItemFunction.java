package info.u_team.u_team_core.intern.loot_item_function;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

import info.u_team.u_team_core.intern.init.UCoreLootItemFunctions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class SetBlockEntityNBTLootItemFunction extends LootItemConditionalFunction {
	
	public static LootItemConditionalFunction.Builder<?> builder() {
		return simpleBuilder(SetBlockEntityNBTLootItemFunction::new);
	}
	
	private SetBlockEntityNBTLootItemFunction(LootItemCondition[] conditions) {
		super(conditions);
	}
	
	@Override
	public ItemStack run(ItemStack stack, LootContext context) {
		if (context.hasParam(LootContextParams.BLOCK_ENTITY)) {
			final BlockEntity blockEntity = context.getParam(LootContextParams.BLOCK_ENTITY);
			blockEntity.saveToItem(stack);
		}
		return stack;
	}
	
	@Override
	public LootItemFunctionType getType() {
		return UCoreLootItemFunctions.SET_BLOCKENTITY_NBT.get();
	}
	
	public static class Serializer extends LootItemConditionalFunction.Serializer<SetBlockEntityNBTLootItemFunction> {
		
		@Override
		public SetBlockEntityNBTLootItemFunction deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootItemCondition[] conditions) {
			return new SetBlockEntityNBTLootItemFunction(conditions);
		}
	}
}
