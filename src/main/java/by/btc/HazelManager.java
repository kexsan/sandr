package by.btc;

import ch.qos.logback.classic.Level;
import com.hazelcast.config.Config;
import com.hazelcast.core.*;

import org.springframework.stereotype.Component;

@Component
public class HazelManager {

    public HazelManager() {


        System.out.println("tttt");
       // start();


    }

    public void start()
    {

        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
        ITopic<String> terminalTopic = getTerminalTopic(hazelcastInstance);
        terminalTopic.publish("asdadadadadadadaaadad");
        terminalTopic.addMessageListener(new MessageListener<String>()
        {
            @Override
            public void onMessage(Message<String> message)
            {
                System.out.println("terminal-call-event : "  + message.getMessageObject());
            }
        });


        ITopic<String> callTopic = getCallTopic(hazelcastInstance);
        callTopic.addMessageListener(new MessageListener<String>()
        {
            @Override
            public void onMessage(Message<String> message)
            {
                System.out.println("avaya-call-event : "  + message.getMessageObject());
            }
        });

    }

    private ITopic<String> getTerminalTopic(HazelcastInstance hazelcastInstance)
    {
        return hazelcastInstance.getReliableTopic("terminal-call-event");
    }

    private ITopic<String> getCallTopic(HazelcastInstance hazelcastInstance)
    {
        return hazelcastInstance.getReliableTopic("avaya-call-event");
    }

}
