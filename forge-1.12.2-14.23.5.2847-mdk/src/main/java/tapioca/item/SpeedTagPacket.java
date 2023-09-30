package tapioca.item;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import tapioca.TapiocaMod;

public class SpeedTagPacket  implements IMessage
{
	private NBTTagCompound nbt;

	public SpeedTagPacket() {}
	
	public SpeedTagPacket(NBTTagCompound nbt) 
	{
	    this.nbt = nbt;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) 
	{
		this.nbt = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		ByteBufUtils.writeTag(buf, nbt);
	}
	
	public static class Handler implements IMessageHandler<SpeedTagPacket, IMessage>
	{
		@Override
		public IMessage onMessage(final SpeedTagPacket message, MessageContext ctx) 
		{
			Minecraft.getMinecraft().addScheduledTask(new Runnable()
			{
	            public void run() 
	            {
	            	World world = TapiocaMod.proxy.getClientWorld();
	              	if (world == null) return; 
	              	int playerid = message.nbt.getInteger("playerID");
	              	Entity player = world.getEntityByID(playerid);	              	
	              	if (player != null && player instanceof EntityPlayerSP) 
	              	{
	              		System.out.println("Hi");
	              		if(!message.nbt.hasKey(TapiocaMod.MOD_ID, 10)) return;
	              		((EntityPlayerSP)player).capabilities.setFlySpeed(message.nbt.getCompoundTag(TapiocaMod.MOD_ID).getFloat("flySpeed"));
	              		((EntityPlayerSP)player).capabilities.setPlayerWalkSpeed(message.nbt.getCompoundTag(TapiocaMod.MOD_ID).getFloat("walkSpeed"));
	              		
	              		((EntityPlayerSP)player).sendPlayerAbilities();
	              	} 
	            }
	        });
			return null;
		}
	}
}
