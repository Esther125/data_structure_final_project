package com.example.ranking_master;

import java.util.Comparator;

public class WebComparator implements Comparator<WebPage> {

	@Override
	public int compare(WebPage t1,WebPage t2) {
		// TODO Auto-generated method stub
		if(t1.getScore()>t2.getScore()) {
			return -1;
		}else if(t1.getScore()<t2.getScore()){
			return 1;
		}
		return 0;
	}

}
