package dev.thelabradors.yorkpirates.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import java.awt.*;
import org.lwjgl.util.Dimension;

import dev.thelabradors.yorkpirates.TileMap;
import dev.thelabradors.yorkpirates.YorkPiratesGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		java.awt.Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		config.setWindowedMode((int) size.getWidth(), (int) size.getHeight());
		new Lwjgl3Application(new TileMap(), config);
	}
}
