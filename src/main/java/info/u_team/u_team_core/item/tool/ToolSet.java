package info.u_team.u_team_core.item.tool;

import info.u_team.u_team_core.api.registry.IUArrayRegistryType;
import net.minecraft.item.Item;

public class ToolSet implements IUArrayRegistryType<Item> {
	
	private final UAxeItem axe;
	private final UHoeItem hoe;
	private final UPickaxeItem pickaxe;
	private final UShovelItem shovel;
	private final USwordItem sword;
	
	public ToolSet(UAxeItem axe, UHoeItem hoe, UPickaxeItem pickaxe, UShovelItem shovel, USwordItem sword) {
		this.axe = axe;
		this.hoe = hoe;
		this.pickaxe = pickaxe;
		this.shovel = shovel;
		this.sword = sword;
	}
	
	@Override
	public Item[] getArray() {
		return new Item[] { axe, hoe, pickaxe, shovel, sword };
	}
	
	public UAxeItem getAxe() {
		return axe;
	}
	
	public UHoeItem getHoe() {
		return hoe;
	}
	
	public UPickaxeItem getPickaxe() {
		return pickaxe;
	}
	
	public UShovelItem getShovel() {
		return shovel;
	}
	
	public USwordItem getSword() {
		return sword;
	}
	
}
