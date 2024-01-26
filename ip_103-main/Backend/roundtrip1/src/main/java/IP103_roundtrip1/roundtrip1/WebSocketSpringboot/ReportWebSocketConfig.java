//package IP103_roundtrip1.roundtrip1.WebSocketSpringboot;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.server.standard.ServerEndpointExporter;
//
///**
// *
// * What happens here is that the serverendpoint -- in this case it is
// * the /chat endpoint handler is registered with SPRING
// * so that requests to ws:// will be honored.
// */
//@Configuration
//public class ReportWebSocketConfig {
//    @Bean
//    public ServerEndpointExporter ReportserverEndpointExporter(){
//        return new ServerEndpointExporter();
//    }
//}