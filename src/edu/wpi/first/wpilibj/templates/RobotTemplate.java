/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Dashboard;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends SimpleRobot
{

    DriverStationLCD dashboardLCD;
    SwerveDrive swerveDrive;
    Joystick throttle;
    Joystick controls;
    double swerveAngle;
    CameraController camController;
    boolean initialized = false;
    Solenoid shooterSensor;
    RobotLight lights;
    Shooter shooter;
    //todo take this out
    HammerOfThor hammer;
    NetworkTable cameraTable;
    /**
     * Autonomous mode state:
     * 1 = shoot, spin, & drive, while setting rollers to suck in
     * 2 = shoot only 
     */
    int autonomousMode = 1;
    
    int navInfo_xOffset = 0;
    double navInfo_distance = 0.5;

    public void robotInit()
    {
        dashboardLCD = DriverStationLCD.getInstance();
        System.out.println("Initializing everything...");
        throttle = new Joystick(1); //initialize
        controls = new Joystick(2);

        cameraTable = NetworkTable.getTable("cameraTable");
        hammer = new HammerOfThor(Constants.motorDescriptor_hotLift, Constants.motorDescriptor_hotRoller, Constants.hotLiftEncoderChannel);
        swerveDrive = new SwerveDrive();
        shooter = new Shooter();
//        camController = new CameraController();
        swerveDrive.enableSwerveModule(true);

        initialized = true;

        shooterSensor = new Solenoid(4);
        shooterSensor.set(true);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomous()
    {
        try
        {
            //Shoot
            if (autonomousMode == 1 || autonomousMode == 2)
            {
                swerveDrive.setCarSwerve(0.999999, 0);
                shooter.setShooterOn();
                shooter.setTargetRate(0.015);
                Thread.sleep(2000);
                shooter.setFeeder(true);
                Thread.sleep(5000);
                shooter.setShooterOff();;
                shooter.setFeeder(false);
                hammer.setRollerState(1);
                Thread.sleep(1000);
            }
            //turn and drive forward
            if (autonomousMode == 1)
            {
                swerveDrive.setCarSwerve(0.9999999, 0.4);
                Thread.sleep(1000);
                swerveDrive.setCarSwerve(0, 0);
                hammer.setState(0);
                Thread.sleep(70);
                swerveDrive.setCarSwerve(0, -0.3);
                Thread.sleep(2500);
                swerveDrive.setCarSwerve(0, 0);
            }

        } catch (InterruptedException ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * This function is called periodically during operator control
     */
    public void operatorControl()
    {
        while (true)
        {
            cameraTable.beginTransaction();
            navInfo_xOffset = cameraTable.getInt("xOffset", -1);
            navInfo_distance = cameraTable.getDouble("distance", -1.0);
            cameraTable.endTransaction();
            
            dashboardLCD.println(DriverStationLCD.Line.kUser3, 3, "xO: " + navInfo_xOffset);
            dashboardLCD.println(DriverStationLCD.Line.kUser4, 3, "d: " + navInfo_distance);
            dashboardLCD.updateLCD();
            if (controls.getRawButton(8))
            {
                shooter.setShooterOn();
                shooter.setTargetRate(((controls.getX() + 1) / 2) * (0.015586 * 4));

                //TODO remove this if needed
                dashboardLCD.println(DriverStationLCD.Line.kUser2, 2, "Shooter power: " + shooter.getTargetRate());
                dashboardLCD.updateLCD();

                if (controls.getRawButton(4))
                {
                    shooter.setFeeder(true);
                } else
                {
                    shooter.setFeeder(false);
                }
            } else
            {
                shooter.setShooterOff();
            }

            if (controls.getRawButton(10))
            {
//                camController.TurnCamControlOn();
            } else
            {
//                camController.TurnCamControlOff();
            }
            
            /*if (throttle.getRawButton(3))
            {
                swerveDrive.setBrakes();
            }
            else*/ if (throttle.getRawButton(1))
            {
                swerveDrive.setNormalSwerve(controls.getY() * 95, throttle.getY());
            } else
            {
                swerveDrive.setCarSwerve(controls.getY(), throttle.getY());
            }




            int lastState = 0;
            if (hammer.liftState != 2)
            {
                lastState = hammer.liftState;
            }

            //HOT stuff
            if (throttle.getRawButton(2))
            {
                if (hammer.liftState != 2)
                {
                    lastState = hammer.liftState;
                }

                hammer.setRollerState(1);
                hammer.setState(2);
            } else
            {
                if (controls.getRawButton(2))
                {
                    hammer.setState(0);
                    lastState = 0;
                    hammer.setRollerState(1);
                } else if (controls.getRawButton(7))
                {
                    hammer.setState(1);
                    lastState = 1;
                    hammer.setRollerState(2);
                } else
                {
                    hammer.setState(lastState);
                    hammer.setRollerState(0);
                }
            }




            try
            {
                Thread.sleep(10);
            } catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }

        }
        //

    }
}
