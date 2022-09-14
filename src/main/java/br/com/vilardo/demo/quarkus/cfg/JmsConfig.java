package br.com.vilardo.demo.quarkus.cfg;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class JmsConfig {

    @ConfigProperty(name = "app.jms.broker.url")
    String url;

    @ConfigProperty(name = "app.jms.broker.username")
    String brokerUser;

    @ConfigProperty(name = "app.jms.broker.password")
    String brokerPassword;

    @Produces
    @ApplicationScoped
    @Named("jmsConnectionFactory")
    public PooledConnectionFactory jmsConnectionFactoryProducao() throws UnknownHostException {
		return this.configConnectionFactory(brokerUser,brokerPassword);
	}

	public JmsConnectionFactory getJmsConnectionFactory(String user, String password) throws UnknownHostException {
		final JmsConnectionFactory factory = new JmsConnectionFactory();
		factory.setRemoteURI(url);
		factory.setUsername(user);
		factory.setPassword(password);
		factory.setPopulateJMSXUserID(true);

		String hostname = InetAddress.getLocalHost().getHostName();
		StringBuilder sb = new StringBuilder();
		sb = sb.append(brokerUser).append(hostname);
		factory.setClientIDPrefix(sb.toString());
		
		factory.setValidatePropertyNames(false);
		return factory;
	}

	private PooledConnectionFactory configConnectionFactory(String user, String password) throws UnknownHostException {
		final JmsConnectionFactory jmsConnectionFactory = this.getJmsConnectionFactory(user,password);

		final PooledConnectionFactory factory = new PooledConnectionFactory();
		factory.setIdleTimeout(2 * 60 * 1000);
		factory.setConnectionFactory(jmsConnectionFactory);
		factory.setReconnectOnException(true);
		factory.setMaxConnections(1);

		return factory;
	}
}
