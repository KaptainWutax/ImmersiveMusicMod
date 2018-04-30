package com.kaptainwutax.immersivemusic.objects.blocks.BlockMidi;

import java.io.IOException;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiDevice.Info;

import com.kaptainwutax.immersivemusic.objects.blocks.BlockNote.BlockNoteTileEntity;
import com.kaptainwutax.immersivemusic.util.handlers.SoundsHandler;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import scala.Console;

public class BlockMidiGui extends GuiScreen {
	
	private BlockPos blockPos;
	
	public BlockMidiGui(BlockPos blockPos) {
	    this.blockPos = blockPos;
	    
	}
	
	public Info[] devices;
    MidiDevice output;
    Receiver rcvr = new MyReceiver();
	
	public boolean devicesOpen = false;

	final int DEVICE_1 = 1;
	final int DEVICE_2 = 2;
	final int DEVICE_3 = 3;
	final int DEVICE_4 = 4;
	final int DEVICE_5 = 5;
	final int DEVICE_6 = 6;
	final int DEVICE_7 = 7;
	final int DEVICE_8 = 8;
	final int DEVICE_9 = 9;
	final int DEVICE_10 = 10;
	
	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
       
    	drawDefaultBackground();
    	setText();
        super.drawScreen(mouseX, mouseY, partialTicks);
       //org.lwjgl.input.Mouse.setGrabbed(false);
        
	}
	
	public void setText() {
		
		int centerX = (width / 2);
	    int centerY = (height / 2);
		
		if (devicesOpen) {
			
			fontRenderer.drawString("Receiver is open, listening...", centerX - 95, centerY - 45, 0x747c83);
			
		}
		else if (!devicesOpen) {
			
			fontRenderer.drawString("Reciever is close.", centerX - 95, centerY - 45, 0x747c83);
			
		}
		
	}

	public void initGui() {
				
		devices = MidiSystem.getMidiDeviceInfo();
		
		int centerX = (width / 2) - 100;
	    int centerY = (height / 2) - 10;
	
		buttonList.add(new GuiButton(0, centerX, centerY - 80, 200, 20, "Open Reciever"));    		
		buttonList.add(new GuiButton(1, centerX, centerY - 58, 200, 20, "Close Reciever"));  
		
		super.initGui();
		
	}
	
	@Override
    protected void actionPerformed(GuiButton button) throws IOException {
		
		if(button.id == 0 && devicesOpen == false) {

            devicesOpen = true; 
            
			try {
				rcvr = new MyReceiver();
	            MidiSystem.getTransmitter().setReceiver(rcvr);
	            initDevices();                        
            } catch (MidiUnavailableException e) {        	
                e.printStackTrace();               
            }         
             
		} else if (button.id == 1 && devicesOpen == true) {
			
	        devicesOpen = false;
			
			for (int i = 0 ; i < devices.length ; i++) {				
				try {						
					MidiSystem.getMidiDevice(devices[i]).close();
					rcvr.close();					
				} catch (MidiUnavailableException e) {
					e.printStackTrace();
				}				
			}
			
		}
		
	
		super.actionPerformed(button);
        
	}
	
    public void initDevices() {
        
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

	@Override
    public boolean doesGuiPauseGame() {
        
    	return false;
        
    }
	
    @Override   
	public void onGuiClosed() {
    	
        devicesOpen = false;
		
		for (int i = 0 ; i < devices.length ; i++) {				
			try {						
				MidiSystem.getMidiDevice(devices[i]).close();
				rcvr.close();					
			} catch (MidiUnavailableException e) {
				e.printStackTrace();
			}				
		}
		
	}
    
    private class MyReceiver implements Receiver  {
        
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
           //System.out.println(b[1] & 0xFF);
          
           if (SoundsHandler.NOTE_SOUND[1][b[1] & 0xFF] != null)
         	  Minecraft.getMinecraft().world.playSound(blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundsHandler.NOTE_SOUND[1][b[1] & 0xFF], SoundCategory.AMBIENT, 1F, 1F, false);
          
          }             
          rcvr.send(message, timeStamp);

       }

       @Override
       public void close() {
		rcvr.close();		
	   }
       
    }
        
	
}
