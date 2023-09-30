package tapioca;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.RequiresMcRestart;

@Config(modid=TapiocaMod.MOD_ID,name="tapioca",type=Config.Type.INSTANCE)
public class TapiocaConfig 
{
	@LangKey("config.uutapioca")
	@Comment("Enable recipes with UU-Tapioca.")
	@RequiresMcRestart
	public static boolean enable_uutapioca_recipes = true;
}
