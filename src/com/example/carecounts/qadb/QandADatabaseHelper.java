package com.example.carecounts.qadb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QandADatabaseHelper extends SQLiteOpenHelper {
		private static final String DATABASE_NAME = "carecounts_qa.db";
		private static final int DATABASE_VERSION = 3;

		QandADatabaseHelper(Context context) {
			// calls the super constructor, requesting the default cursor factory.
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		public void onCreate(SQLiteDatabase database) {
			database.execSQL(QuestionAnswerTable.DATABASE_CREATE);
		}

		// Method is called during an upgrade of the database,
		// e.g. if you increase the database version
		@Override
		public void onUpgrade(SQLiteDatabase database, int oldVersion,
				int newVersion) {
			//Change in the future so info isn't lost
			database.execSQL("DROP TABLE IF EXISTS " + QuestionAnswerTable.TABLE_NAME + ";");
			onCreate(database);
		}

}
