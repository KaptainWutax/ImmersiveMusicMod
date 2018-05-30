package com.kaptainwutax.immersivemusic;

import com.kaptainwutax.immersivemusic.proxy.CommonProxy;
import com.kaptainwutax.immersivemusic.util.Reference;
import com.kaptainwutax.immersivemusic.util.handlers.RegistryHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.IFixableData;
import net.minecraftforge.common.util.ModFixs;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class ImmersiveMusic {

    public static final int DATA_FIXER_VERSION = 1;
    @Instance
    public static ImmersiveMusic instance;
    @SidedProxy(clientSide = Reference.CLIENT, serverSide = Reference.COMMON)
    public static CommonProxy proxy;
    public static Logger logger;

    @EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        RegistryHandler.preInitRegistries();
        logger = event.getModLog();
    }

    @EventHandler
    public static void init(FMLInitializationEvent event) {
        RegistryHandler.initRegistries();
        ModFixs fixes = FMLCommonHandler.instance().getDataFixer().init(Reference.MOD_ID, DATA_FIXER_VERSION);
        fixes.registerFix(FixTypes.BLOCK_ENTITY, new IFixableData() {
            // array only needs to cover legacy tile entity ids, no need to add future tile entity ids to list.
            private final String[] teNames = {"blockmidi_TE", "blocknote_TE"};
            private final String resourceLocationPrefix = Reference.MOD_ID + ":";

            @Override
            public int getFixVersion() {
                return 1;
            }

            @Override
            public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
                ResourceLocation tileEntityLocation = new ResourceLocation(compound.getString("id"));

                if ("minecraft".equals(tileEntityLocation.getResourceDomain())) {
                    for (String string : teNames) {
                        if (string.equals(tileEntityLocation.getResourcePath())) {
                            compound.setString("id", resourceLocationPrefix + string);
                            break;
                        }
                    }
                }

                return compound;
            }
        });
    }

    @EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
        RegistryHandler.postInitRegistries();
    }
}