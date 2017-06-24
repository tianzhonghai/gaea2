package com.tim.gaea2.core.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianzhonghai on 2017/6/23.
 */
@Configuration
public class ElasticSearchConfig {
    @Bean
    public TransportClient esClient(){
        List<InetAddress> addrs = new ArrayList<InetAddress>();
        try {
            InetAddress addr = InetAddress.getByName("192.168.11.78");
            addrs.add(addr);
        }catch (UnknownHostException ex){}

        Settings settings = Settings.builder().put("cluster.name", "gaea").build();

        TransportClient client = new PreBuiltTransportClient(settings);

        for (InetAddress addr :addrs) {
            client.addTransportAddress(new InetSocketTransportAddress(addr, 9300));
        }

        return client;
    }
}
