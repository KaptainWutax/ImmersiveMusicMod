package com.kaptainwutax.immersivemusic.init;

import java.util.ArrayList;
import java.util.List;

import com.kaptainwutax.immersivemusic.objects.blocks.BlockBase;
import com.kaptainwutax.immersivemusic.objects.blocks.BlockFrame.BlockFrame;
import com.kaptainwutax.immersivemusic.objects.blocks.BlockJukebox.BlockJukebox;
import com.kaptainwutax.immersivemusic.objects.blocks.BlockMidi.BlockMidi;
import com.kaptainwutax.immersivemusic.objects.blocks.BlockNote.BlockNote;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class BlockInit {
	
	public static final List<Block> BLOCKS = new ArrayList<Block>();

	public static final Block BLOCK_FRAME = new BlockFrame("block_frame", Material.IRON);
	public static final Block BLOCK_NOTE = new BlockNote("block_note", Material.IRON);
	public static final Block BLOCK_MIDI = new BlockMidi("block_midi", Material.IRON);
	public static final Block BLOCK_JUKEBOX = new BlockJukebox("block_jukebox", Material.IRON);
	
}
