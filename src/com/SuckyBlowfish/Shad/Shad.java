package com.SuckyBlowfish.Shad;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.*;

public class Shad {
	public static final String GAME_TITLE = "My Game";
	private static final int FRAMERATE = 60;
	private static boolean finished;
	private static float a=1;
	private static Camera camera;
	
	public static void main(String[] args) {
		boolean fullscreen = (args.length == 1 && args[0].equals("-fullscreen"));
 
		try {
            init(true);
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
		camera = new Camera();
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
		
	}
  
	private static void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
	    	GL11.glLoadIdentity();
	    	GL11.glFrustum(-1, 1, -1, 1, 1, 1000);
//	    	GLU.gluLookAt((float)camera.getXPos(), (float)camera.getYPos() , (float)camera.getZPos(), 
//	    			(float)camera.getXLPos(), (float)camera.getYLPos(), (float)camera.getZLPos(),
//                    0.0f, 1.0f, 0.0f);
	    
	    GL11.glMatrixMode(GL11.GL_MODELVIEW);
	    
	    GL11.glPushMatrix();
	    	GL11.glTranslatef( 0, 0, 0 );
	    	GL11.glRotatef(10f,0,0,0);
	    	GL11.glBegin(GL11.GL_TRIANGLES);
		    	GL11.glColor4f(0f, 1f, 0f, 1f);
	    		GL11.glVertex3f(0f, 1f, 0f);
	    		GL11.glVertex3f(1f, 0f, 1f);
	    		GL11.glVertex3f(1f, 0f, -1f);
	    		
	    		GL11.glColor4f(1f, 0f, 0f, 0.2f);
	    		GL11.glVertex3f(0f, 1f, 0f);
	    		GL11.glVertex3f(1f, 0f, -1f);
	    		GL11.glVertex3f(-1f, 0f, -1f);
	    		
	    		GL11.glColor4f(0f, 0f, 1f, 0.2f);
	    		GL11.glVertex3f(0f, 1f, 0f);
	    		GL11.glVertex3f(-1f, 0f, -1f);
	    		GL11.glVertex3f(-1f, 0f, 1f);
	    		
	    		GL11.glColor4f(0f, 0f, 0f, 0.2f);
	    		GL11.glVertex3f(0f, 1f, 0f);
	    		GL11.glVertex3f(-1f, 0f, 1f);
    		GL11.glVertex3f(1f, 0f, 1f);
	        GL11.glEnd();
	    GL11.glPopMatrix();
	    
	}
}
