package lcu.backend.server.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HelpRequestRepository extends JpaRepository<HelpRequest, Integer> {

}
