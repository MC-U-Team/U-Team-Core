package info.u_team.u_team_core.intern.loot;

import com.google.gson.*;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.ILootCondition;

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
	
	public static class Serializer extends LootFunction.Serializer<SetTileEntityNBTLootFunction> {
		
		public Serializer() {
			super(new ResourceLocation(UCoreMain.MODID, "set_tileentity_nbt"), SetTileEntityNBTLootFunction.class);
		}
		
		@Override
		public SetTileEntityNBTLootFunction deserialize(JsonObject object, JsonDeserializationContext deserializationContext, ILootCondition[] conditions) {
			return new SetTileEntityNBTLootFunction(conditions);
		}
	}
}