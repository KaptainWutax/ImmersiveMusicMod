package com.kaptainwutax.immersivemusic.init;

import com.kaptainwutax.immersivemusic.objects.blocks.blockframe.BlockFrame;
import com.kaptainwutax.immersivemusic.objects.blocks.blockmidi.BlockMidi;
import com.kaptainwutax.immersivemusic.objects.blocks.blocknote.BlockNote;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.registries.IForgeRegistry;

public class BlockInit {

    public static final BlockFrame BLOCK_FRAME = new BlockFrame("block_frame", Material.IRON);
    public static final BlockNote BLOCK_NOTE = new BlockNote("block_note", Material.IRON);
    public static final BlockMidi BLOCK_MIDI = new BlockMidi("block_midi", Material.IRON);

    public static void register(IForgeRegistry<Block> registry) {
        registry.registerAll(BLOCK_FRAME, BLOCK_NOTE, BLOCK_MIDI);
    }

    public static void registerModels() {
        BLOCK_FRAME.registerItemModels();
        BLOCK_NOTE.registerItemModels();
        BLOCK_MIDI.registerItemModels();
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        registry.registerAll(
                new ItemBlock(BLOCK_FRAME).setRegistryName(BLOCK_FRAME.getRegistryName()),
                new ItemBlock(BLOCK_NOTE).setRegistryName(BLOCK_NOTE.getRegistryName()),
                new ItemBlock(BLOCK_MIDI).setRegistryName(BLOCK_MIDI.getRegistryName())
        );
    }
}