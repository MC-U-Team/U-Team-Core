package info.u_team.u_team_core.intern.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

import info.u_team.u_team_core.intern.init.UCoreLootFunctions;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class SetBlockEntityNBTLootFunction extends LootItemConditionalFunction {
	
	private SetBlockEntityNBTLootFunction(LootItemCondition[] conditions) {
		super(conditions);
	}
	
	@Override
	public ItemStack run(ItemStack stack, LootContext context) {
		if (context.hasParam(LootContextParams.BLOCK_ENTITY)) {
			final var compound = new CompoundTag();
			final var blockEntity = context.getParamOrNull(LootContextParams.BLOCK_ENTITY);
			if (blockEntity instanceof UTileEntity uBlockEntity) {
				uBlockEntity.writeNBT(compound);
			} else {
				blockEntity.save(compound);
			}
			if (!compound.isEmpty()) {
				stack.addTagElement("BlockEntityTag", compound);
			}
		}
		return stack;
	}
	
	public static LootItemConditionalFunction.Builder<?> builder() {
		return simpleBuilder((conditions) -> new SetBlockEntityNBTLootFunction(conditions));
	}
	
	@Override
	public LootItemFunctionType getType() {
		return UCoreLootFunctions.SET_BLOCKENTITY_NBT;
	}
	
	public static class Serializer extends LootItemConditionalFunction.Serializer<SetBlockEntityNBTLootFunction> {
		
		@Override
		public SetBlockEntityNBTLootFunction deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootItemCondition[] conditions) {
			return new SetBlockEntityNBTLootFunction(conditions);
		}
	}
}
