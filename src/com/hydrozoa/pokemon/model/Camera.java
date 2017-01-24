package com.hydrozoa.pokemon.model;

/**
 * @author hydrozoa
 */
public class Camera {
	
	private float cameraX = 0f;
	private float cameraY = 0f;
	
	public void update(float newCamX, float newCamY) {
		this.cameraX = newCamX;
		this.cameraY = newCamY;
	}

	public float getCameraX() {
		return cameraX;
	}

	public float getCameraY() {
		return cameraY;
	}
}
