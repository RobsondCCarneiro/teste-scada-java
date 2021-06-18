package com.caern.scadabr.test.config;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.caern.scadabr.test.entities.Dispositivos;
import com.caern.scadabr.test.entities.ScadaBR;
import com.caern.scadabr.test.repositories.DispositivosRepositories;
import com.caern.scadabr.test.repositories.ScadaBRRepositories;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner{

	@Autowired
	private ScadaBRRepositories scadaBRRepositories;
	
	@Autowired
	private DispositivosRepositories dispositivosRepositories;

	@Override
	public void run(String... args) throws Exception {
		Integer moduloID;
		String date;
		Double pressure;
		Integer counter;
		String timeZone;
		
		Date dataAtual;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		List<ScadaBR> lista;
		lista = new ArrayList<ScadaBR>();
		
		List<Dispositivos> listaDispositivos;
		listaDispositivos = new ArrayList<Dispositivos>();
		
		
		Random gerador = new Random();
		
		int i=1;
		while(true) {
			moduloID = i;
			pressure = gerador.nextDouble()*10;
			BigDecimal bd = new BigDecimal(pressure).setScale(2, RoundingMode.HALF_EVEN); //Arrendondando 'pressure' para duas casas decimais 
			pressure = bd.doubleValue(); //Variavel 'pressure' recebendo o proprio valor arrendondado
			counter = gerador.nextInt(500);
			dataAtual = new Date();
			date = sdf.format(dataAtual);
			timeZone = TimeZone.getDefault().getID();
			ScadaBR scadaBR = new ScadaBR(moduloID, date, pressure, counter, timeZone);
			lista.add(scadaBR);
			
			Dispositivos dispositivos = new Dispositivos(moduloID, date, pressure, counter, timeZone);
			listaDispositivos.add(dispositivos);
			
			scadaBRRepositories.saveAll(lista);
			dispositivosRepositories.saveAll(listaDispositivos);
			
			i++;
			if(i==3) {
				i=1;
				TimeUnit.SECONDS.sleep(5);
				lista.clear();
				scadaBRRepositories.deleteAll();
			}
		}
	}
}
