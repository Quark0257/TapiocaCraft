package tapioca.item;

import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemTapiocaIceTea extends ItemFood
{
	public ItemTapiocaIceTea() 
	{
		super(7,1.0F, false);
		this.setAlwaysEdible();
	}	
	
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
	{
		if(!worldIn.isRemote)
		{
			Random rand = new Random();
			if(rand.nextInt(100) < 5)
			{
				entityLiving.addPotionEffect(new PotionEffect(MobEffects.NAUSEA,600,0));
				entityLiving.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS,200,0));
			}
			
			entityLiving.addPotionEffect(new PotionEffect(MobEffects.HASTE,2400,1));
			entityLiving.addPotionEffect(new PotionEffect(MobEffects.SPEED,2400,3));
			entityLiving.addPotionEffect(new PotionEffect(MobEffects.REGENERATION,600,0));
			
			if(entityLiving instanceof EntityPlayer)
			{
				((EntityPlayer)entityLiving).addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
			}
		}
		
		return super.onItemUseFinish(stack, worldIn, entityLiving);
	}
	
	public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.DRINK;
    }
}
