package helper;

import android.content.Context;
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

    public boolean signup() {
        return false;
    }

    public int validateLogin() {
        return 0;
    }

    public Employee getEmployeeData(int empId) {
        return null;
    }

    private static class EmployeeDbHelper extends SQLiteOpenHelper {


        public EmployeeDbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("CREATE TABLE employee(empId INTEGER PRIMARY KEY AUTOINCREMENT, login TEXT, password TEXT)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("CREATE TABLE employee(empId INTEGER PRIMARY KEY AUTOINCREMENT, login TEXT, password TEXT)");
        }
    }
}
