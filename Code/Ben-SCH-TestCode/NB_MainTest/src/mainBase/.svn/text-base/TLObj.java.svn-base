/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mainBase;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ben
 */
public class TLObj implements Runnable {
    private int mLightID=-1;
    private boolean mIsGreen=false;
    private boolean mIsActive=true;
    private boolean mDoKill=false;
    private int mChangeFreq=sharedUtil.LIGHT_SEQUENCE_FREQ;
    private Date mLastChange;

    public TLObj(int iLightID){
        this.mLightID = iLightID;
        this.mIsGreen=false;
        this.mIsActive=true;
    }

    public void run() {
        mLastChange = new Date();
        mIsGreen=true;

        while (!mDoKill){
            if (this.mIsActive){ //is this light currently set to change

                if (doChange()) {
                    mIsGreen = !mIsGreen;
                    this.mLastChange=new Date();

                    sharedUtil.writeDebugMsg("TLObj[ID=" + this.mLightID + "]", "run", "Light Changed - Is Green?" + this.mIsGreen);
                    Calendar c = Calendar.getInstance();
                    c.setTime(this.mLastChange);
                    c.add(Calendar.SECOND, this.mChangeFreq);

                    sharedUtil.writeDebugMsg("TLObj[ID=" + this.mLightID + "]", "run", "....Next Change Due At:" + sharedUtil.formatDate(c));
                }
                try {
                    Thread.sleep(800);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TLObj.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }


    public boolean getIsGreen(){
        return this.mIsGreen;
    }

    public boolean getIsActive(){
        return this.mIsActive;
    }

    public void setIsActive(boolean b){
        this.mIsActive=b;
    }

    public void doKillLight(){
        this.mDoKill=true;
    }

    public int getLightID(){
        return this.mLightID;
    }

    public void setFrequency(int f){
        this.mChangeFreq=f;
    }

    private boolean doChange(){
        long timeElapse = (new Date().getTime());
        timeElapse = timeElapse - this.mLastChange.getTime();
        timeElapse = timeElapse / 1000; //convert from milliseconds to seconds

        if (timeElapse > this.mChangeFreq){
            return true;
        }
        else
            return false;
    }

}
