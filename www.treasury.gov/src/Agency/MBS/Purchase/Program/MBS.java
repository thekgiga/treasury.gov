package Agency.MBS.Purchase.Program;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MBS{
	
	public static final String USER_AGENT = "Mozilla/5.0";	
	public static String baseUrl = "https://www.treasury.gov";
	public static String url = "https://www.treasury.gov/resource-center/data-chart-center/Pages/mbs-purchase-program.aspx";
	public static ArrayList<String> listToBeDownlaoded = new ArrayList<>();
	public static void getFiles(){
		
		String pageSource;
		
			try {
				pageSource = sendGet(url);
				Document doc =  Jsoup.parse(pageSource);
				//System.out.println(doc);
				Elements elements = doc.getElementsByTag("a");

				
				for (Element element : elements) {
					String attHref = element.attr("href");
					//System.out.println(attHref);
					if (attHref.endsWith("xls")||
							attHref.endsWith("xlsx")||
								attHref.endsWith("csv")	) {
						listToBeDownlaoded.add(baseUrl+attHref);
						//String extension = attHref.substring(attHref.lastIndexOf('.'),attHref.length());
						//downloadFile(baseUrl+attHref, extension);
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	}
	
	public static void listAll(){
		for (String string : listToBeDownlaoded) {
			System.out.println(string);
		}
	}
	public static String sendGet(String urlUrl) throws Exception {

		String url = urlUrl;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		return response.toString();

	}
	
	public static void downloadFile(ArrayList<String> downloadLink) {
		int i =0;
		for (String string : downloadLink) {
			try {
				String extension1 = string.substring(string.lastIndexOf('.'),string.length());
				
				URL website = new URL(string);
				ReadableByteChannel rbc = Channels.newChannel(website.openStream());
				
				FileOutputStream fos = new FileOutputStream("FileName "+i+extension1);
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				i++;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				System.err.println("file not exist on :" + downloadLink);
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			

	}



}
