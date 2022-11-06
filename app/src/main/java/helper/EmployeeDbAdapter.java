package helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Locale;

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
    public boolean signup(String username, String password, String empName, String number, String post, boolean isAdmin, int cmpId) {
        if (employeeDb == null) {
            this.open();
        }
        int user = checkUserId(username);
        if(user != -1) {
            return false;
        }
        username = username.toLowerCase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",password);
        contentValues.put("empName",empName);
        contentValues.put("mobNumber",number);
        contentValues.put("post",post);
        contentValues.put("cmpId",cmpId);
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
    public int getEmpid(String username)
    {
        if (employeeDb == null) {
            this.open();
        }
        username = username.toLowerCase();
        Cursor cursor = employeeDb.query("employee",null,null,null,null,null,null);
        if(cursor.moveToFirst()) {
            do {
                String user = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                if (user.equals(username)) {
                    int userId = cursor.getInt(cursor.getColumnIndexOrThrow("empId"));
                    return userId;
                }
            } while(cursor.moveToNext());
        }
        return -1;
    }


    private int checkUserId(String username) {
        username = username.toLowerCase();
        Cursor cursor = employeeDb.query("employee",null,null,null,null,null,null);
        if(cursor.moveToFirst()) {
            do {
                String user = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                if (user.equals(username)) {
                    int userId = cursor.getInt(cursor.getColumnIndexOrThrow("empId"));
                    return userId;
                }
            } while(cursor.moveToNext());
        }
        return -1;
    }
    private boolean validatePassword(int empId, String password) {
        String query = "empId = " + empId;
        Cursor cursor = employeeDb.query("employee",null,query,null,null,null,null);
        String pass = null;
        if (cursor.moveToFirst()) {
            pass = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            if (pass.equals(password)) {
                return true;
            }
        }
        return false;
    }
    public String getPassword(int empId){
        String query = "empId = " + empId;
        Cursor cursor = employeeDb.query("employee",null,query,null,null,null,null);
        String pass = null;
        if (cursor.moveToFirst()) {
            pass = cursor.getString(cursor.getColumnIndexOrThrow("password"));
        }
        return pass;
    }
    public void deleteEntries() {
        employeeDb.execSQL("DROP TABLE employee");
        employeeDb.execSQL("CREATE TABLE employee(empId INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, empName TEXT, mobNumber TEXT, post TEXT, isAdmin INTEGER, cmpId INTEGER)");
    }

    public void deleteEntry(int empId) {
        if (employeeDb == null) {
            this.open();
        }
        String query = "DELETE from employee where empId = " + empId;
        employeeDb.execSQL(query);
    }
    public void updatdb(int empId,String number, String post){
        if (employeeDb == null) {
            this.open();
        }
        String query = "UPDATE employee SET mobNumber = '"+number+"', post = '"+post+"' where empId = " + empId;
        employeeDb.execSQL(query);
    }
    // Use this to see for logging in the user.
    // Will return empId on successful login, -1 if user does not exist and -2 for wrong password
    public int validateLogin(String username, String password) {
        if (employeeDb == null) {
            this.open();
        }
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
        if (employeeDb == null) {
            this.open();
        }
        String query = "empId = " + empId;
        Cursor cursor = employeeDb.query("employee",null,query,null,null,null,null);
        if (cursor.moveToFirst()) {
            String userName = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String empName = cursor.getString(cursor.getColumnIndexOrThrow("empName"));
            String number = cursor.getString(cursor.getColumnIndexOrThrow("mobNumber"));
            String post = cursor.getString(cursor.getColumnIndexOrThrow("post"));
            int isAdm = cursor.getInt(cursor.getColumnIndexOrThrow("isAdmin"));
            boolean isAdmin = false;
            if (isAdm == 1) {
                isAdmin = true;
            }
            int cmpId = cursor.getInt(cursor.getColumnIndexOrThrow("cmpId"));
            Employee emp = new Employee(empId,userName,empName,number,post,isAdmin,cmpId);
            return emp;
        }
        return null;
    }

    public ArrayList<Employee> searchCompany (int cmpId) {
        if (employeeDb == null) {
            this.open();
        }
        ArrayList<Employee> empList = new ArrayList<Employee>();
        empList.clear();
        String query = "cmpId = " + cmpId;
        Cursor cursor = employeeDb.query("employee",null,query,null,null,null,null);
        if(cursor.moveToFirst()) {
            do {
                int empId = cursor.getInt(cursor.getColumnIndexOrThrow("empId"));
                String userName = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String empName = cursor.getString(cursor.getColumnIndexOrThrow("empName"));
                String number = cursor.getString(cursor.getColumnIndexOrThrow("mobNumber"));
                String post = cursor.getString(cursor.getColumnIndexOrThrow("post"));
                int isAdm = cursor.getInt(cursor.getColumnIndexOrThrow("isAdmin"));
                boolean isAdmin = false;
                if (isAdm == 1) {
                    isAdmin = true;
                }
                int cmp = cursor.getInt(cursor.getColumnIndexOrThrow("cmpId"));
                Employee emp = new Employee(empId,userName,empName,number,post,isAdmin,cmp);
                empList.add(emp);
            } while(cursor.moveToNext());
        }
        return empList;

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
            sqLiteDatabase.execSQL("CREATE TABLE employee(empId INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, empName TEXT, mobNumber TEXT, post TEXT, isAdmin INTEGER, cmpId INTEGER)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE employee");
            sqLiteDatabase.execSQL("CREATE TABLE employee(empId INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, empName TEXT, mobNumber TEXT, post TEXT, isAdmin INTEGER, cmpId INTEGER)");
        }
    }
}