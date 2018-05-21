package com.kaptainwutax.immersivemusic.objects.blocks.blockmidi;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class BlockMidiReadThreadPacket implements IMessage {
	
	BlockPos pos;
	
	public BlockMidiReadThreadPacket() {}
	
	public BlockMidiReadThreadPacket(BlockPos pos) {
		
		this.pos = pos;				
		
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());		
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
		buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());		
		
	}
	
	public static class BlockMidiReadThreadPacketHandler implements IMessageHandler<BlockMidiReadThreadPacket, IMessage> {
		
		@Override
		public IMessage onMessage(BlockMidiReadThreadPacket message, MessageContext ctx) {
						
			EntityPlayerMP player = ctx.getServerHandler().player;
			WorldServer sworld = player.getServerWorld();
			
			if(sworld.getTileEntity(message.pos) instanceof BlockMidiTileEntity)
			sworld.addScheduledTask(() -> {
				World world = player.world;
				BlockMidiTileEntity TE = (BlockMidiTileEntity)world.getTileEntity(message.pos);	
				Thread readingThread = new Thread(new BlockMidiReadThread(TE, sworld, TE.getFile()));
	    		readingThread.start();
			});
			return null;
		}
			
	}

}
