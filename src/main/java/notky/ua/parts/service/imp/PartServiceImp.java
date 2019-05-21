package notky.ua.parts.service.imp;

import notky.ua.parts.models.Part;
import notky.ua.parts.repository.PartRepository;
import notky.ua.parts.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartServiceImp implements PartService {
    private PartRepository repository;

    @Override
    public Part getOne(int id) {
        return repository.getOne(id);
    }

    @Override
    public List<Part> findAll() {
        return repository.findAllByOrderByNameAsc();
    }

    @Override
    public List<Part> findOnlyNecessary() {
        return repository.findByNecessaryOrderByNameAsc(true);
    }

    @Override
    public List<Part> findOther() {
        return repository.findByNecessaryOrderByNameAsc(false);
    }

    @Override
    public List<Part> findByContaining(String name) {
        return repository.findByNameContainingOrderByNameAsc(name);
    }

    @Override
    public void save(Part part) {
        repository.save(part);
    }

    @Override
    public void update(Part part) {
        repository.save(part);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }

    @Autowired
    public void setRepository(PartRepository repository) {
        this.repository = repository;
    }
}
