package cl.duoc.pedidos360.notificaciones.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import cl.duoc.pedidos360.notificaciones.dto.EventoPedidoCreado;
import cl.duoc.pedidos360.notificaciones.service.EmailService;

@Component
public class NotificacionConsumer {

	private static final Logger logger = LoggerFactory.getLogger(NotificacionConsumer.class);

	private final EmailService emailService;

	public NotificacionConsumer(EmailService emailService) {
		this.emailService = emailService;
	}

	@RabbitListener(queues = "cola.notificaciones")
	public void recibirPedidoCreado(EventoPedidoCreado evento) {
		try {
			logger.info("Pedido recibido desde RabbitMQ -> ID: {}, Total: {}", evento.getIdOrden(), evento.getTotal());

			String asunto = "Confirmación de Compra - Pedido #" + evento.getIdOrden();
			String cuerpo = "¡Hola!\n\nHemos recibido tu orden con éxito.\nDetalle de compra:\n- N° de Orden: "
					+ evento.getIdOrden() + "\n- Total pagado: $" + evento.getTotal()
					+ "\n\n¡Gracias por comprar en Pedidos360!";

			emailService.enviarCorreo(evento.getClienteEmail(), asunto, cuerpo);
			logger.info("Notificación enviada para el pedido #{}", evento.getIdOrden());
		} catch (Exception e) {
			logger.error("Error al procesar el pedido recibido: {}", e.getMessage(), e);
		}
	}
}
