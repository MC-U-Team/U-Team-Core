package info.u_team.u_team_core.intern.command.uteamcore;

import java.util.Random;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.*;

import info.u_team.u_team_core.util.MathUtil;
import net.minecraft.command.*;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.*;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.*;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;

public class LocateBiomeSubCommand {
	
	private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.uteamcore.locatebiome.failed"));
	
	public static ArgumentBuilder<CommandSource, ?> register() {
		return Commands.literal("locatebiome") //
				.requires(source -> source.hasPermissionLevel(2)) //
				.then(Commands.argument("biome", ResourceLocationArgument.resourceLocation()) //
						.suggests((context, builder) -> ISuggestionProvider.suggest(ForgeRegistries.BIOMES.getKeys().stream().map(ResourceLocation::toString), builder)) //
						.executes(context -> locateBiome(context.getSource(), context.getArgument("biome", ResourceLocation.class))));
	}
	
	private static int locateBiome(CommandSource source, ResourceLocation biomeName) throws CommandSyntaxException {
		final Biome biome = ForgeRegistries.BIOMES.getValue(biomeName);
		
		if (biome == null) {
			throw FAILED_EXCEPTION.create();
		}
		
		final ServerWorld world = source.getWorld();
		final BiomeProvider biomeProvider = world.getChunkProvider().getChunkGenerator().getBiomeProvider();
		final Random random = new Random(world.getSeed());
		final BlockPos pos = new BlockPos(source.getPos());
		
		final BlockPos foundPos = findBiome(biomeProvider, pos.getX(), pos.getY(), pos.getZ(), 6400, 8, biome, random, true);
		
		if (foundPos == null) {
			throw FAILED_EXCEPTION.create();
		}
		
		final int distance = MathHelper.floor(MathUtil.getPlaneDistance(pos.getX(), pos.getZ(), foundPos.getX(), foundPos.getZ()));
		
		final ITextComponent text = TextComponentUtils.wrapInSquareBrackets(new TranslationTextComponent("chat.coordinates", foundPos.getX(), "~", foundPos.getZ())).applyTextStyle((style) -> {
			style.setColor(TextFormatting.GREEN).setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tp @s " + foundPos.getX() + " ~ " + foundPos.getZ())).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslationTextComponent("chat.coordinates.tooltip")));
		});
		source.sendFeedback(new TranslationTextComponent("commands.uteamcore.locatebiome.success", biomeName, text, distance), false);
		
		return distance;
	}
	
	/*
	 * This code is from 1.16-pre6 for the biome locate (It is like the method func_225531_a_#func_225531_a_ but has more
	 * options)
	 */
	private static BlockPos findBiome(BiomeProvider provider, int x, int y, int z, int radius, int accuracy, Biome biome, Random random, boolean randomPoint) {
		final int j = x >> 2;
		final int k = z >> 2;
		final int l = radius >> 2;
		final int m = y >> 2;
		final int o = randomPoint ? 0 : l;
		BlockPos blockPos = null;
		
		int n = 0;
		
		for (int p = o; p <= l; p += accuracy) {
			for (int q = -p; q <= p; q += accuracy) {
				final boolean bl2 = Math.abs(q) == p;
				
				for (int r = -p; r <= p; r += accuracy) {
					if (randomPoint) {
						final boolean bl3 = Math.abs(r) == p;
						if (!bl3 && !bl2) {
							continue;
						}
					}
					
					final int s = j + r;
					final int t = k + q;
					if (biome == (provider.getNoiseBiome(s, m, t))) {
						if (blockPos == null || random.nextInt(n + 1) == 0) {
							blockPos = new BlockPos(s << 2, y, t << 2);
							if (randomPoint) {
								return blockPos;
							}
						}
						++n;
					}
				}
			}
		}
		return blockPos;
	}
	
}
