/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Relay;

/**
 *
 * @author frc3928
 */
public class HammerOfThor implements Runnable
{

    Jaguar liftMotor;
    AnalogChannel liftEncoder;
    Jaguar rollerMotor;
    int liftErrorTolerance;
    
    /**
     * Roller state determines whether the motors are idle, rolling in balls, or repelling them.
     * 0 = idle
     * 1 = suck in balls
     * 2 = repel balls
     */
    int rollerState;
    int liftState;

    public HammerOfThor(DigitalDescriptor liftMotorDescriptor, DigitalDescriptor rollerMotorDescriptor, int liftEncoderChannel)
    {
        liftMotor = new Jaguar(liftMotorDescriptor.slot, liftMotorDescriptor.channel);
        liftEncoder = new AnalogChannel(liftEncoderChannel);
        rollerMotor = new Jaguar(rollerMotorDescriptor.slot, rollerMotorDescriptor.channel); //create a new relay on the default digital input
        rollerState = 0; //set new module to idle
        liftState = 0;
        new Thread(this).start();
    }

    private void setAngle(int setAngle)
    {
        int getAngle = liftEncoder.getValue();
        if (getAngle < (setAngle - Constants.hotLiftErrorTolerance))
        {
            liftMotor.set(-0.8);
            //System.out.println("Speeding up.");
        } else if (getAngle > (setAngle + Constants.hotLiftErrorTolerance))
        {
            liftMotor.set(0.8);
            //System.out.println("Slowing down.");
        } else
        {
            liftMotor.set(0);
            //System.out.println("Stopping.");
        }
    }

    public void setState(int state)
    {
        if (state >= 0 && state <= 2)
        {
            liftState = state;
        }
    }

    /**
     * Sets the lift motor to one of three preset positions.
     * @param position The position of the lift. 1 = deployed to floor; 2 = down to bridge; 0 = tucked into structure.
     */
    private void setPosition()
    {
        if (liftState == 0)
        {
            setAngle(Constants.hotLiftPositionAngle_0);
        } else if (liftState == 1)
        {
            setAngle(Constants.hotLiftPositionAngle_1);
        } else if (liftState == 2)
        {
            setAngle(Constants.hotLiftPositionAngle_2);
        }
    }

    public void setRollerState(int mode)
    {
        if (mode < 0 || mode > 2)
        {
            System.err.println("ERROR: roller mode " + mode + "is grossly incorrect.");
        } else
        {
            rollerState = mode;
        }
    }

    /**
     * Sets the ball-sucking roller's operation mode.
     * @param mode The roller operation mode. 0 = off; 1 = suck in balls; 2 = reject balls. 
     */
    private void setRollers(int mode)
    {
        if (mode < 0 || mode > 2)
        {
            System.err.println("Error: HOT roller setting " + mode + " is grossly incorrect.");
        }

        if (mode == 0)
        {
            rollerMotor.set(0);
        } else if (mode == 1)
        {
            rollerMotor.set(-1); //TODO see which way roller motor spins to suck in.
        } else if (mode == 2)
        {
            rollerMotor.set(1);
        }
    }

    public void run()
    {
        while (true)
        {
            setPosition();
            setRollers(rollerState);

            try
            {
                Thread.sleep(50);
            } catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
