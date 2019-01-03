package io.duke;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HttpURLConnectionのサンプル
 */
public class KinmuCsvDownload {

	private String []memberList;
	private String kinmuUrl = "http://ndcokinmu.appspot.com/download/";

	public KinmuCsvDownload (String adminId, String adminCode, String time_yyyyMM, String[] memberList) {
		this.memberList = memberList;
		this.kinmuUrl += adminId + "/" + adminCode + "/" + time_yyyyMM + "/";
	}

	public Map<String, List<String>> getMemberCsvMap(){

		Map<String, List<String>> resultMap = new HashMap<>();
		for(String index : memberList) {
			resultMap.put(index, getKinmu(index));
		}

		return resultMap;
	}

	public List<String> getKinmu(String memberId) {

		String strUrl = this.kinmuUrl + memberId + "?" + (new Date().getTime()/1000);
		HttpURLConnection  urlConn = null;
		InputStream in = null;
		BufferedReader reader = null;
		List<String> resultCsv = new ArrayList<>();
		try {
			//接続するURLを指定する
			URL url = new URL(strUrl);

			//コネクションを取得する
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setRequestMethod("GET");
			urlConn.connect();

			int status = urlConn.getResponseCode();

		    if (status == HttpURLConnection.HTTP_OK) {
				in = urlConn.getInputStream();

		    	reader = new BufferedReader(new InputStreamReader(in));

				StringBuilder output = new StringBuilder();
				String line;

				while ((line = reader.readLine()) != null) {
					resultCsv.add(line);
				}
		      }
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (urlConn != null) {
					urlConn.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultCsv;
	}
}
