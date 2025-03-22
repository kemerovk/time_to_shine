package me.project.time_to_shine.CDR.repo;

import me.project.time_to_shine.CDR.model.ReportSDR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SDRRepository extends JpaRepository<ReportSDR, Integer> {
}
