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
	
	private int counter;
	
	public BasicSyncBlockEntity(BlockPos pos, BlockState state) {
		super(TestBlockEntityTypes.BASIC_SYNC.get(), pos, state);
	}
	
	public void triggerCounter() {
		counter++;
		setChanged();
		sendChangesToClient();
	}
	
	public int getCounter() {
		return counter;
	}
	
	@Override
	public void saveNBT(CompoundTag tag) {
		tag.putInt("counter", counter);
	}
	
	@Override
	public void loadNBT(CompoundTag tag) {
		counter = tag.getInt("counter");
	}
	
	@Override
	public void sendChunkLoadData(CompoundTag tag) {
		tag.putInt("chunk-val", counter);
		tag.putString("info", "Chunk load data");
		
		logMethod(tag);
	}
	
	@Override
	public void handleChunkLoadData(CompoundTag tag) {
		counter = tag.getInt("chunk-val");
		
		logMethod(tag);
	}
	
	@Override
	public void sendUpdateStateData(CompoundTag tag) {
		tag.putInt("update-val", counter);
		tag.putString("info", "Update data");
		
		logMethod(tag);
	}
	
	@Override
	public void handleUpdateStateData(CompoundTag tag) {
		counter = tag.getInt("update-val");
		
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
