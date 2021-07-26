package info.u_team.u_team_core.item.tool;

import java.util.Iterator;

import com.google.common.collect.Iterators;

import net.minecraft.world.item.TieredItem;
import net.minecraftforge.fmllegacy.RegistryObject;

public class ToolSet implements Iterable<RegistryObject<? extends TieredItem>> {

	private final RegistryObject<UAxeItem> axe;
	private final RegistryObject<UHoeItem> hoe;
	private final RegistryObject<UPickaxeItem> pickaxe;
	private final RegistryObject<UShovelItem> shovel;
	private final RegistryObject<USwordItem> sword;

	public ToolSet(RegistryObject<UAxeItem> axe, RegistryObject<UHoeItem> hoe, RegistryObject<UPickaxeItem> pickaxe, RegistryObject<UShovelItem> shovel, RegistryObject<USwordItem> sword) {
		this.axe = axe;
		this.hoe = hoe;
		this.pickaxe = pickaxe;
		this.shovel = shovel;
		this.sword = sword;
	}

	public RegistryObject<UAxeItem> getAxe() {
		return axe;
	}

	public RegistryObject<UHoeItem> getHoe() {
		return hoe;
	}

	public RegistryObject<UPickaxeItem> getPickaxe() {
		return pickaxe;
	}

	public RegistryObject<UShovelItem> getShovel() {
		return shovel;
	}

	public RegistryObject<USwordItem> getSword() {
		return sword;
	}

	@Override
	public Iterator<RegistryObject<? extends TieredItem>> iterator() {
		return Iterators.forArray(axe, hoe, pickaxe, shovel, sword);
	}
}
