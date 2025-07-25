package edu.ijse.BakeTrack.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AttendanceDto {
    private int employee_id;
    private LocalDate attend_date;
    private LocalTime check_in_time;
    private LocalTime check_out_time;
    private String status;

    public AttendanceDto() {
    }

    public AttendanceDto(int employee_id,  LocalDate attend_date, LocalTime check_in_time, LocalTime check_out_time, String status) {
        this.employee_id = employee_id;
        this.attend_date = attend_date;
        this.check_in_time = check_in_time;
        this.check_out_time = check_out_time;
        this.status = status;
    }


    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public LocalDate getAttend_date() {
        return attend_date;
    }

    public void setAttend_date(LocalDate attend_date) {
        this.attend_date = attend_date;
    }

    public LocalTime getCheck_in_time() {
        return check_in_time;
    }

    public void setCheck_in_time(LocalTime check_in_time) {
        this.check_in_time = check_in_time;
    }

    public LocalTime getCheck_out_time() {
        return check_out_time;
    }

    public void setCheck_out_time(LocalTime check_out_time) {
        this.check_out_time = check_out_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
