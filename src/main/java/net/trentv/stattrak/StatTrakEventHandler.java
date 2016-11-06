
package net.trentv.stattrak;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class StatTrakEventHandler
{
	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event)
	{
		if(!event.getEntity().getEntityWorld().isRemote)
		{
			Entity attacker = event.getSource().getEntity();
			if(attacker != null)
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
							
							// Create the Lore tag and assign it. I18n is used to translate from the language file.
							NBTTagList loreTagList = new NBTTagList();
							loreTagList.appendTag(new NBTTagString(I18n.translateToLocal("stattrak-killcount") + stattrakKillCount));
							NBTTagCompound displayTag = heldItem.getTagCompound().getCompoundTag("display");
							displayTag.setTag("Lore", loreTagList);
							heldItem.getTagCompound().setTag("display", displayTag);
						}
					}
				}
			}
		}
	}
}
