package autoclicker;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

public class AutoClickerModConfigCore {
	public static final String GENERAL = "General";
	private static final String AUTOATTACK = GENERAL + ".AutoAttacker";
	private static final String AUTOBREAK = GENERAL + ".AutoBreaker";
	private static final String AUTOFISHING = GENERAL + ".AutoFishing";

	public static Configuration cfg;

	public static void loadConfig(FMLPreInitializationEvent event) {
		cfg = new Configuration(event.getSuggestedConfigurationFile(), AutoClickerMod.MOD_VERSION, true);
		// 初期化する。
		initConfig();
		// コンフィグファイルの内容を変数と同期させる。
		syncConfig();
	}

	private static void initConfig() {
		// カテゴリのコメントなどを設定する。
		// General
		cfg.addCustomCategoryComment(GENERAL, "A settings of AutoClicker Mod");
		cfg.addCustomCategoryComment(AUTOATTACK, "A settings of Auto Attacker in AutoClicker Mod");
		// Difficulty
		//cfg.addCustomCategoryComment(DIFFICULTY, "The settings of difficulty.");
		//cfg.setCategoryLanguageKey(DIFFICULTY, "config.aluminium.category.difficulty");
		//cfg.setCategoryRequiresMcRestart(DIFFICULTY, true);
	}

	public static void syncConfig() {
		// 各項目の設定値を反映させる。
		// AUTOATTACK
		AutoClickerModGui.isNoticeAutoClicker = cfg.getBoolean("NoticeAutoClicker", AUTOATTACK,
				AutoClickerModGui.isNoticeAutoClicker,
				"Make it clear that AutoClicker works when this setting is true. ");
		AutoClickerModGui.isNoticeAutoClicker = cfg.getBoolean("NoticeAutoClicker", AUTOATTACK,
				AutoClickerModGui.isNoticeAutoClicker,
				"Make it clear that AutoClicker works when this setting is true. ");
		AutoClickerModGui.edgeWidth = cfg.getInt("edgeWidth", AUTOATTACK, AutoClickerModGui.edgeWidth, 0, 10,
				"Width of the edge displayed when AutoLeftClicker is enabled");
		AutoClickerModGui.edgeColor = cfg.getInt("edgeColor", AUTOATTACK, AutoClickerModGui.edgeColor, -2147483648,
				2147483647,
				"Edge color when AutoLeftClicker is enabled. \nGive the format of 0xAARRGGBB in decimal. \n(ex. opaque red=0xffff0000=-65536)\n");
		AutoClickerModGui.stringColor = cfg.getInt("stringColor", AUTOATTACK, AutoClickerModGui.stringColor,
				-2147483648,
				2147483647,
				"String color when AutoLeftClicker is enabled. \nGive the format of 0xAARRGGBB in decimal. \n(ex. opaque red=0xffff0000=-65536)\n");

		// AUTOBREAK
		AutoClickerMod.isAutoBreakerValid = cfg.getBoolean("AutoBreaker", AUTOBREAK,
				AutoClickerMod.isAutoBreakerValid,
				"Automatically break object with the specified id when this setting is true. \n");
		AutoClickerMod.idString = cfg.getString("breakId", AUTOBREAK, AutoClickerMod.idString,
				"For objects that can be destroyed in 1 tick.\nEnter object IDs separated by commas! \n(ex. Grass=31)\n");

		// AUTOFISHING
		AutoClickerMod.isAutoFishingValid = cfg.getBoolean("AutoFishing", AUTOFISHING,
				AutoClickerMod.isAutoFishingValid,
				"You can fish and be afk when this setting is true. ");
		AutoClickerMod.fishingInterval = cfg.getInt("fishingInterval", AUTOFISHING, AutoClickerMod.fishingInterval, 0,
				1000, "Auto fishing interval time.\n");
		AutoClickerMod.maxFishingTime = cfg.getInt("maxFishingTime", AUTOFISHING, AutoClickerMod.maxFishingTime, 0,
				100000, "Maximum time for one fishing. \n");

		// 設定内容をコンフィグファイルに保存する。
		cfg.save();
	}
}
