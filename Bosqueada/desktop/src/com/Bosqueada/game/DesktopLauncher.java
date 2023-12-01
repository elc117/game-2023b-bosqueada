package com.bosqueada.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3WindowConfiguration;
import com.bosqueada.game.Bosqueada;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Bosqueada");

		// Isso define o modo de tela cheia com as configuracoes padrao do monitor
		config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
		
		new Lwjgl3Application(new Bosqueada(), config);
	}
}
