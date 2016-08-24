package com.project3;
import java.net.Authenticator;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;

import org.glassfish.jersey.jackson.JacksonFeature;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.project3.FleetArrivedResponse.FleetArrivedResult.Arrival;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

// Inspired by http://stackoverflow.com/questions/14458450/what-to-use-instead-of-org-jboss-resteasy-client-clientrequest
public class FleetArrivals {
	static class Task extends TimerTask {
		private Client client;
		public FleetArrivedResponse getFleetArrivedResponse() {
			Invocation.Builder bldr = client.target("http://flightxml.flightaware.com/json/FlightXML2/FleetArrived?fleet=AAL&howMany=3&offset=0").request("application/json");
			return bldr.get(FleetArrivedResponse.class);
		}

		public FlightInfoExResponse getFlightInfoExResponse(String ident, int departureTime) {
			String uri= String.format("http://flightxml.flightaware.com/json/FlightXML2/FlightInfoEx?ident=%s@%d&howMany=1&offset=0", ident, departureTime);
			Invocation.Builder bldr = client.target(uri).request("application/json");
			return bldr.get(FlightInfoExResponse.class);

		}

		public Task() {
			client = ClientBuilder.newClient();
			// enable POJO mapping using Jackson - see
			// https://jersey.java.net/documentation/latest/user-guide.html#json.jackson
			client.register(JacksonFeature.class); 
		}

		@Override
		public void run() {
			// Adapted from http://hortonworks.com/hadoop-tutorial/simulating-transporting-realtime-events-stream-apache-kafka/
			Properties props = new Properties();
			props.put("metadata.broker.list", "sandbox.hortonworks.com:6667");
			props.put("zk.connect", "localhost:2181");
			//	        props.put("metadata.broker.list", "hadoop-m.c.mpcs53013-2015.internal:6667");
			//	        props.put("zk.connect", "hadoop-w-1.c.mpcs53013-2015.internal:2181,hadoop-w-0.c.mpcs53013-2015.internal:2181,hadoop-m.c.mpcs53013-2015.internal:2181");
			props.put("serializer.class", "kafka.serializer.StringEncoder");
			props.put("request.required.acks", "1");

			String TOPIC = "spertus-flight-events";
			ProducerConfig config = new ProducerConfig(props);

			Producer<String, String> producer = new Producer<String, String>(config);
			FleetArrivedResponse response = getFleetArrivedResponse();
			if(response == null || response.getFleetArrivedResult() == null)
				return;
			ObjectMapper mapper = new ObjectMapper();

			for(Arrival arrival : response.getFleetArrivedResult().getArrivals()) {
				KeyedMessage<String, String> data;
				try {
					data = new KeyedMessage<String, String>
					(TOPIC, 
					 mapper.writeValueAsString(getFlightInfoExResponse(arrival.getIdent(), arrival.getActualdeparturetime())));
					producer.send(data);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
	public static class CustomAuthenticator extends Authenticator {

		// Called when password authorization is needed
		protected PasswordAuthentication getPasswordAuthentication() {

			// Get information about the request
			String prompt = getRequestingPrompt();
			String hostname = getRequestingHost();
			InetAddress ipaddr = getRequestingSite();
			int port = getRequestingPort();

			String username = "Put Your Username here"; // TODO
			String password = "Put your password here"; // TODO

			// Return the information (a data holder that is used by Authenticator)
			return new PasswordAuthentication(username, password.toCharArray());

		}

	}



	public static void main(String[] args) {
		Authenticator.setDefault(new CustomAuthenticator());
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new Task(), 0, 60*1000);
	}
}
