package com.kaptainwutax.immersivemusic.objects.blocks.blocknote;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class BlockNoteTileEntity extends TileEntity {

	private int note = 0;
	private int octave = 4;
	private int noteToPlay = 60;
	private int instrumentToPlay = 0;
	private float volume = 1F;
	private boolean previousRedstoneState = false;
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		
		super.writeToNBT(compound);
		compound.setInteger("note", note);
		compound.setInteger("octave", octave);
		compound.setInteger("noteToPlay", noteToPlay);
		compound.setInteger("instrumentToPlay", instrumentToPlay);
		compound.setFloat("volume", volume);
		compound.setBoolean("powered", previousRedstoneState);
		return compound;
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {

		super.readFromNBT(compound);
		note = compound.getInteger("note");
		octave = compound.getInteger("octave");
		noteToPlay = compound.getInteger("noteToPlay");
		instrumentToPlay = compound.getInteger("instrumentToPlay");
		volume = compound.getFloat("volume");
		previousRedstoneState = compound.getBoolean("powered");
		
		
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
    public void setNote(int enote) {
    	
    	note = enote;
    	//markDirty();
    	markForUpdate();
    	
    }
    
    public void setNoteToPlay(int enoteToPlay) {
    	
    	noteToPlay = enoteToPlay;
    	//markDirty();
    	markForUpdate();
    }
    
    public void setOctave(int eoctave) {
    	
    	octave = eoctave;
    	//markDirty();
    	markForUpdate();
    }
    
    public void setInstrumentToPlay(int einstrumentToPlay) {
    	
    	instrumentToPlay = einstrumentToPlay;
    	//markDirty();
    	markForUpdate();
    }
    
   public void setVolume(float evolume) {
    	
    	volume = evolume;
    	//markDirty();
    	markForUpdate();
    }
    
    public void setPowered(Boolean epowered) {
    	
    	previousRedstoneState = epowered;
    	//markDirty();
    	markForUpdate();
    }
    
    //GET
    public int getNote() {
    	
    	return note;
    	
    }
    
    public int getNoteToPlay() {
    	
       	return noteToPlay;
       	
    }
    
    public int getOctave() {

       	return octave;
       	
    }
    
    public int getInstrumentToPlay() {
    	
       	return instrumentToPlay;
       	
    }
    
    public float getVolume() {
    	
       	return volume;
       	
    }
    
   public Boolean getPowered() {
    	
       	return previousRedstoneState;
       	
    }
    	
}
