package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.item.USpawnEggItem;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

public class UCoreLazySpawnEggs {
	
	private static void setup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			USpawnEggItem.LAZY_EGGS.forEach(pair -> {
				final EntityType<?> type = pair.getKey().get();
				final USpawnEggItem item = pair.getValue();
				
				item.defaultType = type;
				
				DispenserBlock.registerBehavior(item, (source, stack) -> {
					final Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
					final EntityType<?> entityType = ((USpawnEggItem) stack.getItem()).getType(stack.getTag());
					entityType.spawn(source.getLevel(), stack, (Player) null, source.getPos().relative(direction), MobSpawnType.DISPENSER, direction != Direction.UP, false);
					stack.shrink(1);
					return stack;
				});
			});
		});
	}
	
	private static void loadComplete(FMLLoadCompleteEvent event) {
		event.enqueueWork(() -> {
			USpawnEggItem.LAZY_EGGS.forEach(pair -> {
				final EntityType<? extends Mob> type = (EntityType<? extends Mob>) pair.getKey().get();
				final USpawnEggItem item = pair.getValue();
				
				SpawnEggItem.BY_ID.put(type, item);
			});
		});
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(UCoreLazySpawnEggs::setup);
		bus.addListener(UCoreLazySpawnEggs::loadComplete);
	}
}
