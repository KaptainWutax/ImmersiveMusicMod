package com.kaptainwutax.immersivemusic.objects.blocks.blockframe;

import com.kaptainwutax.immersivemusic.ImmersiveMusic;
import com.kaptainwutax.immersivemusic.init.BlockInit;
import com.kaptainwutax.immersivemusic.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockFrame extends Block {

    public BlockFrame(String name, Material materialIn) {

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

        return Item.getItemFromBlock(BlockInit.BLOCK_FRAME);

    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(BlockInit.BLOCK_FRAME);

    }

}
