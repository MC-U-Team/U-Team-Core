package info.u_team.u_team_core.intern.command.uteamcore;

import java.util.*;

import com.mojang.brigadier.builder.ArgumentBuilder;

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
		
		final BlockPos pos = new BlockPos(source.getPos());
		
		final BlockPos foundPos = locateBiome(biomeProvider, pos.getX(), pos.getY(), pos.getZ(), 6400, 8, Arrays.asList(ForgeRegistries.BIOMES.getValue(biome)), random, true);
		
		System.out.println(foundPos);
		
		int i = MathHelper.floor(getDistance(pos.getX(), pos.getZ(), foundPos.getX(), foundPos.getZ()));
		ITextComponent itextcomponent = TextComponentUtils.wrapInSquareBrackets(new TranslationTextComponent("chat.coordinates", foundPos.getX(), "~", foundPos.getZ())).applyTextStyle((p_211746_1_) -> {
			p_211746_1_.setColor(TextFormatting.GREEN).setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tp @s " + foundPos.getX() + " ~ " + foundPos.getZ())).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslationTextComponent("chat.coordinates.tooltip")));
		});
		source.sendFeedback(new TranslationTextComponent("commands.locate.success", biome, itextcomponent, i), false);
		return i;
	}
	
	private static float getDistance(int x1, int z1, int x2, int z2) {
		int i = x2 - x1;
		int j = z2 - z1;
		return MathHelper.sqrt((float) (i * i + j * j));
	}
	
	public static BlockPos locateBiome(BiomeProvider provider, int x, int y, int z, int radius, int i, List<Biome> biomes, Random random, boolean bl) {
		int j = x >> 2;
		int k = z >> 2;
		int l = radius >> 2;
		int m = y >> 2;
		BlockPos blockPos = null;
		int n = 0;
		int o = bl ? 0 : l;
		
		for (int p = o; p <= l; p += i) {
			for (int q = -p; q <= p; q += i) {
				boolean bl2 = Math.abs(q) == p;
				
				for (int r = -p; r <= p; r += i) {
					if (bl) {
						boolean bl3 = Math.abs(r) == p;
						if (!bl3 && !bl2) {
							continue;
						}
					}
					
					int s = j + r;
					int t = k + q;
					if (biomes.contains(provider.getNoiseBiome(s, m, t))) {
						if (blockPos == null || random.nextInt(n + 1) == 0) {
							blockPos = new BlockPos(s << 2, y, t << 2);
							if (bl) {
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
