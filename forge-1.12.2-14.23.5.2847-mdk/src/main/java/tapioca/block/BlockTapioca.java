package tapioca.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockTapioca extends Block
{
	public BlockTapioca(float hardness,float resis,SoundType sound) 
	{
		super(Material.CLAY);
		this.setHarvestLevel("axe", 0);
		this.setHardness(hardness);
		this.setResistance(resis);
		this.setSoundType(sound);
	}
}
