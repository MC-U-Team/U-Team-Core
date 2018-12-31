package test;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class TileEntitySpecialRenderTest extends TileEntitySpecialRenderer<TileEntityTest> {
	
	@Override
	public void render(TileEntityTest te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
		
		this.setLightmapDisabled(true);
		this.drawNameplate(te, "" + te.clientInt, x, y + 0.2, z, 12);
		this.setLightmapDisabled(false);
	}
	
}
