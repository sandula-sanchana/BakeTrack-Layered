package edu.ijse.BakeTrack.bo.custom.impl;

import edu.ijse.BakeTrack.bo.custom.SupplierBO;
import edu.ijse.BakeTrack.dao.DAOFactory;
import edu.ijse.BakeTrack.dao.custom.SupplierDAO;
import edu.ijse.BakeTrack.dto.SupplierDto;
import edu.ijse.BakeTrack.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {

    SupplierDAO supplierDAO=((SupplierDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SUPPLIER));

    public SupplierBOImpl() throws SQLException, ClassNotFoundException {
    }

    @Override
    public String addSupplier(SupplierDto dto) throws Exception {
        Supplier entity = new Supplier(
                dto.getName(),
                dto.getContact(),
                dto.getAddress(),
                dto.getEmail()
        );
        return supplierDAO.save(entity);
    }

    @Override
    public String deleteSupplier(int supplierId) throws Exception {
        return supplierDAO.delete(supplierId);
    }

    @Override
    public String updateSupplier(SupplierDto dto) throws Exception {
        Supplier entity = new Supplier(
                dto.getSupplier_id(),
                dto.getName(),
                dto.getContact(),
                dto.getAddress(),
                dto.getEmail()
        );
        return supplierDAO.update(entity);
    }

    @Override
    public ArrayList<SupplierDto> getAllSuppliers() throws Exception {
        ArrayList<SupplierDto> list = new ArrayList<>();
        for (Supplier s : supplierDAO.getAll()) {
            list.add(new SupplierDto(
                    s.getSupplier_id(),
                    s.getName(),
                    s.getContact(),
                    s.getAddress(),
                    s.getEmail()
            ));
        }
        return list;
    }

    @Override
    public ArrayList<SupplierDto> getAllSuppliersWIthEmail() throws Exception {
        ArrayList<SupplierDto> filteredList = new ArrayList<>();
        for (Supplier s : supplierDAO.getAll()) {
            if (s.getEmail() != null && !s.getEmail().trim().isEmpty()) {
                filteredList.add(new SupplierDto(
                        s.getSupplier_id(),
                        s.getName(),
                        s.getContact(),
                        s.getAddress(),
                        s.getEmail()
                ));
            }
        }
        return filteredList;
    }
}
