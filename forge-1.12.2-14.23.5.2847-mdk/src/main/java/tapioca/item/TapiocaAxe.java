package tapioca.item;

import net.minecraft.item.ItemAxe;

public class TapiocaAxe extends ItemAxe
{
	public TapiocaAxe(ToolMaterial material) 
	{
		super(material, material.getAttackDamage() + 5.0F, -4 + (float)((int)((float)material.getHarvestLevel() + 8.0F)) / 10.0F);
	}
}
