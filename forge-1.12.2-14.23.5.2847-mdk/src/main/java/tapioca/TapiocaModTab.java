package tapioca;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TapiocaModTab extends CreativeTabs 
{
  public TapiocaModTab(String label) 
  {
    super(label);
  }

  @Override
  public ItemStack getTabIconItem() 
  {
	return new ItemStack(TapiocaMod.tapioca_milk_tea);
  }
}
