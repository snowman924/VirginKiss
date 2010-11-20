/**
 * 
 */
package com.virginkiss.time;

import java.util.Calendar;

/**
 * @author sangtai
 *
 */
public class WorkTimeImpl implements IWorkTime {

	/* (non-Javadoc)
	 * @see com.virginkiss.IWorkTime#getCurretTime()
	 */
	private static final int LUNCHTIME = 12;
	private static final int  WORKTIME_NORMAL= 9;
	private static final int  WORKTIME_LATE = 8;
	private static final int  OVERTIME_A = 2;
	private static final int  OVERTIME_B = 3;
	
	
	
	private ITime startTime;
	private ITime finishTime;
	
	private Calendar cal;
	
	public WorkTimeImpl() {
		cal = Calendar.getInstance();
	}
	
	public ITime getCurretTime() {
		// TODO Auto-generated method stub		
		ITime time = new TimeImpl(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
		return time;
	}
	
	public ITime setStartTime(ITime time) {		
		this.startTime = time;
//		ITime start = getCurretTime();		
//		startTime = start;
		return this.startTime;
	}
	
	/* (non-Javadoc)
	 * @see com.virginkiss.IWorkTime#getStartTime()
	 */
	
	public ITime getStartTime() {
		// TODO Auto-generated method stub		
		return startTime;
	}

	/* (non-Javadoc)
	 * @see com.virginkiss.IWorkTime#getFinishTime()
	 */
	public ITime getFinishTime() {
		
		// TODO Auto-generated method stub
		
		if(this.startTime.getHour() <= LUNCHTIME){ 
			finishTime =  new TimeImpl(this.startTime.getHour() + WORKTIME_NORMAL, this.startTime.getMinute());
			
		}
		else
		{
			finishTime =  new TimeImpl(this.startTime.getHour() + WORKTIME_LATE, this.startTime.getMinute());
		
		}	
		return finishTime;
	}

	/* (non-Javadoc)
	 * @see com.virginkiss.IWorkTime#getOverTimeA()
	 */
	public ITime getOverTimeA() {
		// TODO Auto-generated method stub
		ITime overTime;
		overTime = new TimeImpl(this.finishTime.getHour() + OVERTIME_A, this.finishTime.getMinute());
		return overTime;
	}

	/* (non-Javadoc)
	 * @see com.virginkiss.IWorkTime#getOverTimeB()
	 */
	public ITime getOverTimeB() {
		// TODO Auto-generated method stub
		ITime overTime;
		overTime = new TimeImpl(this.finishTime.getHour() + OVERTIME_B, this.finishTime.getMinute());
		return overTime;		
	}


	/* (non-Javadoc)
	 * @see com.virginkiss.ITime#getHour()
	 */
	public int getHour() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.virginkiss.ITime#getMinute()
	 */
	public int getMinute() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void printMessage() {
		// TODO Auto-generated method stub
			System.out.println("printMessage Test");		
	}
	
}
