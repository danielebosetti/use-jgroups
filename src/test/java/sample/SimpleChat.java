package sample;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ObjectMessage;
import org.jgroups.Receiver;
import org.jgroups.View;
import org.jgroups.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleChat implements Receiver {
	
	private static final Logger LOG = LoggerFactory.getLogger(SimpleChat.class);
	
	JChannel channel;
	String user_name = System.getProperty("user.name", "n/a");

	private void start() throws Exception {
		channel = new JChannel();
		channel.setReceiver(this);
		channel.connect("ChatCluster");
		
	    channel.getState(null, 10000);
	    eventLoop();
	    channel.close();
	}

	private void eventLoop() {
		for(int i=0;i<1_000;i++) {
			try {
				LOG.info("sending {}", i);
	            Message msg=new ObjectMessage(null, "val-"+i);
				channel.send(null, msg);
				Thread.sleep(5_000);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
			
	}
	public void viewAccepted(View new_view) {
	    LOG.info("** view: {}", new_view);
	}
	
	@Override
	public void getState(OutputStream output) throws Exception {
		//synchronized(state)
		{
	        Util.objectToStream("state-"+System.currentTimeMillis(), new DataOutputStream(output));
	    }
	}
	
	@Override
	public void setState(InputStream input) throws Exception {
		Object o = Util.objectFromStream(new DataInputStream(input));
		LOG.info("setState: o={}", o);
	}
	
	@Override
	public void receive(Message msg) {
		LOG.info("receive: src={} dest={}", msg.getSrc(), msg.getDest());
	}

	public static void main(String[] args) throws Exception {
		new SimpleChat().start();
	}
}