package edu.ijse.BakeTrack.tm;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceTM {
    private int employeeID;
    private LocalDate attendDate;
    private LocalTime check_in_time;
    private LocalTime check_out_time;
    private String status;

    public AttendanceTM(int employeeID, LocalDate attendDate, LocalTime check_in_time, LocalTime check_out_time, String status) {
        this.employeeID = employeeID;
        this.attendDate = attendDate;
        this.check_in_time = check_in_time;
        this.check_out_time = check_out_time;
        this.status = status;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public LocalDate getAttendDate() {
        return attendDate;
    }

    public void setAttendDate(LocalDate attendDate) {
        this.attendDate = attendDate;
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
