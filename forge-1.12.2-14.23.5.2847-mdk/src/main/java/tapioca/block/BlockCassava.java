package tapioca.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockCassava extends Block 
{
	public BlockCassava() 
	{
		super(Material.CLAY);
		this.setHarvestLevel("axe", 0);
		this.setHardness(3.0F);
		this.setResistance(5.0F);
		this.setSoundType(SoundType.WOOD);
	}
}
