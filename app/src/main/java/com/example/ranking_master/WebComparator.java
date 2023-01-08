package com.example.ranking_master;

import java.util.Comparator;

public class WebComparator implements Comparator<WebTree> {

	@Override
	public int compare(WebTree t1,WebTree t2) {
		// TODO Auto-generated method stub
		if(t1.getPostOrderScore()>t2.getPostOrderScore()) {
			return -1;
		}else if(t1.getPostOrderScore()<t2.getPostOrderScore()){
			return 1;
		}
		return 0;
	}

}
