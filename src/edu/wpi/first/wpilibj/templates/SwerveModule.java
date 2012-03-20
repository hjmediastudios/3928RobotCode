/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Victor;

/**
 * A Swerve module, comprised of a rotation motor to orient the wheel and a drive motor to drive the wheel, plus associated sensors.
 */
public class SwerveModule {
    //Name all of these variables
    /**
     * the encoder that reports the orientation motor's angle
     */
    private AnalogChannel encoder;
    
    /**
     * the PID controller that controls the angle of the module
     */
    private PIDController pidController;
    
    /**
     * the Jaguar motor controller for the orientation motor
     */
    private Jaguar swerveMotor;
    
    /**
     * the Victor motor controller for the drive motor
     */
    private Victor driveMotor;
    
    /**
     * the minimum value for the orientation motor's PID controller
     */
    private int pidMin;
    
    /** 
     * the maximum value for the orientation motor's PID controller
     */
    private int pidMax;
    
    /**
     * The X-offset (in inches) of the rotation point of the module from the center of the wheel base . If the robot is symmetrical, this will be equal to half the width of the robot. Note that if the module is to the left of the center of the wheel base, this value will be negative.
     */
    public double offsetX;
    
    /**
     * The Y-offset (in inches) of the rotation point of the module from the center of the wheel base. If the module is behind the center of the wheel base, this value will be negative.  
     */
    public double offsetY;

    /**
     * The constructor to create a new Swerve module.
     * @param swerveDescriptor The digital descriptor for the swerve motor.
     * @param driveSlot The digital descriptor for the drive motor.
     * @param encoderChannel The analog channel on the default analog input block that the orientation-motor encoder is plugged into.
     * @param min The minimum PID controller value of the orientation motor.
     * @param max The maximum PID controller value of the orientation motor.
     * @param offX The X-offset (in inches) of the rotation point of the module from the center of the wheel base . If the robot is symmetrical, this will be equal to half the width of the robot. Note that if the module is to the left of the center of the wheel base, this value will be negative.
     * @param offY The Y-offset (in inches) of the rotation point of the module from the center of the wheel base. If the module is behind the center of the wheel base, this value will be negative.  
     */
    public SwerveModule(DigitalDescriptor swerveDescriptor, DigitalDescriptor driveDescriptor, int encoderChannel, int min, int max, double offX, double offY)
    {
        //initializes the swerve module based on the given parameters
        encoder = new AnalogChannel(encoderChannel);
        System.out.println("In S Mod: " + swerveDescriptor.slot + " " + swerveDescriptor.channel);
        swerveMotor = new Jaguar(swerveDescriptor.slot, swerveDescriptor.channel);
        driveMotor = new Victor(driveDescriptor.slot, driveDescriptor.channel);
        pidController = new PIDController(Constants.swerveModule_p, Constants.swerveModule_i, Constants.swerveModule_d, encoder, swerveMotor);
        pidMin = min; //sets the PID controller's minimum possible value
        pidMax = max; //sets the PID controller's maxiumum possible value
        offsetX = offX;
        offsetY = offY;
        pidController.setSetpoint(0); //zero the PID controller to start out with
        pidController.setInputRange(pidMin, pidMax);
    }

    /**
     * Sets the orientation of the Swerve module, from -180 (rotated 180 degrees to the left) to 0 (straight forward) to 180 (rotated 180 degrees to the right).
     * @param angle The angle to set the module's wheel orientation to.
     */
    public void setAngle(double angle) //sets the swerve module's angle to a given value from -180 to 180 degrees
    {
        //System.out.print("Angle in= "+ angle);
        //check if the angle is out of bounds
        if ((angle<-180) || (angle>180))
        {
            System.err.println("Input " + angle + " is out of bounds. Error.");
        }

        angle = 512 - ((angle/360)*(pidMax-pidMin) + pidMin); //Scale the angle from FIRST (-180,180) coordinates to (0,1024) scaling
        
        //Set the PID controller that controls the Swerve module's angle to the proper scaled angle
        pidController.setSetpoint(angle);
    }
    
    /**
     * Sets the speed of the Swerve module's drive motor, from -1 (full reverse) to 0 (stopped) to 1 (full forward).
     * @param speed The speed to set the module's drive motor to.
     */
    public void setSpeed(double speed) //sets the speed the drive wheels move at
    {
        //check if the speed is lower than the allowed -1 value
        if (speed < -1)
        {
            speed = -1;
            System.err.println("Speed " + speed + " is out of bounds." );
        }

        //check if the speed is greater than the allowed value of 1
        if (speed > 1)
        {
            speed = 1;
            System.err.println("Speed " + speed + " is out of bounds." );
        }
        //set the Victor that controls the drive wheel's speed to the speed given
        driveMotor.set(speed);
    }

    /**
     * Sets if the Swerve module's control is enabled or not.
     * @param toggle A true or false value that determines if the module is enabled or not.
     */
    public void setSwerveEnabled(boolean toggle) //Changes whether the controller is enabled or not
    {
        //enable the PID controller if the toggle value is true
        if (toggle)
        {
            pidController.enable();
        }

        //disable the PID controller if the toggle value is false
        else
        {
           pidController.disable();
        }
    }


}

