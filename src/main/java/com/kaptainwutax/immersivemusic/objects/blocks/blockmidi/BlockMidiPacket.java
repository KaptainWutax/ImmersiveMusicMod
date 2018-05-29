package com.kaptainwutax.immersivemusic.objects.blocks.blockmidi;

import com.kaptainwutax.immersivemusic.util.handlers.SoundsHandler;
import io.netty.buffer.ByteBuf;
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
    float volume;
    float speed;
    Boolean isFilePlaying;

    public BlockMidiPacket() {
    }

    public BlockMidiPacket(BlockPos pos, int instrument, int noteToPlay, float volume, float speed, Boolean isFilePlaying) {

        this.pos = pos;
        this.instrument = instrument;
        this.noteToPlay = noteToPlay;
        this.volume = volume;
        this.speed = speed;
        this.isFilePlaying = isFilePlaying;

    }

    @Override
    public void fromBytes(ByteBuf buf) {

        pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        instrument = buf.readInt();
        noteToPlay = buf.readInt();
        volume = buf.readFloat();
        speed = buf.readFloat();
        isFilePlaying = buf.readBoolean();

    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        buf.writeInt(instrument);
        buf.writeInt(noteToPlay);
        buf.writeFloat(volume);
        buf.writeFloat(speed);
        buf.writeBoolean(isFilePlaying);

    }

    public static class BlockMidiPacketHandler implements IMessageHandler<BlockMidiPacket, IMessage> {

        @Override
        public IMessage onMessage(BlockMidiPacket message, MessageContext ctx) {

            EntityPlayerMP player = ctx.getServerHandler().player;
            WorldServer world = player.getServerWorld();

            if (world.getTileEntity(message.pos) instanceof BlockMidiTileEntity)
                world.addScheduledTask(() -> {
                    BlockMidiTileEntity TE = (BlockMidiTileEntity) world.getTileEntity(message.pos);
                    TE.setInstrumentToPlay(message.instrument);
                    TE.setVolume(message.volume);
                    TE.setSpeed(message.speed);
                    TE.setIsFilePlaying(message.isFilePlaying);
                    if (message.noteToPlay != 1000 && SoundsHandler.NOTE_SOUND[TE.getInstrumentToPlay()][message.noteToPlay] != null) {
                        world.playSound(player, message.pos.getX(), message.pos.getY(), message.pos.getZ(), SoundsHandler.NOTE_SOUND[message.instrument][message.noteToPlay], SoundCategory.RECORDS, message.volume, 1F);
                    }
                });
            return null;
        }

    }

}


