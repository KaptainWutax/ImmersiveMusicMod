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
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		
		super.writeToNBT(compound);
		compound.setInteger("note", note);
		compound.setInteger("octave", octave);
		compound.setInteger("noteToPlay", noteToPlay);
		compound.setInteger("instrumentToPlay", instrumentToPlay);
		return compound;
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {

		super.readFromNBT(compound);
		this.note = compound.getInteger("note");
		this.octave = compound.getInteger("octave");
		this.noteToPlay = compound.getInteger("noteToPlay");
		this.instrumentToPlay = compound.getInteger("instrumentToPlay");
		
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
    
    //SET
    public void setNote(int enote) {
    	
    	note = enote;
    	markDirty();
    	
    }
    
    public void setNoteToPlay(int enoteToPlay) {
    	
    	noteToPlay = enoteToPlay;
    	markDirty();
    	
    }
    
    public void setOctave(int eoctave) {
    	
    	octave = eoctave;
    	markDirty();
    	
    }
    
    public void setInstrumentToPlay(int einstrumentToPlay) {
    	
    	instrumentToPlay = einstrumentToPlay;
    	System.out.println(instrumentToPlay + "TEEEEEEEEEEEEEEEEEEEEEE");
    	markDirty();
    	
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
    	
}
