package com.kaptainwutax.immersivemusic.util.handlers;

import com.kaptainwutax.immersivemusic.ImmersiveMusic;
import com.kaptainwutax.immersivemusic.init.BlockInit;
import com.kaptainwutax.immersivemusic.init.ItemInit;
import com.kaptainwutax.immersivemusic.objects.blocks.blockmidi.BlockMidiTileEntity;
import com.kaptainwutax.immersivemusic.objects.blocks.blocknote.BlockNoteTileEntity;
import com.kaptainwutax.immersivemusic.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@EventBusSubscriber
public class RegistryHandler {

    public static void preInitRegistries() {

    }

    public static void initRegistries() {
        PacketHandler.registerMessages(Reference.MOD_ID);
        NetworkRegistry.INSTANCE.registerGuiHandler(ImmersiveMusic.instance, new GuiHandler());
        GameRegistry.registerTileEntity(BlockNoteTileEntity.class, Reference.MOD_ID + ":blocknote_TE");
        GameRegistry.registerTileEntity(BlockMidiTileEntity.class, Reference.MOD_ID + ":blockmidi_TE");
    }

    public static void postInitRegistries() {

    }

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        ItemInit.register(event.getRegistry());
        BlockInit.registerItemBlocks(event.getRegistry());
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        BlockInit.register(event.getRegistry());
    }

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
        ItemInit.registerModels();
        BlockInit.registerModels();
    }

    @SubscribeEvent
    public static void onSoundRegister(RegistryEvent.Register<SoundEvent> event) {
        SoundsHandler.registerSounds();
    }
}