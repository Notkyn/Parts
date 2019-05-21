package notky.ua.parts.service;

import notky.ua.parts.models.Part;

import java.util.List;

public interface PartService {
    Part getOne(int id);
    void save(Part part);
    void update(Part part);
    void delete(int id);
    List<Part> findAll();
    List<Part> findOnlyNecessary();
    List<Part> findOther();
    List<Part> findByContaining(String name);
}
