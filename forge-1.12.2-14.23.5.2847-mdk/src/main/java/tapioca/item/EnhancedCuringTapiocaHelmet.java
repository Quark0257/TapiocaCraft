package tapioca.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import tapioca.TapiocaMod;

public class EnhancedCuringTapiocaHelmet extends EnhancedTapiocaArmor
{
	public static final Map<Item,Integer> tapiocas = new HashMap<Item,Integer>();

	public EnhancedCuringTapiocaHelmet() 
	{
		super(TapiocaMod.armorEnhancedTapioca, 3, EntityEquipmentSlot.HEAD);
	}
	
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(TextFormatting.GRAY + I18n.format("tapioca.allowCuring"));
	}
	
	public void onArmorTick(World world,EntityPlayer player,ItemStack stack)
	{
		if(!world.isRemote && EnhancedFlightableTapiocaChestplate.isArmorFullTapioca(player))
		{
			List<PotionEffect> effects = new ArrayList<PotionEffect>();
			for(PotionEffect effect : player.getActivePotionEffects())
			{
				if(effect.getPotion().isBadEffect())
				{
					effects.add(effect);
				}
			}
			
			for(PotionEffect effect : effects)
			{
				player.removePotionEffect(effect.getPotion());
			}
		}
	}
}
