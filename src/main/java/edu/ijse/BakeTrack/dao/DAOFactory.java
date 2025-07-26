package edu.ijse.BakeTrack.dao;

import edu.ijse.BakeTrack.dao.custom.QueryDAO;
import edu.ijse.BakeTrack.dao.custom.impl.*;

import java.sql.SQLException;

public class DAOFactory {

    private static DAOFactory daoFactory;

    private  DAOFactory(){

    }

    public static DAOFactory getInstance(){

        return (daoFactory==null)?new DAOFactory():daoFactory;
    }

    public enum DAOType{
        ATTENDANCE,
        CUSTOMER,
        INGREDIENT,
        ORDER,
        ORDER_DETAIL,
        USER,
        VEHICLE,
        DELIVERY,
        EMPLOYEE,
        PAYMENT,
        PAYROLL,
        PRODUCT,
        PRODUCT_INGREDIENT,
        QUERY,
        SUPPLIER,
        SUPPLIER_INGREDIENT,
        SYSTEM_SETTING
    }

    public SuperDAO getDAO(DAOType daoType) throws SQLException, ClassNotFoundException {
        switch (daoType) {

            case ATTENDANCE:
                return new AttendanceDAOImpl();

            case CUSTOMER:
                return new CustomerDAOImpl();

            case EMPLOYEE:
                return new EmployeeDAOImplj();

            case PAYMENT:
                return new PaymentDAOImplT();

            case SYSTEM_SETTING:
                return new SystemSettingDAOImpl();

            case USER:
                return new UsersDAOImpl();

            case INGREDIENT:
                return new IngredientDAOImpl();

            case ORDER:
                return new OrdersDAOImplT();

            case ORDER_DETAIL:
                return new OrderDetailDAOImpl();

            case VEHICLE:
                return new VehicleDAOImpl();

            case DELIVERY:
                return new DeliveryDAOImplT();

            case PAYROLL:
                return new PayrollDAOImpl();

            case PRODUCT:
                return new ProductDAOImpl();

            case PRODUCT_INGREDIENT:
                return new ProductIngredientDAOImpl();

            case QUERY:
                return new QueryDAOImpl();

            case SUPPLIER:
                return new SupplierDAOImpl();

            case SUPPLIER_INGREDIENT:
                return new SupplierIngredientsDAOImpl();

            default:
                throw new IllegalArgumentException("Unknown DAOType: " + daoType);
        }
    }
}
