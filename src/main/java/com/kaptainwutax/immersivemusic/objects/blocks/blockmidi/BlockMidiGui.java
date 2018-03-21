package com.kaptainwutax.immersivemusic.objects.blocks.blockmidi;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class BlockMidiGui extends GuiScreen {

	GuiButton OpenDevices;
	final int OPEN_DEVICES = 0;
	
	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
       
    	drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
       //org.lwjgl.input.Mouse.setGrabbed(false);
        
	}
	
	public void initGui() {
		
		BlockMidiInput.start();
		super.initGui();
		
	}
	
	@Override
    protected void actionPerformed(GuiButton button) throws IOException {
		
		switch(button.id) {
		
		
		}
	
		super.actionPerformed(button);
        
	}
	
    @Override
    public boolean doesGuiPauseGame() {
        
    	return false;
        
    }
	
}
