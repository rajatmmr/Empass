package helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import daatamodel.Employee;

public class EmployeeDbAdapter {
    private static String dbName = "employeeDb";
    private static int dbVersion = 1;
    private EmployeeDbHelper helper;
    private SQLiteDatabase employeeDb;
    public EmployeeDbAdapter(Context context) {
        helper = new EmployeeDbHelper(context,dbName,null, dbVersion);
        employeeDb = null;
    }
    public void open() {
        employeeDb = helper.getWritableDatabase();
    }
    // Use this method for signing up new users. Will return true on successful insert false otherwise.
    public boolean signup(String username, String password, String empName, String number, String post, boolean isAdmin) {
        if (employeeDb == null) {
            this.open();
        }
        int user = checkUserId(username);
        if(user != -1) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",password);
        contentValues.put("empName",empName);
        contentValues.put("mobNumber",number);
        contentValues.put("post",post);
        if (isAdmin == true) {
            contentValues.put("isAdmin", 1);
        }
        else {
            contentValues.put("isAdmin", 0);
        }
        long error = employeeDb.insert("employee",null,contentValues);
        if (error == -1) {
            return false;
        }
        return true;
    }


    private int checkUserId(String username) {
        Cursor cursor = employeeDb.query("employee",null,null,null,null,null,null);
        while(cursor.moveToNext()) {
            String user = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            if (user.equals(username)) {
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow("empId"));
                return userId;
            }
        }
        return -1;
    }
    private boolean validatePassword(int empId, String password) {
        String query = "empId = " + empId;
        Cursor cursor = employeeDb.query("employee",null,query,null,null,null,null);
        String pass = cursor.getString(cursor.getColumnIndexOrThrow("password"));
        if (pass.equals(password)) {
            return true;
        }
        return false;
    }

    // Use this to see for logging in the user.
    // Will return empId on successful login, -1 if user does not exist and -2 for wrong password
    public int validateLogin(String username, String password) {
        int userId = checkUserId(username);
        if (userId == -1) {
            return -1;
        }
        if (validatePassword(userId,password)) {
            return userId;
        }
        return -2;
    }
    //Use this method to get employee data. Will return a Employee class with all employee info.
    public Employee getEmployeeData(int empId) {
        String query = "empId = " + empId;
        Cursor cursor = employeeDb.query("employee",null,query,null,null,null,null);
        String empName = cursor.getString(cursor.getColumnIndexOrThrow("empName"));
        String number = cursor.getString(cursor.getColumnIndexOrThrow("mobNumber"));
        String post = cursor.getString(cursor.getColumnIndexOrThrow("post"));
        int isAdm = cursor.getInt(cursor.getColumnIndexOrThrow("isAdmin"));
        boolean isAdmin = false;
        if (isAdm == 1) {
            isAdmin = true;
        }
        Employee emp = new Employee(empId,empName,number,post,isAdmin);
        return null;
    }
    public void close() {
        employeeDb.close();
        employeeDb = null;
    }
    private static class EmployeeDbHelper extends SQLiteOpenHelper {

        public EmployeeDbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("CREATE TABLE employee(empId INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, empName TEXT, mobNumber TEXT, post TEXT, isAdmin INTEGER)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE employee");
            sqLiteDatabase.execSQL("CREATE TABLE employee(empId INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, empName TEXT, mobNumber TEXT, post TEXT, isAdmin INTEGER)");
        }
    }
}
