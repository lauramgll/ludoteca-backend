package com.ccsw.tutorial.loan;

import java.time.LocalDate;
import java.util.List;

import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;

/**
 * @author ccsw
 *
 */
public interface LoanService {
	
    /**
     * Recupera los préstamos filtrando opcionalmente por juego, cliente y/o fecha
     *
     * @param idGame    PK del juego
     * @param idClient  PK del cliente
     * @param date      fecha del préstamo
     * @return {@link List} de {@link Loan}
     */
    List<Loan> find(Long idGame, Long idClient, LocalDate date);
	
    /**
     * Guarda o modifica un préstamo, dependiendo de si el identificador está o no informado
     *
     * @param id PK de la entidad
     * @param dto datos de la entidad
     */
    void save(Long id, LoanDto dto);
}
