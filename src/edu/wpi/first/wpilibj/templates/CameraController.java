/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 * @author Tony Milosch
 */
public class CameraController implements Runnable
{

    private Solenoid cameraLights;
    private boolean camControl;
    NetworkTable cameraTable;
    private int navInfo_xOffset = -555;
    private double navInfo_distance = -222;

    public CameraController()
    {
        cameraLights = new Solenoid(Constants.cameraLights_channel);
        cameraLights.set(true);
        camControl = false;
        cameraTable = NetworkTable.getTable("cameraTable");
    }

    public void TurnCamControlOn()
    {
        if(!camControl)
        {
            camControl = true;
            new Thread(this).start();
        }
    }

    public void TurnCamControlOff()
    {
        camControl = false;
    }

    public void run()
    {
        while (camControl == true)
        {
            getNavigationValues();

        }
    }

    private void getNavigationValues()
    {
        cameraTable.beginTransaction();
        navInfo_xOffset = cameraTable.getInt("xOffset", -666);
        navInfo_distance = cameraTable.getDouble("distance", -666);
        cameraTable.endTransaction();
    }
    
    public int getXOffset()
    {
        return navInfo_xOffset;
    }
    
    public double getDistance()
    {
        return navInfo_distance;
    }
}
