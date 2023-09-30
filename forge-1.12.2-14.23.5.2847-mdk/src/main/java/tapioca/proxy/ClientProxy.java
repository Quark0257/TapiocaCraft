package tapioca.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;

public class ClientProxy extends CommonProxy
{
	public World getClientWorld() 
	{
	    return (World)(FMLClientHandler.instance().getClient()).world;
	}
	
	public boolean isSneakKeyPressing()
	{
		return Minecraft.getMinecraft().gameSettings.keyBindSneak.isPressed();
	}
	
	public boolean isJumpKeyPressing()
	{
		return Minecraft.getMinecraft().gameSettings.keyBindJump.isPressed();
	}
}
