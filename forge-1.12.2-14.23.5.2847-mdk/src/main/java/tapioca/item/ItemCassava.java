package tapioca.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemCassava extends ItemFood
{

	public ItemCassava() 
	{
		super(2, 0.375F, false);
	}
	
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
	{
		if(!worldIn.isRemote)
		{
			entityLiving.addPotionEffect(new PotionEffect(MobEffects.NAUSEA,200,0));
			entityLiving.addPotionEffect(new PotionEffect(MobEffects.POISON,200,3));
		}
		return super.onItemUseFinish(stack, worldIn, entityLiving);
	}
	
	public int getItemBurnTime(ItemStack stack)
	{
		return 100;
	}
}
