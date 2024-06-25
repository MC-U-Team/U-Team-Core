package info.u_team.u_team_core.api.item;

import net.minecraft.world.item.Tier;

/**
 * This interface extends the {@link Tier} interface for tool materials of vanilla style tools.
 *
 * @author HyCraftHD
 */
public interface ExtendedTier extends Tier {
	
	/**
	 * Returns the additional damage that is added to the {@link #getAttackDamageBonus()} per given tool.
	 *
	 * @param tools The tool
	 * @return Attack damage
	 */
	float getAttackDamage(Tools tools);
	
	/**
	 * Returns the attack speed per given tool.
	 *
	 * @param tools The tool
	 * @return Attack speed
	 */
	float getAttackSpeed(Tools tools);
	
	/**
	 * Contains all vanilla tools.
	 *
	 * @author HyCraftHD
	 */
	public static enum Tools {
		
		AXE,
		HOE,
		PICKAXE,
		SHOVEL,
		SWORD,
	}
	
}
