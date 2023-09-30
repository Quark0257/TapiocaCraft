package tapioca.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class EnhancedTapiocaPickaxe extends TapiocaPickaxe
{

	public EnhancedTapiocaPickaxe(ToolMaterial material) {
		super(material);
		// TODO Auto-generated constructor stub
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
}
