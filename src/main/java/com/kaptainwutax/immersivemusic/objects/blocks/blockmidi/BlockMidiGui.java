package com.kaptainwutax.immersivemusic.objects.blocks.blockmidi;

import java.io.IOException;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;

import com.kaptainwutax.immersivemusic.util.handlers.PacketHandler;
import com.kaptainwutax.immersivemusic.util.handlers.SoundsHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiPageButtonList.GuiResponder;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlider;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMidiGui extends GuiScreen {
	
	private BlockMidiTileEntity TE;
	private BlockPos blockPos;
	private World world;
	
	public BlockMidiGui(World world, BlockMidiTileEntity te, BlockPos blockPos) {
		
		this.TE = te;
		this.world = world;
	    this.blockPos = blockPos;
	    
	}
	
	public Info[] devices;
    MidiDevice output;
    Receiver rcvr = new MyReceiver();
	
	boolean devicesOpen = false;
	boolean filePlaying = false;	
	
	int noteToPlay;
	int instrumentToPlay;
	float volume;
	float speed;
	Boolean isFilePlaying;

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
	
	String instrumentText = "Loading...";
	String fileNameText;
	
	GuiButton instrument;
	GuiButton instrument_increment;
	GuiButton instrument_decrement;
	
	final int INSTRUMENT = 100;
	final int INSTRUMENT_DECREMENT = 13;
	final int INSTRUMENT_INCREMENT = 14;
	
	GuiSlider volume_slider;
	GuiSlider speed_slider;
	
	GuiTextField text;
	
	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
       
    	drawDefaultBackground();
    	setText();
    	this.text.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
		instrumentToPlay = TE.getInstrumentToPlay();
		volume = TE.getVolume();
		speed = TE.getSpeed();
		fileNameText = TE.getFileName();
		isFilePlaying = TE.getIsFilePlaying();
		
		if(volume != volume_slider.getSliderValue()) {
			
			volume = volume_slider.getSliderValue();
			TE.setVolume(volume);
			PacketHandler.INSTANCE.sendToServer(new BlockMidiPacket(blockPos, TE.getInstrumentToPlay(), 1000, TE.getVolume(), TE.getSpeed(), TE.getIsFilePlaying()));
			
		}
		
		if(speed != speed_slider.getSliderValue()) {
			
			speed = speed_slider.getSliderValue();
			TE.setSpeed(speed);
			PacketHandler.INSTANCE.sendToServer(new BlockMidiPacket(blockPos, TE.getInstrumentToPlay(), 1000, TE.getVolume(), TE.getSpeed(), TE.getIsFilePlaying()));
			
		}
        
	}
	
	public void setText() {
		
		int centerX = (width / 2) - ((20) / 2);
	    int centerY = (height / 2) - ((20) / 2);
		
		if (devicesOpen) {
			
			fontRenderer.drawString("Receiver is open, listening...", centerX - 85, centerY - 35, 0x747c83);
			
		}
		else if (!devicesOpen) {
			
			fontRenderer.drawString("Reciever is close.", centerX - 85, centerY - 35, 0x747c83);
			
		}
		
		//UPDATE INSTRUMENT TEXT
		switch (instrumentToPlay) {
				
		case 0 : instrumentText = "Piano";
			break;
				
		case 1 : instrumentText =  "Violon Staccados";
			break;
					
		case 2 : instrumentText = "French Horn";
			break;
				
		}
		
		buttonList.remove(instrument);
     	buttonList.add(instrument = new GuiButton(INSTRUMENT, centerX - 90, 100, 200, 20, instrumentText));
     	
     	if(fileNameText == "") {fontRenderer.drawString("Storing no MIDI file." + fileNameText, centerX - 85, centerY + 115, 0x747c83);}
     	else {fontRenderer.drawString("Storing MIDI file " + fileNameText, centerX - 85, centerY + 115, 0x747c83);}
		
	}

	public void initGui() {
	
		
		int centerX = (width / 2) - 100;
	    int centerY = (height / 2) - 10;
				
		devices = MidiSystem.getMidiDeviceInfo();
	
		buttonList.add(new GuiButton(0, centerX, centerY - 80, 200, 20, "Open Reciever"));    		
		buttonList.add(new GuiButton(1, centerX, centerY - 58, 200, 20, "Close Reciever"));  
		
		buttonList.add(instrument_increment = new GuiButton(INSTRUMENT_INCREMENT, centerX + 200, 100 , 20, 20, ">"));
     	buttonList.add(instrument_decrement = new GuiButton(INSTRUMENT_DECREMENT, centerX - 20, 100, 20, 20, "<"));
     	
     	buttonList.remove(instrument);
     	
		buttonList.add(volume_slider = new GuiSlider(new GuiResponder() {

			@Override
			public void setEntryValue(int id, boolean value) {}

			@Override
			public void setEntryValue(int id, float value) {}

			@Override
			public void setEntryValue(int id, String value) {}

		}, 888, centerX - 50, 122, "Volume", 0, 1, TE.getVolume(), null));
		
		buttonList.add(speed_slider = new GuiSlider(new GuiResponder() {

			@Override
			public void setEntryValue(int id, boolean value) {}

			@Override
			public void setEntryValue(int id, float value) {}

			@Override
			public void setEntryValue(int id, String value) {}

		}, 889, centerX + 100, 122, "Speed", 0, 1, TE.getSpeed(), null));
		
		text = new GuiTextField(15, fontRenderer, centerX, 158, 200, 20);
	    text.setMaxStringLength(23);
	    text.setText("");
        this.text.setFocused(true);
        
    	buttonList.add(new GuiButton(16, centerX, 184, 200, 20, "Add Midi"));	
    	buttonList.add(new GuiButton(17, centerX, 206, 100, 20, "Play"));
    	buttonList.add(new GuiButton(18, centerX + 100, 206, 100, 20, "Stop"));	
		
		super.initGui();
		
	}
	
	@Override
	protected void keyTyped(char par1, int par2) throws IOException {
        super.keyTyped(par1, par2);
        this.text.textboxKeyTyped(par1, par2);
    }

 
	@Override
    public void updateScreen() {
        super.updateScreen();
        this.text.updateCursorCounter();
    }
	   
	@Override
    protected void actionPerformed(GuiButton button) throws IOException {
		
		if (button.id == 12)
    		return;
    		
    	if (button.id == 13) {
    		instrumentToPlay --;
    		TE.setInstrumentToPlay(instrumentToPlay);
    	}
    	
    	if (button.id == 14) {
    		instrumentToPlay ++;
    		TE.setInstrumentToPlay(instrumentToPlay);
    	}
    	
    	if(button.id == 16) {
    		isFilePlaying = false;
      		TE.setIsFilePlaying(isFilePlaying);
    		new Thread(new BlockMidiWriteThread(world, TE, text.getText().toCharArray())).start();
      		text.setText("");
    	}
    	
    	if(button.id == 17) {
    		isFilePlaying = true;
    		TE.setIsFilePlaying(isFilePlaying);
       		PacketHandler.INSTANCE.sendToServer(new BlockMidiReadThreadPacket(blockPos));
    	}
    	
    	if(button.id == 18) {
    		isFilePlaying = false;
    		TE.setIsFilePlaying(isFilePlaying);
    	}
    	
    	if (instrumentToPlay < 0) {
    		instrumentToPlay = 0;
    		TE.setInstrumentToPlay(instrumentToPlay);
    	}
    	
    	if (instrumentToPlay > SoundsHandler.instrumentsAmount - 1) {
    		instrumentToPlay = SoundsHandler.instrumentsAmount - 1;
			TE.setInstrumentToPlay(instrumentToPlay);
    	}
		
		
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
		
		PacketHandler.INSTANCE.sendToServer(new BlockMidiPacket(blockPos, TE.getInstrumentToPlay(), 1000, TE.getVolume(), TE.getSpeed(), TE.getIsFilePlaying()));
		super.actionPerformed(button);
        
	}
	
    public void initDevices() {
        
    	for (MidiDevice.Info info: devices) {
            
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
    
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0)
        {
            for (int i = 0; i < this.buttonList.size(); ++i)
            {
                GuiButton guibutton = this.buttonList.get(i);

                if (guibutton.mousePressed(this.mc, mouseX, mouseY))
                {
                    net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Pre event = new net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Pre(this, guibutton, this.buttonList);
                    if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event))
                        break;
                    guibutton = event.getButton();
                    this.selectedButton = guibutton;
                    this.actionPerformed(guibutton);
                    if (this.equals(this.mc.currentScreen))
                        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Post(this, event.getButton(), this.buttonList));
                }
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

           if (SoundsHandler.NOTE_SOUND[TE.getInstrumentToPlay()][b[1] & 0xFF] != null) {
        	   Minecraft.getMinecraft().addScheduledTask(() -> {
                   Minecraft.getMinecraft().world.playSound(blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundsHandler.NOTE_SOUND[TE.getInstrumentToPlay()][b[1] & 0xFF], SoundCategory.RECORDS, TE.getVolume(), 1F, false);
        		 });
               PacketHandler.INSTANCE.sendToServer(new BlockMidiPacket(blockPos, TE.getInstrumentToPlay(), b[1] & 0xFF, b[2] * TE.getVolume(), TE.getSpeed(), TE.getIsFilePlaying()));

           }
           
          }             

       }

       @Override
       public void close() {
		rcvr.close();		
	   }
       
    }
        
	
}
