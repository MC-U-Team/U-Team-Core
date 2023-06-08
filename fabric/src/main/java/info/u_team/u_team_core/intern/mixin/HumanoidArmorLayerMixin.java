package info.u_team.u_team_core.intern.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import info.u_team.u_team_core.item.armor.UArmorItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

@Mixin(HumanoidArmorLayer.class)
abstract class HumanoidArmorLayerMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {
	
	private HumanoidArmorLayerMixin(RenderLayerParent<T, M> renderLayerParent) {
		super(renderLayerParent);
	}
	
	@Shadow
	@Final
	private static Map<String, ResourceLocation> ARMOR_LOCATION_CACHE;
	
	@Shadow
	abstract ResourceLocation getArmorLocation(ArmorItem armorItem, boolean layer2, String suffix);
	
	@Inject(method = "renderArmorPiece", locals = LocalCapture.CAPTURE_FAILEXCEPTION, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;renderModel(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/item/ArmorItem;Lnet/minecraft/client/model/HumanoidModel;ZFFFLjava/lang/String;)V", ordinal = 2))
	private void uteamcore$renderArmorPiece(PoseStack poseStack, MultiBufferSource buffer, T entity, EquipmentSlot slot, int packedLight, M model, CallbackInfo callbackInfo, ItemStack stack, ArmorItem item, boolean layer2) {
		if (item instanceof final UArmorItem armorItem) {
			final ResourceLocation resource;
			final String texture = armorItem.resolveArmorTexture(stack, entity, slot, null);
			if (texture == null) {
				resource = getArmorLocation(armorItem, layer2, null);
			} else {
				resource = ARMOR_LOCATION_CACHE.computeIfAbsent(texture, ResourceLocation::new);
			}
			final VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.armorCutoutNoCull(resource));
			model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
		}
	}
	
	@Inject(method = "renderModel", at = @At("HEAD"), cancellable = true)
	private void uteamcore$renderArmorPiece(PoseStack poseStack, MultiBufferSource buffer, int packedLight, ArmorItem item, A model, boolean layer2, float red, float green, float blue, String armorSuffix, CallbackInfo callbackInfo) {
		if (item instanceof final UArmorItem armorItem) {
			callbackInfo.cancel();
		}
	}
}
