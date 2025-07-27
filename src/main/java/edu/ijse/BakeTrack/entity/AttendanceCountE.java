package edu.ijse.BakeTrack.entity;

import java.time.LocalDate;

public class AttendanceCountE {
    private int count;
    private LocalDate date;

    public AttendanceCountE(int count, LocalDate date) {
        this.count = count;
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
