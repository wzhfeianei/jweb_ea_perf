package com.ea.trade.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class HttpClient {

	public static String POSTJSONData(String urlAddress, String JSONData) {
		String result = "";
		DataOutputStream out = null;
		BufferedReader in = null;
		try {
			URL url = new URL(urlAddress);
			try {
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();

				connection.setDoOutput(true);
				connection.setDoInput(true);
				connection.setRequestMethod("POST");
				connection.setUseCaches(false);
				connection.setInstanceFollowRedirects(true);
				connection.setRequestProperty("Content-Type",
						"application/json;charset=utf-8");
				try {
					out = new DataOutputStream(connection.getOutputStream());
					out.write(JSONData.getBytes("UTF-8"));
					out.flush();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (out != null) {
						out.close();
					}

				}

				connection.connect();

				Map<String, List<String>> map = connection.getHeaderFields();

				for (@SuppressWarnings("unused") String key : map.keySet()) {
					//System.out.println(key + "--->" + map.get(key));
				}

				try {
					in = new BufferedReader(new InputStreamReader(
							connection.getInputStream(), "utf-8"));
					String line;
					while ((line = in.readLine()) != null) {
						result = result + line;
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (in != null) {
						in.close();
					}
				}

				connection.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
