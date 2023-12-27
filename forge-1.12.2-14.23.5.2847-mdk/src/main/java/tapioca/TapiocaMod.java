package tapioca;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.SoundType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import tapioca.block.BlockCassava;
import tapioca.block.BlockTapioca;
import tapioca.block.CropCassava;
import tapioca.block.CropTea;
import tapioca.item.CassavaSeeds;
import tapioca.item.EnhancedCuringTapiocaHelmet;
import tapioca.item.EnhancedFlightableTapiocaChestplate;
import tapioca.item.EnhancedSpeedTapiocaBoots;
import tapioca.item.EnhancedTapiocaArmor;
import tapioca.item.EnhancedTapiocaAxe;
import tapioca.item.EnhancedTapiocaHoe;
import tapioca.item.EnhancedTapiocaPaxel;
import tapioca.item.EnhancedTapiocaPickaxe;
import tapioca.item.EnhancedTapiocaSpade;
import tapioca.item.EnhancedTapiocaSword;
import tapioca.item.EnhancedUnfireTapiocaLeggings;
import tapioca.item.ItemBlockCassava;
import tapioca.item.ItemCassava;
import tapioca.item.ItemEnchanted;
import tapioca.item.ItemTapioca;
import tapioca.item.ItemTapiocaIceTea;
import tapioca.item.ItemTapiocaMilkTea;
import tapioca.item.SpeedTagPacket;
import tapioca.item.TapiocaArmor;
import tapioca.item.TapiocaAxe;
import tapioca.item.TapiocaPaxel;
import tapioca.item.TapiocaPickaxe;
import tapioca.item.TeaSeeds;
import tapioca.proxy.CommonProxy;

@Mod(modid = TapiocaMod.MOD_ID, name = TapiocaMod.MOD_NAME, version = TapiocaMod.MOD_VERSION)
@Mod.EventBusSubscriber(modid = TapiocaMod.MOD_ID)
public class TapiocaMod
{
	public static final String MOD_ID = "tapioca";
	public static final String MOD_NAME = "Tapioca Craft";
	public static final String MOD_VERSION = "1.0.15";
	public static final String ACCEPTED_MINECRAFT_VERSIONS = "[1.12]";
	
	public static final CreativeTabs tab = (CreativeTabs)new TapiocaModTab("tapioca");
	
	public static final Block tapioca_block = new BlockTapioca(0.0F,5.0F,SoundType.SLIME).setRegistryName(MOD_ID,"tapioca_block").setCreativeTab(tab).setUnlocalizedName("tapioca_block");
	public static final Block cassava_block = new BlockCassava().setRegistryName(MOD_ID,"cassava_block").setCreativeTab(tab).setUnlocalizedName("cassava_block");
	public static final Block compressed_tapioca_block = new BlockTapioca(1.0F,10.0F,SoundType.WOOD).setRegistryName(MOD_ID,"compressed_tapioca_block").setCreativeTab(tab).setUnlocalizedName("compressed_tapioca_block");
	public static final Block compressed_compressing_tapioca_block = new BlockTapioca(2.0F,20.0F,SoundType.STONE).setRegistryName(MOD_ID,"compressed_compressing_tapioca_block").setCreativeTab(tab).setUnlocalizedName("compressed_compressing_tapioca_block");
	public static final Block overcompressed_tapioca_block = new BlockTapioca(4.0F,40.0F,SoundType.METAL).setRegistryName(MOD_ID,"overcompressed_tapioca_block").setCreativeTab(tab).setUnlocalizedName("overcompressed_tapioca_block");
	public static final Block crop_cassava = new CropCassava().setRegistryName(MOD_ID, "crop_cassava").setCreativeTab(null).setUnlocalizedName("crop_cassava");
	public static final Block crop_tea = new CropTea().setRegistryName(MOD_ID, "crop_tea").setCreativeTab(null).setUnlocalizedName("crop_tea");
	
	public static final Item tapioca = new ItemTapioca().setRegistryName(MOD_ID, "tapioca").setCreativeTab(tab).setUnlocalizedName("tapioca");
	public static final Item tapioca_milk_tea = new ItemTapiocaMilkTea().setRegistryName(MOD_ID, "tapioca_milk_tea").setCreativeTab(tab).setUnlocalizedName("tapioca_milk_tea");
	public static final Item tapioca_ice_tea = new ItemTapiocaIceTea().setRegistryName(MOD_ID, "tapioca_ice_tea").setCreativeTab(tab).setUnlocalizedName("tapioca_ice_tea");
	public static final Item cassava_seeds = new CassavaSeeds(crop_cassava).setRegistryName(MOD_ID,"cassava_seeds").setCreativeTab(tab).setUnlocalizedName("cassava_seeds");
	public static final Item tea_seeds = new TeaSeeds(crop_tea).setRegistryName(MOD_ID,"tea_seeds").setCreativeTab(tab).setUnlocalizedName("tea_seeds");
	public static final Item cassava = new ItemCassava().setRegistryName(MOD_ID,"cassava").setCreativeTab(tab).setUnlocalizedName("cassava");
	public static final Item tea_leaves = new Item().setRegistryName(MOD_ID,"tea_leaf").setCreativeTab(tab).setUnlocalizedName("tea_leaf");
	public static final Item dried_tea_leaves = new Item().setRegistryName(MOD_ID,"dried_tea_leaf").setCreativeTab(tab).setUnlocalizedName("dried_tea_leaf");
	public static final Item cassava_powder = new Item().setRegistryName(MOD_ID,"cassava_powder").setCreativeTab(tab).setUnlocalizedName("cassava_powder");
	public static final Item fermented_cassava = new Item().setRegistryName(MOD_ID,"fermented_cassava").setCreativeTab(tab).setUnlocalizedName("fermented_cassava");
	public static final Item water_soluble_cassava_powder = new Item().setRegistryName(MOD_ID,"water_soluble_cassava_powder").setCreativeTab(tab).setUnlocalizedName("water_soluble_cassava_powder");
	public static final Item cooked_water_soluble_cassava_powder = new Item().setRegistryName(MOD_ID,"cooked_water_soluble_cassava_powder").setCreativeTab(tab).setUnlocalizedName("cooked_water_soluble_cassava_powder");
	public static final Item tapioca_pearl = new Item().setRegistryName(MOD_ID,"tapioca_pearl").setCreativeTab(tab).setUnlocalizedName("tapioca_pearl");
	public static final Item universal_usable_tapioca = new ItemEnchanted().setRegistryName(MOD_ID,"universal_usable_tapioca").setCreativeTab(tab).setUnlocalizedName("universal_usable_tapioca");
	public static final Item unstable_uu_tapioca = new Item().setRegistryName(MOD_ID, "unstable_enhanced_uu_tapioca").setCreativeTab(tab).setUnlocalizedName("unstable_uu_tapioca");
	public static final Item stable_uu_tapioca = new ItemEnchanted().setRegistryName(MOD_ID, "stable_enhanced_uu_tapioca").setCreativeTab(tab).setUnlocalizedName("stable_uu_tapioca");
	
