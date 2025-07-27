package edu.ijse.BakeTrack.bo.custom.impl;

import edu.ijse.BakeTrack.bo.custom.OrderDetailBO;
import edu.ijse.BakeTrack.dao.DAOFactory;
import edu.ijse.BakeTrack.dao.custom.OrderDetailDAO;
import edu.ijse.BakeTrack.dto.OrderDetailDto;
import edu.ijse.BakeTrack.entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDAOImpl implements OrderDetailBO {

    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER_DETAIL);

    public OrderDetailDAOImpl() throws SQLException, ClassNotFoundException {
    }

    @Override
    public ArrayList<OrderDetailDto> getOrderDetailsByOrderID(int order_id) throws SQLException {
        return orderDetailDAO.getOrderDetailsByOrderID(order_id);
    }

    @Override
    public ArrayList<OrderDetailDto> getAllOrderDetails() throws Exception {
        ArrayList<OrderDetail> orderDetailList = orderDetailDAO.getAll();
        ArrayList<OrderDetailDto> orderDetailDtos = new ArrayList<>();

        for (OrderDetail detail : orderDetailList) {
            orderDetailDtos.add(new OrderDetailDto(
                    detail.getOrderID(),
                    detail.getProductID(),
                    detail.getQuantity(),
                    detail.getPriceAtOrder()
            ));
        }

        return orderDetailDtos;
    }
}
