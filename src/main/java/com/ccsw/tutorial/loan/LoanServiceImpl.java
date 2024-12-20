package com.ccsw.tutorial.loan;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;

import jakarta.transaction.Transactional;

/**
 * @author ccsw
 *
 */
@Service
@Transactional
public class LoanServiceImpl implements LoanService {

	@Autowired
	LoanRepository loanRepository;

	@Autowired
	GameService gameService;

	@Autowired
	ClientService clientService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Loan get(Long id) {

		return this.loanRepository.findById(id).orElse(null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<Loan> findPage(LoanSearchDto dto) {
		return loanRepository.findLoansByFilters(dto.getIdGame(), dto.getIdClient(), dto.getDate(),
				dto.getPageable().getPageable());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(Long id, LoanDto dto) {

		Loan loan;

		if (id == null) {
			loan = new Loan();
		} else {
			loan = this.loanRepository.findById(id).orElse(null);
		}

		BeanUtils.copyProperties(dto, loan, "id", "game", "client");

		loan.setGame(gameService.get(dto.getGame().getId()));
		loan.setClient(clientService.get(dto.getClient().getId()));

		validateLoanDates(loan);

		this.loanRepository.save(loan);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(Long id) throws Exception {

		if (this.get(id) == null) {
			throw new Exception("Not exists");
		}

		this.loanRepository.deleteById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Loan> findAll() {
		return (List<Loan>) this.loanRepository.findAll();
	}

	private void validateLoanDates(Loan loan) {
		
		// Validar que el juego no esté prestado en el rango de fechas
		List<Loan> existingLoansByGame = loanRepository.findLoansByGameIdAndDateRange(loan.getGame().getId(), loan.getStartDate(), loan.getEndDate());
		
		if (!existingLoansByGame.isEmpty()) {
			throw new LoanDateConflictException("El juego ya está prestado a otro cliente en el rango de fechas especificado.");
		}

		// Validar que el cliente no tenga más de dos préstamos en un mismo rango de fechas
		List<Loan> existingLoansByClient = loanRepository.findLoansByClientIdAndDateRange(loan.getClient().getId(), loan.getStartDate(), loan.getEndDate());

		for (Loan existingLoan : existingLoansByClient) {
			if (loan.getStartDate().isBefore(existingLoan.getEndDate().plusDays(1))
			 && loan.getEndDate().isAfter(existingLoan.getStartDate().minusDays(1))) {
				throw new LoanDateConflictException("El cliente no puede tener más de dos préstamos activos en un mismo rango de fechas.");
			}
		}
	}
}
