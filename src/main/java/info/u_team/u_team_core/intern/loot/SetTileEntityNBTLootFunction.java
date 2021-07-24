package info.u_team.u_team_core.intern.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

import info.u_team.u_team_core.intern.init.UCoreLootFunctions;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

public class SetTileEntityNBTLootFunction extends LootFunction {
	
	private SetTileEntityNBTLootFunction(ILootCondition[] conditions) {
		super(conditions);
	}
	
	@Override
	public ItemStack run(ItemStack stack, LootContext context) {
		if (context.hasParam(LootParameters.BLOCK_ENTITY)) {
			final CompoundNBT compound = new CompoundNBT();
			final TileEntity tileEntity = context.getParamOrNull(LootParameters.BLOCK_ENTITY);
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
	
	public static LootFunction.Builder<?> builder() {
		return simpleBuilder((conditions) -> new SetTileEntityNBTLootFunction(conditions));
	}
	
	@Override
	public LootFunctionType getType() {
		return UCoreLootFunctions.SET_TILEENTITY_NBT;
	}
	
	public static class Serializer extends LootFunction.Serializer<SetTileEntityNBTLootFunction> {
		
		@Override
		public SetTileEntityNBTLootFunction deserialize(JsonObject object, JsonDeserializationContext deserializationContext, ILootCondition[] conditions) {
			return new SetTileEntityNBTLootFunction(conditions);
		}
	}
}
