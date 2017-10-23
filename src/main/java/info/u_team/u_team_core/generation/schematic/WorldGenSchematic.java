package info.u_team.u_team_core.generation.schematic;

import java.net.URL;
import java.util.Random;

import info.u_team.u_team_core.schematic.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

/**
 * Generation API<br>
 * -> World Generator Schematic
 * 
 * @author HyCraftHD
 * @date 23.10.2017
 */
public class WorldGenSchematic extends WorldGenerator {
	
	private URL url;
	private boolean centerstart;
	private USchematicRotation rotation;
	
	public WorldGenSchematic(URL url, boolean centerstart, USchematicRotation rotation) {
		this.url = url;
		this.centerstart = centerstart;
		this.rotation = rotation;
	}
	
	@Override
	public boolean generate(World world, Random rand, BlockPos position) {
		USchematicLoadRegion region = new USchematicLoadRegion(world, position);
		if (centerstart) {
			region.center();
		}
		region.rotate(rotation);
		try {
			USchematicSyncReader reader = new USchematicSyncReader(region, url.openStream());
			reader.start();
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
}
