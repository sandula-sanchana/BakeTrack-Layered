package edu.ijse.BakeTrack.tm;

import java.time.LocalDate;

public class PayrollTM {

    private int payroll_id;
    private int employee_id;
    private LocalDate pay_Date;
    private Double over_time_hours;
    private double base_salary;
    private double full_salary;
    private String status;

    public PayrollTM(int payroll_id, int employee_id, LocalDate pay_Date, Double over_time_hours, double base_salary, String status, double full_salary) {
        this.payroll_id = payroll_id;
        this.employee_id = employee_id;
        this.pay_Date = pay_Date;
        this.over_time_hours = over_time_hours;
        this.base_salary = base_salary;
        this.status = status;
        this.full_salary = full_salary;
    }

    public PayrollTM(int employee_id, LocalDate pay_Date, Double over_time_hours, double base_salary, double full_salary, String status) {
        this.employee_id = employee_id;
        this.pay_Date = pay_Date;
        this.over_time_hours = over_time_hours;
        this.base_salary = base_salary;
        this.full_salary = full_salary;
        this.status = status;
    }

    public PayrollTM(int payroll_id, int employee_id, LocalDate pay_Date, Double over_time_hours, double base_salary, double full_salary, String status) {
        this.payroll_id = payroll_id;
        this.employee_id = employee_id;
        this.pay_Date = pay_Date;
        this.over_time_hours = over_time_hours;
        this.base_salary = base_salary;
        this.full_salary = full_salary;
        this.status = status;
    }

    public int getPayroll_id() {
        return payroll_id;
    }

    public void setPayroll_id(int payroll_id) {
        this.payroll_id = payroll_id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public LocalDate getPay_Date() {
        return pay_Date;
    }

    public void setPay_Date(LocalDate pay_Date) {
        this.pay_Date = pay_Date;
    }

    public Double getOver_time_hours() {
        return over_time_hours;
    }

    public void setOver_time_hours(Double over_time_hours) {
        this.over_time_hours = over_time_hours;
    }

    public double getBase_salary() {
        return base_salary;
    }

    public void setBase_salary(double base_salary) {
        this.base_salary = base_salary;
    }

    public double getFull_salary() {
        return full_salary;
    }

    public void setFull_salary(double full_salary) {
        this.full_salary = full_salary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
