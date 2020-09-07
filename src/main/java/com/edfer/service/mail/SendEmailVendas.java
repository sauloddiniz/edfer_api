package com.edfer.service.mail;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.edfer.model.agenda.Agenda;
import com.edfer.repository.agenda.AgendaRepository;

@Configuration
@EnableScheduling
public class SendEmailVendas {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private AgendaRepository repository;

	@Scheduled(cron = "00 11 00 ? * MON-SAT")
	private void senEmail() {

		LocalDateTime dateNow = LocalDateTime.now();

		List<Agenda> list = repository
				.findAllByDataRetornoLessThanEqualAndAndDataRetornoGreaterThanEqual(dateNow.plusDays(3), dateNow);

		if (!list.isEmpty()) {

			DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
			DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

			for (Agenda agenda : list) {

				LocalDateTime date = LocalDateTime.parse(agenda.getDataRetorno().toString(), inputFormatter);
				String localDateTime = outputFormatter.format(date);

				MimeMessage mailMessage = mailSender.createMimeMessage();

				MimeMessageHelper helper = new MimeMessageHelper(mailMessage);
				try {
					String[] lista = { agenda.getUsuario().getEmail(), "vendas4@edfer.com.br" };
					helper.setTo(lista);
					helper.setSubject(agenda.getUsuario().getUsuario() + ": lembrete para efetuar um retorno");
					helper.setText("<p>Favor entrar em contato com seu cliente: <b>" + agenda.getCliente()
							+ "</b> no dia: " + localDateTime + "</p> <p> E-mail automático favor não responder</p>",
							true);
					mailSender.send(mailMessage);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
