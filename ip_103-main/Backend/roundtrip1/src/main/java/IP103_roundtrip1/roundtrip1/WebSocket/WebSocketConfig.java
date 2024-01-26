package IP103_roundtrip1.roundtrip1.WebSocket;


import IP103_roundtrip1.roundtrip1.WebSocketSpringboot.ReportChatServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 *


 * What happens here is that the serverendpoint -- in this case it is
 * the /chat endpoint handler is registered with SPRING
 * so that requests to ws:// will be honored.
 */
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
    @Bean
    public ReportChatServer reportChatServerBean() {
        return new ReportChatServer();
    }

    @Bean
    public ChatServer chatServerBean() {
        return new ChatServer();
    }
}