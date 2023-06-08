package info.u_team.u_team_test.block;

import info.u_team.u_team_core.block.UBlock;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class BasicBlock extends UBlock {
	
	public BasicBlock() {
		super(Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(2F).sound(SoundType.GRAVEL).friction(0.8F).lightLevel(state -> 1), new Item.Properties().rarity(Rarity.UNCOMMON));
	}
	
}
