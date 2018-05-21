package com.kaptainwutax.immersivemusic.util.handlers;

import com.kaptainwutax.immersivemusic.util.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SoundsHandler {

    public static final String[] NOTE_NAMES = {"c", "c#", "d", "d#", "e", "f", "f#", "g", "g#", "a", "a#", "b"};
	public static int instrumentsAmount = 3;
	static int violon_staccados_id = 1; //3g to 7a / 55 to 105
	static int piano_id = 0; //1a to 8b / 33 to 119
	
	public static SoundEvent[][] NOTE_SOUND = new SoundEvent[instrumentsAmount][127]; 
	
	public static void registerSounds() {
		
		for(int i = 55 ; i <= 105 ; i++) {
			
			NOTE_SOUND[violon_staccados_id][i] = registerSound("notes.violon_staccados." + ((i / 12) - 1) + NOTE_NAMES[(i % 12)]);
			
		}
		
		for(int i = 33 ; i <= 119 ; i++) {
			
			NOTE_SOUND[piano_id][i] = registerSound("notes.piano." + ((i / 12) - 1) + NOTE_NAMES[(i % 12)]);
			
		}

		/*
		NOTE_SOUND[violon_staccados_id][55] = registerSound("notes.violon_staccados.3g");
		NOTE_SOUND[violon_staccados_id][56] = registerSound("notes.violon_staccados.3g#");
		NOTE_SOUND[violon_staccados_id][57] = registerSound("notes.violon_staccados.3a");
		NOTE_SOUND[violon_staccados_id][58] = registerSound("notes.violon_staccados.3a#");
		NOTE_SOUND[violon_staccados_id][59] = registerSound("notes.violon_staccados.3b");
		
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
		
		NOTE_SOUND[violon_staccados_id][72] = registerSound("notes.violon_staccados.5c");
		NOTE_SOUND[violon_staccados_id][73] = registerSound("notes.violon_staccados.5c#");
		NOTE_SOUND[violon_staccados_id][74] = registerSound("notes.violon_staccados.5d");
		NOTE_SOUND[violon_staccados_id][75] = registerSound("notes.violon_staccados.5d#");
		NOTE_SOUND[violon_staccados_id][76] = registerSound("notes.violon_staccados.5e");
		NOTE_SOUND[violon_staccados_id][77] = registerSound("notes.violon_staccados.5f");
		NOTE_SOUND[violon_staccados_id][78] = registerSound("notes.violon_staccados.5f#");
		NOTE_SOUND[violon_staccados_id][79] = registerSound("notes.violon_staccados.5g");
		NOTE_SOUND[violon_staccados_id][80] = registerSound("notes.violon_staccados.5g#");
		NOTE_SOUND[violon_staccados_id][81] = registerSound("notes.violon_staccados.5a");
		NOTE_SOUND[violon_staccados_id][82] = registerSound("notes.violon_staccados.5a#");
		NOTE_SOUND[violon_staccados_id][83] = registerSound("notes.violon_staccados.5b");
		
		NOTE_SOUND[violon_staccados_id][84] = registerSound("notes.violon_staccados.6c");
		NOTE_SOUND[violon_staccados_id][85] = registerSound("notes.violon_staccados.6c#");
		NOTE_SOUND[violon_staccados_id][86] = registerSound("notes.violon_staccados.6d");
		NOTE_SOUND[violon_staccados_id][87] = registerSound("notes.violon_staccados.6d#");
		NOTE_SOUND[violon_staccados_id][88] = registerSound("notes.violon_staccados.6e");
		NOTE_SOUND[violon_staccados_id][89] = registerSound("notes.violon_staccados.6f");
		NOTE_SOUND[violon_staccados_id][90] = registerSound("notes.violon_staccados.6f#");
		NOTE_SOUND[violon_staccados_id][91] = registerSound("notes.violon_staccados.6g");
		NOTE_SOUND[violon_staccados_id][92] = registerSound("notes.violon_staccados.6g#");
		NOTE_SOUND[violon_staccados_id][93] = registerSound("notes.violon_staccados.6a");
		NOTE_SOUND[violon_staccados_id][94] = registerSound("notes.violon_staccados.6a#");
		NOTE_SOUND[violon_staccados_id][95] = registerSound("notes.violon_staccados.6b");
		
		NOTE_SOUND[violon_staccados_id][96] = registerSound("notes.violon_staccados.7c");
		NOTE_SOUND[violon_staccados_id][97] = registerSound("notes.violon_staccados.7c#");
		NOTE_SOUND[violon_staccados_id][98] = registerSound("notes.violon_staccados.7d");
		NOTE_SOUND[violon_staccados_id][99] = registerSound("notes.violon_staccados.7d#");
		NOTE_SOUND[violon_staccados_id][100] = registerSound("notes.violon_staccados.7e");
		NOTE_SOUND[violon_staccados_id][101] = registerSound("notes.violon_staccados.7f");
		NOTE_SOUND[violon_staccados_id][102] = registerSound("notes.violon_staccados.7f#");
		NOTE_SOUND[violon_staccados_id][103] = registerSound("notes.violon_staccados.7g");
		NOTE_SOUND[violon_staccados_id][104] = registerSound("notes.violon_staccados.7g#");
		NOTE_SOUND[violon_staccados_id][105] = registerSound("notes.violon_staccados.7a");
		*/
	
	}
	
	public static SoundEvent registerSound(String name) {
		
		ResourceLocation location = new ResourceLocation(Reference.MOD_ID, name);
		SoundEvent event = new SoundEvent(location);
		event.setRegistryName(name);
		ForgeRegistries.SOUND_EVENTS.register(event);
		return event;
		
	}
	
}
