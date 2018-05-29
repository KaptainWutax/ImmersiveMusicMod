package com.kaptainwutax.immersivemusic.util.handlers;

import com.kaptainwutax.immersivemusic.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SoundsHandler {

    public static final String[] NOTE_NAMES = {"c", "c#", "d", "d#", "e", "f", "f#", "g", "g#", "a", "a#", "b"};
    public static int instrumentsAmount = 3;
    public static int violon_staccados_id = 1; //3g to 7a / 55 to 105
    public static int piano_id = 0; //1a to 8b / 33 to 119

    public static SoundEvent[][] NOTE_SOUND = new SoundEvent[instrumentsAmount][127];

    public static void registerSounds() {

        for (int i = 55; i <= 105; i++) {
            NOTE_SOUND[violon_staccados_id][i] = registerSound("notes.violon_staccados." + ((i / 12) - 1) + NOTE_NAMES[(i % 12)]);
        }

        for (int i = 33; i <= 119; i++) {
            NOTE_SOUND[piano_id][i] = registerSound("notes.piano." + ((i / 12) - 1) + NOTE_NAMES[(i % 12)]);
        }

    }

    public static SoundEvent registerSound(String name) {

        ResourceLocation location = new ResourceLocation(Reference.MOD_ID, name);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(name);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return event;

    }

}
