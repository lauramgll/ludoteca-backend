package com.ccsw.tutorial.loan.model;

import java.time.LocalDate;

import com.ccsw.tutorial.common.pagination.PageableRequest;

/**
 * @author ccsw
 *
 */
public class LoanSearchDto {

	private PageableRequest pageable;
	private Long idGame;
	private Long idClient;
	private LocalDate date;

	public PageableRequest getPageable() {
		return pageable;
	}

	public void setPageable(PageableRequest pageable) {
		this.pageable = pageable;
	}

	public Long getIdGame() {
		return idGame;
	}

	public void setIdGame(Long idGame) {
		this.idGame = idGame;
	}

	public Long getIdClient() {
		return idClient;
	}

	public void setIdClient(Long idClient) {
		this.idClient = idClient;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}