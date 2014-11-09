package dc.mobdev.ftpremote.configs;

import java.io.File;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;

public class FTPConnection {

	private static final String MYPREFERENCE = "MyPreference";
	private static Context mContext;

	public static void init(Context context) {
		mContext = context;
	}

	public static boolean checkFileConfig(String filename) {
		File file = new File("/data/data/" + mContext.getPackageName()
				+ "/shared_prefs");
		boolean fileExists = false;
		if (file.exists() && file.isDirectory()) {
			for (File child : file.listFiles()) {
				if(child.getName().equalsIgnoreCase(filename)){
					fileExists = true;
				}
			}
		} else {
			fileExists = false;
		}
		return fileExists;
	}

	public static void writeToSharedPreferenceFile(String key, String value) {
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(
				MYPREFERENCE, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static void writeToSharedPreferenceFile(String[] keys,
			String[] values) {
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(
				MYPREFERENCE, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		for (int i = 0; i < keys.length; i++) {
			editor.putString(keys[i], values[i]);
		}
		editor.commit();
	}

	public static String readFromSharedPreferenceFile(String key) {
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(
				MYPREFERENCE, Context.MODE_PRIVATE);
		return sharedPreferences.getString(key, "");
	}

	public static String readFromSharedPreferenceFile(String[] keys) {
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(
				MYPREFERENCE, Context.MODE_PRIVATE);
		String values = "";
		for (int i = 0; i < keys.length; i++) {
			if (i < (keys.length - 1)) {
				values += sharedPreferences.getString(keys[i], "") + "#";
			} else {
				values += sharedPreferences.getString(keys[i], "");
			}
		}
		return values;
	}

	public static String[] findKeys() {
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(
				MYPREFERENCE, Context.MODE_PRIVATE);
		try {
			String[] keys = new String[4];
			int count = 0;
			Map<String, ?> keysMap = sharedPreferences.getAll();
			for (Map.Entry<String, ?> entry : keysMap.entrySet()) {
				keys[count] = entry.getKey();
				count++;
			}
			return keys;
		} catch (NullPointerException npe) {
			npe.printStackTrace();
			return null;
		}
	}

}
