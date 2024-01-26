package IP103_roundtrip1.roundtrip1.WebSocketSpringboot;

import java.io.IOException;
import java.util.*;


import IP103_roundtrip1.roundtrip1.Model.Report;
import IP103_roundtrip1.roundtrip1.Services.UserServices;
import IP103_roundtrip1.roundtrip1.repository.UserRepo;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Represents a WebSocket chat server for handling real-time communication
 * between users. Each user connects to the server using their unique
 * username.
 *
 * This class is annotated with Spring's `@ServerEndpoint` and `@Component`
 * annotations, making it a WebSocket endpoint that can handle WebSocket
 * connections at the "/chat/{username}" endpoint.
 *
 * Example URL: ws://localhost:8080/chat/username
 *
 * The server provides functionality for broadcasting messages to all connected
 * users and sending messages to specific users.
 */
@ServerEndpoint("/chat/{username}")
@Component
public class ReportChatServer {


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserServices userServices;

    // Store all socket session and their corresponding username
    // Two maps for the ease of retrieval by key
    private static Map <Session, String > sessionUsernameMap = new Hashtable < > ();
    private static Map < String, Session > usernameSessionMap = new Hashtable < > ();


    private Map<String, List<Report>> reportStorage = new HashMap<>();

    // server side logger
    private final Logger logger = LoggerFactory.getLogger(ReportChatServer.class);

    /**
     * This method is called when a new WebSocket connection is established.
     *
     * @param session represents the WebSocket session for the connected user.
     * @param username username specified in path parameter.
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException {

        // server side log
        logger.info("[onOpen] " + username);

        //initialize ADMIN as the first user
        sessionUsernameMap.put(session, "ADMIN");

        usernameSessionMap.put("ADMIN", session);

        //Checks if another admin is trying to join
        if (!username.equals("ADMIN")) {
            // map current session with username
            sessionUsernameMap.put(session, username);

            // map current username with session
            usernameSessionMap.put(username, session);

            // send to the user joining in
            sendMessageToPArticularUser(username, "Welcome to the chat server, " + username);

            // send initial message from admin to the user
            sendMessageToPArticularUser(username, "Hello, " + username + ". Welcome to the chat.");

            // send to everyone in the chat
            sendMessageToPArticularUser("ADMIN", "User: " + username + " has Joined the Chat");
        } else {
            // map admin session
            sessionUsernameMap.put(session, "ADMIN");
            usernameSessionMap.put("ADMIN", session);
        }
    }

    /**
     * Handles incoming WebSocket messages from a client.
     *
     * @param session The WebSocket session representing the client's connection.
     * @param message The message received from the client.
     */
    @OnMessage
    public void onMessage(Session session, String message) throws IOException {

        // get the username by session
        String username = sessionUsernameMap.get(session);

        // server side log
        logger.info("[onMessage] " + username + ": " + message);

        // Direct message to a user using the format "@username <message>"

            if (message.startsWith("/report")) { // Report command
                String advisorName = message.substring(8).trim(); // Extract the advisor's name
                String reportMessage = "User " + username + " is reporting their advisor, " + advisorName;
                sendMessageToPArticularUser("ADMIN", reportMessage); // Send the report to the admin
                sendMessageToPArticularUser(username, "Your report has been sent to the admin."); // Notify the user
            } else { // Message to whole chat
                sendMessageToPArticularUser(username, "Please start method with /report to report your advisor");
            }


    }

    /**
     * Store a report for a specific message.
     *
     * @param reporterUsername The username of the user who reported the message.
     * @param reportedMessage  The reported message.
     * @param details          Additional details about the report.
     */
    public void storeReport(String reporterUsername, String reportedMessage, String details) {
        Report report = new Report(reporterUsername, reportedMessage, details);
        // Use the reported message as the key to organize reports
        reportStorage.computeIfAbsent(reportedMessage, k -> new ArrayList<>()).add(report);
    }

    /**
     * Handles the closure of a WebSocket connection.
     *
     * @param session The WebSocket session that is being closed.
     */
    @OnClose
    public void onClose(Session session) throws IOException {

        // get the username from session-username mapping
        String username = sessionUsernameMap.get(session);

        // server side log
        logger.info("[onClose] " + username);

        // remove user from memory mappings
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);

        // send the message to chat
        broadcast(username + " disconnected");
    }

    /**
     * Handles WebSocket errors that occur during the connection.
     *
     * @param session   The WebSocket session where the error occurred.
     * @param throwable The Throwable representing the error condition.
     */
    @OnError
    public void onError(Session session, Throwable throwable) {

        // get the username from session-username mapping
        String username = sessionUsernameMap.get(session);

        // do error handling here
        logger.info("[onError]" + username + ": " + throwable.getMessage());
    }

    /**
     * Sends a message to a specific user in the chat (DM).
     *
     * @param username The username of the recipient.
     * @param message  The message to be sent.
     */
    private void sendMessageToPArticularUser(String username, String message) {
        try {
            usernameSessionMap.get(username).getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.info("[DM Exception] " + e.getMessage());
        }
    }

    /**
     * Broadcasts a message to all users in the chat.
     *
     * @param message The message to be broadcasted to all users.
     */
    private void broadcast(String message) {
        sessionUsernameMap.forEach((session, username) -> {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.info("[Broadcast Exception] " + e.getMessage());
            }
        });
    }
}