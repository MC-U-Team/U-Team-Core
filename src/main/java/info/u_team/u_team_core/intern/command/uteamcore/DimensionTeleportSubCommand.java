package info.u_team.u_team_core.intern.command.uteamcore;

import java.util.Collection;

import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.builder.ArgumentBuilder;

import info.u_team.u_team_core.util.world.WorldUtil;
import net.minecraft.command.*;
import net.minecraft.command.arguments.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.DimensionType;

public class DimensionTeleportSubCommand {
	
	public static ArgumentBuilder<CommandSource, ?> register() {
		return Commands.literal("dimteleport") //
				.requires(source -> source.hasPermissionLevel(2)) //
				.then(Commands.argument("targets", EntityArgument.entities()) //
						.then(Commands.argument("dimension", DimensionArgument.getDimension()) //
								.executes(context -> {
									return execute(context.getSource(), EntityArgument.getEntities(context, "targets"), DimensionArgument.func_212592_a(context, "dimension"));
								}) //
								.then(Commands.argument("location", Vec3Argument.vec3()) //
										.executes(context -> {
											return execute(context.getSource(), EntityArgument.getEntities(context, "targets"), DimensionArgument.func_212592_a(context, "dimension"), Vec3Argument.getVec3(context, "location"));
										}) //
										.then(Commands.argument("yaw", FloatArgumentType.floatArg(0, 360)) //
												.then(Commands.argument("pitch", FloatArgumentType.floatArg(-90, 90)) //
														.executes(context -> {
															return execute(context.getSource(), EntityArgument.getEntities(context, "targets"), DimensionArgument.func_212592_a(context, "dimension"), Vec3Argument.getVec3(context, "location"), FloatArgumentType.getFloat(context, "yaw"), FloatArgumentType.getFloat(context, "pitch"));
														}))))));
	}
	
	private static int execute(CommandSource source, Collection<? extends Entity> targets, DimensionType type) {
		targets.forEach(entity -> WorldUtil.teleportEntity(entity, type, entity.getPositionVector()));
		return 0;
	}
	
	private static int execute(CommandSource source, Collection<? extends Entity> targets, DimensionType type, Vec3d pos) {
		targets.forEach(entity -> WorldUtil.teleportEntity(entity, type, pos));
		return 0;
	}
	
	private static int execute(CommandSource source, Collection<? extends Entity> targets, DimensionType type, Vec3d pos, float yaw, float pitch) {
		targets.forEach(entity -> WorldUtil.teleportEntity(entity, type, pos.getX(), pos.getY(), pos.getZ(), yaw, pitch));
		return 0;
	}
	
}
