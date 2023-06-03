package info.u_team.u_team_core.intern.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import info.u_team.u_team_core.blockentity.UBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.chunk.LevelChunk;

@Mixin(LevelChunk.class)
abstract class LevelChunkMixin {
	
	@Inject(method = { "method_31716", "lambda$replaceWithPacketData$3" }, locals = LocalCapture.CAPTURE_FAILEXCEPTION, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/BlockEntity;load(Lnet/minecraft/nbt/CompoundTag;)V", remap = true), remap = false, cancellable = true)
	private void uteamcore$lambda$replaceWithPacketData$3(BlockPos pos, BlockEntityType<?> type, CompoundTag tag, CallbackInfo info, BlockEntity blockEntity) {
		if (blockEntity instanceof final UBlockEntity uBlockEntity) {
			info.cancel();
			uBlockEntity.receiveUpdateTag(tag);
		}
	}
	
}
