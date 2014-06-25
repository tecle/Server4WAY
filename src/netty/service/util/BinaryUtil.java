package netty.service.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BinaryUtil {
	private static final String[] charset = { "UTF-8", "GBK", "GB2312",
			"ISO-8859-1" };
	public static final String PING = "ping";
	public static ByteBuf encode(byte encode, Map<String, String> values) {
		ByteBuf buf = Unpooled.buffer();
		try {
			for (Iterator<String> it = values.keySet().iterator(); it.hasNext();) {
				String key = it.next();
				String value = values.get(key);
				byte[] keyB = key.getBytes(charset[encode]);
				byte[] valueB = value.getBytes(charset[encode]);
				buf.writeInt(keyB.length);
				buf.writeBytes(keyB);
				buf.writeInt(valueB.length);
				buf.writeBytes(valueB);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buf;
	}

	public static Map<String, String> decode(byte encode, ByteBuf dataBuffer) {
		Map<String, String> values = new HashMap<String, String>();
		int keyl, valuel;
		ByteBuf keyB, valueB;
		try {
			while (dataBuffer.readableBytes() > 0) {
				keyl = dataBuffer.readInt();
				keyB = dataBuffer.readBytes(keyl);
				valuel = dataBuffer.readInt();
				valueB = dataBuffer.readBytes(valuel);
				String key = new String(keyB.array(), charset[encode]);
				String value = new String(valueB.array(), charset[encode]);
				values.put(key, value);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return values;
	}

	public static String getClientIp(ChannelHandlerContext ctx) {
		InetSocketAddress addr = (InetSocketAddress) ctx.channel()
				.remoteAddress();
		Inet4Address net4 = (Inet4Address) addr.getAddress();
		String ip = net4.getHostAddress();
		int port = addr.getPort();
		return ip + ":" + port;
	}

}