package cl.duoc.pedidos360.notificaciones.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

	private final JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String from;

	public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void enviarCorreo(String to, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		message.setFrom(from);
		mailSender.send(message);
		logger.info("Correo enviado exitosamente a {} con asunto '{}'", to, subject);
	}

	public void enviarCorreoConAdjunto(String to, String subject, String htmlContent, String rutaArchivo) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(htmlContent, true);

			FileSystemResource file = new FileSystemResource(rutaArchivo);
			helper.addAttachment(file.getFilename(), file);

			mailSender.send(message);
			logger.info("Correo con adjunto enviado exitosamente a {}", to);
		} catch (Exception e) {
			logger.error("Error al enviar correo con adjunto a {}: {}", to, e.getMessage(), e);
			throw new RuntimeException("Error al enviar el correo con adjunto: " + e.getMessage(), e);
		}
	}
}
