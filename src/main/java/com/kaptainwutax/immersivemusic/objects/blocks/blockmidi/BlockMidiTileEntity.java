package com.kaptainwutax.immersivemusic.objects.blocks.blockmidi;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class BlockMidiTileEntity extends TileEntity {

	private int instrumentToPlay = 0;
	private float volume = 1F;
	private float speed = 1F;
	private boolean previousRedstoneState = false;
	private int[] file = new int[7400];
	private String fileName = "";
	private Boolean isFilePlaying = false;
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		
		super.writeToNBT(compound);
		compound.setInteger("instrumentToPlay", instrumentToPlay);
		compound.setFloat("volume", volume);
		compound.setFloat("speed", speed);
		compound.setBoolean("powered", previousRedstoneState);
		compound.setIntArray("file", file);
		compound.setString("file_name", fileName);
		compound.setBoolean("isFilePlaying", isFilePlaying);
		return compound;
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {

		super.readFromNBT(compound);
		instrumentToPlay = compound.getInteger("instrumentToPlay");
		volume = compound.getFloat("volume");
		speed = compound.getFloat("speed");
		previousRedstoneState = compound.getBoolean("powered");
		file = compound.getIntArray("file");
		fileName = compound.getString("file_name");
		isFilePlaying = compound.getBoolean("isFilePlaying");		
		
	}
	
	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		handleUpdateTag(pkt.getNbtCompound());
	}
	
	private IBlockState getState() {
		return world.getBlockState(pos);
	}
	
	private void markForUpdate() {
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, getState(), getState(), 3);
		world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
		markDirty();
	}
    

    //SET
    public void setInstrumentToPlay(int einstrumentToPlay) {
    	
    	instrumentToPlay = einstrumentToPlay;
    	markForUpdate();

    }
    
   public void setVolume(float evolume) {
    	
    	volume = evolume;
    	markForUpdate();

    }
   
    public void setSpeed(float espeed) {
    	
    	speed = espeed;
    	markForUpdate();
    	
    }
    
    public void setPowered(Boolean epowered) {
    	
    	previousRedstoneState = epowered;
    	markForUpdate();

    }
    
    public void setFile(int[] efile) {
    	
    	file = efile;
    	markForUpdate();

    }
    
    public void setFileName(String efileName) {
    	
    	fileName = efileName;
    	markForUpdate();

    }
    
    public void setIsFilePlaying(Boolean eisFilePlaying) {
    	
    	isFilePlaying = eisFilePlaying;
    	markForUpdate();

    }
    
    //GET  
    public int getInstrumentToPlay() {
    	
       	return instrumentToPlay;
       	
    }
    
    public float getVolume() {
    	
       	return volume;
       	
    }
    
    public float getSpeed() {
    	
    	return speed;
    	
    }
    
   public Boolean getPowered() {
    	
       	return previousRedstoneState;
       	
    }
   
   public int[] getFile() {
   	
      	return file;
      	
   }
   
   public String getFileName() {
   	
      	return fileName;
      	
   }
   
   public Boolean getIsFilePlaying () {
	   
	   return isFilePlaying;
	   
   }
    	
}
