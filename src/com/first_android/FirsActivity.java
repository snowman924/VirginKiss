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
    /** Called when the activity is first created. */
	private Calendar cal;
	private TextView digitalClock;
	private TimeImpl currentTime;
	
	private TimeImpl workStartTime;
	
	private WorkStore workStore;
		
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
        TimerTask ClockTick = new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub		
				Log.v("run", "test");
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
		};
        // call ClockTick for every 1 sec
        timeTick.schedule(ClockTick, 0, 1000);

        testButton.setOnClickListener(new ViewClickLisenter());
        
        workStartTime = (TimeImpl)workStore.loadData();
        showWorkStartTIme();
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	saveWorkStartTime();
    }
    
    private void showWorkStartTIme() {
    	if(currentTime == null){
    		return ;
    	}
    	
		TextView startTime = (TextView)findViewById(R.id.startTime);
		
		StringBuilder builder = new StringBuilder();
		builder.append(currentTime.getHour());
		builder.append(":");
		builder.append(currentTime.getMinute());
		
		workStartTime = new TimeImpl(currentTime.getHour(), currentTime.getMinute());
		
		startTime.setText(builder.toString());
	}
    
    private void saveWorkStartTime() {
		workStore.saveData(workStartTime);
	}

	class ViewClickLisenter implements View.OnClickListener{
		public void onClick(View v) {
			showWorkStartTIme();
		}
	}
}
