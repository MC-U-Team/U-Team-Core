package info.u_team.u_team_test.init;

import info.u_team.u_team_test.gui.GuiTileEntity;
import info.u_team.u_team_test.tileentity.TileEntityTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.fml.*;

@OnlyIn(Dist.CLIENT)
public class TestGuis {
	
	public static void contruct() {
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.GUIFACTORY, () -> {
			return (openContainer) -> {
				ResourceLocation location = openContainer.getId();
				
				if (location.toString().equals("uteamtest:tileentity")) {
					
					System.out.println("NEW ID: " + openContainer.getWindowId());
					
					System.out.println("OUR ID: "+ Minecraft.getInstance().player.openContainer.windowId);
					
					EntityPlayerSP player = Minecraft.getInstance().player;
					World world = player.getEntityWorld();
					
					BlockPos pos = openContainer.getAdditionalData().readBlockPos();
					TileEntity tileentity = world.getTileEntity(pos);
					if (tileentity instanceof TileEntityTileEntity) {
						TileEntityTileEntity tileentitytilentity = (TileEntityTileEntity) tileentity;
						return new GuiTileEntity(player.inventory, tileentitytilentity);
					}
				}
				return null;
			};
		});
	}
	
}
