package com.nelioalves.cursomc.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.nelioalves.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired 
	private JavaMailSender javaMailSender;
	
	@Value("${default.email.sender}")
	private String defaultSender;
	
	@Override
	public void sendOrderConfirmationEmail(Pedido pedido) {
		
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(pedido);
		sendEmail(sm);
	}
		
	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido pedido) {
		
		MimeMessage mm;
		
		try {
			mm = prepareMimeMessageFromPedido(pedido);
			sendHtmlEmail(mm);
		} 
		catch (MessagingException e) {
			sendOrderConfirmationEmail(pedido);
		}
		
	}
	

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {

		SimpleMailMessage sm = new SimpleMailMessage();
		
		sm.setTo(pedido.getCliente().getEmail());
		sm.setFrom(defaultSender);
		sm.setSubject("Pedido " + pedido.getId() + " Confirmado. ");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(pedido.toString());
		
		return sm;
	}
	
	protected MimeMessage prepareMimeMessageFromPedido(Pedido pedido) throws MessagingException {
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		
		mmh.setTo(pedido.getCliente().getEmail());
		mmh.setFrom(defaultSender);
		mmh.setSubject("Pedido " + pedido.getId() + " Confirmado. ");
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplatePedido(pedido));
		
		return mimeMessage;
		
	}
	
	protected String htmlFromTemplatePedido(Pedido pedido) {
		
		Context ctx = new Context();
		ctx.setVariable("pedido", pedido);

		return templateEngine.process("/email/ConfirmacaoPedido", ctx);
	}
	
}
