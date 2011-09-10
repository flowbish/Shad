package com.SuckyBlowfish.Shad;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.*;
import org.lwjgl.util.vector.Vector3f;

public class Level {
	
	private int length;
	private int width;
	private int depth;
	private ArrayList<LightObject> lightObjects = new ArrayList<LightObject>();
	
	public Level(){
		length = 300;
		width = 150;
		depth = 25;
	}
	
	public void putLight(LightObject l){
		lightObjects.add(l);
	}
	
	public void render(){
		GL11.glPushMatrix();
			GL11.glTranslatef(-width/2f, 0f, -length/2f);
			GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex3d(0, 0, 0);
				GL11.glVertex3d(0, 0, length);
				GL11.glVertex3d(width, 0, length);
				GL11.glVertex3d(width, 0, 0);
				
				GL11.glVertex3d(0, 0, 0);
				GL11.glVertex3d(0, 0, length);
				GL11.glVertex3d(0, 0-depth, length);
				GL11.glVertex3d(0, 0-depth, 0);
				
				GL11.glVertex3d(width, 0, 0);
				GL11.glVertex3d(width, 0, length);
				GL11.glVertex3d(width, 0-depth, length);
				GL11.glVertex3d(width, 0-depth, 0);
				
				GL11.glVertex3d(0, 0, 0);
				GL11.glVertex3d(width, 0, 0);
				GL11.glVertex3d(width, 0-depth, 0);
				GL11.glVertex3d(0, 0-depth, 0);
				
				GL11.glVertex3d(0, 0, length);
				GL11.glVertex3d(width, 0, length);
				GL11.glVertex3d(width, 0-depth, length);
				GL11.glVertex3d(0, 0-depth, length);
				
				GL11.glVertex3d(0, 0-depth, 0);
				GL11.glVertex3d(0, 0-depth, length);
				GL11.glVertex3d(width, 0-depth, length);
				GL11.glVertex3d(width, 0-depth, 0);
			GL11.glEnd();
			for(LightObject l:lightObjects){
				l.render();
			}
		GL11.glPopMatrix();
	}
}
