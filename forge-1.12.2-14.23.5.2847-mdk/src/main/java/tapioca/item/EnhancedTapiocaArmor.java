package tapioca.item;


import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.collect.Multimap;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class EnhancedTapiocaArmor extends TapiocaArmor
{
	public static final UUID[] ARMOR = new UUID[] {UUID.fromString("e35a11d7-966d-4168-9370-71e2c23ec9d1"),UUID.fromString("0bbe463b-6273-4bb7-a3b9-7fa4c7b60fea"),UUID.fromString("cf9b8eea-71f1-488a-a9bd-7a2dcec83aa4"),UUID.fromString("abdb4acf-1969-491a-b61a-c1e8fd0af6d3")};
	
	public EnhancedTapiocaArmor(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) 
	{
		super(materialIn, renderIndexIn, equipmentSlotIn);
	}
	
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if(stack.getTagCompound() == null || !stack.getTagCompound().getBoolean("Unbreakable"))
		{
			tooltip.add(TextFormatting.BLUE + I18n.format("item.unbreakable"));
		}
	}
	
	public int getMaxDamage(ItemStack stack)
	{
		return 0;
	}
	
	public boolean hasEffect(ItemStack item)
	{
		return true;
	}
	
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
