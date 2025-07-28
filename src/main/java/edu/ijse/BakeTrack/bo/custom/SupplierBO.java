package edu.ijse.BakeTrack.bo.custom;

import edu.ijse.BakeTrack.dto.SupplierDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBO {
    String addSupplier(SupplierDto supplierDto) throws Exception;

    String deleteSupplier(int supplierId) throws Exception;

    String updateSupplier( SupplierDto supplierDto) throws Exception;

    ArrayList<SupplierDto> getAllSuppliers() throws Exception;

    ArrayList<SupplierDto> getAllSuppliersWIthEmail() throws Exception;
}
