package dc.mobdev.ftpremote.services;

import dc.mobdev.ftpremote.views.ListFileActivity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ListService extends Service{

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String task  = intent.getExtras().getString("task");
		if(task.toLowerCase().endsWith("listtask")) {
			String[] names = intent.getExtras().getStringArray("names");
			int[] types = intent.getExtras().getIntArray("types");
			String currentDir = intent.getExtras().getString("currentDir");
			
			Intent intent4la = new Intent(this, ListFileActivity.class);
			intent4la.putExtra("names", names);
			intent4la.putExtra("types", types);
			intent4la.putExtra("currentDir", currentDir);
			intent4la.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);		
			System.out.println("Preparing to start ListFileActivity");
			try {
				this.startActivity(intent4la);
			}catch(Exception ex) {
				System.out.println("ListFileActivity started failed!");
				ex.printStackTrace();
			}
		}		
		return START_STICKY;
	}
		
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
