package dev.thelabradors.yorkpirates.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import dev.thelabradors.yorkpirates.YorkPiratesGame;

import java.awt.*;
/**
 * Main function, used to run the code.
 */
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		// Gets the size of the monitor of the user
		java.awt.Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		// Set to 80% of the screen.
		config.setWindowedMode((int) (size.getWidth() * 0.8), (int) (size.getHeight() * 0.8));
		new Lwjgl3Application(new YorkPiratesGame(), config);
	}
}