package net.trentv.stattrak;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentStatTrak extends Enchantment
{
	protected EnchantmentStatTrak(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots)
	{
		super(rarityIn, typeIn, slots);
		setName("stattrak");
		this.setRegistryName(StatTrak.MODID, "stattrak");
	}
	
    public boolean isAllowedOnBooks()
    {
        return true;
    }
    
    public int getMaxLevel()
    {
        return 1;
    }
}
