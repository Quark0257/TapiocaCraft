package tapioca.proxy;

import net.minecraft.world.World;

public class CommonProxy {
	public World getClientWorld() 
	{
	    return null;
	}
	
	public boolean isSneakKeyPressing()
	{
		return false;
	}
	
	public boolean isJumpKeyPressing()
	{
		return false;
	}
}
