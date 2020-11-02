package info.u_team.u_team_core.intern.loot;

import com.google.gson.*;

import info.u_team.u_team_core.intern.init.UCoreLootFunctions;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

public class SetTileEntityNBTLootFunction extends LootFunction {
	
	private SetTileEntityNBTLootFunction(ILootCondition[] conditions) {
		super(conditions);
	}
	
	public ItemStack doApply(ItemStack stack, LootContext context) {
		if (context.has(LootParameters.BLOCK_ENTITY)) {
			final CompoundNBT compound = new CompoundNBT();
			final TileEntity tileEntity = context.get(LootParameters.BLOCK_ENTITY);
			if (tileEntity instanceof UTileEntity) {
				((UTileEntity) tileEntity).writeNBT(compound);
			} else {
				tileEntity.write(compound);
			}
			if (!compound.isEmpty()) {
				stack.setTagInfo("BlockEntityTag", compound);
			}
		}
		return stack;
	}
	
	public static LootFunction.Builder<?> builder() {
		return builder((conditions) -> new SetTileEntityNBTLootFunction(conditions));
	}
	
	@Override
	public LootFunctionType getFunctionType() {
		return UCoreLootFunctions.SET_TILEENTITY_NBT;
	}
	
	public static class Serializer extends LootFunction.Serializer<SetTileEntityNBTLootFunction> {
		
		@Override
		public SetTileEntityNBTLootFunction deserialize(JsonObject object, JsonDeserializationContext deserializationContext, ILootCondition[] conditions) {
			return new SetTileEntityNBTLootFunction(conditions);
		}
	}
}
