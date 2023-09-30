package tapioca.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Multimap;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import tapioca.TapiocaMod;

public class EnhancedSpeedTapiocaBoots extends EnhancedTapiocaArmor
{
	public static final Map<Item,Integer> tapiocas = new HashMap<Item,Integer>();
	public EnhancedSpeedTapiocaBoots() 
	{
		super(TapiocaMod.armorEnhancedTapioca, 3, EntityEquipmentSlot.FEET);
	}
	
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(TextFormatting.GRAY + I18n.format("tapioca.allowSpeed"));
	}
	
	public void onArmorTick(World world,EntityPlayer player,ItemStack stack)
	{
		if(!world.isRemote && EnhancedFlightableTapiocaChestplate.isArmorFullTapioca(player))
		{
			player.getEntityData().setBoolean("isTapiocaBoots",true);
		}
		
		if(EnhancedFlightableTapiocaChestplate.isArmorFullTapioca(player))
		{
			float speed = (player.capabilities.isFlying) ? 1.0F : 0.3F;
			player.moveRelative(0, 0, 1f, Math.signum(player.moveForward) * speed * 0.7f);
			player.moveRelative(1f, 0, 0, Math.signum(player.moveStrafing) * speed * 0.5f);
		}
	}
	/*
	private static float getMoveVertical()
	{
		float jump = TapiocaMod.proxy.isJumpKeyPressing() ? 1.0f : 0.0f;
		float sneak = TapiocaMod.proxy.isSneakKeyPressing() ? -1.0f : 0.0f;
		return jump + sneak;
	}
	*/
	
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == this.armorType)
        {
        	multimap.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(),new AttributeModifier(ARMOR[equipmentSlot.getIndex()],"Armor Knockback Resistance",0.25,0));
        }

        return multimap;
    }
}
