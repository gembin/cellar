package net.cellar.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;
import net.cellar.core.Node;
import net.cellar.core.command.Result;
import net.cellar.core.control.BasicSwitch;
import net.cellar.core.control.Switch;
import net.cellar.core.control.SwitchStatus;
import net.cellar.core.event.Event;
import net.cellar.core.event.EventProducer;


/**
 * Produces {@code Event}s into the distributed {@code ITopic}.
 *
 * @author iocanel
 */
public class TopicProducer<E extends Event> implements EventProducer<E> {

    public static final String SWITCH_ID = "net.cellar.topic.producer";

    private final Switch eventSwitch = new BasicSwitch(SWITCH_ID);

    private HazelcastInstance instance;
    private ITopic topic;
    private Node node;

    /**
     * Initialization method.
     */
    public void init() {
        if (topic == null) {
            topic = instance.getTopic(Constants.TOPIC);
        }
    }

    /**
     * Destruction method.
     */
    public void destroy() {
    }


    /**
     * Propagates an event into the distributed {@code ITopic}.
     *
     * @param event
     */
    public void produce(E event) {
        if (eventSwitch.getStatus().equals(SwitchStatus.ON) || event.getForce() || event instanceof Result) {
            event.setSourceNode(node);
            topic.publish(event);
        }
    }

    public Switch getSwitch() {
        return eventSwitch;
    }

    public ITopic<? extends Event> getTopic() {
        return topic;
    }

    public void setTopic(ITopic<Event> topic) {
        this.topic = topic;
    }

    public HazelcastInstance getInstance() {
        return instance;
    }

    public void setInstance(HazelcastInstance instance) {
        this.instance = instance;
    }


    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
