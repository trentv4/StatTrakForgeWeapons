package net.trentv.stattrak;

import java.util.Random;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class RecipeTracker implements IRecipe
{
	private ItemStack item;
	private Random random = new Random();
	
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn)
	{
		ItemStack itemTracker = null;
		ItemStack trackedItem = null;
		item = null;
		
		int nonNullSlots = 0;
		for(int i = 0; i < inv.getSizeInventory(); i++)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if(stack != null)
			{
				nonNullSlots++;
				if(stack.getItem() == StatTrak.itemTracker || stack.getItem() == StatTrak.itemDefectiveTracker)
				{
					itemTracker = stack;
				}
				else
				{
					trackedItem = stack;
				}
			}
		}
		if(nonNullSlots == 2)
		{
			item = trackedItem;
			String s = itemTracker.getDisplayName();
			if(itemTracker.getItem() == StatTrak.itemDefectiveTracker)
			{
				s = I18n.translateToLocal("defective-stattrak-message-" + random.nextInt(10));
			}
			String q = I18n.translateToLocal("item.stattrak-tracker.name");
			String message = I18n.translateToLocal("stattrak-killcount");
			if(!s.equals(q)) message = s;
			if(!item.hasTagCompound()) item.setTagCompound(new NBTTagCompound());
			NBTTagCompound tags = item.getTagCompound();
			tags.setString("stattrak-message", s);
			return true;
		}
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv)
	{
		if(item == null) return null;
		ItemStack a = item.copy();
		a.addEnchantment(StatTrak.STATTRAK, 1);
		return a;
	}

	@Override
	public int getRecipeSize()
	{
		return 2;
	}

	@Override
	public ItemStack getRecipeOutput()
	{
		if(item == null) return null;
		ItemStack a = item.copy();
		a.addEnchantment(StatTrak.STATTRAK, 1);
		return a;
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inv)
	{
		return new ItemStack[]{null, null, null, null, null, null, null, null, null};
	}
}
