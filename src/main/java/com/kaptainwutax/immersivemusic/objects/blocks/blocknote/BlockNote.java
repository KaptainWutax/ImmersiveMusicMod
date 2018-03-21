package com.kaptainwutax.immersivemusic.objects.blocks.blocknote;

import java.util.Random;

import com.kaptainwutax.immersivemusic.ImmersiveMusic;
import com.kaptainwutax.immersivemusic.init.BlockInit;
import com.kaptainwutax.immersivemusic.init.ItemInit;
import com.kaptainwutax.immersivemusic.util.handlers.GuiHandler;
import com.kaptainwutax.immersivemusic.util.interfaces.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockNote extends Block implements IHasModel, ITileEntityProvider {

	public BlockNote(String name, Material materialIn) {
		
		super(materialIn);
		
		setSoundType(SoundType.METAL);
		setHardness(5.0F);
		setResistance(18000000F);
		setHarvestLevel("pickaxe", 0);
		setLightLevel(0F);
		setLightOpacity(15);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.MISC);
		
		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
		
	}

	@Override
	public void registerModels() {
		
		ImmersiveMusic.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
		
	} 

    @Override
    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
        
    	return false;
    	
    }
    

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		
		return Item.getItemFromBlock(BlockInit.BLOCK_NOTE);
		
	}
	
	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		
		return new ItemStack(BlockInit.BLOCK_NOTE);
		
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {

		return new BlockNoteTileEntity();
		
	}
	
	//CUSTOM
	   @Override
	    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
	        
	    	if (worldIn.isRemote) {
	           
	    		playerIn.openGui(ImmersiveMusic.instance, GuiHandler.BLOCK_NOTE, worldIn, pos.getX(), pos.getY(), pos.getZ());
	    		
	        } else {
	           
	        	return false;
	        	
	        }
	        
	    	return true;
	    }

}
	

