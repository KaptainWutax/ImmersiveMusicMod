package com.kaptainwutax.immersivemusic.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.registries.IForgeRegistry;

public class ItemInit {
	
	public static void register(IForgeRegistry<Item> registry) {
        registry.registerAll();
    }

    public static void registerModels() {
        
    }
    
}
