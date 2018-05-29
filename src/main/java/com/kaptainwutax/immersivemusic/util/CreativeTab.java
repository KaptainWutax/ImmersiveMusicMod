package com.kaptainwutax.immersivemusic.util;

import com.kaptainwutax.immersivemusic.init.BlockInit;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTab extends CreativeTabs {

    public CreativeTab(String label) {
        super(label);
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(BlockInit.BLOCK_FRAME);
    }
}
