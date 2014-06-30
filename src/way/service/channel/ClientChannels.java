package way.service.channel;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;
public class ClientChannels{
	private static ConcurrentHashMap<String, Channel> clientChannels = new ConcurrentHashMap<String, Channel>();
	
	public static void setChannel(String id, Channel channel){
		clientChannels.put(id, channel);
	}
	
	public static boolean contains(String id){
		return clientChannels.containsKey(id);
	}
	
	public static void write(String id, Object msg){
		if(contains(id)){
			getChannel(id).writeAndFlush(msg);
		}
	}
	public static Channel getChannel(String id){
		if(clientChannels.containsKey(id)){
			return clientChannels.get(id);
		}
		return null;
	}
	
	public static void remove(String id){
		clientChannels.remove(id);
	}
	
	public static int getSize(){
		return clientChannels.size();
	}
	
	public static void refresh(){
		System.out.println("begin refresh");
		int start = getSize();
		for(Iterator<String> it = clientChannels.keySet().iterator(); it.hasNext();){
			String key = it.next();
			Channel channel = getChannel(key);
			if(!channel.isOpen()){
				remove(key);
			}
		}
		System.out.println("finish refresh! cleaned " + (start - getSize()) + " channels");
	}
}