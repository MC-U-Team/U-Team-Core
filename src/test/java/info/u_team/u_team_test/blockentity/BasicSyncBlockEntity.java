package info.u_team.u_team_test.blockentity;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import info.u_team.u_team_core.blockentity.UBlockEntity;
import info.u_team.u_team_test.init.TestBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

public class BasicSyncBlockEntity extends UBlockEntity {
	
	private static final Logger LOGGER = LogUtils.getLogger();
	
	public BasicSyncBlockEntity(BlockPos pos, BlockState state) {
		super(TestBlockEntityTypes.BASIC_SYNC.get(), pos, state);
	}
	
	@Override
	public void sendChunkLoadData(CompoundTag tag) {
		tag.putString("data", "This is chunk load data");
		
		logMethod(tag);
	}
	
	@Override
	public void handleChunkLoadData(CompoundTag tag) {
		logMethod(tag);
	}
	
	private void logMethod(Object data) {
		final String marker = StringUtils.repeat('#', 70);
		final String methodName = StackWalker.getInstance().walk(frames -> frames.skip(1).findFirst().map(StackWalker.StackFrame::getMethodName)).get();
		final String side = hasLevel() ? (level.isClientSide() ? "client" : "server") : "unknown";
		
		LOGGER.info(marker);
		LOGGER.info("# Method '{}' was called", methodName);
		LOGGER.info("# Side is '{}'", side);
		if (data != null) {
			LOGGER.info("# Data is '{}'", data.toString());
		}
		LOGGER.info(marker);
	}
}
