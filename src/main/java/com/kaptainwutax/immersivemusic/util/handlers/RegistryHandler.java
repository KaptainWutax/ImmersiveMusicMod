package com.kaptainwutax.immersivemusic.util.handlers;

import com.kaptainwutax.immersivemusic.ImmersiveMusic;
import com.kaptainwutax.immersivemusic.init.BlockInit;
import com.kaptainwutax.immersivemusic.init.ItemInit;
import com.kaptainwutax.immersivemusic.objects.blocks.blocknote.BlockNoteTileEntity;
import com.kaptainwutax.immersivemusic.util.interfaces.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@EventBusSubscriber
public class RegistryHandler {

	public static void preInitRegistries()
	{

	}
	
	public static void initRegistries()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(ImmersiveMusic.instance, new GuiHandler());
		SoundsHandler.registerSounds();
		GameRegistry.registerTileEntity(BlockNoteTileEntity.class, "blocknote_TE");
	}
	
	public static void postInitRegistries()
	{

	}
	
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));
	}
	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(BlockInit.BLOCKS.toArray(new Block[0]));

	}
	
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event)
	{
		for(Item item : ItemInit.ITEMS)
		{
			if(item instanceof IHasModel)
			{
				((IHasModel)item).registerModels();
			}
		}
		
		for(Block block : BlockInit.BLOCKS)
		{
			if(block instanceof IHasModel)
			{
				((IHasModel)block).registerModels();
			}
		}
	}
	
}
