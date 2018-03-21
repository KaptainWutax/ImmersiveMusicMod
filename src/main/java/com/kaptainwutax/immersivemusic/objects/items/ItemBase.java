package com.kaptainwutax.immersivemusic.objects.items;

import com.kaptainwutax.immersivemusic.ImmersiveMusic;
import com.kaptainwutax.immersivemusic.init.ItemInit;
import com.kaptainwutax.immersivemusic.util.interfaces.IHasModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel {

	public ItemBase(String name) {
		
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.MISC);
		
		ItemInit.ITEMS.add(this);
		
	}
	
	@Override
	public void registerModels() {
		
		ImmersiveMusic.proxy.registerItemRenderer(this, 0, "inventory");
		
	}

}
