package info.u_team.u_team_core.intern.mixin;

import org.spongepowered.asm.mixin.Mixin;

import info.u_team.u_team_core.blockentity.UBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(UBlockEntity.class)
abstract class UBlockEntityMixin extends BlockEntity {
	
	private UBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}
	
	@Override
	public void handleUpdateTag(CompoundTag tag) {
		((UBlockEntity) (Object) (this)).receiveUpdateTag(tag);
	}
	
	@Override
	public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket packet) {
		((UBlockEntity) (Object) (this)).receiveUpdatePacket(connection, packet);
	}
}
