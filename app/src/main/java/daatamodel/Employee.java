package daatamodel;

public class Employee {
    private int empId;
    private String empName;
    private String mobNumber;
    private String post;
    private boolean isAdmin;
    public Employee (int empId, String empName, String mobNumber, String post, boolean isAdmin) {
        this.empId = empId;
        this.empName = empName;
        this.mobNumber = mobNumber;
        this.post = post;
        this.isAdmin = isAdmin;
    }

}
