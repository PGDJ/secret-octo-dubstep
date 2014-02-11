package com.example.carecounts.qadb;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class QandAContentProvider extends ContentProvider {

	public static final String AUTH = "com.example.carecount.provider";
	private static final String QUESTION_ANSWER = "qa_table";
	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static
	{
		sURIMatcher.addURI(AUTH, QUESTION_ANSWER, 1); // 1 for case 1:   
	}
	public static final Uri CONTENT_QandA_URI = Uri.parse("content://" + AUTH+ "/" + QUESTION_ANSWER);
	private QandADatabaseHelper dbHelper;

	@Override
	public boolean onCreate() {
		dbHelper = new QandADatabaseHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase sqlDB = dbHelper.getReadableDatabase();
		Cursor c = null;
		int match = sURIMatcher.match(uri);
		switch(match){
		case 1:
			String statement = "SELECT * from "+QuestionAnswerTable.TABLE_NAME
			+" where "+QuestionAnswerTable.TOPIC_NAME+" = ?";
			c = sqlDB.rawQuery(statement, selectionArgs);
			
			/*c = sqlDB.query(QuestionAnswerTable.TABLE_NAME, 
					new String[]{"*"}, selection, selectionArgs, null, null, sortOrder);*/
			/*String statement = "SELECT "+
					QuestionTable.TABLE_NAME+"."+QuestionTable._ID+", "+
					QuestionTable.TABLE_NAME+"."+QuestionTable.QUESTION+", "+
					AnswerTable.TABLE_NAME+"."+AnswerTable.ANSWER+" "+
					"from " + 
					QuestionTable.TABLE_NAME + " INNER JOIN " + AnswerTable.TABLE_NAME +
					" ON " +QuestionTable.TABLE_NAME+"."+QuestionTable._ID+"="+AnswerTable.TABLE_NAME+"."+AnswerTable.QUESTION_ID+";";
			c = sqlDB.rawQuery(statement, selectionArgs);
			 */
			c.setNotificationUri(getContext().getContentResolver(), uri);
			break;
		}
		return c;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase sqlDB = dbHelper.getReadableDatabase();
		int match = sURIMatcher.match(uri);
		switch(match){
		case 1:
			long id = sqlDB.insert(QuestionAnswerTable.TABLE_NAME, null, values);
			return Uri.parse("content://" + AUTH + "/" + QUESTION_ANSWER + "/" + id);
		}
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Uri uri, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

}
