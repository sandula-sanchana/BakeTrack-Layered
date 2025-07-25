package edu.ijse.BakeTrack.entity;


    public class OrderTrend {
        private String date;
        private int count;

        public OrderTrend(String date, int count) {
            this.date = date;
            this.count = count;
        }

        public String getDate() {
            return date; }
        public int getCount() {
            return count; }
    }


