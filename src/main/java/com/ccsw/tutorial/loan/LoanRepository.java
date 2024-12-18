package com.ccsw.tutorial.loan;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ccsw.tutorial.loan.model.Loan;

/**
 * @author ccsw
 *
 */
public interface LoanRepository extends CrudRepository<Loan, Long> {
	@Query("SELECT l FROM Loan l WHERE " +
	           "(:idGame IS NULL OR l.game.id = :idGame) AND " +
	           "(:idClient IS NULL OR l.client.id = :idClient) AND " +
	           "(:fecha IS NULL OR :fecha BETWEEN l.startDate AND l.endDate)")
	    List<Loan> findLoansByFilters(@Param("idGame") Long idGame, 
	                                  @Param("idClient") Long idClient, 
	                                  @Param("fecha") LocalDate fecha);
}	
