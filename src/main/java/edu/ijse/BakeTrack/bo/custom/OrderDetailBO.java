package edu.ijse.BakeTrack.bo.custom;

import edu.ijse.BakeTrack.bo.SuperBO;
import edu.ijse.BakeTrack.dto.OrderDetailDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailBO  extends SuperBO {

    ArrayList<OrderDetailDto> getOrderDetailsByOrderID(int order_id) throws SQLException;

    ArrayList<OrderDetailDto> getAllOrderDetails() throws Exception;
}
