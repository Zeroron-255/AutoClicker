package zeroron.autoClicker;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

@Mod(modid = "autoLeftClicker", name = "autoLeftClicker", version = "1.0.0")
public class Main {
	public static int clickTickInterval = 10;
	private int leftCooldown = 0;

	// https://mcmodding.jp/modding/index.php/1.7のキーボードイベント追加
	public static final KeyBinding toggleLClick = new KeyBinding("toggle Auto LeftClick", Keyboard.KEY_F10, "autoLeftClicker");
	public static final KeyBinding temp = new KeyBinding("toggle Auto LeftClick", Keyboard.KEY_F7, "autoLeftClicker");
	// KeyBind を登録する
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		// first called
		ClientRegistry.registerKeyBinding(toggleLClick);
		FMLCommonHandler.instance().bus().register(this);
	}
	// Keyが押されることで Macro の On/Off を切り替える
	public static boolean isPressedLClick = false;
	@SubscribeEvent
	public void KeyHandlingEvent(KeyInputEvent event) {
		if(toggleLClick.isPressed()) {
			isPressedLClick = !isPressedLClick;
		}
	}

	// WorldTick毎に呼ばれる
	@SubscribeEvent
	public void onWorldTick(TickEvent.PlayerTickEvent event) {
		// System.out.println(ispressedLClick);
		if(isPressedLClick) {
			boolean mode = false;
			if(leftCooldown <= 0) mode = true;
			if(leftClick(mode))leftCooldown = clickTickInterval + 1;
			if(0 < leftCooldown) leftCooldown--;
		}
	}

	// mobを攻撃する関数, 成功したら true を返す
	public boolean leftClick(boolean mode) {
		if(mode == true) {
			net.minecraft.entity.Entity target = Minecraft.getMinecraft().objectMouseOver.entityHit;
			if(target != null) {
				System.out.println("target");
				Minecraft.getMinecraft().playerController.attackEntity(Minecraft.getMinecraft().thePlayer, target);
				return true;
			}
		}
		return false;
	}
}