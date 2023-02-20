package autoclicker;

import java.util.Set;

import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

public class AutoClickerModConfigGuiFactory implements IModGuiFactory {
	@Override
	public void initialize(Minecraft minecraftInstance) {
	}

	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass() {
		return AutoClickerModConfigGui.class;
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}

	@Override
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
		return null;
	}

	public static class AutoClickerModConfigGui extends GuiConfig {
		public AutoClickerModConfigGui(GuiScreen parent) {
			super(parent,
					(new ConfigElement<Object>(
							AutoClickerModConfigCore.cfg.getCategory(AutoClickerModConfigCore.GENERAL)))
									.getChildElements(),
					AutoClickerMod.MOD_ID, false, false, AutoClickerMod.MOD_NAME);
		}
	}
}
