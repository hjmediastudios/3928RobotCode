/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

/**
 *
 * @author Tony Milosch
 */
public class EthernetData 
{
    public float[] data;

    public EthernetData(String str)
    {
        int count = 0;
        //make a big array first, then copy over
        float tmpData[] = new float[10];
        while(true)
        {
            int justFound = str.indexOf(';');
            if(justFound == -1)
            {
                tmpData[count] = Float.parseFloat(str);
                count ++;
                break;
            }
            tmpData[count] = Float.parseFloat(str.substring(0, justFound));
            str = str.substring(justFound + 1);
            count ++;
        }
        data = new float[count];
        System.arraycopy(tmpData, 0, data, 0, count);
    }
}
