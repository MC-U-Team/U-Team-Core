package info.u_team.u_team_core.intern.mixin;

import org.spongepowered.asm.mixin.Mixin;

import info.u_team.u_team_core.screen.UContainerMenuScreen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

@Mixin(UContainerMenuScreen.class)
abstract class UContainerMenuScreenMixin<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
	
	private UContainerMenuScreenMixin(T menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
	}
	
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		setDragging(false);
		getChildAt(mouseX, mouseY).ifPresent(listener -> listener.mouseReleased(mouseX, mouseY, button));
		return super.mouseReleased(mouseX, mouseY, button);
	}
	
}
