/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import com.sun.squawk.util.MathUtils;

/**
 * A four-wheeled complete Swerve drive system.
 * @author Nick Royer, Sarah Pinkerton, Trey Miller-Dean
 */
public class SwerveDrive {

    /**
     * A set of Swerve modules. By default, 
     * module 0 = front left
     * module 1 = front right
     * module 2 = back left
     * module 3 = back right
     */
    SwerveModule[] swerveModules;

    /**
     * Initializes the Swerve Drive system
     */
    public SwerveDrive() {

        swerveModules = new SwerveModule[4]; //create a set if swerve swerveModules called swerveModules

        System.out.println("Mod0 " + Constants.motorDescriptor_frontLeftSwerve.slot + " " + Constants.motorDescriptor_frontLeftSwerve.channel);
        swerveModules[0] = new SwerveModule(Constants.motorDescriptor_frontLeftSwerve,
                Constants.motorDescriptor_frontLeftDrive,
                Constants.frontLeft_encoderChannel,
                Constants.frontLeft_pidMin,
                Constants.frontLeft_pidMax,
                -Constants.width / 2,
                Constants.height / 2);
        System.out.println("Finished initializing Module 0.");
        swerveModules[1] = new SwerveModule(Constants.motorDescriptor_frontRightSwerve,
                Constants.motorDescriptor_frontRightDrive,
                Constants.frontRight_encoderChannel,
                Constants.frontRight_pidMin,
                Constants.frontRight_pidMax,
                Constants.width / 2,
                Constants.height / 2);
        System.out.println("Finished initializing Module 1.");

        swerveModules[2] = new SwerveModule(Constants.motorDescriptor_backLeftSwerve,
                Constants.motorDescriptor_backLeftDrive,
                Constants.backLeft_encoderChannel,
                Constants.backLeft_pidMin,
                Constants.backLeft_pidMax,
                -Constants.width / 2,
                -Constants.height / 2);
        System.out.println("Finished initializing Module 2.");

        swerveModules[3] = new SwerveModule(Constants.motorDescriptor_backRightSwerve,
                Constants.motorDescriptor_backRightDrive,
                Constants.backRight_encoderChannel,
                Constants.backRight_pidMin,
                Constants.backRight_pidMax,
                Constants.width / 2,
                -Constants.height / 2);
        System.out.println("Finished initializing Module 3.");


    }

    /**
     * Enables or disables every Swerve module on the drive system.
     * @param toggle A true-or-false value determining whether every module is switched either on or off
     */
    public void enableSwerveModule(boolean toggle) //enables or disables swerve swerveModules depending on parameter
    {
        for (int i = 0; i < 4; i++) {
            swerveModules[i].setSwerveEnabled(toggle);
        }
    }

    /**
     * Sets every Swerve module to the same angle and speed, in so-called "normal Swerve" mode.
     * @param angle The angle every module is to be set to. The angle is from -180 degrees to 0 degrees (straight forward) to 180 degrees.
     * @param speed The speed every module is set to, from -1 (full reverse) to 0 (stopped) to 1 (full forward)
     */
    public void setNormalSwerve(double angle, double speed) 
    {
        //System.out.println("Angle: " + angle + "Speed: " + speed);
        for (int i = 0; i < 4; i++) 
        {
            swerveModules[i].setAngle(angle);
            swerveModules[i].setSpeed(speed);
        }
    }

    /**
     * Sets each Swerve module to a different value in such a manner that each wheel is oriented perpendicular to an imaginary line from the center of
     * each wheel to an arbitrarily-defined rotation point on a line parallel to the front of the robot through the center of the robot.
     * @param jVal The joystick value passed to the function, which then takes that value (from -1 to 1) and transforms it into the rotation point.
     * @param speed The speed each wheel is set to. All wheels are set to the same speed to ensure uniform motion.
     */
    public void setCarSwerve(double jVal, double speed) //steers robot based on a rotation point set relative to robot
    {
        double x = getSteerPointFromJoystick(jVal);
        //System.out.println("JStick " + jVal + ";   point " + x);
        //Front left
        double angle0 = Math.toDegrees(MathUtils.atan(swerveModules[0].offsetY / (x - swerveModules[0].offsetX))); //:)
        if (x >= swerveModules[0].offsetX && x <= 0) //:)
        {
            angle0 = angle0 - 180; //:)
        }
        
        //Front right
        double angle1 = Math.toDegrees(MathUtils.atan(swerveModules[1].offsetY / (x - swerveModules[1].offsetX)));
        if (x < swerveModules[1].offsetX && x > 0) {
            angle1 = angle1 + 180;
        }
        
        //Back left
        double angle2 = Math.toDegrees(MathUtils.atan(swerveModules[2].offsetY / (x - swerveModules[2].offsetX)));
        if (x >= swerveModules[2].offsetX && x <= 0) {
            angle2 = angle2 + 180;
        }
        
        //Back right
        double angle3 = Math.toDegrees(MathUtils.atan(swerveModules[3].offsetY / (x - swerveModules[3].offsetX)));
        if (x < swerveModules[3].offsetX && x > 0) {
            angle3 = angle3 - 180;
        }

        swerveModules[0].setAngle(angle0);
        swerveModules[1].setAngle(angle1);
        swerveModules[2].setAngle(angle2);
        swerveModules[3].setAngle(angle3);



        for (int i = 0; i < 4; i++) {
            swerveModules[i].setSpeed(speed); //set speeds of swerve modules
        }


    }

    /**
     * A private helper method for setCarSwerve that performs the mapping from the joystick value (from -1 to 1) into the proper rotation point.
     * private double getSteerPointFromJoystick(double jVal) 
     * //Convert input of joystick into a steering point located relative to robot.
    * @param jVal The joystick value input into the function.
     * @return The steering point relative to the robot.
     */
    private double getSteerPointFromJoystick(double jVal) 
    {
        //our steering wheel pot sucks so we need to do this
        if(jVal > 0.93)
        {
            jVal = 1;
        }
        if(jVal < -0.93)
        {
            jVal = -1;
        }
        double steerPoint;
        if (jVal > Constants.steering_deadBandWidth) //right side rotation points
        {
            steerPoint = Constants.steering_multiplier * 1/Math.tan(.5*Math.PI*jVal);
        } else if (jVal < -Constants.steering_deadBandWidth) //left side rotation points
        {
            steerPoint = Constants.steering_multiplier * 1/Math.tan(.5*Math.PI*jVal);
        } else //"dead zone" in the center for straight steering
        {
            steerPoint = -Double.MAX_VALUE;
        }

        return steerPoint;
    }
    //Jeremy's code nugget
    
    public void setBrakes()
    {
        swerveModules[0].setAngle(-45);
        swerveModules[1].setAngle(45);
        swerveModules[2].setAngle(45);
        swerveModules[3].setAngle(-45);
        
        for (int i=0; i<4; i++)
        {
            swerveModules[i].setSpeed(0);
        }
    }
    //end of class
}
