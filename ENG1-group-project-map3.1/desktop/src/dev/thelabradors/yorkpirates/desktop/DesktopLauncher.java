package dev.thelabradors.yorkpirates.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import java.awt.*;

import dev.thelabradors.yorkpirates.YorkPiratesGame;
import org.lwjgl.util.Dimension;

import dev.thelabradors.yorkpirates.TileMap2;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		java.awt.Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		//config.setWindowedMode((int) (size.getWidth() * 0.4), (int) (size.getHeight() * 0.4));
		config.setWindowedMode(1280, 800);
		new Lwjgl3Application(new YorkPiratesGame(), config);
	}
}