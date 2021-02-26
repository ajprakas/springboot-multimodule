package com.ajay.example.mongo;


import com.hazelcast.client.HazelcastClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DBService
{
    public static void main( String[] args )
    {
        HazelcastClient.newHazelcastClient();
        System.out.println( "DB Sever started......" );
    }
}
