package dc.mobdev.ftpremote.tasks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import dc.mobdev.ftpremote.configs.FTPConnection;
import android.content.Context;
import android.os.Environment;

public class UploadTask extends BaseTask {

	public UploadTask(Context context) {
		this.init(context);	
	}

	private void init(Context context) {
		this.context = context;
	}

	private boolean upload(File fileUpload) {
		boolean result = false;
		FileInputStream fis = null;
		try {
			String[] keys = FTPConnection.findKeys();
			String connectionStr = FTPConnection.readFromSharedPreferenceFile(keys);	
			this.openConnection(connectionStr);
			fis = new FileInputStream(fileUpload);
			result = this.ftpClient.storeFile(fileUpload.getName(), fis);
			return result;
		} catch (FileNotFoundException fnfE) {
			fnfE.printStackTrace();
			result = false;
			return result;
		} catch (IOException ioE) {
			ioE.printStackTrace();
			result = false;
			return result;
		}finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (this.ftpClient != null) {
				this.disconnect();
			}
		}
	}

	private boolean upload(String filename, String currentDir){
		boolean result = false;
		File file = null;
		FileInputStream fis = null;
		try{
			String[] keys = FTPConnection.findKeys();
			String connectionStr = FTPConnection.readFromSharedPreferenceFile(keys);	
			this.openConnection(connectionStr);
			String pathFileUpload = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + filename;
			file = new File(pathFileUpload);
			fis = new FileInputStream(file);
			result = this.ftpClient.storeFile(currentDir + "/" + file.getName(), fis);
			return result;
		}catch(FileNotFoundException fnfE){
			fnfE.printStackTrace();
			result = false;
			return result;
		}catch(IOException ioE){
			ioE.printStackTrace();
			result = false;
			return result;
		}finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (this.ftpClient != null) {
				this.disconnect();
			}
		}
	}
	
	@Override
	protected Object doInBackground(Object... params) {

		return this.upload((String)params[0], (String)params[1]);
	}

	@Override
	protected void onPostExecute(Object result) {
		super.onPostExecute(result);
		if((Boolean) result){
			System.out.println("RESULT: " + result);
		}
	}

}
