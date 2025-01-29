package info.u_team.u_team_test.test_multiloader.init;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.api.event.ClientEvents;
import info.u_team.u_team_core.util.KeyMappingUtil;
import info.u_team.u_team_test.test_multiloader.network.TestPayload.TestMessage;
import info.u_team.u_team_test.test_multiloader.screen.ButtonTestScreen;
import info.u_team.u_team_test.test_multiloader.screen.ButtonTestScreenVanilla;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;

public class TestMultiLoaderClientEvents {
	
	private static void onEndClientTick(Minecraft minecraft) {
		while (TestMultiLoaderKeyMappings.TEST_NETWORK_EXPLICIT_C2S.get().consumeClick()) {
			TestMultiLoaderNetwork.TEST_CLIENT_TO_SERVER_MESSAGE.sendToServer(new TestMessage("Hello server"));
		}
	}
	
	private static boolean onScreenAfterKeyPressed(Screen screen, int keyCode, int scanCode, int modifiers) {
		if (KeyMappingUtil.matches(TestMultiLoaderKeyMappings.TEST_GUI_SCREEN.get(), InputConstants.getKey(keyCode, scanCode))) {
			Minecraft.getInstance().setScreen(new ButtonTestScreen());
			return true;
		}
		if (KeyMappingUtil.matches(TestMultiLoaderKeyMappings.TEST_VANILLA_GUI_SCREEN.get(), InputConstants.getKey(keyCode, scanCode))) {
			Minecraft.getInstance().setScreen(new ButtonTestScreenVanilla());
			return true;
		}
		return false;
	}
	
	private static boolean onRenderBlockOutline(LevelRenderer levelRenderer, Camera camera, BlockHitResult target, DeltaTracker deltaTracker, PoseStack poseStack, MultiBufferSource bufferSource) {
		final Player player = Minecraft.getInstance().player;
		final Level level = player.level();
		final BlockPos pos = target.getBlockPos();
		final Vec3 cameraPos = camera.getPosition();
		if (level.getBlockState(pos).getBlock() == TestMultiLoaderBlocks.TEST.get() && player.isShiftKeyDown()) {
			LevelRenderer.renderVoxelShape(poseStack, bufferSource.getBuffer(RenderType.lines()), Shapes.create(AABB.unitCubeFromLowerCorner(Vec3.ZERO).inflate(0.005)), pos.getX() - cameraPos.x(), pos.getY() - cameraPos.y(), pos.getZ() - cameraPos.z(), 1, 0, 0, 1, false);
			return false;
		}
		return true;
	}
	
	static void register() {
		ClientEvents.registerEndClientTick(TestMultiLoaderClientEvents::onEndClientTick);
		ClientEvents.registerScreenAfterKeyPressed(TestMultiLoaderClientEvents::onScreenAfterKeyPressed);
		ClientEvents.registerRenderBlockOutline(TestMultiLoaderClientEvents::onRenderBlockOutline);
	}
	
}
