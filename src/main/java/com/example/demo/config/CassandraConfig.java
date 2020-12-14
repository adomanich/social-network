package com.example.demo.config;

import com.example.demo.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.Objects;

@Configuration
@EnableCassandraRepositories(basePackageClasses = {UserRepository.class})
public class CassandraConfig extends AbstractCassandraConfiguration {
    private static final String KEYSPACE_NAME = "cassandraTest";
    private static final String CONTACT_POINTS = "127.0.0.1";
    private static final int PORT = 9042;

    @Override
    protected String getKeyspaceName() {
        return KEYSPACE_NAME;
    }

    @Override
    protected String getContactPoints() {
        return CONTACT_POINTS;
    }

    @Override
    protected int getPort() {
        return PORT;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.RECREATE_DROP_UNUSED;
    }

    @Bean
    public CassandraOperations operations() throws Exception {
        return new CassandraTemplate(Objects.requireNonNull(session().getObject()), new MappingCassandraConverter(new BasicCassandraMappingContext()));
    }

    @Bean
    public CqlSessionFactoryBean session() {

        CqlSessionFactoryBean session = new CqlSessionFactoryBean();
        session.setContactPoints(CONTACT_POINTS);
        session.setKeyspaceName(KEYSPACE_NAME);
        return session;
    }
}
