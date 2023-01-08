package com.example.ranking_master;

import java.io.IOException;
import java.util.ArrayList;

public class WebTree {
	public WebNode root;

	public WebNode getRoot() {
		return root;
	}

	public WebTree(WebPage rootPage){
		this.root = new WebNode(rootPage);
	}

	public void setPostOrderScore(ArrayList<Keyword> keywords,ArrayList<Keyword>charac,ArrayList<Keyword>scan) throws IOException{
		setPostOrderScore(root, keywords,charac,scan);
	}

	public double getPostOrderScore() {
		return root.nodeScore;
	}

	private void setPostOrderScore(WebNode startNode, ArrayList<Keyword> keywords,ArrayList<Keyword>charac,ArrayList<Keyword>scan) throws IOException{
		//2. compute the score of children nodes via post-order, then setNodeScore for startNode

		for(WebNode child : startNode.children){
			setPostOrderScore(child, keywords,charac,scan);

		}
		//**setNode score of startNode
		startNode.setNodeScore(keywords,charac,scan);
	}






	public void eularPrintTree(){
		eularPrintTree(root);
	}

	private void eularPrintTree(WebNode startNode){
		int nodeDepth = startNode.getDepth();

		if(nodeDepth > 1) System.out.print("\n" + repeat("\t", nodeDepth-1));

		System.out.print("(");
		System.out.print(startNode.webPage.name + "," + startNode.nodeScore);

		//3. print child via pre-order
		//System.out.println(startNode);

//		for(int i=0;i<startNode.children.size();i++) {
//			System.out.print("	(");
//			System.out.print(startNode.children.get(i).webPage.name+ "," + startNode.children.get(i).nodeScore);
//			if(startNode.children.get(i).children!=null) {
//				System.out.println(" ");
//				for(int j=0;j<startNode.children.get(i).children.size();j++) {
//				System.out.print("		(");
//				System.out.print(startNode.children.get(i).children.get(j).webPage.name+"," + startNode.children.get(i).children.get(j).nodeScore);
//				System.out.print("	)");
//				}
//			}
//			System.out.println("	)");
//		}

		if(!startNode.children.isEmpty()) {
			for(int i=0;i<startNode.children.size();i++){
				eularPrintTree(startNode.children.get(i));
			}
		}



		System.out.print(")");

		if(startNode.isTheLastChild()) System.out.print("\n" + repeat("\t", nodeDepth-2));
	}

	private String repeat(String str, int repeat){
		String retVal = "";
		for(int i = 0; i < repeat; i++){
			retVal += str;
		}
		return retVal;
	}



}