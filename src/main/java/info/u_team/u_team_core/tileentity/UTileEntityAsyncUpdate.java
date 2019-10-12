/*-*****************************************************************************
 * Copyright 2018 U-Team
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/

package info.u_team.u_team_core.tileentity;

import java.util.concurrent.*;

import info.u_team.u_team_core.UCoreConstants;
import net.minecraft.util.ITickable;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * TileEntity API<br>
 * -> Async Update TileEntity
 * 
 * @date 25.12.2018
 * @author HyCraftHD
 */
@Deprecated
@EventBusSubscriber(modid = UCoreConstants.MODID)
public abstract class UTileEntityAsyncUpdate extends UTileEntity implements ITickable {
	
	private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
	
	private ScheduledFuture<?> future;
	
	@Override
	public void invalidate() {
		super.invalidate();
		stopTicking();
	}
	
	@Override
	public void onChunkUnload() {
		super.onChunkUnload();
		stopTicking();
	}
	
	@Override
	public void onLoad() {
		super.onLoad();
		startTicking();
	}
	
	@SubscribeEvent
	public static void on(WorldEvent.Unload event) {
		event.getWorld().loadedTileEntityList.stream().filter(tileentity -> tileentity instanceof UTileEntityAsyncUpdate).map(tileentity -> (UTileEntityAsyncUpdate) tileentity).forEach(tileentity -> {
			tileentity.stopTicking();
		});
	}
	
	private void startTicking() {
		if (future == null) {
			future = executor.scheduleAtFixedRate(() -> {
				updateAsync();
			}, getUpdateRate(), getUpdateRate(), TimeUnit.MILLISECONDS);
		}
	}
	
	private void stopTicking() {
		if (future != null) {
			future.cancel(true);
			future = null;
		}
	}
	
	public int getUpdateRate() {
		return 50;
	}
	
	public abstract void updateAsync();
	
}
