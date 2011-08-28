package com.SuckyBlowfish.Shad;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.*;

public class Shad {
	public static final String GAME_TITLE = "My Game";
	private static final int FRAMERATE = 60;
	private static boolean finished;
	private float a=1;
	private Camera camera;
	private Level level;
	
	public static void main(String[] args) {
		boolean fullscreen = true; //(args.length == 1 && args[0].equals("-fullscreen"));
		Shad game = new Shad();
		try {
            game.init(fullscreen);
		} catch (Exception e) {
			e.printStackTrace(System.err);
			Sys.alert(GAME_TITLE, "An error occured and the game will exit.");
		} finally {
			game.cleanup();
		}
		System.exit(0);
	}
	
	private void init(boolean fullscreen) throws Exception {
		Display.setTitle(GAME_TITLE);
		Display.setFullscreen(fullscreen);
		Display.setVSyncEnabled(true);
		Display.create();
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_LIGHT0);
		GL11.glLightf(GL11.GL_LIGHT0, GL11.GL_CONSTANT_ATTENUATION, 1.0f);
		GL11.glLightf(GL11.GL_LIGHT0, GL11.GL_SPECULAR, 0.3f);
		run();
	}
 
	private void run() {		
		camera = new Camera();
		level = new Level();
		Mouse.setGrabbed(true);
		
		while (!finished) {
			Display.update();
			if (Display.isCloseRequested())finished = true;
			else if (Display.isActive()) {
				logic();
				render();
				Display.sync(FRAMERATE);
			}else{
				try{
					Thread.sleep(100);
				}catch (InterruptedException e) {}
				logic();
				if (Display.isVisible() || Display.isDirty()) {
					render();
				}
			}
		}
	}
 
	private void cleanup() {
		Display.destroy();
	}

	private void logic() {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)||
			Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			finished = true;
		}
		camera.mouseRotate(Mouse.getDX(),Mouse.getDY());
		camera.wheelZoom(Mouse.getDWheel());
	}
  
	private void render() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
	    	GL11.glLoadIdentity();
	    	GLU.gluPerspective(70f, 1.6f, 1f, 3000f);
	    	GLU.gluLookAt((float)camera.getX(), (float)camera.getY(), (float)camera.getZ(),
	    				  (float)camera.getXL(), (float)camera.getYL(), (float)camera.getZL(),
	    				  0f, 1f, 0f);
	    	
	    GL11.glMatrixMode(GL11.GL_MODELVIEW);
		    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT |
		    		     GL11.GL_DEPTH_BUFFER_BIT);
		    level.render();
	}
}
