package com.ccsw.tutorial.loan;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author ccsw
 *
 */
@Tag(name = "Loan", description = "API of Loan")
@RequestMapping(value = "/loan")
@RestController
@CrossOrigin(origins = "*")
public class LoanController {

	@Autowired
	LoanService loanService;
	
	@Autowired
	ModelMapper mapper;
	
	/**
	 * Método para recuperar una lista de {@link Loan}
	 *
	 * @param idGame   PK del game
	 * @param idClient PK del client
	 * @param date     Fecha del préstamo
	 * @return {@link List} de {@link LoanDto}
	 */
	@Operation(summary = "Find", description = "Method that return a filtered list of Loans")
	@RequestMapping(path = "", method = RequestMethod.GET)
	public List<LoanDto> find(@RequestParam(value = "idGame", required = false) Long idGame,
	                         @RequestParam(value = "idClient", required = false) Long idClient,
	                         @RequestParam(value = "date", required = false) LocalDate date) {

	    List<Loan> loans = loanService.find(idGame, idClient, date);

	    return loans.stream().map(e -> mapper.map(e, LoanDto.class)).collect(Collectors.toList());
	}
	
	/**
     * Método para crear o actualizar un {@link Loan}
     *
     * @param id PK de la entidad
     * @param dto datos de la entidad
     */
    @Operation(summary = "Save or Update", description = "Method that saves or updates a Loan")
    @RequestMapping(path = { "", "/{id}" }, method = RequestMethod.PUT)
    public void save(@PathVariable(name = "id", required = false) Long id, @RequestBody LoanDto dto) {

        loanService.save(id, dto);
    }
}
