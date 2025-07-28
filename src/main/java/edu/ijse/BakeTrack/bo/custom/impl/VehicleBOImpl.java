package edu.ijse.BakeTrack.bo.custom.impl;

import edu.ijse.BakeTrack.bo.custom.VehicleBO;
import edu.ijse.BakeTrack.dao.DAOFactory;
import edu.ijse.BakeTrack.dao.custom.VehicleDAO;
import edu.ijse.BakeTrack.dto.VehicleDto;
import edu.ijse.BakeTrack.entity.Vehicle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class VehicleBOImpl implements VehicleBO {

    VehicleDAO vehicleDAO=(VehicleDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.VEHICLE);

    public VehicleBOImpl() throws SQLException, ClassNotFoundException {
    }

    @Override
    public String addVehicle(VehicleDto vehicleDto) throws Exception {
        Vehicle vehicle = new Vehicle(
                vehicleDto.getVehicle_id(),
                vehicleDto.getType(),
                vehicleDto.getLicensePlate(),
                vehicleDto.getStatus()
        );
        return vehicleDAO.save(vehicle);
    }

    @Override
    public String deleteVehicle(int vehicleId) throws Exception {
        return vehicleDAO.delete(vehicleId);
    }

    @Override
    public String updateVehicle(VehicleDto vehicleDto) throws Exception {
        Vehicle vehicle = new Vehicle(
                vehicleDto.getVehicle_id(),
                vehicleDto.getType(),
                vehicleDto.getLicensePlate(),
                vehicleDto.getStatus()
        );
        return vehicleDAO.update(vehicle);
    }

    @Override
    public ArrayList<VehicleDto> getAllVehicles() throws Exception {
        ArrayList<VehicleDto> list = new ArrayList<>();
        for (Vehicle v : vehicleDAO.getAll()) {
            list.add(new VehicleDto(
                    v.getVehicle_id(),
                    v.getType(),
                    v.getLicensePlate(),
                    v.getStatus()
            ));
        }
        return list;
    }

    @Override
    public ArrayList<VehicleDto> getAvailableVehicles(String status) throws SQLException {
        return vehicleDAO.getAvailableVehicles(status);
    }

    @Override
    public String getLicensePlateById(int vehicleId) throws SQLException {
        return vehicleDAO.getLicensePlateById(vehicleId);
    }

    @Override
    public Map<String, Integer> getVehicleStatusCount() throws SQLException {
        return vehicleDAO.getVehicleStatusCount();
    }
}
