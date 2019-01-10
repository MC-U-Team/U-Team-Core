package test;

import info.u_team.u_team_core.gui.UGuiContainerTileEntity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiSlider;

public class GuiTestBuggy extends UGuiContainerTileEntity {
	
	private TileEntityTestBuggy tile;
	
	private GuiSlider slider;
	
	public GuiTestBuggy(InventoryPlayer playerinv, TileEntityTestBuggy tile) {
		super(new ContainerTest(playerinv, tile), new ResourceLocation("textures/gui/container/furnace.png"));
		this.tile = tile;
	}
	
	@Override
	public void initGui() {
		System.out.println("--------------------------------");
		System.out.println(this);
		super.initGui();
		buttonList.add(slider = new GuiSlider(1, 20, 20, 200, 20, "test", " t", 0, 100, 0, false, true, slider -> {
			System.out.println("sync to server");
			tile.myvalue = slider.getValueInt();
			System.out.println(tile.myvalue);
			tile.syncClientToServer(tile.getPos());
		}));
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		mc.fontRenderer.drawString(tile.myvalue + "", 30, 30, 0xFF0F02);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
	}
	
	@Override
	public void handleServerDataOnFirstArrival(NBTTagCompound compound) {
		System.out.println(compound);
		int test = compound.getInteger("myvalue");
		System.out.println("SLIDER  " + test);
		System.out.println(this);
		slider.setValue(test);
		slider.updateSlider();
	}
	
}
