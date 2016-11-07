package net.trentv.stattrak.client;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.trentv.stattrak.CommonProxy;
import net.trentv.stattrak.StatTrak;

public class ClientProxy implements CommonProxy
{
	@Override
	public void registerRenderers()
	{
		ModelLoader.setCustomModelResourceLocation(StatTrak.itemTracker, 0, new ModelResourceLocation(StatTrak.itemTracker.getRegistryName(), "inventory"));
	}
}
