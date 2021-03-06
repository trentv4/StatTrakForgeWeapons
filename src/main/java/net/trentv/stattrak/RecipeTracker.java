package net.trentv.stattrak;

import java.util.ArrayList;
import java.util.Random;

import javax.annotation.Nonnull;

import mcjty.lib.compat.CompatIRecipe;
import mcjty.lib.tools.ItemStackList;
import mcjty.lib.tools.ItemStackTools;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class RecipeTracker implements CompatIRecipe
{
	private ItemStack item;
	private Random random = new Random();
	
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn)
	{
		ItemStack[] inventory = getNonNullItemStacks(inv);
		
		if(inventory.length == 2)
		{
			// Shut up.
			// Also, xor. First time I've ever seriously used it.
			if(isTracker(inventory[0]) ^ isTracker(inventory[1]))
			{
				// Shut up. I mean it.
				ItemStack tracker = isTracker(inventory[0]) ? inventory[0] : inventory[1];
				ItemStack actualItem = isTracker(inventory[0]) ? inventory[1] : inventory[0];

				String message = "";
				if(tracker.getItem() == StatTrak.itemTracker)
				{
					message = I18n.translateToLocal("stattrak-killcount");
				}
				else
				{
					message = I18n.translateToLocal("defective-stattrak-message-" + (random.nextInt(10)+1));
				}
				
				if(!actualItem.hasTagCompound()) actualItem.setTagCompound(new NBTTagCompound());
				NBTTagCompound tags = actualItem.getTagCompound();
				tags.setString("stattrak-message", message);
				
				item = actualItem;
				return true;
			}
		}
		
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv)
	{
		if(item == null) return ItemStackTools.getEmptyStack();
		ItemStack a = item.copy();
		boolean addEnchant = true;

		NBTTagList enchants = a.getEnchantmentTagList();
		for(int i = 0; i < enchants.tagCount(); i++)
		{
			NBTTagCompound tag = (NBTTagCompound) enchants.get(i);
			if(tag.getShort("id") == (short)Enchantment.getEnchantmentID(StatTrak.STATTRAK))
			{
				addEnchant = false;
			}
		}
		if(addEnchant) a.addEnchantment(StatTrak.STATTRAK, 1);
		return a;
	}

	@Override
	public int getRecipeSize()
	{
		return 2;
	}
	
	@Override
	public ItemStackList getRemainingItems(InventoryCrafting inv) {
		return ItemStackList.create(9);
	}
	
	@Override
	public ItemStack getRecipeOutput() {
		return ItemStackTools.getEmptyStack();
	}

	private ItemStack[] getNonNullItemStacks(InventoryCrafting inv)
	{
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		//wish I had an easy for-each here...
		for(int i = 0; i < inv.getSizeInventory(); i++)
		{
			ItemStack item = inv.getStackInSlot(i);
			if(!ItemStackTools.isEmpty(item))
			{
				list.add(item);
			}
		}
		
		return list.toArray(new ItemStack[list.size()]);
	}
	
	private boolean isTracker(ItemStack a)
	{
		return (a.getItem() == StatTrak.itemDefectiveTracker | a.getItem() == StatTrak.itemTracker);
	}
}
