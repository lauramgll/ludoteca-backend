package com.ccsw.tutorial.client;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author ccsw
 *
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

	@Autowired
	ClientRepository clientRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Client get(Long id) {

		return this.clientRepository.findById(id).orElse(null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Client> findAll() {

		return (List<Client>) this.clientRepository.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(Long id, ClientDto dto) {

		// Validar que el nombre no exista
		Optional<Client> existingClient = clientRepository.findByName(dto.getName());

		if (existingClient.isPresent() && (id == null || !existingClient.get().getId().equals(id))) {
			throw new IllegalArgumentException("El nombre del cliente ya existe: " + dto.getName());
		}

		Client client;

		if (id == null) {
			client = new Client();
		} else {
			client = this.get(id);
		}

		client.setName(dto.getName());

		this.clientRepository.save(client);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(Long id) throws Exception {

		if (this.get(id) == null) {
			throw new Exception("Not exists");
		}

		this.clientRepository.deleteById(id);
	}
}