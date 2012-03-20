/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

/**
 *
 * @author frc3928
 */
public class DigitalDescriptor {

    public int channel;
    public int slot;

    public DigitalDescriptor(int slt, int chnl) {
        slot = slt;
        channel = chnl;
    }
}
