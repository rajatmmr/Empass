package daatamodel;

public class Employee {
    private int empId;
    private String empName;
    private String mobNumber;
    private String post;
    private boolean isAdmin;
    private int cmpId;
    public Employee (int empId, String empName, String mobNumber, String post, boolean isAdmin, int cmpId) {
        this.empId = empId;
        this.empName = empName;
        this.mobNumber = mobNumber;
        this.post = post;
        this.isAdmin = isAdmin;
        this.cmpId = cmpId;
    }
    public int getEmpID () {
        return empId;
    }
    public String getEmpName() {
        return empName;
    }

    public String getMobNumber() {
        return mobNumber;
    }

    public String getPost() {
        return post;
    }
    public boolean isAdmin() {
        return isAdmin;
    }

    public int getCmpId() {
        return cmpId;
    }
}