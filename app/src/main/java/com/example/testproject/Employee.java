package com.example.testproject;

public class Employee {
    private String EmpId;
    private String Name;
    private String DOJ;

    public Employee() {
    }

    public Employee(String empId, String name, String DOJ) {
        this.EmpId = empId;
        Name = name;
        this.DOJ = DOJ;
    }

    public String getEmpId() {
        return EmpId;
    }

    public void setEmpId(String empId) {
        this.EmpId = empId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDOJ() {
        return DOJ;
    }

    public void setDOJ(String DOJ) {
        this.DOJ = DOJ;
    }
}
