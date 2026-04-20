package lcu.backend.server.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HelpRequestRepository extends JpaRepository<HelpRequest, Integer> {
    List<HelpRequest> findByRequesterUsername(String username);
    List<HelpRequest> findByMediatorUsername(String username);
    List<HelpRequest> findByStatus(String status);
    List<HelpRequest> findByUrgency(String urgency);
    List<HelpRequest> findByType(String type);
    List<HelpRequest> findByLocationIn(List<Location> location);
    List<HelpRequest> findByMediatorIsNull();
}
