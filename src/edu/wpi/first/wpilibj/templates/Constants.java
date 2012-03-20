/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

/**
 * A set of physical constants and PID controller variables that won't change from robot chassis to robot chassis.
 * @author Nick Royer
 */

//TODO change pidMins and pidMaxes to [256, 512] to prevent wire slippage

public class Constants {

    public static final int defaultSlot = 1;
    

    public static int speedResponseCurve;


    public static double width = 20.2;
    

    public static double height = 24.125;

    

    public static double swerveModule_p = 0.007900000000000008; //0.005700000000000002
    public static double swerveModule_i = 0; //0
    public static double swerveModule_d = 0; //0
        
    //Front left module settings [0]
    public static DigitalDescriptor motorDescriptor_frontLeftSwerve = new DigitalDescriptor(1, 7); // 1, 7
    public static DigitalDescriptor motorDescriptor_frontLeftDrive = new DigitalDescriptor(1, 3); // 1, 3
    public static int frontLeft_encoderChannel = 4; // 4
    public static int frontLeft_pidMin = 0; //24
    public static int frontLeft_pidMax = 959; //965
    
    //Front right module settings [1] 
    public static DigitalDescriptor motorDescriptor_frontRightSwerve = new DigitalDescriptor(2, 3); // 2, 3
    public static DigitalDescriptor motorDescriptor_frontRightDrive = new DigitalDescriptor(2, 2); // 2, 2
    public static int frontRight_encoderChannel = 5; // 5
    public static int frontRight_pidMin = 0; //24
    public static int frontRight_pidMax = 959; //965

    //Back left module settings [2]
    public static DigitalDescriptor motorDescriptor_backLeftSwerve = new DigitalDescriptor(1, 1); // 1, 1
    public static DigitalDescriptor motorDescriptor_backLeftDrive = new DigitalDescriptor(1, 2); // 1, 2
    public static int backLeft_encoderChannel = 3; // 3
    public static int backLeft_pidMin = 0; //10
    public static int backLeft_pidMax = 960; //994

    //Back right module settings [3]
    public static DigitalDescriptor motorDescriptor_backRightSwerve = new DigitalDescriptor(2, 10); // 2, 10
    public static DigitalDescriptor motorDescriptor_backRightDrive = new DigitalDescriptor(2, 1); // 2, 1
    public static int backRight_encoderChannel = 2; // 2
    public static int backRight_pidMin = 0; //24
    public static int backRight_pidMax = 959; //965
    
    //steering constants
    public static double steering_deadBandWidth = 0.03;
    public static double steering_maxSteeringPoint = 100;
    public static double steering_minAdjustment = steering_maxSteeringPoint*0.9999999999;
    public static int steering_adjustmentExponent = 3;
    public static double steering_multiplier = 22.5;

    public static int lightChannel1 = 1; 
    public static int lightChannel2 = 2;

    public static int hotLiftErrorTolerance = 10;
    public static DigitalDescriptor motorDescriptor_hotLift = new DigitalDescriptor(2, 9); // 2, 9
    public static DigitalDescriptor motorDescriptor_hotRoller = new DigitalDescriptor(2, 7); // 1, 1
    public static int hotLiftEncoderChannel = 7; // 8
    
    //The HOT's lift position angles [-180, 180]
    public static int hotLiftPositionAngle_0 = 925; //the angle for tucked into structure
    public static int hotLiftPositionAngle_1 = 36; //the angle for manipulator to floor
    public static int hotLiftPositionAngle_2 = 658; //the angle for lowered down to knock bridge down
    //[cRIO]  -0.609375
    
    //Shooter constants
    public static DigitalDescriptor motorDescriptor_shooterShooterA = new DigitalDescriptor(1, 5); // 1, 5
    public static DigitalDescriptor motorDescriptor_shooterShooterB = new DigitalDescriptor(1, 6); // 1, 6
    public static DigitalDescriptor shooterShooterEncoderChannelA = new DigitalDescriptor(2, 1); //TODO get shooter encoder channels & data
    public static DigitalDescriptor shooterShooterEncoderChannelB = new DigitalDescriptor(2, 2);
    public static double shooterAcceptableError = 0.0036;
    public static DigitalDescriptor shooterFeedMotor = new DigitalDescriptor(1, 4); // 2, 5
    
    public static int shooterAngleEncoderChannel = 0;
    public static int shooterTurnsPerFullRange = 10;
    public static int shooterAngleTolerance = 5;
    
    public static int shooterAtBottomSensorChannel = 0;
    
    public static int cameraLights_channel = 5;
    public static int cameraControl_acceptableAlignmentError = 5;
    
    //ball manager constants
    public static DigitalDescriptor motorDescriptor_ballChannelMotor = new DigitalDescriptor(1, 4); // 1, 4
}
