package com.nabilanam.downloader.util;

import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class HttpFileReader {

	public String readDataFromUrlResource(String infoUrl) throws IOException {
		HttpURLConnection con = (HttpURLConnection) new URL(infoUrl).openConnection();
		con.setInstanceFollowRedirects(true);
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent",
				"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
		int code = con.getResponseCode();
		StringBuilder sb = new StringBuilder();
		if (code == HttpURLConnection.HTTP_OK) {
			try (InputStream stream = new BufferedInputStream(con.getInputStream())) {
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
					String line;
					while ((line = reader.readLine()) != null) {
						sb.append(line);
					}
				}
			}
		}
		con.disconnect();
		return sb.toString();
	}
}
