package tapioca.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import tapioca.TapiocaMod;

public class EnhancedUnfireTapiocaLeggings extends EnhancedTapiocaArmor
{
	public static final Map<Item,Integer> tapiocas = new HashMap<Item,Integer>();

	public EnhancedUnfireTapiocaLeggings() 
	{
		super(TapiocaMod.armorEnhancedTapioca, 3, EntityEquipmentSlot.LEGS);
	}
	
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(TextFormatting.GRAY + I18n.format("tapioca.allowUnfire"));
	}
	
	public void onArmorTick(World world,EntityPlayer player,ItemStack stack)
	{
		if(!world.isRemote && EnhancedFlightableTapiocaChestplate.isArmorFullTapioca(player))
		{
			if(player.isBurning()) player.extinguish();
			player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE,1,0,false,false));
			if(player.ticksExisted % 20 == 0 && player.getFoodStats().getFoodLevel() < 16) player.getFoodStats().addStats(1, 2.0F);
		}
	}
}
