package com.kaptainwutax.immersivemusic.objects.blocks.blockmidi;

import com.kaptainwutax.immersivemusic.util.handlers.PacketHandler;
import com.kaptainwutax.immersivemusic.util.handlers.SoundsHandler;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class BlockMidiReadThread extends Thread {

    BlockMidiTileEntity TE;
    World world;
    int[] file = new int[7400];
    BlockPos pos;

    long lastTick = 0;

    public BlockMidiReadThread(BlockMidiTileEntity TE, World world, int[] file) {

        this.TE = TE;
        this.world = world;

        for (int i = 0; i < file.length; i++) {
            this.file[i] = file[i];
        }

        pos = TE.getPos();

    }

    @Override
    public void run() {
        read();
    }

    public void read() {

        Thread.currentThread().setName(TE.getPos().toString());

        for (int i = 0; i < TE.getFile().length / 2; i++) {

            if (world.getTileEntity(pos) != TE) {
                return;
            }
            if (!world.isBlockLoaded(pos)) {
                return;
            }
            if (((BlockMidiTileEntity) world.getTileEntity(pos)).getIsFilePlaying() == false) {
                return;
            }

            int index = i;
            int note = file[i * 2];
            int time = file[(i * 2) + 1];

            try {
                if (time != 0)
                    this.sleep((long) ((time - lastTick) / TE.getSpeed()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (note == 0 && time == 0) {
            } else {
                if (SoundsHandler.NOTE_SOUND[TE.getInstrumentToPlay()][note] != null) {
                    PacketHandler.INSTANCE.sendToServer(new BlockMidiPacket(TE.getPos(), TE.getInstrumentToPlay(), note, TE.getVolume(), TE.getSpeed(), TE.getIsFilePlaying()));
                    ((WorldServer) world).addScheduledTask(() -> {
                        world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundsHandler.NOTE_SOUND[TE.getInstrumentToPlay()][note], SoundCategory.RECORDS, TE.getVolume(), 1F);
                        ((WorldServer) world).spawnParticle(EnumParticleTypes.NOTE, (double) pos.getX() + 0.5D, (double) pos.getY() + 1.1D, (double) pos.getZ() + 0.5D, 1, 0.0D, 0.0D, 0.0D, 1, null);
                    });
                }
            }

            if (time != 0)
                lastTick = time;

        }

    }

}
