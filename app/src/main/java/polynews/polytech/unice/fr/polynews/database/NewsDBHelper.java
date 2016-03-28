package polynews.polytech.unice.fr.polynews.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import polynews.polytech.unice.fr.polynews.model.News;

/**
 * @see <a href="http://www.vogella.com/tutorials/AndroidSQLite/article.html">Android SQLite database and content provider - Tutorial</a>
 * @see <a href="http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/">Android SQLite Database Tutorial</a>
 */
public class NewsDBHelper extends SQLiteOpenHelper {
    public static final String TAG = "MyActivity";
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "polynews_database";
    // News table name
    private static final String TABLE_NEWS = "news";
    // Database path
    private static final String DATABASE_PATH = "/data/data/polynews.polytech.unice.fr.polynews/databases/";

    private SQLiteDatabase myDataBase;
    private final Context myContext;

    public NewsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if(!dbExist){
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;
        try{
            String myPath = DATABASE_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch(SQLiteException e){
            Log.e(TAG, "Database doesn't exist");
        }
        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null;
    }

    private void copyDataBase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DATABASE_NAME);
        String outFileName = DATABASE_PATH + DATABASE_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0){
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException {
        //Open the database
        String myPath = DATABASE_PATH + DATABASE_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if(myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(NewsDBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);
        onCreate(db);
    }

    /**
     * Method to return all records in the table news.
     *
     * moveToFirst():
     * Move the cursor to the first row.
     * This method will return false if the cursor is empty.
     *
     * moveToNext():
     * Move the cursor to the next row.
     * This method will return false if the cursor is already past the
     * last entry in the result set.
     * @return all records in the table news
     */
    public List<News> selectRecords() {
        Cursor cursor = myDataBase.rawQuery("SELECT * FROM news ORDER BY date DESC", null);
        List<News> newsList = new ArrayList<>();

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                News news = new News();
                news.setId(Integer.parseInt(cursor.getString(0)));
                news.setTitle(cursor.getString(1));
                news.setContent(cursor.getString(2));
                news.setAuthor(cursor.getString(3));
                news.setDate(cursor.getString(4));
                news.setCategory(Integer.parseInt(cursor.getString(5)));
                news.setMedia_type(Integer.parseInt(cursor.getString(6)));
                news.setMedia_path(cursor.getString(7));
                // Adding new to list
                newsList.add(news);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return newsList;
    }
}