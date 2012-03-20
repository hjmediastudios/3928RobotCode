/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Jaguar;

/**
 * A class for the ball management system, a.k.a. the ball channel that runs through the center of the robot.
 * @author Nick Royer
 */
public class BallManager implements Runnable
{
    DigitalInput ballSensor; //the sensor at the start of the chute
    DigitalInput fireButton;
    int mode = 0; //0 is normal operation; 1 is "just-fired" 
    private boolean isOn;
    

    Jaguar ballPathMotor;
  /**
   * Default constructor for a new BallManager.
   * @param ballPathMotorDescriptor A DigitalDescriptor that describes the motor which circulates balls toward the shooter.
   * @param ballSensorDescriptor The input for the ball-detecting beam-break sensor located at the "mouth" of the ball management system.
   */
    public BallManager(DigitalDescriptor ballPathMotorDescriptor, DigitalDescriptor ballSensorDescriptor)
    {
        ballPathMotor = new Jaguar(ballPathMotorDescriptor.slot, ballPathMotorDescriptor.channel); //TODO find which way this spins to suck balls in
        ballSensor = new DigitalInput(ballSensorDescriptor.slot, ballSensorDescriptor.channel);
        fireButton = new DigitalInput(0, 1); //TODO how is operator input handled?
        this.setOn();   
    }
    
    /**
     * Sets the ball management system on and starts a new thread for ball management operations.
     **/
    public void setOn()
    {
        isOn = true;
        new Thread(this).start(); //TODO will this make ball management have 2 threads if it's re-started?
    }
    
    /**
     * Disables the ball management system.
     */
    public void setOff()
    {
        isOn = false;
    }

    /**
    * Runs the ball management system.
    **/
    public void run()
    {
        while (isOn == true)
        {
            if (fireButton.get() == true) //check if fire button is pressed
            {
                ballPathMotor.set(1); //feed balls in
                mode = 1;          
            }
            else //if the fire button's not pressed
            {
                if (mode == 1) //if the button was just released
                {
                    if ( ballSensor.get() == false) //if the sensor's triggered on the back motion
                    {
                        ballPathMotor.set(-1); //cycle balls back
                    }
                    else
                    {
                        ballPathMotor.set(0); //stop the movement! The balls want to get off!
                        mode = 0;
                    }
                }
                else
                {
                    if (ballSensor.get() == true) //cycle balls forward if fire button's not pressed and sensor is triggered
                    {
                        ballPathMotor.set(1);
                    }
                    else //stop the movement
                    {
                        ballPathMotor.set(0);
                    }
                    mode = 0;
                }

            }
            try {
                Thread.sleep(30);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        } //end while
    }
    
}
