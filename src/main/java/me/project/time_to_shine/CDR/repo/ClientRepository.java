package me.project.time_to_shine.CDR.repo;

import me.project.time_to_shine.CDR.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
