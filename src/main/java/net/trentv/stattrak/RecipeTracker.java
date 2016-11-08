package net.trentv.stattrak;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RecipeTracker implements IRecipe
{
	private ItemStack item;
	
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn)
	{
		item = null;
		
		int nonNullSlots = 0;
		for(int i = 0; i < inv.getSizeInventory(); i++)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if(stack != null)
			{
				nonNullSlots++;
			}
		}
		if(nonNullSlots == 2)
		{
			ItemStack item1 = null;
			ItemStack item2 = null;
			for(int i = 0; i < inv.getSizeInventory(); i++)
			{
				ItemStack a = inv.getStackInSlot(i);
				if(a != null)
				{
					if(item1 == null)
					{
						item1 = a;
					}
					else
					{
						item2 = a;
					}
				}
			}
			if(item1.getItem() != StatTrak.itemTracker && item2.getItem() == StatTrak.itemTracker) item = item1;
			if(item1.getItem() == StatTrak.itemTracker && item2.getItem() != StatTrak.itemTracker) item = item2;
			if(item != null)
			{
				return true;
			}
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
