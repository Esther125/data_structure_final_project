package com.example.ranking_master;

public class Keyword {
	public String name;
	public double weight;
	public String type;
	
	public Keyword(String name, double weight){
		this.name = name;
		this.type = type;
		this.weight = weight;
		
//		if(this.type == "name") {
//			this.setWeight(3);
//		}else if(this.type == "char") {
//			this.setWeight(2);
//		}
	}
	
	@Override
	public String toString(){
		return "["+name+","+weight+","+type+"]";
	}
	
	
	public String getName() {
		return name;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
