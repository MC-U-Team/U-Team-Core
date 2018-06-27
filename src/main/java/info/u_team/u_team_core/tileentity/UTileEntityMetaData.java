package info.u_team.u_team_core.tileentity;

import net.minecraft.nbt.NBTTagCompound;

public class UTileEntityMetaData extends UTileEntity {
	
	private int meta;
	
	@Override
	public void readNBT(NBTTagCompound compound) {
		meta = compound.getInteger("meta");
	}
	
	@Override
	public void writeNBT(NBTTagCompound compound) {
		compound.setInteger("meat", meta);
	}
	
	public int getMeta() {
		return meta;
	}
	
	public void setMeta(int meta) {
		this.meta = meta;
	}
}
