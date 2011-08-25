package com.SuckyBlowfish.Shad;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class Shad {
	public static final String GAME_TITLE = "My Game";
	private static final int FRAMERATE = 60;
	private static boolean finished;
	private static float angle;
	private static float velocity;
	private static float acceleration;
	private static float scale;
	
	public static void main(String[] args) {
		boolean fullscreen = (args.length == 1 && args[0].equals("-fullscreen"));
 
		try {
			init(fullscreen);
			run();
		} catch (Exception e) {
			e.printStackTrace(System.err);
			Sys.alert(GAME_TITLE, "An error occured and the game will exit.");
		} finally {
			cleanup();
		}
		System.exit(0);
	}
	
	private static void init(boolean fullscreen) throws Exception {
		Display.setTitle(GAME_TITLE);
		Display.setFullscreen(fullscreen);
		Display.setVSyncEnabled(true);
		Display.create();
	}
 
	private static void run() {
 
		while (!finished) {
			Display.update();
 
			if (Display.isCloseRequested()) {
				finished = true;
			} 
 
			else if (Display.isActive()) {
				logic();
				render();
				Display.sync(FRAMERATE);
			} 
 
			else {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					
				}
				logic();
				
				if (Display.isVisible() || Display.isDirty()) {
					render();
				}
			}
		}
	}
 
	private static void cleanup() {
		Display.destroy();
	}

	private static void logic() {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)||
			Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			finished = true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			acceleration -= .05f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
			acceleration += .05f;
		}
		velocity += acceleration;
		angle += velocity % 360;
		scale = (float) (1+Math.abs(Math.pow(velocity/10,2)));
		acceleration -= acceleration/3;
	}
  
	private static void render() {
	  
		GL11.glMatrixMode(GL11.GL_PROJECTION);
	    GL11.glLoadIdentity();
	    GL11.glOrtho(0, Display.getDisplayMode().getWidth(), 0, Display.getDisplayMode().getHeight(), -1, 1);
	    GL11.glMatrixMode(GL11.GL_MODELVIEW);
	 
	    // clear the screen
	    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
	 
	    // center square according to screen size
	    GL11.glPushMatrix();
	    GL11.glTranslatef(Display.getDisplayMode().getWidth() / 2, Display.getDisplayMode().getHeight() / 2, 0.0f);{
	 
	      // rotate and scale square according to angle
	    	GL11.glRotatef(angle, 0, 0, 1.0f);
	    	GL11.glBegin(GL11.GL_QUADS);{
	        	GL11.glVertex2i((int)(-50*scale),(int)(-50*scale));
	        	GL11.glVertex2i((int)(50*scale),(int)(-50*scale));
	        	GL11.glVertex2i((int)(50*scale),(int)(50*scale));
	        	GL11.glVertex2i((int)(-50*scale),(int)(50*scale));
	    	}
	        GL11.glEnd();
	    }
	    GL11.glPopMatrix();
	}
}