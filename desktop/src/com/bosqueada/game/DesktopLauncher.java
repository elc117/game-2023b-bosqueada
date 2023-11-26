package com.bosqueada.game;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
//import com.bosqueada.game.Bosqueada;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Bosqueada");
		config.setWindowedMode(1280, 720);
		config.setWindowIcon(Files.FileType.Internal, "BosqueadaIcon2.jpg");

		new Lwjgl3Application(new Bosqueada(), config);
	}
}
