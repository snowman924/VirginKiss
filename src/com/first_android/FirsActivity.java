package com.first_android;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.virginkiss.database.WorkStore;
import com.virginkiss.time.ITime;
import com.virginkiss.time.TimeImpl;

public class FirsActivity extends Activity  {
	private final String LOG_TAG = "FirstActivity";
	
    /** Called when the activity is first created. */
	private Calendar cal;
	private TextView digitalClock;
	private TimeImpl currentTime;
	
	private TimeImpl workStartTime;
	
	private WorkStore workStore;
	private TimeTickTask timerTask;
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); 
        
        workStore = new WorkStore(this);
        
        // Get Test Button
        View testButton = this.findViewById(R.id.TestButton);
        
        digitalClock = (TextView)findViewById(R.id.DigitalClock);        
        
        final Handler handler = new Handler() {
        	public void handleMessage(Message msg) {        		
        		Bundle b = msg.getData();
        		Log.v("ClockHandler",b.toString());        		
        		digitalClock.setText(b.getString("CurrentClock"));      		
        	}
        };
        
        // Set Timer event        
        Timer timeTick = new Timer();
        timerTask = new TimeTickTask(handler);
        
        // call ClockTick for every 1 sec
        timeTick.schedule(timerTask, 0, 1000);
        
        testButton.setOnClickListener(new ViewClickLisenter());
        
        workStartTime = (TimeImpl)workStore.loadData();
        showWorkStartTIme();
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	saveWorkStartTime();
    	workStore.close();
    	timerTask.cancel();
    }
    
    private void showWorkStartTIme() {
    	if(workStartTime == null){
    		return ;
    	}
    	
		TextView startTime = (TextView)findViewById(R.id.startTime);
		
		StringBuilder builder = new StringBuilder();
		builder.append(workStartTime.getHour());
		builder.append(":");
		builder.append(workStartTime.getMinute());
		
		startTime.setText(builder.toString());
	}
    
    private void setWorkStartTime(ITime time){
    	workStartTime = new TimeImpl(time.getHour(), time.getMinute());
    	showWorkStartTIme();
    }
    
    private void saveWorkStartTime() {
		workStore.saveData(workStartTime);
	}

	class ViewClickLisenter implements View.OnClickListener{
		public void onClick(View v) {
			setWorkStartTime(currentTime);
		}
	}
	
	class TimeTickTask extends TimerTask {
		private Handler handler;
		
		public TimeTickTask(Handler handler){
			this.handler = handler;
		}
		
		@Override
		public void run() {
			Log.v(LOG_TAG, "TimeTickTask.run()<<<");
			
			Message msg = handler.obtainMessage();				
			Bundle b = new Bundle();
			 
			// Update ITime instance
			 cal = Calendar.getInstance();
			currentTime = new TimeImpl(cal.get(Calendar.HOUR_OF_DAY) , cal.get(Calendar.MINUTE));

			// Now get Current Time
			b.putString("CurrentClock", createCurrentClockString());
			
			// Send msg to change Clock UI.
			msg.setData(b);
			handler.sendMessage(msg);
		}
		
		public String createCurrentClockString() {
			String clockString=null;			      
			clockString = String.format("%d : %d : %d", cal.get(Calendar.HOUR_OF_DAY) ,
									cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
			return clockString;
		}			
	}
}
