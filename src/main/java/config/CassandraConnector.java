/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.Session;

/**
 *
 * @author Alberto
 */
public class CassandraConnector {
    
    private static Cluster cluster;
    private static Session session;
    private static CassandraConnector cassandraConnector;
    
    public static CassandraConnector getInstance() {
        if(cassandraConnector == null)
            cassandraConnector = new CassandraConnector();
        return cassandraConnector;
    }

    public CassandraConnector() {
        session = connect("localhost", 9042);
    }
    
    public static Session connect(String node, Integer port) {
        Builder builder = Cluster.builder().addContactPoint(node);
        if (port != null) {
            builder.withPort(port);
        }
        cluster = builder.build();
 
        return cluster.connect();
    }
 
    public Session getSession() {
        return this.session;
    }
 
    public void close() {
        session.close();
        cluster.close();
    }
    
    public static void main(String[] args) {
        
        session.execute("use javafrm");
        
    }
}
