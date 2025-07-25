package edu.ijse.BakeTrack.dto;


    public class OrderTrendDto {
        private String date;
        private int count;

        public OrderTrendDto(String date, int count) {
            this.date = date;
            this.count = count;
        }

        public String getDate() {
            return date; }
        public int getCount() {
            return count; }
    }


