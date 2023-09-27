package info.u_team.u_team_core.intern.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import info.u_team.u_team_core.blockentity.UBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.CommonListenerCookie;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;

@Mixin(ClientPacketListener.class)
abstract class ClientPacketListenerMixin extends ClientCommonPacketListenerImpl {
	
	private ClientPacketListenerMixin(Minecraft minecraft, Connection connection, CommonListenerCookie commonListenerCookie) {
		super(minecraft, connection, commonListenerCookie);
	}
	
	@Inject(method = { "method_38542", "lambda$handleBlockEntityData$6" }, at = @At(value = "HEAD", remap = true), remap = false, cancellable = true)
	private void uteamcore$lambda$handleBlockEntityData$6(ClientboundBlockEntityDataPacket packet, BlockEntity blockEntity, CallbackInfo info) {
		if (blockEntity instanceof final UBlockEntity uBlockEntity) {
			info.cancel();
			uBlockEntity.receiveUpdatePacket(connection, packet);
		}
	}
	
}
