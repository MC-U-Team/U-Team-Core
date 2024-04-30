package info.u_team.u_team_core.intern.loot_item_function;

import java.util.List;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import info.u_team.u_team_core.intern.init.UCoreLootItemFunctions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class SetBlockEntityNBTLootItemFunction extends LootItemConditionalFunction {
	
	public static final MapCodec<SetBlockEntityNBTLootItemFunction> CODEC = RecordCodecBuilder.mapCodec(instance -> {
		return commonFields(instance).apply(instance, SetBlockEntityNBTLootItemFunction::new);
	});
	
	public static LootItemConditionalFunction.Builder<?> builder() {
		return simpleBuilder(SetBlockEntityNBTLootItemFunction::new);
	}
	
	private SetBlockEntityNBTLootItemFunction(List<LootItemCondition> conditions) {
		super(conditions);
	}
	
	@Override
	public ItemStack run(ItemStack stack, LootContext context) {
		if (context.hasParam(LootContextParams.BLOCK_ENTITY)) {
			final BlockEntity blockEntity = context.getParam(LootContextParams.BLOCK_ENTITY);
			blockEntity.saveToItem(stack, blockEntity.getLevel().registryAccess());
		}
		return stack;
	}
	
	@Override
	public LootItemFunctionType<SetBlockEntityNBTLootItemFunction> getType() {
		return UCoreLootItemFunctions.SET_BLOCKENTITY_NBT.get();
	}
}
