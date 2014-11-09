package dc.mobdev.ftpremote.tasks;

import java.io.IOException;
import java.net.InetAddress;

import org.apache.commons.net.ftp.FTPClient;

import android.content.Context;
import android.os.AsyncTask;

public abstract class BaseTask extends AsyncTask<Object, Void, Object> {

	protected FTPClient ftpClient;
	protected Context context;
	
	protected boolean openConnection(String strConnection) {
		String[] cnnInfos = strConnection.split("#");
		boolean result = false;

		this.ftpClient = new FTPClient();
		this.ftpClient.setConnectTimeout(1000 * 60 * 30);
		try {
			this.ftpClient.connect(InetAddress.getByName(cnnInfos[1]),
					Integer.parseInt(cnnInfos[0]));
			result = this.ftpClient.login(cnnInfos[2], cnnInfos[3]);
			this.ftpClient.enterLocalPassiveMode();
			System.out.println("#Basetaks_ResultConnection: " + result);
			return result;
		} catch (IOException ioE) {
			ioE.printStackTrace();
			result = false;
			return result;
		}	
	}

	protected void disconnect() {
		try{
			System.out.println("#BaseTask_Disconnect");
			this.ftpClient.logout();
			this.ftpClient.disconnect();
		}catch(IOException ioE){
			ioE.printStackTrace();
		}
	}

}
