package awspock.poc.sqs.example.common.config;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.services.sqs.AmazonSQS;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@EnableJms
public class JmsConfig {

    private final SQSConnectionFactory sqsConnectionFactory;

    public JmsConfig(final AmazonSQS amazonSQS) {
        this.sqsConnectionFactory = new SQSConnectionFactory(new ProviderConfiguration(), amazonSQS);
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        final DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(sqsConnectionFactory);
        factory.setSessionAcknowledgeMode(JmsProperties.AcknowledgeMode.CLIENT.getMode());
        return factory;
    }

    @Bean
    public JmsTemplate defaultJmsTemplate() {
        return new JmsTemplate(sqsConnectionFactory);
    }

}
