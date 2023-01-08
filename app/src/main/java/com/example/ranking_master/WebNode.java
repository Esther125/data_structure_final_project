package com.example.ranking_master;

import java.io.IOException;
import java.util.ArrayList;

import java.io.IOException;
import java.util.ArrayList;

public class WebNode {
	public WebNode parent;
	public ArrayList<WebNode> children;
	public WebPage webPage;
	public double nodeScore;//This node's score += all its children's nodeScore

	public WebNode(WebPage webPage){
		this.webPage = webPage;
		this.children = new ArrayList<WebNode>();
	}


	public WebPage getwebPage() {
		return webPage;
	}

	public void setNodeScore(ArrayList<Keyword> keywords,ArrayList<Keyword>charac,ArrayList<Keyword>scan) throws IOException{
		//this method should be called in post-order mode

		//compute webPage score
		webPage.setScore(keywords,charac,scan);
		//set webPage score to nodeScore
		nodeScore = webPage.score;

		//nodeScore += all children's nodeScore
		for(WebNode child : children){
			nodeScore += child.nodeScore*0.5;
		}
	}

	public void addChild(WebNode child){
		//add the WebNode to its children list
		this.children.add(child);
		child.parent = this;
	}

	public boolean isTheLastChild(){
		if(this.parent == null) return true;
		ArrayList<WebNode> siblings = this.parent.children;

		return this.equals(siblings.get(siblings.size() - 1));
	}

	public int getDepth(){
		int retVal = 1;
		WebNode currNode = this;
		while(currNode.parent != null){
			retVal++;
			currNode = currNode.parent;
		}
		return retVal;
	}
}