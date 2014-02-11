package com.example.carecounts.q_and_a;

public class QandAObject {
	public String question,answer,link;
	public int lang;
	public QandAObject(String question, 
			String answer,
			String link, 
			String type, 
			int lang){
		this.question = question; 
		this.answer = answer;
		this.link = link;
		this.lang = lang;
	}
}
