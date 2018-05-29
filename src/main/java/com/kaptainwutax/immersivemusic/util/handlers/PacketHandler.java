package com.kaptainwutax.immersivemusic.util.handlers;

import com.kaptainwutax.immersivemusic.objects.blocks.blockmidi.BlockMidiPacket;
import com.kaptainwutax.immersivemusic.objects.blocks.blockmidi.BlockMidiPacket.BlockMidiPacketHandler;
import com.kaptainwutax.immersivemusic.objects.blocks.blockmidi.BlockMidiReadThreadPacket;
import com.kaptainwutax.immersivemusic.objects.blocks.blockmidi.BlockMidiReadThreadPacket.BlockMidiReadThreadPacketHandler;
import com.kaptainwutax.immersivemusic.objects.blocks.blockmidi.BlockMidiWriteThreadPacket;
import com.kaptainwutax.immersivemusic.objects.blocks.blockmidi.BlockMidiWriteThreadPacket.BlockMidiWriteThreadPacketHandler;
import com.kaptainwutax.immersivemusic.objects.blocks.blocknote.BlockNotePacket;
import com.kaptainwutax.immersivemusic.objects.blocks.blocknote.BlockNotePacket.BlockNotePacketHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

    public static SimpleNetworkWrapper INSTANCE = null;
    private static int packetId = 0;

    public PacketHandler() {

    }

    private static int nextID() {
        return packetId++;
    }

    public static void registerMessages(String channelName) {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
        registerMessages();
    }

    private static void registerMessages() {
        INSTANCE.registerMessage(BlockMidiPacketHandler.class, BlockMidiPacket.class, packetId, Side.SERVER);
        INSTANCE.registerMessage(BlockNotePacketHandler.class, BlockNotePacket.class, nextID(), Side.SERVER);
        INSTANCE.registerMessage(BlockMidiWriteThreadPacketHandler.class, BlockMidiWriteThreadPacket.class, nextID(), Side.SERVER);
        INSTANCE.registerMessage(BlockMidiReadThreadPacketHandler.class, BlockMidiReadThreadPacket.class, nextID(), Side.SERVER);
    }

}
