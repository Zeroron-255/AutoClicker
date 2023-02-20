package autoclicker;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class AutoClickerModGui {
	private static final Minecraft mc = Minecraft.getMinecraft();

	public static boolean isNoticeAutoClicker = true;
	public static int stringColor = 0xffff3600;
	public static int edgeColor = 0xffff3600;
	public static int edgeWidth = 3;
	private static String text = "AUTCLICKER IS ENABLED";

	public static void setGui() {
		if (isNoticeAutoClicker) {
			ScaledResolution resolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
			int width = resolution.getScaledWidth();
			int height = resolution.getScaledHeight();

			// draw text
			int stringWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(text);
			Minecraft.getMinecraft().fontRenderer.drawString(text, width / 2 - stringWidth / 2, edgeWidth + 1,
					stringColor);

			// draw edge
			Minecraft.getMinecraft().ingameGUI.drawRect(0, 0, width - edgeWidth, edgeWidth, edgeColor);
			Minecraft.getMinecraft().ingameGUI.drawRect(width - edgeWidth, 0, width, height - edgeWidth, edgeColor);
			Minecraft.getMinecraft().ingameGUI.drawRect(edgeWidth, height - edgeWidth, width, height, edgeColor);
			Minecraft.getMinecraft().ingameGUI.drawRect(0, edgeWidth, edgeWidth, height, edgeColor);
		}
	}
}
