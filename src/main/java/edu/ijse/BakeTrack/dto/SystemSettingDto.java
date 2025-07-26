package edu.ijse.BakeTrack.dto;

public class SystemSettingDto {
    private int ot_rate;

    public SystemSettingDto(int ot_rate) {
        this.ot_rate = ot_rate;
    }

    public int getOt_rate() {
        return ot_rate;
    }

    public void setOt_rate(int ot_rate) {
        this.ot_rate = ot_rate;
    }
}
