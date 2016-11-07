package net.trentv.stattrak;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = StatTrak.MODID, version = StatTrak.VERSION, acceptedMinecraftVersions = "1.10.2")
public class StatTrak
{
	@Instance(StatTrak.MODID)
	public static StatTrak instance;

	public static final String MODID = "stattrak";
	public static final String VERSION = "1.0.0";

	@SidedProxy(clientSide = "net.trentv.stattrak.client.ClientProxy", serverSide = "net.trentv.stattrak.server.ServerProxy")
	public static CommonProxy proxy;

	public static EnchantmentStatTrak STATTRAK = new EnchantmentStatTrak(Enchantment.Rarity.RARE, EnumEnchantmentType.ALL, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND});
	public static Item itemTracker = new Item().setRegistryName(MODID, "tracker").setCreativeTab(CreativeTabs.MISC).setUnlocalizedName("stattrak-tracker");
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		GameRegistry.register(STATTRAK);
		GameRegistry.register(itemTracker);
		GameRegistry.addRecipe(new RecipeTracker());
		GameRegistry.addShapedRecipe(new ItemStack(itemTracker, 1), 
		                             new Object[] {"RRR",
		                                           "IOI",
		                                           " I ",
		                                           'R', Items.REDSTONE, 'I', Items.IRON_INGOT, 'O', new ItemStack(Items.DYE, 1, EnumDyeColor.ORANGE.getDyeDamage())});
		proxy.registerRenderers();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new StatTrakEventHandler());
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{

	}
}
