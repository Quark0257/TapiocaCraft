package tapioca.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import tapioca.TapiocaMod;

public class EnhancedFlightableTapiocaChestplate extends EnhancedTapiocaArmor
{
	private static final int tapioca_energy = 20;
	public static final Map<Item,Integer> tapiocas = new HashMap<Item,Integer>();

	public EnhancedFlightableTapiocaChestplate() 
	{
		super(TapiocaMod.armorEnhancedTapioca, 3, EntityEquipmentSlot.CHEST);
	}
	
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		super.addInformation(stack, worldIn, tooltip, flagIn);
		int value = 0;
		if(stack.getTagCompound() != null && stack.getTagCompound().hasKey("flightEnergy",3))
		{
			value = (stack.getTagCompound().getInteger("flightEnergy")+tapioca_energy-1)/tapioca_energy;
		}
		tooltip.add(TextFormatting.BLUE + I18n.format("tapioca.storedEnergy") + " : " + value);
		tooltip.add(TextFormatting.GRAY + I18n.format("tapioca.allowFlight"));
		tooltip.add(TextFormatting.GRAY + I18n.format("tapioca.energy"));
		tooltip.add(TextFormatting.GRAY + I18n.format("item.tapioca.name") + " : " + 1);
		tooltip.add(TextFormatting.GRAY + I18n.format("tile.tapioca_block.name") + " : " + 5);
		tooltip.add(TextFormatting.GRAY + I18n.format("tile.compressed_tapioca_block.name") + " : " + 50);
		tooltip.add(TextFormatting.GRAY + I18n.format("tile.compressed_compressing_tapioca_block.name") + " : " + 500);
		tooltip.add(TextFormatting.GRAY + I18n.format("tile.overcompressed_tapioca_block.name") + " : " + 5000);
	}
	
	public void onArmorTick(World world,EntityPlayer player,ItemStack stack)
	{
		tapiocaInit();
		if(stack.getTagCompound() == null)
		{
			stack.setTagCompound(new NBTTagCompound());
		}
		
		if(!stack.getTagCompound().hasKey("flightEnergy",3))
		{
			stack.getTagCompound().setInteger("flightEnergy",0);
		}
		
		if(!world.isRemote)
		{
			EntityPlayerMP playermp = (EntityPlayerMP)player;
			
			if(playermp.capabilities.isCreativeMode)
			{
				return;
			}
			
			if(isArmorFullTapioca(playermp))
			{
				playermp.capabilities.allowFlying = true;
				playermp.getEntityData().setBoolean("isTapiocaChestplate", true);
				playermp.getEntityData().setBoolean("isTapiocaArmorFullSet", true);
				int energy = stack.getTagCompound().getInteger("flightEnergy");
				
				if(playermp.capabilities.isFlying)
				{
					if(energy <= 0)
					{
						boolean flag = false;
						int osize = playermp.inventory.offHandInventory.size();
						for(int i = 0;i < osize;i++)
						{
							Item item = playermp.inventory.offHandInventory.get(i).getItem();
							if(tapiocas.containsKey(item))
							{
								stack.getTagCompound().setInteger("flightEnergy",tapiocas.get(item));
								playermp.inventory.offHandInventory.get(i).shrink(1);
								flag= true;
								break;
							}
						}
						
						if(!flag)
						{
							int msize = playermp.inventory.mainInventory.size();
							for(int i = 0;i < msize;i++)
							{
								Item item = playermp.inventory.mainInventory.get(i).getItem();
								if(tapiocas.containsKey(item))
								{
									stack.getTagCompound().setInteger("flightEnergy",tapiocas.get(item));
									playermp.inventory.mainInventory.get(i).shrink(1);
									flag = true;
									break;
								}
							}
						}
						
						if(!flag)
						{
							playermp.capabilities.isFlying = false;
							playermp.sendPlayerAbilities();
							return;
						}
					}
					
					stack.getTagCompound().setInteger("flightEnergy",stack.getTagCompound().getInteger("flightEnergy") - 1);
				}
			}
			
			playermp.sendPlayerAbilities();
		}
	}
	
	public static boolean isArmorFullTapioca(EntityPlayer player)
	{
		tapiocaInit();
		boolean flag = true;
		for(ItemStack stack : player.getArmorInventoryList())
		{
			flag = false;
			for(Item key : getEnhancedTapiocaArmors())
			{
				if(stack.getItem() == key)
				{
					flag = true;
					break;
				}
			}
			
			if(!flag) return false;
		}
		return flag;
	}
	
	public static void tapiocaInit()
	{
		tapiocas.put(TapiocaMod.tapioca,tapioca_energy);
		tapiocas.put(Item.getItemFromBlock(TapiocaMod.tapioca_block),tapioca_energy*5);
		tapiocas.put(Item.getItemFromBlock(TapiocaMod.compressed_tapioca_block),tapioca_energy*50);
		tapiocas.put(Item.getItemFromBlock(TapiocaMod.compressed_compressing_tapioca_block),tapioca_energy*500);
		tapiocas.put(Item.getItemFromBlock(TapiocaMod.overcompressed_tapioca_block),tapioca_energy*5000);
	}
	
	public static Item[] getEnhancedTapiocaArmors()
	{
		return new Item[] {TapiocaMod.enhanced_curing_tapioca_helmet,TapiocaMod.enhanced_flightable_tapioca_chestplate,TapiocaMod.enhanced_speed_tapioca_boots,TapiocaMod.enhanced_unfire_tapioca_leggings,
				TapiocaMod.enhanced_tapioca_helmet,TapiocaMod.enhanced_tapioca_chestplate,TapiocaMod.enhanced_tapioca_leggings,TapiocaMod.enhanced_tapioca_boots};
	}
}
