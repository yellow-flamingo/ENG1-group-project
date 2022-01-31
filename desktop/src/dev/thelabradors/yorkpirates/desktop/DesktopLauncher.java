package dev.thelabradors.yorkpirates.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import dev.thelabradors.yorkpirates.YorkPiratesGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		//java.awt.Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		//config.setW  indowedMode((int) (size.getWidth() * 0.8), (int) (size.getHeight() * 0.8));
		//config.setWindowedMode(1280, 800);
		config.setWindowedMode(1280, 800);
		new Lwjgl3Application(new YorkPiratesGame(), config);
	}
}