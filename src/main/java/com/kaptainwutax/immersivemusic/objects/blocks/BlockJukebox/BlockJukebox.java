package com.kaptainwutax.immersivemusic.objects.blocks.BlockJukebox;

import com.kaptainwutax.immersivemusic.ImmersiveMusic;
import com.kaptainwutax.immersivemusic.init.BlockInit;
import com.kaptainwutax.immersivemusic.init.ItemInit;
import com.kaptainwutax.immersivemusic.util.interfaces.IHasModel;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockJukebox extends net.minecraft.block.BlockJukebox implements IHasModel {

	   public BlockJukebox(String name, Material material) {
		   
		    super();
	        setDefaultState(this.blockState.getBaseState().withProperty(HAS_RECORD, Boolean.valueOf(false)));	        
		    setSoundType(SoundType.METAL);
		   	setHardness(5.0F);
			setResistance(18000000F);
			setHarvestLevel("pickaxe", 0);
			setLightLevel(0F);
			setLightOpacity(15);			
	        setRegistryName(name);
	        setUnlocalizedName(name);
	        setCreativeTab(CreativeTabs.MISC);
			//BlockInit.BLOCKS.add(this);
			//ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
			
		}

		@Override
		public void registerModels() {
			
			ImmersiveMusic.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
			
		}

	
	public static final PropertyBool HAS_RECORD = PropertyBool.create("has_record");
	
    public static void registerFixesJukebox(DataFixer fixer) {
        fixer.registerWalker(FixTypes.BLOCK_ENTITY, new ItemStackData(BlockJukebox.BlockJukeboxTileEntity.class, new String[] {"RecordItem"}));
    }

    /**
     * Called when the block is right clicked by a player.
     */
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (((Boolean)state.getValue(HAS_RECORD)).booleanValue())
        {
            this.dropRecord(worldIn, pos, state);
            state = state.withProperty(HAS_RECORD, Boolean.valueOf(false));
            worldIn.setBlockState(pos, state, 2);
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public void insertRecord(World worldIn, BlockPos pos, IBlockState state, ItemStack recordStack) {
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof BlockJukebox.BlockJukeboxTileEntity)
        {
            ((BlockJukebox.BlockJukeboxTileEntity)tileentity).setRecord(recordStack.copy());
            worldIn.setBlockState(pos, state.withProperty(HAS_RECORD, Boolean.valueOf(true)), 2);
        }
    }
    
    private void dropRecord(World worldIn, BlockPos pos, IBlockState state) {
       
    	if (!worldIn.isRemote)
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof BlockJukebox.BlockJukeboxTileEntity)
            {
                BlockJukebox.BlockJukeboxTileEntity blockjukebox$tileentityjukebox = (BlockJukebox.BlockJukeboxTileEntity)tileentity;
                ItemStack itemstack = blockjukebox$tileentityjukebox.getRecord();

                if (!itemstack.isEmpty())
                {
                    worldIn.playEvent(1010, pos, 0);
                    worldIn.playRecord(pos, (SoundEvent)null);
                    blockjukebox$tileentityjukebox.setRecord(ItemStack.EMPTY);
                    float f = 0.7F;
                    double d0 = (double)(worldIn.rand.nextFloat() * 0.7F) + 0.15000000596046448D;
                    double d1 = (double)(worldIn.rand.nextFloat() * 0.7F) + 0.06000000238418579D + 0.6D;
                    double d2 = (double)(worldIn.rand.nextFloat() * 0.7F) + 0.15000000596046448D;
                    ItemStack itemstack1 = itemstack.copy();
                    EntityItem entityitem = new EntityItem(worldIn, (double)pos.getX() + d0, (double)pos.getY() + d1, (double)pos.getZ() + d2, itemstack1);
                    entityitem.setDefaultPickupDelay();
                    worldIn.spawnEntity(entityitem);
                }
            }
        }
    }

    /**
     * Called serverside after this block is replaced with another in Chunk, but before the Tile Entity is updated
     */
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        this.dropRecord(worldIn, pos, state);
        super.breakBlock(worldIn, pos, state);
    }

    /**
     * Spawns this Block's drops into the World as EntityItems.
     */
    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
        if (!worldIn.isRemote)
        {
            super.dropBlockAsItemWithChance(worldIn, pos, state, chance, 0);
        }
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new BlockJukebox.BlockJukeboxTileEntity();
    }
    
    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }
    
    @Override
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof BlockJukebox.BlockJukeboxTileEntity)
        {
            ItemStack itemstack = ((BlockJukebox.BlockJukeboxTileEntity)tileentity).getRecord();

            if (!itemstack.isEmpty())
            {
                return Item.getIdFromItem(itemstack.getItem()) + 1 - Item.getIdFromItem(Items.RECORD_13);
            }
        }

        return 0;
    }

    /**
     * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
     * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
     */
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(HAS_RECORD, Boolean.valueOf(meta > 0));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state) {
        return ((Boolean)state.getValue(HAS_RECORD)).booleanValue() ? 1 : 0;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {HAS_RECORD});
    }

    public static class BlockJukeboxTileEntity extends TileEntity {
            
    	private ItemStack record = ItemStack.EMPTY;
    		
    		@Override
            public void readFromNBT(NBTTagCompound compound) {
                super.readFromNBT(compound);

                if (compound.hasKey("RecordItem", 10))
                {
                    this.setRecord(new ItemStack(compound.getCompoundTag("RecordItem")));
                }
                else if (compound.getInteger("Record") > 0)
                {
                    this.setRecord(new ItemStack(Item.getItemById(compound.getInteger("Record"))));
                }
            }
    		
    		@Override
            public NBTTagCompound writeToNBT(NBTTagCompound compound) {
                super.writeToNBT(compound);

                if (!this.getRecord().isEmpty())
                {
                    compound.setTag("RecordItem", this.getRecord().writeToNBT(new NBTTagCompound()));
                }

                return compound;
            }
    		
            public ItemStack getRecord() {
                return this.record;
            }
            
            public void setRecord(ItemStack recordStack) {
                this.record = recordStack;
                this.markDirty();
            }
            
        }
    
}
