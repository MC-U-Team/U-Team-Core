package info.u_team.u_team_core.api.event;

import java.util.List;

import info.u_team.u_team_core.util.ServiceUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public interface ClientEvents {
	
	static void registerStartClientTick(StartClientTick event) {
		Handler.INSTANCE.registerStartClientTick(event);
	}
	
	static void registerEndClientTick(EndClientTick event) {
		Handler.INSTANCE.registerEndClientTick(event);
	}
	
	static void registerStartClientLevelTick(StartClientLevelTick event) {
		Handler.INSTANCE.registerStartClientLevelTick(event);
	}
	
	static void registerEndClientLevelTick(EndClientLevelTick event) {
		Handler.INSTANCE.registerEndClientLevelTick(event);
	}
	
	static void registerScreenAfterKeyPressed(ScreenAfterKeyPressed event) {
		Handler.INSTANCE.registerScreenAfterKeyPressed(event);
	}
	
	static void registerItemTooltip(ItemTooltip event) {
		Handler.INSTANCE.registerItemTooltip(event);
	}
	
	interface Handler {
		
		Handler INSTANCE = ServiceUtil.loadOne(Handler.class);
		
		void registerStartClientTick(StartClientTick event);
		
		void registerEndClientTick(EndClientTick event);
		
		void registerStartClientLevelTick(StartClientLevelTick event);
		
		void registerEndClientLevelTick(EndClientLevelTick event);
		
		void registerScreenAfterKeyPressed(ScreenAfterKeyPressed event);
		
		void registerItemTooltip(ItemTooltip event);
	}
	
	@FunctionalInterface
	interface StartClientTick {
		
		void onStartTick(Minecraft minecraft);
	}
	
	@FunctionalInterface
	interface EndClientTick {
		
		void onEndTick(Minecraft minecraft);
	}
	
	@FunctionalInterface
	interface StartClientLevelTick {
		
		void onStartTick(ClientLevel level);
	}
	
	@FunctionalInterface
	interface EndClientLevelTick {
		
		void onEndTick(ClientLevel level);
	}
	
	@FunctionalInterface
	interface ScreenAfterKeyPressed {
		
		boolean onKeyPressed(Screen screen, int keyCode, int scanCode, int modifiers);
	}
	
	@FunctionalInterface
	interface ItemTooltip {
		
		void onTooltip(ItemStack stack, TooltipContext context, TooltipFlag flag, List<Component> lines);
	}
	
}
