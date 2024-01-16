package com.example.springbootjwtauthentication.service.implementations;

import com.example.springbootjwtauthentication.model.Order;
import com.example.springbootjwtauthentication.model.Transporter;
import com.example.springbootjwtauthentication.payload.request.TransporterAnswerRequest;
import com.example.springbootjwtauthentication.service.interfaces.TransporterAnswerListener;
import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Se encarga de buscar el transportador a el pedido que tiene
 * asociado en su variable de instancia, esto lo hace utilizando
 * la lista de transportadores candidatos proporcionada.
 * <p>
 * Utiliza el servicio de firebase para notificar a los transportadores (tambíen se podría utilizar websockets).
 * Cuando se envía una notificación se inicia un temporizador de 5 min el cuál al terminar ejecuta el método onDeadlineCompleted
 * el cuál comprueba si el transportador ha ignorado o no la notificación, si la ha ignorado, entonces notifica al siguiente
 * transportador en la lista, y así sucesivamente hasta que la lista esté vacía.
 */
@Service
@Slf4j
@Scope("prototype")
public class TransporterAssignmentService implements TransporterAnswerListener {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderStatusManagerService orderStatusManagerService;
    private List<Transporter> candidateTransportersList;
    private Transporter lastTransporterNotified;
    private Order order;
    private boolean ignored = true;
    private final Long deadline = 60_000L; // 2 minutes


    /**
     * Inicia el trabajo de busqueda del transportador para el pedido solicitado
     * Se debe ejecutar después de que el pedido haya sido aceptado por el productor
     *
     * @param manager                   El gestor de la asignación de pedidos que se encarga de notificar a los oyentes especificos
     * @param candidateTransportersList La lista de candidatos ponderada a la cuál se le enviaran notificaciones
     * @param order                     El pedido al cuál se le está buscando el transportador
     */
    public void startJob(TransporterAssignmentManager manager, List<Transporter> candidateTransportersList, Order order) throws FirebaseMessagingException {
        this.candidateTransportersList = candidateTransportersList;
        this.order = order;
        manager.subscribe(this, order);
        notifyNextTransporter();
    }


    /**
     * El método mediante el cuál el manager notifica cuando este recibe una respuesta de un transportador
     *
     * @param manager     El gestor de peiddos el cuál nos notificó
     * @param transporter El transportador que respondió la notificación.
     * @param data        La respuesta de este transportador (aceptado o rechazado)
     */
    @Override
    public void update(TransporterAssignmentManager manager, Transporter transporter, TransporterAnswerRequest data) throws FirebaseMessagingException, ParseException {
        this.ignored = false;

        log.info("¿ El transportador acepto el pedido ?: {}", data.isAccepted());
        log.info("¿ El transportor suministrado es igual al anterior notificado ? : {}", transporter.equals(lastTransporterNotified));

        boolean match = transporter.equals(lastTransporterNotified);
        if (match) {
            if (data.isAccepted()) {
                handleAccepted(manager, transporter, data);
            } else {
                handleRejected(transporter);
            }
        } else {
            log.info("El transportador no es el esperado");
        }
    }

    /**
     * Método que se ejecuta cuando el temporizador alcanza los 5  minutos
     * <p>
     * Comprueba la variable de instancia ignored para comprobar si el transportador ignoró la notificación,
     * si ignoró la notificación entonces gestiona el rechazo con el ultimo transportador notificado,
     * si no ignoró la notificación, es decir que respondió  el método no tiene nada mas que hacer,
     * iniciar o parar el contador aquí directa o indirectamente se debe realizar con cuidado para no
     * interferir con la lógica de otros componentes.
     * <p>
     * (Tener en cuenta que cuando el transprotador responde pero rechaza la solicitud la variable ignored
     * se vuelve a poner en true, pero en teoría no deberia entrar aquí dado a que se inicia de nuevo el temporizador)
     */
    private void onDeadlineCompleted() throws FirebaseMessagingException {
        log.warn("Se completo el tiempo limite");
        log.info("¿ Se ignoró la notificacion ?: {}", ignored);

        if (ignored) {
            handleRejected(lastTransporterNotified);
        }
    }

    /**
     * Si el transportador acepta el pedido entonces:
     * Asignarlo al pedido y actualizar el estado del pedido a : TRANSPORTER_ASSIGNED
     */
    private void handleAccepted(TransporterAssignmentManager manager, Transporter transporter, TransporterAnswerRequest data) throws ParseException { //TODO : Cambiar el estado del pedido
        log.info("¿ La orden ya asignado un transportador ? : {} ", order.getTransporter() == null);
        order.setTransporter(transporter);
        order.setEstimatedDeliveryDateFromString(data.getEstimatedDeliveryDate());
        order.setEstimatedPickupDateFromString(data.getEstimatedPickupDate());
        orderService.saveOrder(order);
        orderStatusManagerService.transporterAssigned(order);
        manager.unsubscribe(this, order);
        endTimer();
    }

    private void handleRejected(Transporter transporter) throws FirebaseMessagingException {
        candidateTransportersList.removeIf(t -> t.equals(transporter));
        notifyNextTransporter();
    }


    /**
     * Se encarga de notificar al siguiente transportador en la lista,
     * si la lista tiene transportadores, si la lista no tiene
     * transportadores detiene el temporizador.
     * <p>
     * Para notificar al siguiente transportador hace lo siguiente:
     * <p>
     * Obtiene el primer transportador de la lista y lo guarda en una variable
     * luego envía la notificación mediante el servicio de firebase con el pedido que éste objeto esta manejando
     * una vez enviado se guarda el transportador que fue notificado en la variable de instancia correspondiente al ultimo transportador notificado
     * a continuación se establece la variable de instancia booleada en true la cual indica si el transportador ignoró o no la notificación en el tiempo establecido
     * por último se inicia el temporizador
     */
    private void notifyNextTransporter() throws FirebaseMessagingException {
        if (!candidateTransportersList.isEmpty()) {
            Transporter nextTransporter = candidateTransportersList.get(0);

            notificationService.notifyTransporter(order, nextTransporter);

            lastTransporterNotified = nextTransporter;
            ignored = true;
            startTimer();
        } else {
            log.info("La lista de transportadores candidatos está vacía");
            endTimer();
        }
    }

    private void startTimer() {
        timer = new Timer();
        timer.schedule(new DeadlineTask(), deadline);
    }


    private void endTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }
    private Timer timer;

    private class DeadlineTask extends TimerTask {
        @Override
        public void run() {
            try {
                onDeadlineCompleted();
            } catch (Exception e) { //TODO : No creo que se capture ésta excepción acá
                e.printStackTrace(); // Maneja la excepción de manera adecuada
            }
        }
    }
}
/*
 * TODO:
 *  2 Necesito decidir en dónde se invocará al método startJob rta -> cuando el productor acepta del pedido
 *  1 Necesito probar esta funcionalidad si o si-> PRUEBAS UNITARIAS
 *  3 Una vez decidido en dónde se invocará al método necesito decidir cómo se deben crear los objetos ->
 *  4 Posiblemente el gestor de asignación deba ser un singleton
 * */
