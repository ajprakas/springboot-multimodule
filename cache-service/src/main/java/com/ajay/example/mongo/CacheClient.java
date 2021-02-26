package com.ajay.example.mongo;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

public class CacheClient {

    private static final String mapName = "users";

    private static final String StringS = "Strings";

    private HazelcastInstance client = HazelcastClient.newHazelcastClient();

    public String put(String key, String String){
        IMap<String, String> map = client.getMap(mapName);
        return map.putIfAbsent(key, String);
    }

    public String get(String key){
        IMap<String, String> map = client.getMap(StringS);
        return map.get(key);
    }

    public List<String> getAll(){
        return Arrays.asList(new String[]{"Ajay", "Deepali"});
    }


}
