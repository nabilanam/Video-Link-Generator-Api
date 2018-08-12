package com.nabilanam.downloader.util;

import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class HttpResourceReader {

	public String read(String url) throws IOException {
		HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
		con.setInstanceFollowRedirects(true);
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent",
				"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
		int code = con.getResponseCode();
		StringBuilder sb = readIntoStringBuilder(con, code);
		con.disconnect();
		return sb.toString();
	}

	public String read(String url,String[] headers,String[] headerValues) throws IOException {
		HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
		con.setInstanceFollowRedirects(true);
		for (int i=0; i<headers.length; i++){
			con.setRequestProperty(headers[i],headerValues[i]);
		}
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent",
				"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
		int code = con.getResponseCode();
		StringBuilder sb = readIntoStringBuilder(con, code);
		con.disconnect();
		return sb.toString();
	}

	private StringBuilder readIntoStringBuilder(HttpURLConnection con, int code) throws IOException {
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
		return sb;
	}
}
