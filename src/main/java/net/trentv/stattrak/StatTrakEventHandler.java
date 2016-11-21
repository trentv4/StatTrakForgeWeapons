
package net.trentv.stattrak;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class StatTrakEventHandler
{
	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event)
	{
		if(!event.getEntity().getEntityWorld().isRemote)
		{
			Entity attacker = event.getSource().getEntity();
			if(attacker != null && attacker instanceof EntityLivingBase)
			{
				Iterable<ItemStack> b = attacker.getHeldEquipment();
				ItemStack heldItem = b.iterator().next();
				if(heldItem != null)
				{
					if(EnchantmentHelper.getEnchantmentLevel(StatTrak.STATTRAK, heldItem) > 0)
					{
						if(heldItem.hasTagCompound())
						{
							// Get the current kill count plus one. If there isn't a "StatTrak-Tracker" tag, then it'll return 0.
							int stattrakKillCount = (int) heldItem.getTagCompound().getInteger("stattrak-tracker") + 1;
							// Update the kill count with the "new" count
							heldItem.getTagCompound().setInteger("stattrak-tracker", stattrakKillCount);
							
							String s = heldItem.getTagCompound().getString("stattrak-message");
							
							// Create the Lore tag and assign it. I18n is used to translate from the language file.
							NBTTagList loreTagList = new NBTTagList();
							loreTagList.appendTag(new NBTTagString(s + ": " + stattrakKillCount));
							NBTTagCompound displayTag = heldItem.getTagCompound().getCompoundTag("display");
							displayTag.setTag("Lore", loreTagList);
							heldItem.getTagCompound().setTag("display", displayTag);
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onLootTableLoadEvent(LootTableLoadEvent event)
	{
		ResourceLocation[] tables = new ResourceLocation[]{
				LootTableList.CHESTS_SIMPLE_DUNGEON,
				LootTableList.CHESTS_VILLAGE_BLACKSMITH,
				LootTableList.CHESTS_ABANDONED_MINESHAFT,
				LootTableList.CHESTS_VILLAGE_BLACKSMITH,
				LootTableList.CHESTS_NETHER_BRIDGE,
				LootTableList.CHESTS_STRONGHOLD_CROSSING,
				LootTableList.CHESTS_STRONGHOLD_CORRIDOR,
				LootTableList.CHESTS_DESERT_PYRAMID
		};
		for(ResourceLocation a : tables)
		{
			if(event.getName() == a)
			{
				if(event.getTable().getPool("defective-stattrak-trackers") == null)
				{
					event.getTable().addPool(new LootPool(
							new LootEntryItem[]{new LootEntryItem(StatTrak.itemDefectiveTracker, 20, 0, new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 2))},
							new LootCondition[0], "defective-stattrak-trackers")},
	                        new LootCondition[0], new RandomValueRange(0, 1), new RandomValueRange(0, 0), "defective-stattrak-trackers"));
				}
			}
		}
	}
}
