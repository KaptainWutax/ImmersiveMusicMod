package com.kaptainwutax.immersivemusic.util.handlers;

import com.kaptainwutax.immersivemusic.util.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SoundsHandler {

	public static int instrumentsAmount = 3;
	static int violon_staccados_id = 1;
	
	public static SoundEvent[][] NOTE_SOUND = new SoundEvent[instrumentsAmount][127];
	
	public static void registerSounds() {
		NOTE_SOUND[violon_staccados_id][60] = registerSound("notes.violon_staccados.4c");
		NOTE_SOUND[violon_staccados_id][61] = registerSound("notes.violon_staccados.4c#");
		NOTE_SOUND[violon_staccados_id][62] = registerSound("notes.violon_staccados.4d");
		NOTE_SOUND[violon_staccados_id][63] = registerSound("notes.violon_staccados.4d#");
		NOTE_SOUND[violon_staccados_id][64] = registerSound("notes.violon_staccados.4e");
		NOTE_SOUND[violon_staccados_id][65] = registerSound("notes.violon_staccados.4f");
		NOTE_SOUND[violon_staccados_id][66] = registerSound("notes.violon_staccados.4f#");
		NOTE_SOUND[violon_staccados_id][67] = registerSound("notes.violon_staccados.4g");
		NOTE_SOUND[violon_staccados_id][68] = registerSound("notes.violon_staccados.4g#");
		NOTE_SOUND[violon_staccados_id][69] = registerSound("notes.violon_staccados.4a");
		NOTE_SOUND[violon_staccados_id][70] = registerSound("notes.violon_staccados.4a#");
		NOTE_SOUND[violon_staccados_id][71] = registerSound("notes.violon_staccados.4b");
		
	}
	
	public static SoundEvent registerSound(String name) {
		
		ResourceLocation location = new ResourceLocation(Reference.MOD_ID, name);
		SoundEvent event = new SoundEvent(location);
		event.setRegistryName(name);
		ForgeRegistries.SOUND_EVENTS.register(event);
		return event;
		
	}
	
}
