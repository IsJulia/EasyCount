import org.apache.kafka.clients.consumer.*;
 
import java.util.Collections;
import java.util.Properties;
 
public class jkafka_demo {
	public static void main(String[] args) throws Exception {
		
		try {
			Properties props = new Properties();
			props.put("bootstrap.servers","192.168.16.152:9092,192.168.16.153:9092,192.168.154:9092");//kafka集群的服务器地址
			props.put("group.id", "groupIdName");
			props.put("enable.auto.commit", "true");
			props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
			props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
			KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
			consumer.subscribe(Collections.singletonList("topicName"));
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(100);
				for (ConsumerRecord<String, String> record : records) {
					System.out.printf("comsumer:>>>>>offset = %d, key= %s , value = %s\n", record.offset(),
							record.key(), record.value());
					String r = record.offset() + "\t" + record.key() + "\t" + record.value();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("when calling kafka output error." + ex.getMessage());
		}
	}
}
