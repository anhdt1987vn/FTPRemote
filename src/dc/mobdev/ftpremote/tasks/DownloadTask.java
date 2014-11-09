package dc.mobdev.ftpremote.tasks;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import dc.mobdev.ftpremote.configs.FTPConnection;
import android.content.Context;
import android.os.Environment;

public class DownloadTask extends BaseTask{

	public DownloadTask(Context context){
		this.init(context);
	}
	
	private void init(Context context){
		this.context = context;
	}
	
	@Override
	protected Object doInBackground(Object... params) {
		String pathToFileDownload = (String)params[0];
		String fileNameToDownload = (String)params[1];
		String dirNameToSave = (String)params[2];	
		return download(pathToFileDownload, fileNameToDownload, dirNameToSave);
	}

	@Override
	protected void onPostExecute(Object result) {
		super.onPostExecute(result);
		if((Boolean) result == true){
			System.out.println("Download Sucessful!");
		}
	}
	
	
	private boolean download(String pathToFileDownload, String fileNameToDownload, String dirNameToSave){
		boolean result = false;
		String pathToSave = null;
		File dir = null;
		File fileDownload = null;
		OutputStream os = null;
		FileOutputStream fos = null;
		try{
			String[] keys = FTPConnection.findKeys();
			String connectionStr = FTPConnection.readFromSharedPreferenceFile(keys);	
			this.openConnection(connectionStr);
			
			pathToSave = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + dirNameToSave;
			dir = new File(pathToSave);
			dir.mkdirs();
			
			fileDownload = new File(dir, fileNameToDownload);
			fos = new FileOutputStream(fileDownload);
			os = new BufferedOutputStream(fos);	
			result = this.ftpClient.retrieveFile(pathToFileDownload + "/" + fileNameToDownload, os);
			return result;
		}catch(FileNotFoundException fnfE){
			fnfE.printStackTrace();
			result = false;
			return result;
		}catch(IOException ioE){
			ioE.printStackTrace();
			result = false;
			return result;
		}finally{
			try {
				os.close();
				fos.close();
				this.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}
}
