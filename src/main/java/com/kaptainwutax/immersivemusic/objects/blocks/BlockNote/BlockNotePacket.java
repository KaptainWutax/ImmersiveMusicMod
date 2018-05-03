package com.kaptainwutax.immersivemusic.objects.blocks.BlockNote;

import com.kaptainwutax.immersivemusic.objects.blocks.BlockNote.BlockNotePacket;
import com.kaptainwutax.immersivemusic.objects.blocks.BlockNote.BlockNoteTileEntity;
import com.kaptainwutax.immersivemusic.util.handlers.SoundsHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class BlockNotePacket implements IMessage {

	int note;
	int octave;
	int noteToPlay;
	int instrumentToPlay;
	BlockPos pos;

	public BlockNotePacket() {}
	public BlockNotePacket(int note, int octave, int instrument, int noteToPlay, BlockPos pos) {
		
		this.note = note;
		this.octave = octave;
		this.noteToPlay = noteToPlay;
		this.instrumentToPlay = instrument;
		this.pos = pos;
		
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		
		note = buf.readInt();
		octave = buf.readInt();
		noteToPlay = buf.readInt();
		instrumentToPlay = buf.readInt();
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		
	}

	@Override
	public void toBytes(ByteBuf buf) {

        buf.writeInt(note);
        buf.writeInt(octave);
        buf.writeInt(noteToPlay);
        buf.writeInt(instrumentToPlay);  	
		buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
		
	}
	
	public static class BlockNotePacketHandler implements IMessageHandler<BlockNotePacket, IMessage> {

		@Override
		public IMessage onMessage(BlockNotePacket message, MessageContext ctx) {
			
			EntityPlayerMP player = ctx.getServerHandler().player;
			WorldServer world = player.getServerWorld();
			BlockNoteTileEntity TE = (BlockNoteTileEntity)world.getTileEntity(message.pos);
				
			if(world.getTileEntity(message.pos) instanceof BlockNoteTileEntity)
				world.addScheduledTask(() -> {
					TE.setNote(message.note);
					TE.setOctave(message.octave);
					TE.setNoteToPlay(message.noteToPlay);
					TE.setInstrumentToPlay(message.instrumentToPlay);				
				});
			return null;
			
		}
			
	}
	
}
