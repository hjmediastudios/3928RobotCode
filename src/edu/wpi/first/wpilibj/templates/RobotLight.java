/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Solenoid;

/**
 *
 * @author frc3928
 */
public class RobotLight implements Runnable
{
    private Solenoid lights1;
    private Solenoid lights2;
    
    private int state;
    
    public RobotLight(int solenoidChannel1, int solenoidChannel2)
    {
        lights1 = new Solenoid(solenoidChannel1);
        lights2 = new Solenoid(solenoidChannel2);
        state = 0;
    }
    
    public void lightsOn()
    {
        lights1.set(true);
        lights2.set(true);
        state = 1;
    }
    
    
    public void lightsOff()
    {
        lights1.set(false);
        lights2.set(false);
        state = 0; 
    }
    
    public void lightsBlink()
    {
        if (state != 2)
        {
            state = 2;
            new Thread(this).start();
        }
    }
        
    

    public void run() 
    {
        while (state == 2)
        {
            
            try 
            {
                lights1.set(false);
                lights2.set(true);
                Thread.sleep(500);
                lights1.set(true);
                lights2.set(false);
                Thread.sleep(500); 
            } 
            catch (InterruptedException ex)
            {}   
        }    
        
    }
    
}
