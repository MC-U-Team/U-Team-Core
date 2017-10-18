package info.u_team.u_team_core.schematic;

import java.io.*;
import java.util.function.Consumer;

import info.u_team.u_team_core.intern.UCoreConstants;
import info.u_team.u_team_core.util.world.OffsetVec3i;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SchematicSaver {
	
	private World world;
	private SchematicRegion region;
	private OutputStream outputstream;
	private Consumer<Boolean> finished;
	
	public SchematicSaver(World world, SchematicRegion region, OutputStream outputstream) {
		this.world = world;
		this.region = region;
		this.outputstream = outputstream;
	}
	
	public void finished(Consumer<Boolean> finished) {
		this.finished = finished;
	}
	
	public void start() {
		Thread thread = new Thread(() -> {
			boolean success = true;
			try {
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputstream));
				
				writer.write("count:" + region.getCount());
				writer.newLine();
				writer.write("sizex:" + region.getSizeX());
				writer.newLine();
				writer.write("sizey:" + region.getSizeY());
				writer.newLine();
				writer.write("sizez:" + region.getSizeZ());
				writer.newLine();
				
				for (int x = 0; x < region.getSizeX(); x++) {
					for (int z = 0; z < region.getSizeZ(); z++) {
						for (int y = 0; y < region.getSizeY(); y++) {
							BlockPos pos = new BlockPos(new OffsetVec3i(x, y, z).offset(region.getMin()));
							SchematicEntry entry = new SchematicEntry(pos, world);
							writer.write(entry.toString());
							writer.newLine();
						}
					}
				}
				
				writer.close();
			} catch (Exception ex) {
				UCoreConstants.LOGGER.error("Error while trying to save schematic region.", ex);
				success = false;
			}
			if (finished != null) {
				finished.accept(success);
			}
		});
		thread.setName("Schematic Saver");
		thread.start();
	}
	
}
