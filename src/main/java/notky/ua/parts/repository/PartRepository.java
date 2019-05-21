package notky.ua.parts.repository;

import notky.ua.parts.models.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PartRepository extends JpaRepository<Part, Integer> {
    List<Part> findAllByOrderByNameAsc();
    List<Part> findByNecessaryOrderByNameAsc(boolean necessary);
    List<Part> findByNameContainingOrderByNameAsc(String name);
    void deleteById(@Param("id") int id);
}