	public static final ToolMaterial toolTapioca = EnumHelper.addToolMaterial("tool_tapioca", 4, 971, 10.0F, 4.0F, 20).setRepairItem(new ItemStack(tapioca));
	public static final ArmorMaterial armorTapioca = EnumHelper.addArmorMaterial("armor_tapioca", "tapioca:tapioca", 26, new int[] {2,6,7,3}, 20, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 3.0F).setRepairItem(new ItemStack(tapioca));
	public static final ToolMaterial toolEnhancedTapioca = EnumHelper.addToolMaterial("tool_enhanced_tapioca", 36, 8739, 90.0F, 36.0F, 180).setRepairItem(new ItemStack(TapiocaMod.stable_uu_tapioca));
	public static final ArmorMaterial armorEnhancedTapioca = EnumHelper.addArmorMaterial("armor_enhanced_tapioca", "tapioca:tapioca", 234, new int[] {5,9,11,5}, 180, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 5.0F).setRepairItem(new ItemStack(TapiocaMod.stable_uu_tapioca));
	
	public static final Item tapioca_axe = new TapiocaAxe(toolTapioca).setRegistryName(MOD_ID,"tapioca_axe").setCreativeTab(tab).setUnlocalizedName("tapioca_axe");
	public static final Item tapioca_hoe = new ItemHoe(toolTapioca).setRegistryName(MOD_ID,"tapioca_hoe").setCreativeTab(tab).setUnlocalizedName("tapioca_hoe");
	public static final Item tapioca_pickaxe = new TapiocaPickaxe(toolTapioca).setRegistryName(MOD_ID,"tapioca_pickaxe").setCreativeTab(tab).setUnlocalizedName("tapioca_pickaxe");
	public static final Item tapioca_shovel = new ItemSpade(toolTapioca).setRegistryName(MOD_ID,"tapioca_shovel").setCreativeTab(tab).setUnlocalizedName("tapioca_shovel");
	public static final Item tapioca_sword = new ItemSword(toolTapioca).setRegistryName(MOD_ID,"tapioca_sword").setCreativeTab(tab).setUnlocalizedName("tapioca_sword");
	public static final Item tapioca_paxel = new TapiocaPaxel(toolTapioca).setRegistryName(MOD_ID,"tapioca_paxel").setCreativeTab(tab).setUnlocalizedName("tapioca_paxel");
	public static final Item tapioca_helmet = new TapiocaArmor(armorTapioca,3,EntityEquipmentSlot.HEAD).setRegistryName(MOD_ID, "tapioca_helmet").setCreativeTab(tab).setUnlocalizedName("tapioca_helmet");
	public static final Item tapioca_chestplate = new TapiocaArmor(armorTapioca,3,EntityEquipmentSlot.CHEST).setRegistryName(MOD_ID, "tapioca_chestplate").setCreativeTab(tab).setUnlocalizedName("tapioca_chestplate");
	public static final Item tapioca_leggings = new TapiocaArmor(armorTapioca,3,EntityEquipmentSlot.LEGS).setRegistryName(MOD_ID, "tapioca_leggings").setCreativeTab(tab).setUnlocalizedName("tapioca_leggings");
	public static final Item tapioca_boots = new TapiocaArmor(armorTapioca,3,EntityEquipmentSlot.FEET).setRegistryName(MOD_ID, "tapioca_boots").setCreativeTab(tab).setUnlocalizedName("tapioca_boots");
	
