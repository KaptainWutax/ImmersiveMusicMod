package com.kaptainwutax.immersivemusic.init;

import java.util.ArrayList;
import java.util.List;

import com.kaptainwutax.immersivemusic.objects.blocks.BlockBase;
import com.kaptainwutax.immersivemusic.objects.blocks.blockframe.BlockFrame;
import com.kaptainwutax.immersivemusic.objects.blocks.blockmidi.BlockMidi;
import com.kaptainwutax.immersivemusic.objects.blocks.blocknote.BlockNote;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockInit {

	public static final List<Block> BLOCKS = new ArrayList<Block>();

	public static final Block BLOCK_FRAME = new BlockFrame("block_frame", Material.IRON);
	public static final Block BLOCK_NOTE = new BlockNote("block_note", Material.IRON);
	public static final Block BLOCK_MIDI = new BlockMidi("block_midi", Material.IRON);
	
}
