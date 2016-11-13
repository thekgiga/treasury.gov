package www.treasury.gov;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ScrapeTables {
	
	public ArrayList<String> linksToScrape = new ArrayList<>();
	public ArrayList<String> fileNames = new ArrayList<>();
		
	String pageSource = null;
	String baseLink = "https://www.treasury.gov/resource-center/data-chart-center/interest-rates/Pages/";
	
	public ScrapeTables(){
			
		try {
			
		pageSource = ScrapeMain.sendGet("https://www.treasury.gov/resource-center/data-chart-center/interest-rates/Pages/TextView.aspx?data=yieldALL");

		} catch (Exception e) {
			
			e.printStackTrace();
			System.out.println("Couldn't fetch url : "+ "https://www.treasury.gov/resource-center/data-chart-center/interest-rates/Pages/TextView.aspx?data=yieldALL");
		}
		
		Document doc = Jsoup.parse(pageSource);
		
		linksToScrape = getLinks(doc);
		fileNames = getNames(doc);
		
		
	}
	
	public static ArrayList<String> getLinks(Document docc){

		ArrayList<String> list = new ArrayList<>();
		Elements elements = docc.select("#interestRatePages");
		
		for (Element el : elements) {

			Elements links = el.getElementsByTag("option");
			
			for (Element element : links) {
				
				list.add(element.attr("value"));
			}
		}
		list.remove(list.size()-1);//remowing charts
		list.remove(list.size()-1);//remowing charts
		return list;
		
	}
	
	public static ArrayList<String> getNames(Document docc){

		ArrayList<String> list = new ArrayList<>();
		Elements elements = docc.select("#interestRatePages");
		
		for (Element el : elements) {

			Elements links = el.getElementsByTag("option");
			
			for (Element element : links) {
				
				list.add(element.text());
			}
		}
		list.remove(list.size()-1);//remowing charts
		list.remove(list.size()-1);//remowing charts
		return list;
		
	}
	
		
	
}
