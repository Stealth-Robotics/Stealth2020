/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.util;

/**
 * An class to track time
 */
public class StopWatch 
{
    long mStartTime = 0; // !< Time on last iteration
    long mLastTimeDT = 0; // !< Change in time since called
	long mWaitTime = 0; // !< The time to wait
    
    /**
     * Creates a stopwatch with the specified wait time
     * 
     * @param waitTime The specified time in milliseconds
     */
	public StopWatch(long waitTime)
	{
        mWaitTime = waitTime;
        mStartTime = now();
        mLastTimeDT = now();
    }
    
    /**
     * Creates a stopwatch to track time
     */
    public StopWatch()
	{
        mWaitTime = 0;
        mStartTime = now();
        mLastTimeDT = now();
	}
    
    /**
     * Sets time to wait
     * 
     * @param waitTime The time to wait in milliseconds
     */
	public void setTime(int waitTime)
	{
		mWaitTime = waitTime;
	}
    
    /**
     * Checks if the stopwatch is expired
     * 
     * @return If the stopwatch is past its wait time or not
     */
	public boolean isExpired()
	{
        long now = now();

        long num = (now - mStartTime);

        boolean output = num > mWaitTime;
        
        return output;
	}
    
    /**
     * Rests the start time
     */
	public void reset()
	{
        mStartTime = now();
	}
    
    /**
     * Finds the time left until the wait time is reached
     * 
     * @return The time remaining in milliseconds
     */
	public long timeLeft()
	{
		return mWaitTime - (now() - mStartTime);
    }
    
    /**
     * Returns the current time
     * 
     * @return The time in milliseconds
     */
    public static long now()
    {
        return System.nanoTime() / 1000000;
    }

    /**
     * Returns the time passed since the last time this function was called or the stopwatch was initilized
     *
     * @return The change in time in miliseconds 
     */
    public long deltaTime()
    {
        //get the dt
        long output = now() - mLastTimeDT;
        //reset last time
        mLastTimeDT = now();
        //return it
        return output;
    }
}