	public static final Item enhanced_tapioca_axe = new EnhancedTapiocaAxe(toolEnhancedTapioca).setRegistryName(MOD_ID,"enhanced_tapioca_axe").setCreativeTab(tab).setUnlocalizedName("enhanced_tapioca_axe");
	public static final Item enhanced_tapioca_hoe = new EnhancedTapiocaHoe(toolEnhancedTapioca).setRegistryName(MOD_ID,"enhanced_tapioca_hoe").setCreativeTab(tab).setUnlocalizedName("enhanced_tapioca_hoe");
	public static final Item enhanced_tapioca_pickaxe = new EnhancedTapiocaPickaxe(toolEnhancedTapioca).setRegistryName(MOD_ID,"enhanced_tapioca_pickaxe").setCreativeTab(tab).setUnlocalizedName("enhanced_tapioca_pickaxe");
	public static final Item enhanced_tapioca_shovel = new EnhancedTapiocaSpade(toolEnhancedTapioca).setRegistryName(MOD_ID,"enhanced_tapioca_shovel").setCreativeTab(tab).setUnlocalizedName("enhanced_tapioca_shovel");
	public static final Item enhanced_tapioca_sword = new EnhancedTapiocaSword(toolEnhancedTapioca).setRegistryName(MOD_ID,"enhanced_tapioca_sword").setCreativeTab(tab).setUnlocalizedName("enhanced_tapioca_sword");
	public static final Item enhanced_tapioca_paxel = new EnhancedTapiocaPaxel(toolEnhancedTapioca).setRegistryName(MOD_ID,"enhanced_tapioca_paxel").setCreativeTab(tab).setUnlocalizedName("enhanced_tapioca_paxel");
	public static final Item enhanced_tapioca_helmet = new EnhancedTapiocaArmor(armorEnhancedTapioca,3,EntityEquipmentSlot.HEAD).setRegistryName(MOD_ID, "enhanced_tapioca_helmet").setCreativeTab(tab).setUnlocalizedName("enhanced_tapioca_helmet");
	public static final Item enhanced_tapioca_chestplate = new EnhancedTapiocaArmor(armorEnhancedTapioca,3,EntityEquipmentSlot.CHEST).setRegistryName(MOD_ID, "enhanced_tapioca_chestplate").setCreativeTab(tab).setUnlocalizedName("enhanced_tapioca_chestplate");
	public static final Item enhanced_tapioca_leggings = new EnhancedTapiocaArmor(armorEnhancedTapioca,3,EntityEquipmentSlot.LEGS).setRegistryName(MOD_ID, "enhanced_tapioca_leggings").setCreativeTab(tab).setUnlocalizedName("enhanced_tapioca_leggings");
	public static final Item enhanced_tapioca_boots = new EnhancedTapiocaArmor(armorEnhancedTapioca,3,EntityEquipmentSlot.FEET).setRegistryName(MOD_ID, "enhanced_tapioca_boots").setCreativeTab(tab).setUnlocalizedName("enhanced_tapioca_boots");
	
	public static final Item enhanced_curing_tapioca_helmet = new EnhancedCuringTapiocaHelmet().setRegistryName(MOD_ID, "enhanced_curing_tapioca_helmet").setCreativeTab(tab).setUnlocalizedName("enhanced_tapioca_helmet");
	public static final Item enhanced_flightable_tapioca_chestplate = new EnhancedFlightableTapiocaChestplate().setRegistryName(MOD_ID, "enhanced_flightable_tapioca_chestplate").setCreativeTab(tab).setUnlocalizedName("enhanced_tapioca_chestplate");
	public static final Item enhanced_unfire_tapioca_leggings = new EnhancedUnfireTapiocaLeggings().setRegistryName(MOD_ID, "enhanced_unfire_tapioca_leggings").setCreativeTab(tab).setUnlocalizedName("enhanced_tapioca_leggings");
	public static final Item enhanced_speed_tapioca_boots = new EnhancedSpeedTapiocaBoots().setRegistryName(MOD_ID, "enhanced_speed_tapioca_boots").setCreativeTab(tab).setUnlocalizedName("enhanced_tapioca_boots");

	public static final SimpleNetworkWrapper PACKET = NetworkRegistry.INSTANCE.newSimpleChannel("tapioca".toLowerCase());
	
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	MinecraftForge.EVENT_BUS.register(this);
    	
