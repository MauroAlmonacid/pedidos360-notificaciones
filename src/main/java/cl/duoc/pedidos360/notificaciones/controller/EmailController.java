package cl.duoc.pedidos360.notificaciones.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.pedidos360.notificaciones.service.EmailService;

@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmailController {

	private final EmailService emailService;

	public EmailController(EmailService emailService) {
		this.emailService = emailService;
	}

	@PostMapping("/enviar")
	public ResponseEntity<String> enviar(@RequestParam String to,
										 @RequestParam String subject,
										 @RequestParam String body) {
		try {
			emailService.enviarCorreo(to, subject, body);
			return ResponseEntity.ok("¡Correo enviado exitosamente a " + to + "!");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al enviar el correo: " + e.getMessage());
		}
	}
}
