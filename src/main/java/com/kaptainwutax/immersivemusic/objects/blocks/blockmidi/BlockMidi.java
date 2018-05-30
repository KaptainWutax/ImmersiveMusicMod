package com.kaptainwutax.immersivemusic.objects.blocks.blockmidi;

import com.kaptainwutax.immersivemusic.ImmersiveMusic;
import com.kaptainwutax.immersivemusic.init.BlockInit;
import com.kaptainwutax.immersivemusic.util.Reference;
import com.kaptainwutax.immersivemusic.util.handlers.GuiHandler;
import com.kaptainwutax.immersivemusic.util.handlers.PacketHandler;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockMidi extends Block implements ITileEntityProvider {

    public BlockMidi(String name, Material materialIn) {

        super(materialIn);

        setSoundType(SoundType.METAL);
        setHardness(5.0F);
        setResistance(18000000F);
        setHarvestLevel("pickaxe", 0);
        setLightLevel(0F);
        setLightOpacity(15);
        setUnlocalizedName(name);
        setRegistryName(Reference.MOD_ID + ":" + name);
        setCreativeTab(ImmersiveMusic.CREATIVE_TAB);

    }

    public void registerItemModels() {

        ImmersiveMusic.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");

    }

    @Override
    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {

        return false;

    }


    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {

        return Item.getItemFromBlock(BlockInit.BLOCK_MIDI);

    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {

        return new ItemStack(BlockInit.BLOCK_MIDI);

    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {

        return new BlockMidiTileEntity();

    }

    //CUSTOM
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {

        if (worldIn.isRemote) {

            playerIn.openGui(ImmersiveMusic.instance, GuiHandler.BLOCK_MIDI, worldIn, pos.getX(), pos.getY(), pos.getZ());

        } else {

            return false;

        }

        return true;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {

        boolean flag = worldIn.isBlockPowered(pos);
        BlockMidiTileEntity TE = (BlockMidiTileEntity) worldIn.getTileEntity(pos);

        if (TE.getPowered() != flag) {

            if (!worldIn.isRemote && flag) {
                PacketHandler.INSTANCE.sendToServer(new BlockMidiPacket(pos, TE.getInstrumentToPlay(), 1000, TE.getVolume(), TE.getSpeed(), true));
                PacketHandler.INSTANCE.sendToServer(new BlockMidiReadThreadPacket(TE.getPos()));
            } else {
                PacketHandler.INSTANCE.sendToServer(new BlockMidiPacket(pos, TE.getInstrumentToPlay(), 1000, TE.getVolume(), TE.getSpeed(), false));
            }

            TE.setPowered(flag);
        }

    }

}
