package damiancritchfield.sionproxy.server.netty.service;

import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChannelService {

    @Autowired
    private ConcurrentHashMap<Channel, Channel> channelMap;

    public void bindChannel(Channel serverChannel, Channel clientChannel){
        channelMap.put(serverChannel, clientChannel);
    }

    public void unbindByServerChannel(Channel serverChannel){
        channelMap.remove(serverChannel);
    }

    public void unbindByClientChannel(Channel clientChannel){
        for(Channel key : channelMap.keySet()){
            Channel value = channelMap.get(key);
            if(value != null && value.equals(clientChannel)){
                channelMap.remove(key);
            }
        }
    }

    public Channel findClientChannel(Channel serverChannel){
        return channelMap.get(serverChannel);
    }

    public Channel findServerChannel(Channel clientChannel){
        for(Channel key : channelMap.keySet()){
            Channel value = channelMap.get(key);
            if(value != null && value.equals(clientChannel)){
                return key;
            }
        }
        return null;
    }

    @Bean
    public ConcurrentHashMap<Channel, Channel> channelMap(){
        return new ConcurrentHashMap<>();
    }
}
