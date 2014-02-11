package com.example.carecounts.qadb;

public class QuestionAnswerTable {
	public final static String TABLE_NAME = "question_answer_table";
	public final static String _ID = "_id";
	public final static String QUESTION = "question";
	public final static String ANSWER = "answer";
	public final static String LINK = "link";
	public final static String TOPIC_NAME = "topic_name";
	public final static String TOPIC_ID = "topic_id";
	public final static String LANG = "lang";
	public final static String DATE = "date";
	
	public final static String DATABASE_CREATE = "create table " + 
			TABLE_NAME + " (" +
			_ID + " integer primary key autoincrement, " +
			QUESTION + " text," +
			ANSWER + " text," +
			LINK + " text," +
			TOPIC_NAME + " text," +
			LANG + " integer," +
			DATE + " INTEGER NOT NULL DEFAULT (strftime('%s','now')),"+
			TOPIC_ID + " integer" +
           ");";
}
