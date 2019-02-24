package info.u_team.u_team_test.init;

import java.util.Collection;

import com.mojang.brigadier.builder.*;
import com.mojang.brigadier.exceptions.*;

import info.u_team.u_team_core.dimension.UModDimension;
import info.u_team.u_team_core.registry.DimensionRegistry;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.dimension.DimensionBasic;
import net.minecraft.command.*;
import net.minecraft.command.arguments.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.*;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@EventBusSubscriber(modid = TestMod.modid, bus = Bus.FORGE)
public class TestDimensions {
	
	public static final ModDimension basic = new UModDimension("basic", DimensionBasic::new);
	
	public static void construct() {
		DimensionRegistry.register(TestMod.modid, TestDimensions.class);
	}
	
	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public static void on(RegisterDimensionsEvent event) {
		if (!DimensionManager.getRegistry().func_212607_c(basic.getRegistryName())) { // How do we know when the dimension needs to be registered??
			DimensionManager.registerDimension(basic.getRegistryName(), basic, null);
		}
	}
	
	// *****************************************************************************************
	// ONLY TEST COMMAND TO TELEPORT CAUSE FORGE ONES IS BROKEN
	
	@SubscribeEvent
	public static void on(FMLServerStartingEvent event) {
		event.getCommandDispatcher().register(LiteralArgumentBuilder.<CommandSource> literal("test").then(Teleport.register()));
	}
	
	public static class Teleport {
		
		private static final SimpleCommandExceptionType NO_ENTITIES = new SimpleCommandExceptionType(new TextComponentTranslation("commands.forge.setdim.invalid.entity"));
		
		static ArgumentBuilder<CommandSource, ?> register() {
			return Commands.literal("teleport").requires(cs -> cs.hasPermissionLevel(2)) // permission
					.then(Commands.argument("targets", EntityArgument.multipleEntities()).then(Commands.argument("dim", DimensionArgument.func_212595_a()).then(Commands.argument("pos", BlockPosArgument.blockPos()).executes(ctx -> execute(ctx.getSource(), EntityArgument.getEntitiesAllowingNone(ctx, "targets"), DimensionArgument.func_212592_a(ctx, "dim"), BlockPosArgument.getBlockPos(ctx, "pos")))).executes(ctx -> execute(ctx.getSource(), EntityArgument.getEntitiesAllowingNone(ctx, "targets"), DimensionArgument.func_212592_a(ctx, "dim"), new BlockPos(ctx.getSource().getPos())))));
		}
		
		private static int execute(CommandSource sender, Collection<? extends Entity> entities, DimensionType dim, BlockPos pos) throws CommandSyntaxException {
			entities.removeIf(Teleport::checkEntity);
			if (entities.isEmpty())
				throw NO_ENTITIES.create();
			
			final ITeleporter teleporter = new CommandTeleporter(pos);
			entities.stream().filter(e -> e.dimension == dim).forEach(e -> sender.sendFeedback(new TextComponentTranslation("commands.forge.setdim.invalid.nochange", e.getDisplayName(), dim), true));
			entities.stream().filter(e -> e.dimension != dim).forEach(e -> e.changeDimension(dim, teleporter));
			
			return 0;
		}
		
		private static boolean checkEntity(Entity entity) {
			return false;
		}
		
		private static class CommandTeleporter implements ITeleporter {
			
			private final BlockPos targetPos;
			
			private CommandTeleporter(BlockPos targetPos) {
				this.targetPos = targetPos;
			}
			
			@Override
			public void placeEntity(World world, Entity entity, float yaw) {
				entity.moveToBlockPosAndAngles(targetPos, yaw, entity.rotationPitch);
			}
		}
	}
}
