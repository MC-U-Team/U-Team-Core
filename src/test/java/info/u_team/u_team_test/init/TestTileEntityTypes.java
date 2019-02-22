package info.u_team.u_team_test.init;

import info.u_team.u_team_core.registry.TileEntityTypeRegistry;
import info.u_team.u_team_core.tileentitytype.UTileEntityType;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.tileentity.TileEntityTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class TestTileEntityTypes {
	
	public static final TileEntityType<TileEntityTileEntity> tileentity = UTileEntityType.Builder.create("tileentity", TileEntityTileEntity::new).build();
	
	public static void construct() {
		TileEntityTypeRegistry.register(TestMod.modid, TestTileEntityTypes.class);
	}
	
}
