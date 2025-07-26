package edu.ijse.BakeTrack.dao.custom;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.ijse.BakeTrack.dao.CrudDAO;
import edu.ijse.BakeTrack.dto.OrderDetailDto;
import edu.ijse.BakeTrack.entity.OrderDetail;

public interface OrderDetailDAO extends CrudDAO<OrderDetail> {

    ArrayList<OrderDetailDto> getOrderDetailsByOrderID(int order_id) throws SQLException;
}
