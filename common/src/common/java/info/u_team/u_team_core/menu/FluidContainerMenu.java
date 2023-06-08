package info.u_team.u_team_core.menu;

import info.u_team.u_team_core.util.ServiceUtil;
import net.minecraft.world.inventory.MenuType;

/**
 * Unstable api! <br>
 * Adds a management system for fluids in menus like items.
 *
 * @author HyCraftHD
 */
public abstract class FluidContainerMenu extends UAbstractContainerMenu {
	
	private final FluidContainerDelegator delegator = FluidContainerDelegator.Factory.INSTANCE.create(this);
	
	/**
	 * Creates a container menu. Must be implemented by a sub class to be used.
	 *
	 * @param menuType Menu type
	 * @param containerId Container id
	 */
	protected FluidContainerMenu(MenuType<?> menuType, int containerId) {
		super(menuType, containerId);
	}
	
	/**
	 * Sends all menu data to the client.
	 */
	@Override
	public void sendAllDataToRemote() {
		delegator.sendAllDataToRemote();
		super.sendAllDataToRemote();
	}
	
	/**
	 * Broadcast changed data
	 */
	@Override
	public void broadcastChanges() {
		delegator.broadcastChanges();
		super.broadcastChanges();
	}
	
	/**
	 * Broadcast all data
	 */
	@Override
	public void broadcastFullState() {
		delegator.broadcastFullState();
		super.broadcastFullState();
	}
	
	public FluidContainerDelegator getDelegator() {
		return delegator;
	}
	
	public static interface FluidContainerDelegator {
		
		void sendAllDataToRemote();
		
		void broadcastChanges();
		
		void broadcastFullState();
		
		interface Factory {
			
			Factory INSTANCE = ServiceUtil.loadOne(Factory.class);
			
			FluidContainerDelegator create(FluidContainerMenu menu);
		}
	}
}
