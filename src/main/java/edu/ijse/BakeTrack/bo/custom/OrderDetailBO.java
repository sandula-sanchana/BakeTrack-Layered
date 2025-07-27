package edu.ijse.BakeTrack.bo.custom;

import edu.ijse.BakeTrack.dto.OrderDetailDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailBO {

    ArrayList<OrderDetailDto> getOrderDetailsByOrderID(int order_id) throws SQLException;

    ArrayList<OrderDetailDto> getAllOrderDetails() throws Exception;
}
