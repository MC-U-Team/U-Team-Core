package info.u_team.u_team_core.intern.command.uteamcore;

import java.util.*;

import com.mojang.brigadier.builder.ArgumentBuilder;

import net.minecraft.command.*;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;

public class LocateBiomeSubCommand {
	
	public static ArgumentBuilder<CommandSource, ?> register() {
		return Commands.literal("locatebiome") //
				.requires(source -> source.hasPermissionLevel(2)) //
				.then(Commands.argument("biome", ResourceLocationArgument.resourceLocation()) //
						.suggests((context, builder) -> ISuggestionProvider.suggest(ForgeRegistries.BIOMES.getKeys().stream().map(ResourceLocation::toString), builder)) //
						.executes(context -> locateBiome(context.getSource(), context.getArgument("biome", ResourceLocation.class))));
	}
	
	private static int locateBiome(CommandSource source, ResourceLocation biome) {
		final ServerWorld world = source.getWorld();
		final BiomeProvider biomeProvider = world.getChunkProvider().getChunkGenerator().getBiomeProvider();
		final Random random = new Random(world.getSeed());
		
		final BlockPos pos = biomeProvider.func_225531_a_(0, world.getSeaLevel(), 0, 256, Arrays.asList(ForgeRegistries.BIOMES.getValue(biome)), random);
		
		System.out.println(pos);
		
		return 0;
	}
	
}
