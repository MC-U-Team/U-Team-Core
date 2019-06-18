package info.u_team.u_team_core.api;

import info.u_team.u_team_core.item.tool.UToolMaterial;
import net.minecraft.item.IItemTier;

/**
 * This interface extends the {@link IItemTier} interface for tool materials.
 * 
 * @author HyCraftHD
 *
 */
public interface IToolMaterial extends IItemTier {
	
	/**
	 * Returns the additional damage that is added to the {@link #getAttackDamage()} per given tool.
	 * 
	 * @param tools The tool
	 * @return Additional damage
	 */
	int getAdditionalDamage(Tools tools);
	
	/**
	 * Returns the attack speed per given tool.
	 * 
	 * @param tools The tool
	 * @return Attack speed
	 */
	float getAttackSpeed(Tools tools);
	
	/**
	 * Just a little helper enumeration with all vanilla tools.
	 * 
	 * @author HyCraftHD
	 *
	 */
	public static enum Tools {
		AXE(0),
		HOE(1),
		PICKAXE(2),
		SPADE(3),
		SWORD(4);
		
		private final int index;
		
		private Tools(int index) {
			this.index = index;
		}
		
		/**
		 * Returns the index. Only used in {@link UToolMaterial} for simple arrays.
		 * 
		 * @return
		 */
		public int getIndex() {
			return index;
		}
	}
	
}
