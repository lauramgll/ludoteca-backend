package com.ccsw.tutorial.loan;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;

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
	public List<Loan> find(Long idGame, Long idClient, LocalDate date) {
	    return loanRepository.findLoansByFilters(idGame, idClient, date);
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

        this.loanRepository.save(loan);
    }
}
