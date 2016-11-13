package www.treasury.gov;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Tabel {
	
	public ArrayList<String> columns = new ArrayList<>();
	public ArrayList<String> columnsSecondRow = new ArrayList<>();
	public static int numberOfColumns = 0;
	public ArrayList<String> data = new ArrayList<>();
	String columnsFormated = "";
	String columnsSecondRowFormated = "";
	public String pageSource = null;
	public String baseLink = "https://www.treasury.gov/resource-center/data-chart-center/interest-rates/Pages/";
	
	
	public Tabel(String urlPart) {

		try {
			pageSource = ScrapeMain.sendGet(baseLink + urlPart+"all");
		} catch (Exception e) {

			e.printStackTrace();
		}
		Document doc = Jsoup.parse(pageSource);
		
		columns = getColumns(doc,1);
		numberOfColumns = columns.size();
		
		if (urlPart.contains("billrates")) {
			columnsSecondRow = getColumns(doc,2);
			numberOfColumns = columnsSecondRow.size();
		}
		
		
		data = getData(doc);
		
		if (!columns.isEmpty()) {
			columnsFormated = format(columns);
			
		}
		if (!columnsSecondRow.isEmpty()) {
			columnsSecondRowFormated = format(columnsSecondRow);
			
		}
		 
	}




	public static ArrayList<String> getColumns(Document docc, int num){
		
		ArrayList<String> column = new ArrayList<>();
		Elements elements = docc.select("#t-content-main-content > table > tbody > tr > td > div > table > tbody > tr:nth-child("+num+")");
	
		for (Element el : elements) {

			Elements elements1 = el.getElementsByTag("th");
		
			for (Element colName : elements1) {
				
				column.add(colName.text());
			}
		}

		return column;
	
	}

	public static ArrayList<String> getData(Document docc){
		
		ArrayList<String> dataTemp = new ArrayList<>();
		
		Elements elements = docc.getElementsByClass("text_view_data");
		
		if (!elements.isEmpty()) {
			String pom = "";
			int count = 0;
			for (Element el : elements) {
				pom = pom + el.text() +"#";
				count++;
				if (count == numberOfColumns) {
					dataTemp.add(pom.substring(0, pom.length()-1));
					pom ="";
					count =0;				
				}	
			}

		}else{
			elements = docc.select("#t-content-main-content > table > tbody > tr > td > div > table > tbody > tr > td:nth-child(1) > table > tbody");
			boolean skip = false;
			for (Element element : elements) {
				if (!skip) {
					
				}
				element.getElementsByTag("tr");
				
			}
			
		}
		return dataTemp;
		
	
	}

	public static String format(ArrayList<String> formatMe){
		String pom = "";
		
		for (String string : formatMe) {
			pom = pom + string + "#";
		}
		return pom.substring(0, pom.length()-1);
		
	}

}

	

