package uebung_05_aufgabe2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class TestDownload_b {
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
		List<CompletableFuture<Void>> futures = new ArrayList<>();
		for (int i = 0; i < links.length; i++) {
			futures.add(downloader.asyncDownloadUrl_b(links[i]));
		}
		
		//TODO: How to combine with thenAccept()
		CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
		
		long endTime = System.currentTimeMillis();
		System.out.println(String.format("total time: %d ms", endTime - startTime));
	}
}