    	PACKET.registerMessage(SpeedTagPacket.Handler.class, SpeedTagPacket.class, 0, Side.CLIENT);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    }
    
    @SubscribeEvent
    public void registerBlock(RegistryEvent.Register<Block> event)
    {
    	event.getRegistry().register(TapiocaMod.cassava_block);
    	event.getRegistry().register(TapiocaMod.tapioca_block);
    	event.getRegistry().register(TapiocaMod.compressed_tapioca_block);
    	event.getRegistry().register(TapiocaMod.compressed_compressing_tapioca_block);
    	event.getRegistry().register(TapiocaMod.overcompressed_tapioca_block);
    	event.getRegistry().register(TapiocaMod.crop_cassava);
    	event.getRegistry().register(TapiocaMod.crop_tea);
    }
    
    @SubscribeEvent
    public void registerItem(RegistryEvent.Register<Item> event)
    {
    	event.getRegistry().register(new ItemBlockCassava(TapiocaMod.cassava_block).setRegistryName(MOD_ID, "cassava_block"));
    	event.getRegistry().register(new ItemBlock(TapiocaMod.compressed_compressing_tapioca_block).setRegistryName(MOD_ID, "compressed_compressing_tapioca_block"));
    	event.getRegistry().register(new ItemBlock(TapiocaMod.compressed_tapioca_block).setRegistryName(MOD_ID, "compressed_tapioca_block"));
    	event.getRegistry().register(new ItemBlock(TapiocaMod.crop_cassava).setRegistryName(MOD_ID, "crop_cassava"));
    	event.getRegistry().register(new ItemBlock(TapiocaMod.crop_tea).setRegistryName(MOD_ID, "crop_tea"));
    	event.getRegistry().register(new ItemBlock(TapiocaMod.overcompressed_tapioca_block).setRegistryName(MOD_ID, "overcompressed_tapioca_block"));
    	event.getRegistry().register(new ItemBlock(TapiocaMod.tapioca_block).setRegistryName(MOD_ID, "tapioca_block"));
    	
    	event.getRegistry().register(TapiocaMod.cassava);
    	event.getRegistry().register(TapiocaMod.cassava_powder);
    	event.getRegistry().register(TapiocaMod.cassava_seeds);
    	event.getRegistry().register(TapiocaMod.cooked_water_soluble_cassava_powder);
    	event.getRegistry().register(TapiocaMod.dried_tea_leaves);
    	event.getRegistry().register(TapiocaMod.fermented_cassava);
    	event.getRegistry().register(TapiocaMod.tapioca);
    	event.getRegistry().register(TapiocaMod.tapioca_axe);
    	event.getRegistry().register(TapiocaMod.tapioca_boots);
    	event.getRegistry().register(TapiocaMod.tapioca_chestplate);
    	event.getRegistry().register(TapiocaMod.tapioca_helmet);
    	event.getRegistry().register(TapiocaMod.tapioca_hoe);
    	event.getRegistry().register(TapiocaMod.tapioca_leggings);
    	event.getRegistry().register(TapiocaMod.tapioca_milk_tea);
    	event.getRegistry().register(TapiocaMod.tapioca_ice_tea);
    	event.getRegistry().register(TapiocaMod.tapioca_pearl);
    	event.getRegistry().register(TapiocaMod.tapioca_pickaxe);
    	event.getRegistry().register(TapiocaMod.tapioca_shovel);
    	event.getRegistry().register(TapiocaMod.tapioca_sword);
    	event.getRegistry().register(TapiocaMod.tapioca_paxel);
    	event.getRegistry().register(TapiocaMod.tea_leaves);
    	event.getRegistry().register(TapiocaMod.tea_seeds);
    	event.getRegistry().register(TapiocaMod.universal_usable_tapioca);
    	event.getRegistry().register(TapiocaMod.water_soluble_cassava_powder);
    	event.getRegistry().register(TapiocaMod.unstable_uu_tapioca);
    	event.getRegistry().register(TapiocaMod.stable_uu_tapioca);
    	event.getRegistry().register(TapiocaMod.enhanced_tapioca_axe);
    	event.getRegistry().register(TapiocaMod.enhanced_tapioca_hoe);
    	event.getRegistry().register(TapiocaMod.enhanced_tapioca_pickaxe);
    	event.getRegistry().register(TapiocaMod.enhanced_tapioca_shovel);
    	event.getRegistry().register(TapiocaMod.enhanced_tapioca_sword);
    	event.getRegistry().register(TapiocaMod.enhanced_tapioca_helmet);
    	event.getRegistry().register(TapiocaMod.enhanced_tapioca_chestplate);
    	event.getRegistry().register(TapiocaMod.enhanced_tapioca_leggings);
    	event.getRegistry().register(TapiocaMod.enhanced_tapioca_boots);
    	event.getRegistry().register(TapiocaMod.enhanced_tapioca_paxel);
    	
    	event.getRegistry().register(TapiocaMod.enhanced_curing_tapioca_helmet);
    	event.getRegistry().register(TapiocaMod.enhanced_flightable_tapioca_chestplate);
    	event.getRegistry().register(TapiocaMod.enhanced_unfire_tapioca_leggings);
    	event.getRegistry().register(TapiocaMod.enhanced_speed_tapioca_boots);
    	
    	OreDictionary.registerOre("cropCassava",TapiocaMod.cassava);
    	OreDictionary.registerOre("uuTapioca",TapiocaMod.universal_usable_tapioca);
    	OreDictionary.registerOre("tapioca",TapiocaMod.tapioca);
    	OreDictionary.registerOre("cropTea", TapiocaMod.tea_leaves);
    	OreDictionary.registerOre("ueuuTapioca", TapiocaMod.unstable_uu_tapioca);
    	OreDictionary.registerOre("seuuTapioca", TapiocaMod.stable_uu_tapioca);
    	OreDictionary.registerOre("listAllseed", TapiocaMod.cassava_seeds);
    	OreDictionary.registerOre("listAllseed", TapiocaMod.tea_seeds);
    }
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void registerModels(ModelRegistryEvent event) 
    {
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TapiocaMod.cassava_block), 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "cassava_block"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TapiocaMod.compressed_compressing_tapioca_block), 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "compressed_compressing_tapioca_block"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TapiocaMod.compressed_tapioca_block), 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "compressed_tapioca_block"), "inventory"));
    	for(int i=0;i < 8;i++) ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TapiocaMod.crop_cassava), i, new ModelResourceLocation(new ResourceLocation(MOD_ID, "crop_cassava"), "age="+i));
    	for(int i=0;i < 8;i++) ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TapiocaMod.crop_tea), i, new ModelResourceLocation(new ResourceLocation(MOD_ID, "crop_tea"), "age="+i));
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TapiocaMod.overcompressed_tapioca_block), 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "overcompressed_tapioca_block"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TapiocaMod.tapioca_block), 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "tapioca_block"), "inventory"));
    	
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.cassava, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "cassava"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.cassava_powder, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "cassava_powder"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.cassava_seeds, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "cassava_seeds"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.cooked_water_soluble_cassava_powder, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "cooked_water_soluble_cassava_powder"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.dried_tea_leaves, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "dried_tea_leaves"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.fermented_cassava, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "fermented_cassava"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.tapioca, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "tapioca"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.tapioca_axe, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "tapioca_axe"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.tapioca_boots, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "tapioca_boots"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.tapioca_chestplate, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "tapioca_chestplate"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.tapioca_helmet, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "tapioca_helmet"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.tapioca_hoe, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "tapioca_hoe"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.tapioca_leggings, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "tapioca_leggings"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.tapioca_milk_tea, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "tapioca_milk_tea"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.tapioca_ice_tea, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "tapioca_ice_tea"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.tapioca_pearl, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "tapioca_pearl"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.tapioca_pickaxe, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "tapioca_pickaxe"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.tapioca_shovel, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "tapioca_shovel"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.tapioca_sword, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "tapioca_sword"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.tapioca_paxel, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "tapioca_paxel"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.tea_leaves, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "tea_leaves"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.tea_seeds, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "tea_seeds"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.universal_usable_tapioca, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "universal_usable_tapioca"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.water_soluble_cassava_powder, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "water_soluble_cassava_powder"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.unstable_uu_tapioca, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "unstable_uu_tapioca"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.stable_uu_tapioca, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "stable_uu_tapioca"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.enhanced_tapioca_axe, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "tapioca_axe"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.enhanced_tapioca_boots, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "tapioca_boots"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.enhanced_tapioca_chestplate, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "tapioca_chestplate"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.enhanced_tapioca_helmet, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "tapioca_helmet"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.enhanced_tapioca_hoe, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "tapioca_hoe"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.enhanced_tapioca_leggings, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "tapioca_leggings"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.enhanced_tapioca_pickaxe, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "tapioca_pickaxe"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.enhanced_tapioca_shovel, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "tapioca_shovel"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.enhanced_tapioca_sword, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "tapioca_sword"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.enhanced_tapioca_paxel, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "tapioca_paxel"), "inventory"));
    	
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.enhanced_curing_tapioca_helmet, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "tapioca_helmet"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.enhanced_flightable_tapioca_chestplate, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "tapioca_chestplate"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.enhanced_unfire_tapioca_leggings, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "tapioca_leggings"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(TapiocaMod.enhanced_speed_tapioca_boots, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "tapioca_boots"), "inventory"));
    }
    
    @SubscribeEvent
    public void harvestEvent(BlockEvent.HarvestDropsEvent event)
    {
    	if(event.getState().getBlock() instanceof BlockTallGrass)
    	{
    		Random rand = new Random();
    		int i = rand.nextInt(10);
    		if(i == 0)
    		{
    			event.getDrops().add(new ItemStack(TapiocaMod.cassava_seeds));
    		}
    		else if(i == 1)
    		{
    			event.getDrops().add(new ItemStack(TapiocaMod.tea_seeds));
    		}
    	}
    }
    
    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event)
    {
    	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(TapiocaMod.water_soluble_cassava_powder,8),"HHH","HXH","HHH",'X',FluidUtil.getFilledBucket(new FluidStack(FluidRegistry.WATER,1000)),'H',TapiocaMod.cassava_powder).setRegistryName(MOD_ID, "water_soluble_cassava_powder"));
    	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(TapiocaMod.water_soluble_cassava_powder,8),"HHH","HXH","HHH",'X',"listAllwater",'H',TapiocaMod.cassava_powder).setRegistryName(MOD_ID, "water_soluble_cassava_powder_oredic"));
    	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(tapioca_milk_tea, 3),"WVZ", "XXX", "YYY", 'V', Items.MILK_BUCKET, 'W', dried_tea_leaves, 'X', "tapioca", 'Y', Items.GLASS_BOTTLE, 'Z', Items.SUGAR).setRegistryName(MOD_ID, "tapioca_milk_tea"));
    	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(tapioca_ice_tea, 3),"WVZ", "XXX", "YYY", 'V', Items.WATER_BUCKET, 'W', dried_tea_leaves, 'X', "tapioca", 'Y', Items.GLASS_BOTTLE, 'Z', Items.SUGAR).setRegistryName(MOD_ID, "tapioca_ice_tea"));
    	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(tapioca_block, 1),"XX", "XX", 'X', "tapioca" ).setRegistryName(MOD_ID, "tapioca_block"));
    	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(compressed_tapioca_block, 1),"XXX", "XXX", "XXX", 'X', tapioca_block ).setRegistryName(MOD_ID, "compressed_tapioca_block"));
    	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(compressed_compressing_tapioca_block, 1),"XXX", "XXX", "XXX", 'X', compressed_tapioca_block).setRegistryName(MOD_ID, "compressed_compressing_tapioca_block"));
    	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(cassava_block, 1), "XXX", "XXX", "XXX", 'X', "cropCassava").setRegistryName(MOD_ID, "cassava_block"));
    	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(tapioca, 8), "XXX", "XYX", "XXX", 'X', tapioca_pearl, 'Y', FluidUtil.getFilledBucket(new FluidStack(FluidRegistry.WATER,1000))).setRegistryName(MOD_ID, "tapioca"));
    	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(tapioca, 8), "XXX", "XYX", "XXX", 'X', tapioca_pearl, 'Y', "listAllwater").setRegistryName(MOD_ID, "tapioca_oredic"));
    	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(fermented_cassava, 8),"XXX", "XYX", "XXX", 'X', "cropCassava", 'Y', Blocks.BROWN_MUSHROOM).setRegistryName(MOD_ID, "fermented_cassava0"));
    	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(fermented_cassava, 8),"XXX", "XYX", "XXX", 'X', "cropCassava", 'Y', Blocks.RED_MUSHROOM).setRegistryName(MOD_ID, "fermented_cassava1"));
    	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(overcompressed_tapioca_block, 1), "XXX", "XXX", "XXX", 'X', compressed_compressing_tapioca_block).setRegistryName(MOD_ID, "overcompressed_tapioca_block"));
    	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(tapioca_axe, 1), "XX ", "XY ", " Y ", 'X', compressed_compressing_tapioca_block, 'Y', "stickWood").setRegistryName(MOD_ID, "tapioca_axe"));
    	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(tapioca_pickaxe, 1),"XXX", " Y ", " Y ", 'X', compressed_compressing_tapioca_block, 'Y', "stickWood").setRegistryName(MOD_ID, "tapioca_pickaxe"));
    	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(tapioca_hoe, 1), "XX", " Y", " Y", 'X', compressed_compressing_tapioca_block, 'Y', "stickWood").setRegistryName(MOD_ID, "tapioca_hoe"));
    	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(tapioca_shovel, 1), "X", "Y", "Y", 'X', compressed_compressing_tapioca_block, 'Y', "stickWood").setRegistryName(MOD_ID, "tapioca_shovel"));
    	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(tapioca_sword, 1), "X", "X", "Y", 'X', compressed_compressing_tapioca_block, 'Y', "stickWood").setRegistryName(MOD_ID, "tapioca_sword"));
    	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(tapioca_helmet, 1), "XXX", "X X", 'X', compressed_compressing_tapioca_block).setRegistryName(MOD_ID, "tapioca_helmet"));
    	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(tapioca_chestplate, 1), "X X", "XXX", "XXX", 'X', compressed_compressing_tapioca_block).setRegistryName(MOD_ID, "tapioca_chestplate"));
    	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(tapioca_leggings, 1), "XXX", "X X", "X X", 'X', compressed_compressing_tapioca_block).setRegistryName(MOD_ID, "tapioca_leggings"));
    	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(tapioca_boots, 1), "X X", "X X", 'X', compressed_compressing_tapioca_block).setRegistryName(MOD_ID, "tapioca_boots"));
    	event.getRegistry().register(new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(cassava, 9),cassava_block).setRegistryName(MOD_ID, "cassava"));
    	event.getRegistry().register(new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(cassava_powder, 2), fermented_cassava).setRegistryName(MOD_ID, "cassava_powder"));
    	event.getRegistry().register(new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(tapioca_pearl, 2), cooked_water_soluble_cassava_powder).setRegistryName(MOD_ID, "tapioca_pearl"));
    	event.getRegistry().register(new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(tapioca, 4),tapioca_block).setRegistryName(MOD_ID, "tapioca2"));
    	event.getRegistry().register(new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(tapioca_block, 9),compressed_tapioca_block).setRegistryName(MOD_ID, "tapioca_block2"));
    	event.getRegistry().register(new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(compressed_tapioca_block, 9),compressed_compressing_tapioca_block).setRegistryName(MOD_ID, "compressed_tapioca_block2")); 
    	event.getRegistry().register(new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(TapiocaMod.tapioca_paxel), TapiocaMod.tapioca_axe,TapiocaMod.tapioca_hoe,TapiocaMod.tapioca_pickaxe,TapiocaMod.tapioca_shovel,TapiocaMod.tapioca_sword).setRegistryName(MOD_ID, "tapioca_paxel"));
    	 
    	GameRegistry.addSmelting(new ItemStack(TapiocaMod.water_soluble_cassava_powder), new ItemStack(TapiocaMod.cooked_water_soluble_cassava_powder), 0.7F);
    	addOreDictSmelting("cropTea", new ItemStack(TapiocaMod.dried_tea_leaves), 0.1F);
    	
    	if(TapiocaConfig.enable_uutapioca_recipes)
    	{
    		event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Items.ENDER_PEARL, 1), "XXX", "X X", " X ", 'X', "uuTapioca").setRegistryName(MOD_ID, "ender_pearl"));
    		event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Items.GHAST_TEAR, 2), "X X", "XXX", " X ", 'X', "uuTapioca").setRegistryName(MOD_ID, "ghast_tear"));
    		event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Items.BLAZE_ROD, 8), "   ", "X X", "X X", 'X', "uuTapioca").setRegistryName(MOD_ID, "blaze_rod"));
    		event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Items.CHORUS_FRUIT, 64), "X X", "X X", " X ", 'X', "uuTapioca").setRegistryName(MOD_ID, "chorus_fruit"));
    		event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Items.SLIME_BALL, 4), "   ", "  X", "   ", 'X', "uuTapioca").setRegistryName(MOD_ID, "slime_ball"));
    		event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Blocks.QUARTZ_ORE, 10), "X X", "X X", "XXX", 'X', "uuTapioca").setRegistryName(MOD_ID, "quartz_ore"));
    		event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Items.SKULL, 1,1), "X X", "XXX", "XXX", 'X', "uuTapioca").setRegistryName(MOD_ID, "wither_skeleton_skull"));
    		event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Items.ROTTEN_FLESH, 64), "XXX", "X X", "X X", 'X', "uuTapioca").setRegistryName(MOD_ID, "rotten_flesh"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Blocks.STONE, 16),"   ", " X ", "   ", 'X', "uuTapioca").setRegistryName(MOD_ID, "stone"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Blocks.GRASS, 16), "   ", "X  ", "X  ",'X', "uuTapioca").setRegistryName(MOD_ID, "grass"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Blocks.LOG, 16), " X ", "   ", "   ", 'X', "uuTapioca").setRegistryName(MOD_ID, "log"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Items.STRING, 32), "XX ", "   ", "   ", 'X', "uuTapioca").setRegistryName(MOD_ID, "string"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Blocks.GLOWSTONE, 8), " X ", "X X", "XXX", 'X',"uuTapioca").setRegistryName(MOD_ID, "glowstone"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Blocks.OBSIDIAN, 12), "X X", "X X", "   ", 'X', "uuTapioca").setRegistryName(MOD_ID, "obsidian"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Blocks.END_STONE,24), "X X", "   ", "X X", 'X', "uuTapioca").setRegistryName(MOD_ID, "end_stone"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Blocks.SNOW, 16), "X X", "   ", "   ", 'X', "uuTapioca").setRegistryName(MOD_ID, "snow"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Blocks.GLASS, 32), " X ", "X X", " X ", 'X',"uuTapioca").setRegistryName(MOD_ID, "glass"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Blocks.CACTUS, 48), " X ", "XXX", "X X", 'X', "uuTapioca").setRegistryName(MOD_ID, "cactus"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Blocks.VINE, 24), "X  ", "X  ", "X  ", 'X', "uuTapioca").setRegistryName(MOD_ID, "vine"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Blocks.WATERLILY, 64), "X X", " X ", " X ", 'X', "uuTapioca").setRegistryName(MOD_ID, "waterlily"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Items.DYE, 32, 3), "XX ", "  X", "XX ", 'X', "uuTapioca").setRegistryName(MOD_ID, "cacao_beans"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Items.REEDS, 48), "X X", "X X", "X X", 'X',"uuTapioca").setRegistryName(MOD_ID, "sugarcane"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Items.BONE, 32), "X  ", "XX ", "X  ", 'X', "uuTapioca").setRegistryName(MOD_ID, "bone"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Items.GUNPOWDER, 15), "XXX", "X  ", "XXX", 'X', "uuTapioca").setRegistryName(MOD_ID, "gunpowder"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Items.FEATHER, 32), " X ", " X ", "X X", 'X', "uuTapioca").setRegistryName(MOD_ID, "feather"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Items.DYE, 48), " XX", " XX", " X ", 'X', "uuTapioca").setRegistryName(MOD_ID, "ink_sac"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Items.SNOWBALL, 16), "   ", "   ", "XXX", 'X', "uuTapioca").setRegistryName(MOD_ID, "snowball"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Items.CLAY_BALL, 48), "XX ", "X  ", "XX ", 'X', "uuTapioca").setRegistryName(MOD_ID, "clayball"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Items.COAL, 5), "  X", "X  ", "  X", 'X', "uuTapioca").setRegistryName(MOD_ID, "coal"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Blocks.IRON_ORE, 2), "X X", " X ", "X X", 'X', "uuTapioca").setRegistryName(MOD_ID, "iron_ore"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Blocks.GOLD_ORE, 2), " X ", "XXX", " X ", 'X', "uuTapioca").setRegistryName(MOD_ID, "gold_ore"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Items.REDSTONE, 24), "   ", " X ", "XXX", 'X', "uuTapioca").setRegistryName(MOD_ID, "redstone"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Items.DYE, 9, 4), " X ", " X ", " XX", 'X', "uuTapioca").setRegistryName(MOD_ID, "lapis_lazuli"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Items.EMERALD, 2), "XXX", "XXX", " X ", 'X', "uuTapioca").setRegistryName(MOD_ID, "emerald"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Blocks.EMERALD_ORE, 1), "XX ", "X X", " XX", 'X', "uuTapioca").setRegistryName(MOD_ID, "emerald_ore"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(Items.DIAMOND), "XXX", "XXX", "XXX", 'X', "uuTapioca").setRegistryName(MOD_ID, "diamond"));
        	
        	GameRegistry.addSmelting(new ItemStack(TapiocaMod.overcompressed_tapioca_block), new ItemStack(TapiocaMod.universal_usable_tapioca), 1.4F);
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(TapiocaMod.unstable_uu_tapioca), "XXX", "XYX","XXX",'X',"uuTapioca",'Y',"tapioca").setRegistryName(MOD_ID, "unstable_uu_tapioca"));
        	addOreDictSmelting("ueuuTapioca",new ItemStack(TapiocaMod.stable_uu_tapioca),2.8F);
        	
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(TapiocaMod.enhanced_tapioca_axe), "XXX", "XZX", "XXX", 'X', "seuuTapioca",'Z',TapiocaMod.tapioca_axe).setRegistryName(MOD_ID, "enhanced_tapioca_axe"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(TapiocaMod.enhanced_tapioca_hoe), "XXX", "XZX", "XXX", 'X', "seuuTapioca",'Z',TapiocaMod.tapioca_hoe).setRegistryName(MOD_ID, "enhanced_tapioca_hoe"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(TapiocaMod.enhanced_tapioca_pickaxe), "XXX", "XZX", "XXX", 'X', "seuuTapioca",'Z',TapiocaMod.tapioca_pickaxe).setRegistryName(MOD_ID, "enhanced_tapioca_pickaxe"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(TapiocaMod.enhanced_tapioca_shovel), "XXX", "XZX", "XXX", 'X', "seuuTapioca",'Z',TapiocaMod.tapioca_shovel).setRegistryName(MOD_ID, "enhanced_tapioca_shovel"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(TapiocaMod.enhanced_tapioca_sword), "XXX", "XZX", "XXX", 'X', "seuuTapioca",'Z',TapiocaMod.tapioca_sword).setRegistryName(MOD_ID, "enhanced_tapioca_sword"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(TapiocaMod.enhanced_tapioca_helmet), "XXX", "XZX", "XXX", 'X', "seuuTapioca",'Z',TapiocaMod.tapioca_helmet).setRegistryName(MOD_ID, "enhanced_tapioca_helmet"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(TapiocaMod.enhanced_tapioca_chestplate), "XXX", "XZX", "XXX", 'X', "seuuTapioca",'Z',TapiocaMod.tapioca_chestplate).setRegistryName(MOD_ID, "enhanced_tapioca_chestplate"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(TapiocaMod.enhanced_tapioca_leggings), "XXX", "XZX", "XXX", 'X', "seuuTapioca",'Z',TapiocaMod.tapioca_leggings).setRegistryName(MOD_ID, "enhanced_tapioca_leggings"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(TapiocaMod.enhanced_tapioca_boots), "XXX", "XZX", "XXX", 'X', "seuuTapioca",'Z',TapiocaMod.tapioca_boots).setRegistryName(MOD_ID, "enhanced_tapioca_boots"));
        	event.getRegistry().register(new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(TapiocaMod.enhanced_tapioca_paxel), TapiocaMod.enhanced_tapioca_axe,TapiocaMod.enhanced_tapioca_hoe,TapiocaMod.enhanced_tapioca_pickaxe,TapiocaMod.enhanced_tapioca_shovel,TapiocaMod.enhanced_tapioca_sword).setRegistryName(MOD_ID, "enhanced_paxel"));
        	
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(TapiocaMod.enhanced_curing_tapioca_helmet), "XXX", "XZX", "XXX", 'X', Items.GOLDEN_APPLE,'Z',TapiocaMod.enhanced_tapioca_helmet).setRegistryName(MOD_ID, "enhanced_curing_tapioca_helmet"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(TapiocaMod.enhanced_flightable_tapioca_chestplate), "XXX", "XZX", "XXX", 'X', "feather",'Z',TapiocaMod.enhanced_tapioca_chestplate).setRegistryName(MOD_ID, "enhanced_flightable_tapioca_chestplate"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(TapiocaMod.enhanced_unfire_tapioca_leggings), "XXX", "XZX", "XXX", 'X', Items.MAGMA_CREAM,'Z',TapiocaMod.enhanced_tapioca_leggings).setRegistryName(MOD_ID, "enhanced_unfire_tapioca_leggings"));
        	event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),new ItemStack(TapiocaMod.enhanced_speed_tapioca_boots), "XXX", "XZX", "XXX", 'X', Items.SUGAR,'Z',TapiocaMod.enhanced_tapioca_boots).setRegistryName(MOD_ID, "enhanced_flightable_speed_boots"));
        	
        	if(!OreDictionary.getOres("dustTin").isEmpty())
        	{
        		event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),setCount(OreDictionary.getOres("dustTin").get(0).copy(),10), "   ", "X X", "  X",'X',"uuTapioca").setRegistryName(MOD_ID, "dustTin"));
        	}
        	
        	if(!OreDictionary.getOres("dustCopper").isEmpty())
        	{
        		event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),setCount(OreDictionary.getOres("dustCopper").get(0).copy(),10), "  X", "X X", "   ",'X',"uuTapioca").setRegistryName(MOD_ID, "dustCopper"));
        	}
        	
        	if(!OreDictionary.getOres("dustLead").isEmpty())
        	{
        		event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),setCount(OreDictionary.getOres("dustLead").get(0).copy(),14), "XXX", "XXX", "X  ",'X',"uuTapioca").setRegistryName(MOD_ID, "dustLead"));
        	}
        	
        	if(!OreDictionary.getOres("dustPlatinum").isEmpty())
        	{
        		event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),setCount(OreDictionary.getOres("dustPlatinum").get(0).copy(),1), "  X", "XXX", "XXX",'X',"uuTapioca").setRegistryName(MOD_ID, "dustPlatinum"));
        	}
        	
        	if(!OreDictionary.getOres("dustTungsten").isEmpty())
        	{
        		event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),setCount(OreDictionary.getOres("dustTungsten").get(0).copy(),1), " XX", " XX", "XXX",'X',"uuTapioca").setRegistryName(MOD_ID, "dustTungsten"));
        	}
        	
        	if(!OreDictionary.getOres("dustTitanium").isEmpty())
        	{
        		event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),setCount(OreDictionary.getOres("dustTitanium").get(0).copy(),2), "XXX", " X ", " X ",'X',"uuTapioca").setRegistryName(MOD_ID, "dustTitanium"));
        	}
        	
        	if(!OreDictionary.getOres("dustAluminum").isEmpty())
        	{
        		event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),setCount(OreDictionary.getOres("dustAluminum").get(0).copy(),16), " X ", " X ", "XXX",'X',"uuTapioca").setRegistryName(MOD_ID, "dustAluminum"));
        	}
        	
        	if(!OreDictionary.getOres("oreIridium").isEmpty())
        	{
        		event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),setCount(OreDictionary.getOres("oreIridium").get(0).copy(),1), "XXX", " X ", "XXX",'X',"uuTapioca").setRegistryName(MOD_ID, "oreIridium"));
        	}
        	
        	if(!OreDictionary.getOres("dustSilver").isEmpty())
        	{
        		event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),setCount(OreDictionary.getOres("dustSilver").get(0).copy(),10), " XX", "XXX", " XX",'X',"uuTapioca").setRegistryName(MOD_ID, "dustSilver"));
        	}
        	
        	if(!OreDictionary.getOres("dustNickel").isEmpty())
        	{
        		event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation(MOD_ID,"tapioca"),setCount(OreDictionary.getOres("dustNickel").get(0).copy(),20), "XXX", " XX", " XX",'X',"uuTapioca").setRegistryName(MOD_ID, "dustNickel"));
        	}
    	}
    }
    
    private static ItemStack setCount(ItemStack raw, int value)
    {
    	raw.setCount(value);
    	return raw;
    }
    
    @SubscribeEvent
    public void onConfigChangedEvent(OnConfigChangedEvent event)
    {
        if (event.getModID().equals(TapiocaMod.MOD_ID))
        {
            ConfigManager.sync(TapiocaMod.MOD_ID, Config.Type.INSTANCE);
        }
    }
    
    public static void addOreDictSmelting(String oredict,ItemStack result,float exp)
	{
		for(ItemStack stack : OreDictionary.getOres(oredict))
		{
			GameRegistry.addSmelting(stack, result, exp);
		}
	}
    
    @SidedProxy(clientSide = "tapioca.proxy.ClientProxy", serverSide = "tapioca.proxy.CommonProxy")
	public static CommonProxy proxy;
    
    public static final String[] flySpeed = new String[] {"flySpeed","field_75096_f"};
    public static final String[] walkSpeed = new String[] {"walkSpeed","field_75097_g"};
    
    @SubscribeEvent(priority=EventPriority.HIGH)
    public void onLiving(LivingEvent.LivingUpdateEvent event)
    {
    	if(event.getEntityLiving() != null && event.getEntityLiving() instanceof EntityPlayerMP)
    	{	
    		EntityPlayerMP player = (EntityPlayerMP)event.getEntityLiving();
    		if(player.capabilities.isCreativeMode)
			{
				return;
			}
    		
    		if(!contains(player.getArmorInventoryList(),TapiocaMod.enhanced_flightable_tapioca_chestplate) && player.getEntityData().getBoolean("isTapiocaChestplate"))
			{
				player.capabilities.isFlying = false;
				player.capabilities.allowFlying = false;
				
				player.sendPlayerAbilities();
				player.getEntityData().setBoolean("isTapiocaChestplate",false);
			}
    		
    		if(!contains(player.getArmorInventoryList(),TapiocaMod.enhanced_speed_tapioca_boots) && player.getEntityData().getBoolean("isTapiocaBoots"))
			{
				player.getEntityData().setBoolean("isTapiocaBoots",false);
			}
    		
    		if(!EnhancedFlightableTapiocaChestplate.isArmorFullTapioca(player) && player.getEntityData().getBoolean("isTapiocaArmorFullSet"))
    		{
    			player.capabilities.isFlying = false;
				player.capabilities.allowFlying = false;
				
				player.getEntityData().setBoolean("isTapiocaChestplate",false);
				
				NBTTagCompound cap = new NBTTagCompound();
				player.capabilities.writeCapabilitiesToNBT(cap);
				cap.getCompoundTag("abilities").setFloat("flySpeed", 0.05F);
				cap.getCompoundTag("abilities").setFloat("walkSpeed", 0.1F);
				player.capabilities.readCapabilitiesFromNBT(cap);
				
				player.getEntityData().setBoolean("isTapiocaBoots",false);
    			
				player.sendPlayerAbilities();
				player.getEntityData().setBoolean("isTapiocaArmorFullSet", false);
    		}
    	}
    }
    
    private static boolean contains(Iterable<ItemStack> iterable,Item item)
    {
    	for(ItemStack stack : iterable)
    	{
    		if(stack.getItem() == item)
    		{
    			return true;
    		}
    	}
    	return false;
    }
}
