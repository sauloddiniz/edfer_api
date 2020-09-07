package com.edfer.service.mail;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.edfer.model.enuns.TipoServico;
import com.edfer.model.logistica.Manutencao;
import com.edfer.repository.logistica.ManutencaoRepository;

@Configuration
@EnableScheduling
public class SendEmailHodometroAndDate_Valid {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private ManutencaoRepository repository;

	@Scheduled(cron = "00 00 12 ? * MON-SAT")
//	@Scheduled(cron = "00 * 21 ? * *")
	private void senEmailVencimentoHodometro() {
	
		List<Manutencao> list = repository.findAllManutencaoByHodometro();
		System.out.println(list.size());
		if (!list.isEmpty()) {

			for (Manutencao manutencao : list) {

				Long km = (manutencao.getValidadeHodometro() - manutencao.getVeiculo().getHodometro());

				MimeMessage mailMessage = mailSender.createMimeMessage();

				MimeMessageHelper helper = new MimeMessageHelper(mailMessage);
				String servicos = "";
				for (TipoServico servico : manutencao.getTipoServico()) {
					servicos += servico.getDescricao() + ",";
				}

				try {
					String[] lista = { "sauloddiniz@gmail.com" };
					helper.setTo(lista);
					helper.setSubject("Manutenção proxima ao vencimento: " + manutencao.getVeiculo().getModelo() + " " + manutencao.getVeiculo().getPlaca());
					helper.setText("<p>Falta: " + km + " Km para o vencimento dos serviços: " + servicos + "<p>  Observação: " + manutencao.getObservacao() + "</p>", true);
					mailSender.send(mailMessage);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Scheduled(cron = "00 20 12 ? * MON-SAT")
	private void senEmailManutencaoVencida() {
	
		DateTimeFormatter localDateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		List<LocalDate> listDate = new ArrayList<LocalDate>();
		for(int x = 0; x<7;  x++) {
			listDate.add(LocalDate.now().plusDays((x)));
		}
	
		List<Manutencao> list  = repository.findByValidadeManutencaoIn(listDate);
	
		if (!list.isEmpty()) {

			for (Manutencao manutencao : list) {
				MimeMessage mailMessage = mailSender.createMimeMessage();

				MimeMessageHelper helper = new MimeMessageHelper(mailMessage);
				String servicos = "";
				for (TipoServico servico : manutencao.getTipoServico()) {
					servicos += servico.getDescricao() + ",";
				}

				try {
					String[] lista = { "sauloddiniz@gmail.com" };
					helper.setTo(lista);
					helper.setSubject("Manutenção com data proxima ao vencimento: " + manutencao.getVeiculo().getPlaca());
					helper.setText("  Manutenção do veiculo: " + manutencao.getVeiculo().getModelo() + ""
							+ " " + manutencao.getVeiculo().getPlaca() + "<p> Validade Manutenção: " + localDateFormat.format(manutencao.getValidadeManutencao())
							+ "</p> Observação: " + manutencao.getObservacao() + "<p> Serviços: " + servicos + "</p>", true);
					mailSender.send(mailMessage);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
