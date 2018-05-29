package com.kaptainwutax.immersivemusic.objects.blocks.blockmidi;

import com.kaptainwutax.immersivemusic.util.handlers.PacketHandler;
import net.minecraft.world.World;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;

public class BlockMidiWriteThread extends Thread {

    public static final int NOTE_ON = 0x90;
    public static final int NOTE_OFF = 0x80;

    World world;
    BlockMidiTileEntity TE;
    char[] file_name = new char[25];

    int index = 0;
    int trackNumber = 0;
    long lastTick;
    int[] file = new int[7400];

    public BlockMidiWriteThread(World world, BlockMidiTileEntity TE, char[] file_name) {

        this.world = world;
        this.TE = TE;
        this.file_name = file_name;

    }

    @Override
    public void run() {
        write();
    }

    public void write() {

        Sequence sequence;

        try {

            sequence = MidiSystem.getSequence(new File("config/ImmersiveMusic/midi_files/" + new String(file_name) + ".mid"));

            int trackNumber = 0;

            for (Track track : sequence.getTracks()) {

                trackNumber++;

                for (int i = 0; i < track.size(); i++) {

                    MidiEvent event = track.get(i);
                    MidiMessage message = event.getMessage();

                    if (message instanceof ShortMessage) {

                        ShortMessage sm = (ShortMessage) message;

                        if (sm.getCommand() == NOTE_ON) {

                            int key = sm.getData1();

                            file[index * 2] = (int) key;
                            file[(index * 2) + 1] = (int) event.getTick();
                            TE.setFile(file);

                            index++;

                        }
                    }

                }

            }

            PacketHandler.INSTANCE.sendToServer(new BlockMidiWriteThreadPacket(TE.getPos(), file, file_name));

        } catch (InvalidMidiDataException | IOException e) {

            e.printStackTrace();
            this.interrupt();

        }

    }

}
