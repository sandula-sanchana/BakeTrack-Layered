package edu.ijse.BakeTrack.bo;

import edu.ijse.BakeTrack.bo.custom.impl.DeliveryBOImpl;
import edu.ijse.BakeTrack.bo.custom.impl.EmployeeBOImpl;
import edu.ijse.BakeTrack.bo.custom.impl.*;

import java.sql.SQLException;

public class BOFactory {

    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getInstance() {
        return  (boFactory == null)? new BOFactory():boFactory;
    }

    public enum BOType {
        ATTENDANCE,
        CUSTOMER,
        DELIVERY,
        EMPLOYEE,
        INGREDIENT,
        ORDER,
        ORDER_DETAIL,
        PAYMENTS,
        PAYROLL,
        PRODUCT,
        PRODUCT_INGREDIENT,
        SUPPLIER,
        SUPPLIER_INGREDIENT,
        SYSTEM_SETTING,
        USER,
        VEHICLE
    }

    public SuperBO getBO(BOType type) throws SQLException, ClassNotFoundException {
        switch (type) {
            case ATTENDANCE:
                return new AttendanceBOImpl();
            case CUSTOMER:
                return new CustomerBOImol();
            case DELIVERY:
                return new DeliveryBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case INGREDIENT:
                return new IngredientBOImpl();
            case ORDER:
                return new OrderBOImpl();
            case ORDER_DETAIL:
                return new OrderDetailDAOImpl();
            case PAYMENTS:
                return new PaymentsBOImpl();
            case PAYROLL:
                return new PayrollBOImpl();
            case PRODUCT:
                return new ProductBOImpl();
            case PRODUCT_INGREDIENT:
                return new ProductIngredientBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case SUPPLIER_INGREDIENT:
                return new SupplierIngredientBOImpl();
            case SYSTEM_SETTING:
                return new SystemSettingBOImpl();
            case USER:
                return new UserBOImpl();
            case VEHICLE:
                return new VehicleBOImpl();
            default:
                return null;
        }
    }

}
