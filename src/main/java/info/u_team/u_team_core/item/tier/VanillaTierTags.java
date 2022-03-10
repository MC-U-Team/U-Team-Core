package info.u_team.u_team_core.item.tier;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;

public class VanillaTierTags {
	
	// Tags for blocks to determine the preferred mining tool
	
	public static final TagKey<Block> MINEABLE_WITH_AXE = BlockTags.MINEABLE_WITH_AXE;
	public static final TagKey<Block> MINEABLE_WITH_HOE = BlockTags.MINEABLE_WITH_HOE;
	public static final TagKey<Block> MINEABLE_WITH_PICKAXE = BlockTags.MINEABLE_WITH_PICKAXE;
	public static final TagKey<Block> MINEABLE_WITH_SHOVEL = BlockTags.MINEABLE_WITH_SHOVEL;
	
	// Tags for block to determine the tier that is needed to be able to mine that block successfully
	
	public static final TagKey<Block> NEEDS_WOOD_TOOL = Tags.Blocks.NEEDS_WOOD_TOOL;
	public static final TagKey<Block> NEEDS_GOLD_TOOL = Tags.Blocks.NEEDS_GOLD_TOOL;
	public static final TagKey<Block> NEEDS_STONE_TOOL = BlockTags.NEEDS_STONE_TOOL;
	public static final TagKey<Block> NEEDS_IRON_TOOL = BlockTags.NEEDS_IRON_TOOL;
	public static final TagKey<Block> NEEDS_DIAMOND_TOOL = BlockTags.NEEDS_DIAMOND_TOOL;
	public static final TagKey<Block> NEEDS_NETHERITE_TOOL = Tags.Blocks.NEEDS_NETHERITE_TOOL;
	
}
