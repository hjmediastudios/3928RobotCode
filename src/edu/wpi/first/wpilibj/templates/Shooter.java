/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Solenoid;

/**
 *
 * @author Nick Royer
 */
public class Shooter implements Runnable
{
    private Counter shootCounter;
    public RobotLight lights;
    private Solenoid buzzer;
    
    boolean shooterOn;
    private Jaguar motor1;
    private Jaguar motor2;
    private Jaguar feedMotor;
    
    private double targetRate;

    public Shooter() 
    {
        
        shootCounter = new Counter(Constants.shooterShooterEncoderChannelA.slot, Constants.shooterShooterEncoderChannelA.channel);
        shootCounter.start();
        
        lights = new RobotLight(Constants.lightChannel1, Constants.lightChannel2);
        motor1 = new Jaguar(Constants.motorDescriptor_shooterShooterA.slot, Constants.motorDescriptor_shooterShooterA.channel);
        motor2 = new Jaguar(Constants.motorDescriptor_shooterShooterB.slot, Constants.motorDescriptor_shooterShooterB.channel);
        feedMotor = new Jaguar(Constants.shooterFeedMotor.slot, Constants.shooterFeedMotor.channel);
        buzzer = new Solenoid(3);
        //buzzer.set(true);
        shooterOn = false;   
    }
    
    public void setTargetRate(double rate)
    {
        targetRate = rate;
    }
    
    public void setShooterOn()
    {
        if(!shooterOn)
        {
            shooterOn = true;
            new Thread(this).start();
        }
    }
    
     public void setFeeder(boolean on)
    {
        if(on)
        {
            feedMotor.set(1);
        }
        else
        {
            feedMotor.set(0);
        }
    }
    
    
    public void setShooterOff()
    {
        feedMotor.set(0);
        lights.lightsOff();
        buzzer.set(false);
        shooterOn = false;
    }
    
    public void run() 
    {
        //This is the starting speed
        double currentSpeed = -0.90;
        while(shooterOn)
        {
            try {

                
                double currentRate = shootCounter.getPeriod();
                double error = targetRate - currentRate;
                if(Double.isInfinite(currentRate))
                {
                    buzzer.set(true);
                }
                else
                {
                    buzzer.set(false);
                }
                
                //set the speed
                if(error > Constants.shooterAcceptableError)
                {
                    currentSpeed = currentSpeed + 0.005;
                }
                else if(error < -Constants.shooterAcceptableError)
                {
                    currentSpeed = currentSpeed - 0.005;
                }
                
                //once we hit a low error, we know we are up to speed.
                if(Math.abs(error) < Constants.shooterAcceptableError)
                {
                    lights.lightsOn();                            
                   
                    
                }
                else
                {
                    lights.lightsOff();
                }
                
                if(currentSpeed > 0)
                {
                    currentSpeed = 0;
                }
                if(currentSpeed < -1)
                {
                    currentSpeed = -1;
                }
                motor1.set(currentSpeed);
                motor2.set(currentSpeed);
//                System.out.println("TargetRate: " + targetRate + " CurrentRate: " + currentRate + " Error: " + error + " CurrentSpeed: " + currentSpeed);
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        shooterOn = false;
        motor1.set(0);
        motor2.set(0);
        feedMotor.set(0);
    }
    
    public double getTargetRate()
    {
        return targetRate;
    }

}
