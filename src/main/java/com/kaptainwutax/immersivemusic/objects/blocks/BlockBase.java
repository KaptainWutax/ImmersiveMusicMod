package com.kaptainwutax.immersivemusic.objects.blocks;

import com.kaptainwutax.immersivemusic.ImmersiveMusic;
import com.kaptainwutax.immersivemusic.init.BlockInit;
import com.kaptainwutax.immersivemusic.init.ItemInit;
import com.kaptainwutax.immersivemusic.util.interfaces.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockBase extends Block implements IHasModel {

	public BlockBase(String name, Material materialIn) {
		
		super(materialIn);
		
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.MISC);
		
		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
		
	}

	@Override
	public void registerModels() {
		
		ImmersiveMusic.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
		
	}

}
