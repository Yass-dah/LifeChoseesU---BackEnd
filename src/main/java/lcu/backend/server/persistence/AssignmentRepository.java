package lcu.backend.server.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {
    List<Assignment> findByMediator_Username(String username);
    Optional<Assignment> findByRequest_Id(Integer requestId);
    List<Assignment> findByType(String type);
    boolean existsByRequest_Id(Integer requestId);
}