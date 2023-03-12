package com.originals.meraqi;

public class employeeDetails {
    private String name;
    private String role;
    private String email;
    private String phone;
    private String salary;
    private String ml;
    private String cl;
    private String doj;
    private boolean isExpanded;
    public employeeDetails(String name, String role, String email, String phone, String salary, String ml, String cl, String doj) {
        this.name = name;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.salary = salary;
        this.ml = ml;
        this.cl = cl;
        this.doj = doj;
        isExpanded=false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getMl() {
        return ml;
    }

    public void setMl(String ml) {
        this.ml = ml;
    }

    public String getCl() {
        return cl;
    }

    public void setCl(String cl) {
        this.cl = cl;
    }

    public String getDoj() {
        return doj;
    }

    public void setDoj(String doj) {
        this.doj = doj;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
