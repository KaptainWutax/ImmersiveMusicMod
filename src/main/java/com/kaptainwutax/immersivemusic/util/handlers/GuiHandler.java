package com.kaptainwutax.immersivemusic.util.handlers;

import com.kaptainwutax.immersivemusic.objects.blocks.blockmidi.BlockMidiGui;
import com.kaptainwutax.immersivemusic.objects.blocks.blockmidi.BlockMidiTileEntity;
import com.kaptainwutax.immersivemusic.objects.blocks.blocknote.BlockNoteGui;
import com.kaptainwutax.immersivemusic.objects.blocks.blocknote.BlockNoteTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    public static final int BLOCK_NOTE = 0;
    public static final int BLOCK_MIDI = 1;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        switch (ID) {

            case 0:
                return null;
            case 1:
                return null;

        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        switch (ID) {

            case 0:
                return new BlockNoteGui(world, (BlockNoteTileEntity) world.getTileEntity(new BlockPos(x, y, z)), new BlockPos(x, y, z));
            case 1:
                return new BlockMidiGui(world, (BlockMidiTileEntity) world.getTileEntity(new BlockPos(x, y, z)), new BlockPos(x, y, z));

        }

        return null;
    }

}
