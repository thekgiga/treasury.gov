package www.treasury.gov;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import Agency.MBS.Purchase.Program.MBS;

public class ScrapeMain {
	
	private static final String USER_AGENT = "Mozilla/5.0";

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
	
	public static void main(String[] args){
		MBS.getFiles();
		MBS.listAll();
		MBS.downloadFile(MBS.listToBeDownlaoded);
		
		
		
	   
//
//		
//		ScrapeTables scrapeTables = new ScrapeTables();
//		int counter = 0;
//		for (String urlPart : scrapeTables.linksToScrape) {
//			String name = scrapeTables.fileNames.get(counter);
//			
//			try {
//				FileWriter writ = new FileWriter(name+".csv");
//				Tabel t = new Tabel(urlPart);
//		
//				writ.write(t.columnsFormated+"\n");
//				writ.write(t.columnsSecondRowFormated+"\n");
//				for (String string : t.data) {
//					writ.write(string+"\n");
//				}
//				writ.close();
//				//System.out.println(t.columns);
//				//System.out.println(t.columnsSecondRow);
//				//for (String string : t.data) {
//				//	System.out.println(string);
//					
//					
//				//}
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			
//			counter++;
//			
//		}
//		
//		
//		
////		ArrayList<String> listOfLinksForTable = getLinksForTableScrape();
////
////		ArrayList<String> tableData = new ArrayList<>();
////		ArrayList<String> lineOfTableData = new ArrayList<>();
////		
////		
////		for (String linkForTable : listOfLinksForTable) {
////			
////			String url = "https://www.treasury.gov/resource-center/data-chart-center/interest-rates/Pages/"+linkForTable+"all";
////			
////			
////			Document doc = null;
////			try {
////				doc = Jsoup.parse(sendGet(url));
////			} catch (Exception e) {
////				
////				e.printStackTrace();
////			}
////			
////			Elements elements = doc.getElementsByClass("text_view_data");
////				
////			for (Element el : elements) {
////				
////				tableData.add(el.text());
////			}
////			
////			int count = 0;
////			String line = "";
////			
////			for (String s : tableData) {
////				
////				line = line + "#" + s;
////				count++;
////				if (count == 12) {
////					lineOfTableData.add(line);
////					count = 0;
////					line = "";						
////				}
////				
////			}
////			
////			for (String s : lineOfTableData) {
////				System.out.println(s);
////			}
////		}
////		
////		
////		
	}

}
