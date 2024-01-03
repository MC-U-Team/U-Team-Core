package info.u_team.u_team_core.intern.mixin;

import org.spongepowered.asm.mixin.Mixin;

import info.u_team.u_team_core.menutype.UMenuType;
import info.u_team.u_team_core.menutype.UMenuType.DataMenuSupplier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.neoforged.neoforge.network.IContainerFactory;

@Mixin(UMenuType.DataMenuSupplier.class)
abstract interface DataMenuSupplierMixin<T extends AbstractContainerMenu> extends DataMenuSupplier<T>, IContainerFactory<T> {
	
	@Override
	default T create(int containerId, Inventory playerInventory) {
		return DataMenuSupplier.super.create(containerId, playerInventory);
	}
}
