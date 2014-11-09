package dc.mobdev.ftpremote.tasks;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.net.ftp.FTPFile;

import dc.mobdev.ftpremote.services.ListService;
import dc.mobdev.ftpremote.configs.FTPConnection;
import android.content.Context;
import android.content.Intent;

public class ListTask extends BaseTask {

	
	public ListTask(Context context) {
		this.init(context);
	}

	private void init(Context context) {
		this.context = context;
	}

	@Override
	protected Object doInBackground(Object... params) {
		String path = (String) params[0];
		return getList(path);
	}

	@Override
	protected void onPostExecute(Object result) {	
		this.next((ArrayList)result);
	}

	private ArrayList getList(String path) {
		System.out.println("#ListTask Path: " + path);
		boolean result = false;
		ArrayList<FTPFile> listFtpFile = new ArrayList<FTPFile>();
		FTPFile[] listFiles;
		try {
			String[] keys = FTPConnection.findKeys();
			String connectionStr = FTPConnection.readFromSharedPreferenceFile(keys);	
			this.openConnection(connectionStr);
			this.ftpClient.cwd(path);
				listFiles = this.ftpClient.listFiles();
				for (FTPFile ftpFile : listFiles) {
					listFtpFile.add(ftpFile);
				}
				//String temp = path.substring(1, path.length());
				ArrayList newArray = new ArrayList();
				newArray.add(path);
				newArray.add(listFtpFile);
				
				return newArray;
		} catch (IOException ioE) {
			ioE.printStackTrace();
			return null;
		} finally {
			if (this.ftpClient.isConnected()) {
				this.disconnect();
			}
		}
	}

	private void next(ArrayList list) {
		
		ArrayList<FTPFile> listFtpFile = ((ArrayList<FTPFile>)list.get(1));
		
		String[] names = new String[listFtpFile.size()];
		int[] types = new int[listFtpFile.size()];
		for (int i = 0; i < listFtpFile.size(); i++) {
			names[i] = listFtpFile.get(i).getName();
			types[i] = listFtpFile.get(i).getType();
		}

		
		Intent intentService = new Intent(this.context, ListService.class);
		try {
		intentService.putExtra("names", names);
		intentService.putExtra("types", types);
		intentService.putExtra("task", ListTask.class.toString());
		intentService.putExtra("currentDir", (String)list.get(0));
		
			this.context.startService(intentService);
		} catch (SecurityException ex) {
			ex.printStackTrace();
			System.out.println("ERROR!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("ListService is started sucessful!");
	}

}
