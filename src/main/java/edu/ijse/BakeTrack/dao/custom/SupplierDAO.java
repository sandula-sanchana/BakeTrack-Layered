package edu.ijse.BakeTrack.dao.custom;
import edu.ijse.BakeTrack.dao.CrudDAO;
import edu.ijse.BakeTrack.entity.Supplier;

import java.sql.SQLException;

public interface SupplierDAO extends CrudDAO<Supplier> {

    void getSupplierById(int supplierId) throws SQLException;
}
