package com.kaptainwutax.immersivemusic.util.handlers;

import com.kaptainwutax.immersivemusic.objects.blocks.BlockMidi.BlockMidiPacket;
import com.kaptainwutax.immersivemusic.objects.blocks.BlockMidi.BlockMidiPacket.BlockMidiPacketHandler;

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
		 INSTANCE.registerMessage(BlockMidiPacketHandler.class, BlockMidiPacket.class, 1, Side.SERVER);
	 }	 
	    
}
