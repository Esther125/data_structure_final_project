package com.example.ranking_master;

import java.io.IOException;
import java.util.ArrayList;

public class WebPage {
	public String url;
	public String name;
	public WordCounter counter;
	public double score;

	public WebPage(String url, String name){
		this.url = url;
		this.name = name;
		this.counter = new WordCounter(url);
	}

	public WebPage(String url) {
		this.url = url;
		this.counter = new WordCounter(url);
	}

	public String getUrl() {
		return url;
	}

	public String getName() {
		return name;
	}



	public void setScore(ArrayList<Keyword> keywords,ArrayList<Keyword>charac,ArrayList<Keyword>scan) {
		double namescore = 0;
		double characscore = 0;

		try {
			for(int i=0;i<keywords.size();i++) {
				namescore += keywords.get(i).weight*counter.countKeyword(keywords.get(i).getName());
			}

			for(int j=0;j<charac.size();j++) {
				characscore +=charac.get(j).weight*counter.countKeyword(charac.get(j).getName());

			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("讀不到");
			score = 0;
		}
//		1. calculate score

		score = (namescore+characscore);


	}

	public void setcharScore(ArrayList<Keyword> keywords,ArrayList<Keyword>charac,ArrayList<Keyword>scan) throws IOException{
		double namescore = 0;
		double characscore = 0;
		double scanscore=0;

//		1. calculate score
		for(int i=0;i<keywords.size();i++) {
			namescore += keywords.get(i).weight*counter.countKeyword(keywords.get(i).getName());
		}

		for(int j=0;j<charac.size();j++) {
			characscore +=charac.get(j).weight*counter.countKeyword(charac.get(j).getName());

		}

		for(int k=0;k<scan.size();k++) {
			scanscore += keywords.get(k).weight*counter.countKeyword(keywords.get(k).getName());
		}


		score = characscore;


	}


	public void setnameScore(ArrayList<Keyword> keywords,ArrayList<Keyword>charac,ArrayList<Keyword>scan) throws IOException{
		double namescore = 0;
		double characscore = 0;
		double scanscore=0;

//		1. calculate score
		for(int i=0;i<keywords.size();i++) {
			namescore += keywords.get(i).weight*counter.countKeyword(keywords.get(i).getName());
		}

		for(int j=0;j<charac.size();j++) {
			characscore +=charac.get(j).weight*counter.countKeyword(charac.get(j).getName());

		}

		for(int k=0;k<scan.size();k++) {
			scanscore += keywords.get(k).weight*counter.countKeyword(keywords.get(k).getName());
		}


		score = namescore;


	}




	public double getScore() {
		return score;
	}
}