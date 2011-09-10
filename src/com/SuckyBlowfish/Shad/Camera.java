package com.SuckyBlowfish.Shad;

public class Camera {
	private double _x;
    private double _y;
    private double _z;
    
    private double _lx;
    private double _ly;
    private double _lz;
    
    private double distance;
    private double min_distance;
    
    private double pitch;
    private double yaw;
    
    private double zoom;
    
    public Camera()
    {        
        _lx = 0;
        _ly = 0;
        _lz = 0;
        
        distance = 300;
        min_distance=120;
        
        pitch = Math.PI/4;
        yaw = Math.PI/4;
        
        zoom=1;
        
        calculateCoords();
    }
	
    public void mouseRotate(int DX, int DY){
		yaw+=(float)DX/30/Math.PI;
		
		pitch+=((float)DY/30/Math.PI);
		if (pitch>Math.PI/2)pitch=Math.PI/2;
		if (pitch<-Math.PI/2)pitch=-Math.PI/2;
		
		calculateCoords();
	}
    
    private void calculateCoords(){
    	_x=distance*Math.cos(yaw)*Math.cos(pitch);
		_y=distance*-Math.sin(pitch);
		_z=distance*Math.sin(yaw)*Math.cos(pitch);
    }
    
    public void wheelZoom(int DWheel){
    	if (DWheel!=0)
    		distance*=Math.pow(.8f,Math.abs(DWheel)/DWheel);
    	calculateCoords();
	}
    
    public void setPitch(double pitch){
    	this.pitch = pitch;
    }
    
    public void setYaw(double yaw){
    	this.yaw = yaw;
    }

    public void setPosition(double xPos, double yPos, double zPos){
        this._x = xPos;
        this._y = yPos;
        this._z = zPos;
    }
    
    public void setLookPosition(double xLPos, double yLPos, double zLPos){
        this._lx = xLPos;
        this._ly = yLPos;
        this._lz = zLPos;
    }
    
    public void moveForward(double magnitude)
    {
        double xCurrent = this._x;
        double yCurrent = this._y;
        double zCurrent = this._z;
        
        // Spherical coordinates maths
        double xMovement = magnitude * Math.cos(pitch) * Math.cos(yaw); 
        double yMovement = magnitude * Math.sin(pitch);
        double zMovement = magnitude * Math.cos(pitch) * Math.sin(yaw);
              
        double xNew = xCurrent + xMovement;
        double yNew = yCurrent + yMovement;
        double zNew = zCurrent + zMovement;
        
        setPosition(xNew, yNew, zNew);
    }
    
    public void strafeLeft(double magnitude){
        double pitchTemp = pitch;
        pitch = 0;
        yaw = yaw - (0.5 * Math.PI);
        moveForward(magnitude);

        pitch = pitchTemp;
        yaw = yaw + (0.5 * Math.PI);
    }
    
    public void strafeRight(double magnitude){
        double pitchTemp = pitch;
        pitch = 0;

        yaw = yaw + (0.5 * Math.PI);
        moveForward(magnitude);
        yaw = yaw - (0.5 * Math.PI);

        pitch = pitchTemp;
    }   
           
    
    /* -------Get commands--------- */
    public double getX() {return _x;}
    public double getY() {return _y;}
    public double getZ() {return _z;}
    public double getXL(){return _lx;}
    public double getYL(){return _ly;}
    public double getZL(){return _lz;}
    public double getPitch(){return pitch;}
    public double getYaw()  {return yaw;}
    public double getZoom()  {return zoom;}
    
    /* --------------------------- */
    
    /* -------------- Pitch and Yaw commands --------------- */
    
    public void pitchUp(double amount)
    {
        this.pitch += amount;
    }
    
    public void pitchDown(double amount)
    {
        this.pitch -= amount;
    }
    
    public void yawRight(double amount)
    {
        this.yaw += amount;
    }
    
    public void yawLeft(double amount)
    {
        this.yaw -= amount;
    }
}
