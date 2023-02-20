package autoclicker;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

public class AutoClickerModConfigCore {
  public static final String GENERAL = "General";

  private static final String AUTOATTACK = "General.AutoAttacker";

  private static final String AUTOBREAK = "General.AutoBreaker";

  private static final String AUTOFISHING = "General.AutoFishing";

  public static Configuration cfg;

  public static void loadConfig(FMLPreInitializationEvent event) {
    cfg = new Configuration(event.getSuggestedConfigurationFile(), "1.1.0", true);
    initConfig();
    syncConfig();
  }

  private static void initConfig() {
    cfg.addCustomCategoryComment("General", "A settings of AutoClicker Mod");
    cfg.addCustomCategoryComment("General.AutoAttacker", "A settings of Auto Attacker in AutoClicker Mod");
  }

  public static void syncConfig() {
    AutoClickerModGui.isNoticeAutoClicker = cfg.getBoolean("NoticeAutoClicker", "General.AutoAttacker", AutoClickerModGui.isNoticeAutoClicker, "Make it clear that AutoClicker works when this setting is true. ");
    AutoClickerModGui.isNoticeAutoClicker = cfg.getBoolean("NoticeAutoClicker", "General.AutoAttacker", AutoClickerModGui.isNoticeAutoClicker, "Make it clear that AutoClicker works when this setting is true. ");
    AutoClickerModGui.edgeWidth = cfg.getInt("edgeWidth", "General.AutoAttacker", AutoClickerModGui.edgeWidth, 0, 10, "Width of the edge displayed when AutoLeftClicker is enabled");
    AutoClickerModGui.edgeColor = cfg.getInt("edgeColor", "General.AutoAttacker", AutoClickerModGui.edgeColor, -2147483648, 2147483647, "Edge color when AutoLeftClicker is enabled. \nGive the format of 0xAARRGGBB in decimal. \n(ex. opaque red=0xffff0000=-65536)\n");
    AutoClickerModGui.stringColor = cfg.getInt("stringColor", "General.AutoAttacker", AutoClickerModGui.stringColor, -2147483648, 2147483647, "String color when AutoLeftClicker is enabled. \nGive the format of 0xAARRGGBB in decimal. \n(ex. opaque red=0xffff0000=-65536)\n");
    AutoClickerMod.isAutoBreakerValid = cfg.getBoolean("AutoBreaker", "General.AutoBreaker", AutoClickerMod.isAutoBreakerValid, "Automatically break object with the specified id when this setting is true. \n");
    AutoClickerMod.idString = cfg.getString("breakId", "General.AutoBreaker", AutoClickerMod.idString, "For objects that can be destroyed in 1 tick.\nEnter object IDs separated by commas! \n(ex. Grass=31)\n");
    AutoClickerMod.isAutoFishingValid = cfg.getBoolean("AutoFishing", "General.AutoFishing", AutoClickerMod.isAutoFishingValid, "You can fish and be afk when this setting is true. ");
    AutoClickerMod.fishingInterval = cfg.getInt("fishingInterval", "General.AutoFishing", AutoClickerMod.fishingInterval, 0, 1000, "Auto fishing interval time.\n");
    AutoClickerMod.maxFishingTime = cfg.getInt("maxFishingTime", "General.AutoFishing", AutoClickerMod.maxFishingTime, 0, 100000, "Maximum time for one fishing. \n");
    cfg.save();
  }
}
