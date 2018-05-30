package com.kaptainwutax.immersivemusic.util;

import com.kaptainwutax.immersivemusic.init.BlockInit;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTab extends CreativeTabs {

    public CreativeTab() {
        super(Reference.MOD_ID);
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(BlockInit.BLOCK_FRAME);
    }

    @Override
    public boolean hasSearchBar() {
        return true;
    }
}
