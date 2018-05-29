package com.kaptainwutax.immersivemusic.objects.blocks.blocknote;

import com.kaptainwutax.immersivemusic.util.handlers.SoundsHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
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
    float volume;
    BlockPos pos;
    Boolean playSound;

    public BlockNotePacket() {
    }

    public BlockNotePacket(int note, int octave, int noteToPlay, int instrumentToPlay, float volume, BlockPos pos, Boolean playSound) {

        this.note = note;
        this.octave = octave;
        this.noteToPlay = noteToPlay;
        this.instrumentToPlay = instrumentToPlay;
        this.volume = volume;
        this.pos = pos;
        this.playSound = playSound;

    }

    @Override
    public void fromBytes(ByteBuf buf) {

        note = buf.readInt();
        octave = buf.readInt();
        noteToPlay = buf.readInt();
        instrumentToPlay = buf.readInt();
        volume = buf.readFloat();
        pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        playSound = buf.readBoolean();

    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(note);
        buf.writeInt(octave);
        buf.writeInt(noteToPlay);
        buf.writeInt(instrumentToPlay);
        buf.writeFloat(volume);
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        buf.writeBoolean(playSound);

    }

    public static class BlockNotePacketHandler implements IMessageHandler<BlockNotePacket, IMessage> {

        @Override
        public IMessage onMessage(BlockNotePacket message, MessageContext ctx) {

            EntityPlayerMP player = ctx.getServerHandler().player;
            WorldServer world = player.getServerWorld();

            if (world.getTileEntity(message.pos) instanceof BlockNoteTileEntity)
                world.addScheduledTask(() -> {
                    BlockNoteTileEntity TE = (BlockNoteTileEntity) world.getTileEntity(message.pos);
                    TE.setNote(message.note);
                    TE.setOctave(message.octave);
                    TE.setNoteToPlay(message.noteToPlay);
                    TE.setInstrumentToPlay(message.instrumentToPlay);
                    TE.setVolume(message.volume);
                    if (message.playSound && SoundsHandler.NOTE_SOUND[TE.getInstrumentToPlay()][TE.getNoteToPlay()] != null) {
                        world.playSound(message.pos.getX(), message.pos.getY(), message.pos.getZ(), SoundsHandler.NOTE_SOUND[TE.getInstrumentToPlay()][TE.getNoteToPlay()], SoundCategory.RECORDS, message.volume, 1F, false);
                    }
                });
            return null;

        }

    }

}
