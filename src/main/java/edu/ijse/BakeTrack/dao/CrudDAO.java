package edu.ijse.BakeTrack.dao;

import java.util.ArrayList;

public interface CrudDAO <T> extends SuperDAO{
    String save(T t) throws Exception;
    String update(T t) throws Exception;
    String delete(int t) throws Exception;
    ArrayList<T> getAll() throws Exception;
}
