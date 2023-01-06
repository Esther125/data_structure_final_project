package com.example.jsouptest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class JsoupPrintLinks {

	public static final String USER_AGENT = "Chrome/107.0.5304.107";
	public ArrayList<String> getUrl(String searchKeyword) throws IOException{
		System.setProperty("http.agent", "Chrome/107.0.5304.107");

		ArrayList<String>urls = new ArrayList<String>();

		for(int i=0; i<searchKeyword.length(); ++i){
			if(searchKeyword.charAt(i)==' '){
				searchKeyword = searchKeyword.substring(0, i) + "+" + searchKeyword.substring(i+1, searchKeyword.length());
			}
		}
		System.out.println(searchKeyword);
//		Document doc = Jsoup.connect("https://www.google.com/search?q="+searchKeyword+"+udn&oe=utf8&num=20").timeout(0).get();


		Document doc = Jsoup.connect("https://www.google.com/search?q="+searchKeyword+"+udn&hl=en&num=20").timeout(0).get();
//		Document doc = Jsoup.connect("https://www.google.com/search?q="+searchKeyword.trim()+"&oe=utf8&num=20").timeout(0).ignoreHttpErrors(true).get();
//		Document doc = Jsoup.connect("https://www.google.com/search?q="+searchKeyword+"&oq="+searchKeyword+"&sourceid=chrome&ie=utf8&num=20").timeout(0).get();


		Element element = doc.getElementById("search");
		Elements elements = element.getElementsByClass("yuRUbf");

		for(Element el:elements) {
			String url = el.select("a").attr("href");
			//System.out.println(url);
			urls.add(url);
		}
		return urls;
	}

	public String getTitle(String url) throws IOException{

		//System.setProperty("http.agent", "Chrome/107.0.5304.107");



		ArrayList<String>titles = new ArrayList<String>();
//
		Document doc = Jsoup.connect(url).ignoreHttpErrors(true).userAgent(USER_AGENT).get();
//		Document doc = Jsoup.connect(url).get();
		Elements links = doc.select("a[href]");

		String title = doc.title();

		return title;


	}

	public ArrayList<String> getsublinks(String url) throws IOException{
		ArrayList<String>sublinks = new ArrayList<String>();
		ArrayList<String>newsublinks = new ArrayList<String>();


		Document doc = Jsoup.connect(url).get();

		Elements links = doc.select("a[href]");
		for (Element link : links) {
			String sublink = link.select("a").attr("href");

			if(sublink.contains("https")) {
				sublinks.add(sublink);

			}

			for(String link0:sublinks) {
				if(!newsublinks.contains(link0)) {
					newsublinks.add(link0);
				}
			}
		}

		return newsublinks;
	}












}
