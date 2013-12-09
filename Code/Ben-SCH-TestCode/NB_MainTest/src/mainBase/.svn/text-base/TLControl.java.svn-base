/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mainBase;

import java.text.*;
import java.util.*;
import mainBase.sharedUtil.*;


/**
 *
 * @author Ben
 */
public class TLControl {

    private Vector vTL;
    
    public TLControl(){
        vTL = new Vector();
    }
    
    /**
     * Creats a new traffic light
     * 
     * @param startLight: After creating the light, immediately start it
     * @return The newly created traffic light ID
     */
    public int createTL(boolean startLight)
    {
        int i = vTL.size() + 1;

        TLObj newLight = new TLObj(i);

        Thread thrdTL = new Thread(newLight);

        //TLObj newLight = new TLObj(i);

        if (startLight)
            thrdTL.start();

        vTL.add(thrdTL);

        return newLight.getLightID();
    }
    
    public void setTLFreq(int pFreq){
        //TODO: Find it and change it
    }
    
    /**
     * Starts all traffic lights
     */
    public void procTLStart()
    {
        for (int i = 0;i<this.vTL.size();i++){
            this.procTLSingleStart(i);
        }
    }

    /**
     * Stops all traffic lights
     */
    public void procTLStop(){
        for (int i = 0;i<this.vTL.size();i++){
            this.procTLSingleStop(i);
        }
    }

    /**
     * Start a single traffic light
     * @param iTLID
     */
    public void procTLSingleStart(int iTLID){
        sharedUtil.writeDebugMsg("TLControl","procTLSingleStart", "Starting Light: " + iTLID);

        if (this.vTL.size()>0){
            if ((iTLID >= 0) && (iTLID <= vTL.size())){

                Thread thrd = (Thread) vTL.get(iTLID);
                //TLObj light = (TLObj) vTL.get(iTLID);
                //TLObj light = (TLObj) thrd.
                
                //light.run();
                //thrd.run();
                if (!thrd.isAlive())
                {
                    thrd.start();
                    sharedUtil.writeDebugMsg("TLControl","procTLSingleStart", " >> Light " + iTLID + " Started");
                }
                else
                {
                    sharedUtil.writeDebugMsg("TLControl","procTLSingleStart", " >> Light " + iTLID + " already started");
                }
            }
            else
                sharedUtil.writeDebugMsg("TLControl","procTLSingleStart", " >> Could Not Start Light " + iTLID + " - Out of Range");
        }
        else
            sharedUtil.writeDebugMsg("TLControl","procTLSingleStart", " >> Could Not Start Light " + iTLID + " - No Definitions");
    }

    /**
     * Stop a single traffic light
     * @param iTLID
     */
    public void procTLSingleStop(int iTLID){
        sharedUtil.writeDebugMsg("TLControl","procTLSingleStop", "Stopping Light: " + iTLID);

        if (this.vTL.size()>0){
            if ((iTLID >= 0) && (iTLID <= vTL.size())){
                TLObj light = (TLObj) vTL.get(iTLID);

                light.setIsActive(false);
            }
            else
                sharedUtil.writeDebugMsg("TLControl","procTLSingleStop", " >> Could Not Stop Light " + iTLID + " - Out of Range");
        }
        else
            sharedUtil.writeDebugMsg("TLControl","procTLSingleStop", " >> Could Not Stop Light " + iTLID + " - No Definitions");
    }

    /**
     * Kills the lights thread
     * @param iTLID
     */
    public void procTLSingleKill(int iTLID){
        sharedUtil.writeDebugMsg("TLControl","procTLSingleKill", "Killing Light: " + iTLID);

        if (this.vTL.size()>0){
            if ((iTLID >= 0) && (iTLID <= vTL.size())){
                TLObj light = (TLObj) vTL.get(iTLID);

                light.doKillLight();
            }
            else
                sharedUtil.writeDebugMsg("TLControl","procTLSingleKill", " >> Could Not Kill Light " + iTLID + " - Out of Range");
        }
        else
            sharedUtil.writeDebugMsg("TLControl","procTLSingleKill", " >> Could Not Kill Light " + iTLID + " - No Definitions");
    }


    
}
