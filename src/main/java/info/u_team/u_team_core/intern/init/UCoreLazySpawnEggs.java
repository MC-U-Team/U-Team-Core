package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.item.USpawnEggItem;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.*;

public class UCoreLazySpawnEggs {
	
	private static void setup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			USpawnEggItem.LAZY_EGGS.forEach(pair -> {
				final EntityType<?> type = pair.getKey().getValue();
				final USpawnEggItem item = pair.getValue();
				
				item.typeIn = type;
				
				DispenserBlock.registerDispenseBehavior(item, (source, stack) -> {
					final Direction direction = source.getBlockState().get(DispenserBlock.FACING);
					final EntityType<?> entityType = ((USpawnEggItem) stack.getItem()).getType(stack.getTag());
					entityType.spawn(source.getWorld(), stack, (PlayerEntity) null, source.getBlockPos().offset(direction), SpawnReason.DISPENSER, direction != Direction.UP, false);
					stack.shrink(1);
					return stack;
				});
			});
		});
	}
	
	private static void colorItem(ColorHandlerEvent.Item event) {
		USpawnEggItem.LAZY_EGGS.forEach(pair -> {
			final USpawnEggItem item = pair.getValue();
			
			event.getItemColors().register((stack, color) -> {
				return item.getColor(color);
			}, item);
		});
	}
	
	private static void loadComplete(FMLLoadCompleteEvent event) {
		event.enqueueWork(() -> {
			USpawnEggItem.LAZY_EGGS.forEach(pair -> {
				final EntityType<?> type = pair.getKey().getValue();
				final USpawnEggItem item = pair.getValue();
				
				USpawnEggItem.EGGS.put(type, item);
			});
		});
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(UCoreLazySpawnEggs::setup);
		bus.addListener(UCoreLazySpawnEggs::colorItem);
		bus.addListener(UCoreLazySpawnEggs::loadComplete);
	}
	
}

