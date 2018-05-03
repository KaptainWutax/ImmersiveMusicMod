package com.kaptainwutax.immersivemusic.objects.blocks.BlockMidi;

import com.kaptainwutax.immersivemusic.util.handlers.SoundsHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class BlockMidiPacket implements IMessage {
	
	BlockPos pos;
	int instrument;
	int noteToPlay;

	public BlockMidiPacket() {}
	public BlockMidiPacket(BlockPos pos, int instrument, int noteToPlay) {
		
		this.pos = pos;
		this.instrument = instrument;
		this.noteToPlay = noteToPlay;
		
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		instrument = buf.readInt();
		noteToPlay = buf.readInt();
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
		buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        buf.writeInt(instrument);
        buf.writeInt(noteToPlay);
		
	}
	
	public static class BlockMidiPacketHandler implements IMessageHandler<BlockMidiPacket, IMessage> {
		
		@Override
		public IMessage onMessage(BlockMidiPacket message, MessageContext ctx) {
			
			EntityPlayerMP player = ctx.getServerHandler().player;
			WorldServer world = player.getServerWorld();
			
			world.addScheduledTask(() -> {world.playSound(player, message.pos.getX(), message.pos.getY(), message.pos.getZ(), SoundsHandler.NOTE_SOUND[message.instrument][message.noteToPlay], SoundCategory.BLOCKS, 1F, 1F);});
			return null;
		}
			
	}

}


