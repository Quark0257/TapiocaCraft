package tapioca.item;

import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemTapioca extends ItemFood
{
	public ItemTapioca() 
	{
		super(4,0.25F, false);
	}
	
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
	{
		if(!worldIn.isRemote)
		{
			Random rand = new Random();
			if(rand.nextInt(100) < 2)
			{
				entityLiving.addPotionEffect(new PotionEffect(MobEffects.NAUSEA,200,0));
			}
		}
		
		return super.onItemUseFinish(stack, worldIn, entityLiving);
	}
}
