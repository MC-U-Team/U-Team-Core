package info.u_team.u_team_core.intern.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

import info.u_team.u_team_core.intern.init.UCoreLootFunctions;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;

public class SetTileEntityNBTLootFunction extends LootItemConditionalFunction {
	
	private SetTileEntityNBTLootFunction(LootItemCondition[] conditions) {
		super(conditions);
	}
	
	@Override
	public ItemStack run(ItemStack stack, LootContext context) {
		if (context.hasParam(LootContextParams.BLOCK_ENTITY)) {
			final CompoundTag compound = new CompoundTag();
			final BlockEntity tileEntity = context.getParamOrNull(LootContextParams.BLOCK_ENTITY);
			if (tileEntity instanceof UTileEntity) {
				((UTileEntity) tileEntity).writeNBT(compound);
			} else {
				tileEntity.save(compound);
			}
			if (!compound.isEmpty()) {
				stack.addTagElement("BlockEntityTag", compound);
			}
		}
		return stack;
	}
	
	public static LootItemConditionalFunction.Builder<?> builder() {
		return simpleBuilder((conditions) -> new SetTileEntityNBTLootFunction(conditions));
	}
	
	@Override
	public LootItemFunctionType getType() {
		return UCoreLootFunctions.SET_TILEENTITY_NBT;
	}
	
	public static class Serializer extends LootItemConditionalFunction.Serializer<SetTileEntityNBTLootFunction> {
		
		@Override
		public SetTileEntityNBTLootFunction deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootItemCondition[] conditions) {
			return new SetTileEntityNBTLootFunction(conditions);
		}
	}
}
