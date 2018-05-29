package com.kaptainwutax.immersivemusic.objects.blocks.blockmidi;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class BlockMidiWriteThreadPacket implements IMessage {

    BlockPos pos;
    int[] file = new int[7400];
    char[] fileName = new char[25];

    public BlockMidiWriteThreadPacket() {
    }

    public BlockMidiWriteThreadPacket(BlockPos pos, int[] file, char[] fileName) {

        this.pos = pos;

        for (int i = 0; i < file.length; i++) {
            this.file[i] = file[i];
        }

        for (int i = 0; i < fileName.length; i++) {
            this.fileName[i] = fileName[i];
        }

    }

    @Override
    public void fromBytes(ByteBuf buf) {

        pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());

        for (int i = 0; i < file.length; i++) {
            file[i] = buf.readInt();
        }

        for (int i = 0; i < fileName.length; i++) {
            fileName[i] = buf.readChar();
        }

    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());

        for (int i = 0; i < file.length; i++) {
            buf.writeInt(file[i]);
        }

        for (int i = 0; i < fileName.length; i++) {
            buf.writeChar(fileName[i]);
        }

    }

    public static class BlockMidiWriteThreadPacketHandler implements IMessageHandler<BlockMidiWriteThreadPacket, IMessage> {

        @Override
        public IMessage onMessage(BlockMidiWriteThreadPacket message, MessageContext ctx) {

            EntityPlayerMP player = ctx.getServerHandler().player;
            WorldServer world = player.getServerWorld();

            if (world.getTileEntity(message.pos) instanceof BlockMidiTileEntity)
                world.addScheduledTask(() -> {
                    BlockMidiTileEntity TE = (BlockMidiTileEntity) world.getTileEntity(message.pos);
                    TE.setFileName("\"" + new String(message.fileName).trim() + "\" by " + ctx.getServerHandler().player.getName() + ".");
                    TE.setFile(message.file);
                });
            return null;
        }

    }

}
