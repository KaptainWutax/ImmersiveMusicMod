package com.kaptainwutax.immersivemusic.objects.blocks.blockmidi;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;

import com.kaptainwutax.immersivemusic.util.handlers.SoundsHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import scala.actors.UncaughtException;

public class BlockMidiInput {
	
	private static World world = Minecraft.getMinecraft().world;

	public MidiDevice input;
    public static MidiDevice output;           
 
    public static void start()  {
         
        init();  
     
          try {
             output.open();
             Receiver rcvr = new MyReceiver();
             MidiSystem.getTransmitter().setReceiver(rcvr);
          }
          catch (Exception e) {
             e.printStackTrace();
             
          }
     
       }
   
       public static void init() {
 
           MidiDevice.Info[] devices;
 
           devices = MidiSystem.getMidiDeviceInfo();
 
           for (MidiDevice.Info info: devices) {
           
               System.out.println(" Name: " + info.toString() + ", Decription: " + info.getDescription() + ", Vendor: " + info.getVendor());
           
               try {
                   output = MidiSystem.getMidiDevice(info);
               } catch (MidiUnavailableException e) {
                   e.printStackTrace();
               }
               
               if (! output.isOpen()) {
                   try {
                       output.open();
                   } catch (MidiUnavailableException e) {
                       e.printStackTrace();
                   }
               }       
        }
           
       }
           
       private static class MyReceiver implements Receiver  {
          
    	   Receiver rcvr;
    	   
          public MyReceiver() {
        	  
             try {
            	 
             this.rcvr = MidiSystem.getReceiver();
             
             } catch (MidiUnavailableException mue) {
            	 
                mue.printStackTrace();
             }
          }
           
          @Override
          public void send(MidiMessage message, long timeStamp) {
             
        	  byte[] b = message.getMessage();
        	
             if((b[0] & 0xFF) == 144) {
              System.out.println(b[1] & 0xFF);
             
              if (SoundsHandler.NOTE_SOUND[1][b[1] & 0xFF] != null)
            	  world.playSound(0F,0F,0F, SoundsHandler.NOTE_SOUND[1][b[1] & 0xFF], SoundCategory.AMBIENT, 1F, 1F, true);
             
             }             
             
             rcvr.send(null, 0);

          }
     
          @Override
          public void close() {
             rcvr.close();
          }
          
       } 
       
}
