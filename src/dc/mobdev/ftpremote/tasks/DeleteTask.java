package dc.mobdev.ftpremote.tasks;

import java.io.IOException;

import dc.mobdev.ftpremote.configs.FTPConnection;
import android.content.Context;

public class DeleteTask extends BaseTask {

	public DeleteTask(Context context) {
		this.init(context);
	}

	private void init(Context context) {
		this.context = context;
	}

	public boolean delete(String name) {
		try {
			String[] keys = FTPConnection.findKeys();
			String connectionStr = FTPConnection.readFromSharedPreferenceFile(keys);	
			this.openConnection(connectionStr);
			return this.ftpClient.deleteFile(name);
		} catch (IOException ioE) {
			ioE.printStackTrace();
			return false;
		} finally {
			if (this.ftpClient != null) {
				this.disconnect();
			}
		}
	}

	@Override
	protected Object doInBackground(Object... params) {
		String name = (String) params[0];
		System.out.println("file name for delete : " + name);
		return this.delete(name);
	}

	@Override
	protected void onPostExecute(Object result) {
		//this.next();
	}

	private void next() {
//		String[] keys = FTPConnection.findKeys();
//		String connectionStr = FTPConnection.readFromSharedPreferenceFile(keys);
		//new ListTask(this.context).execute(connectionStr);
	}
}
