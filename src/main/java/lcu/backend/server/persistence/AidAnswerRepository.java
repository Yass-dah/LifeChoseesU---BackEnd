package lcu.backend.server.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AidAnswerRepository extends JpaRepository<AidAnswer, Integer> {
    Optional<AidAnswer> findByRequest_Id(Integer requestId);
    boolean existsByRequest_Id(Integer requestId);
}