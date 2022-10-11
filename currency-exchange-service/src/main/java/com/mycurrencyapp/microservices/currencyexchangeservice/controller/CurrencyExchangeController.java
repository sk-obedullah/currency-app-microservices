package com.mycurrencyapp.microservices.currencyexchangeservice.controller;


import org.bouncycastle.crypto.RuntimeCryptoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mycurrencyapp.microservices.currencyexchangeservice.model.CurrencyExchange;
import com.mycurrencyapp.microservices.currencyexchangeservice.repository.CurrencyExchangeRepository;

import java.util.*;

@RestController
public class CurrencyExchangeController {

	@Autowired
	private Environment environment;

	@Autowired
	private CurrencyExchangeRepository currencyExchangeRepository;

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retriveExchangeValue(@PathVariable String from, @PathVariable String to) {
		CurrencyExchange currencyExchange = currencyExchangeRepository.findByFromAndTo(from, to);
		
		if(currencyExchange==null) {
			throw new RuntimeCryptoException("unable to find data for "+from+" To "+to);
		}
		currencyExchange.setEnvironment(environment.getProperty("server.port"));
		return currencyExchange;
	}

	@GetMapping("/all-exchanges")
	public List<CurrencyExchange> getAllExchanges() {
		List<CurrencyExchange> findAll = currencyExchangeRepository.findAll();
		return findAll;
	}
}
