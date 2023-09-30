package tapioca.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockCassava extends ItemBlock
{
	public ItemBlockCassava(Block block) 
	{
		super(block);
	}
	
	public int getItemBurnTime(ItemStack stack)
	{
		return 1000;
	}
}
