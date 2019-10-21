package org.soptorshi.repository;

import org.soptorshi.domain.JournalVoucherGenerator;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the JournalVoucherGenerator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JournalVoucherGeneratorRepository extends JpaRepository<JournalVoucherGenerator, Long> {

}
