package net.cellar.hazelcast;

import com.hazelcast.core.ITopic;
import com.hazelcast.core.MessageListener;
import net.cellar.core.Dispatcher;
import net.cellar.core.Node;
import net.cellar.core.control.BasicSwitch;
import net.cellar.core.control.Switch;
import net.cellar.core.control.SwitchStatus;
import net.cellar.core.event.Event;
import net.cellar.core.event.EventConsumer;


/**
 * Consumes messages from the distributed {@code ITopic} and calls the {@code EventDispatcher}.
 *
 * @author iocanel
 */
public class TopicConsumer<E extends Event> implements EventConsumer<E>, MessageListener<E> {


    public static final String SWITCH_ID = "net.cellar.topic.consumer";

    private final Switch eventSwitch = new BasicSwitch(SWITCH_ID);

    private ITopic topic;
    private Dispatcher dispatcher;
    private Node node;

    /**
     * Initialization method.
     */
    public void init() {
        if (topic != null) {
            topic.addMessageListener(this);
        }
    }

    /**
     * Destruction method.
     */
    public void destroy() {
        if (topic != null) {
            topic.removeMessageListener(this);
        }
    }

    /**
     * Consumes an event form the topic.
     *
     * @param event
     */
    public void consume(E event) {
        //Check if event has a specified destination.
        if (event.getDestination() == null || event.getDestination().contains(node)) {
            //Check is switch is on.
            if (eventSwitch.getStatus().equals(SwitchStatus.ON) || event.getBypassSwitches()) {
                dispatcher.dispatch(event);
            }
        }
    }

    public void onMessage(E message) {
        consume(message);
    }

    public ITopic<Event> getTopic() {
        return topic;
    }

    public void setTopic(ITopic<Event> topic) {
        this.topic = topic;
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public Switch getSwitch() {
        return eventSwitch;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
