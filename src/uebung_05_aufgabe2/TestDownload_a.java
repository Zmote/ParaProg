package uebung_05_aufgabe2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class TestDownload_a {
	private static final String[] links = new String[] {
			"http://www.google.com", 
			"http://www.bing.com",
			"http://www.yahoo.com", 
			"http://www.microsoft.com",
			"http://www.oracle.com" 
	};

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		long startTime = System.currentTimeMillis();
		WebDownload downloader = new WebDownload();
		List<CompletableFuture<String>> futures = new ArrayList<>();
		for (int i = 0; i < links.length; i++) {
			futures.add(downloader.asyncDownloadUrl_a(links[i]));
//			String link = links[i];
//			String result = downloader.downloadUrl(link);
//			System.out.println(String.format("%s downloaded (%d characters)", link, result.length()));
		}
		
		for(int i = 0; i < futures.size();i++){
			String result = (String)futures.get(i).get();
			System.out.println(String.format("%s downloaded (%d characters)", links[i], result.length()));
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println(String.format("total time: %d ms", endTime - startTime));
	}
}
