package uebung_05_aufgabe2;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class WebDownload {
	
	public String downloadUrl(String link) throws IOException {
		URL url = new URL(link);
		StringBuffer stringBuffer = new StringBuffer();
		try (Reader reader = new InputStreamReader(url.openStream())) {
			int i;
			while ((i = reader.read()) >= 0) {
				stringBuffer.append((char) i);
			}
		}
		return stringBuffer.toString();
	}
	
	CompletableFuture<String> asyncDownloadUrl_a(String link){
		return CompletableFuture.supplyAsync(()->{
			URL url;
			StringBuffer stringBuffer = new StringBuffer();
			try{
				url = new URL(link);
				Reader reader = new InputStreamReader(url.openStream());
				int i;
				while ((i = reader.read()) >= 0) {
					stringBuffer.append((char) i);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			return stringBuffer.toString();
		});
	}

	public  CompletableFuture<Void> asyncDownloadUrl_b(String link) {
		return CompletableFuture.runAsync(()->{
			URL url;
			StringBuffer stringBuffer = new StringBuffer();
			try{
				url = new URL(link);
				Reader reader = new InputStreamReader(url.openStream());
				int i;
				while ((i = reader.read()) >= 0) {
					stringBuffer.append((char) i);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			System.out.println(String.format("%s downloaded (%d characters)", link, stringBuffer.toString().length()));
		});
	}
}
