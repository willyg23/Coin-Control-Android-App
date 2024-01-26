package IP103_roundtrip1.roundtrip1.Model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String reporterUsername;

    public Report(String reporterUsername, String reportedMessage, String details) {
    }

    public String getReporterUsername() {
        return reporterUsername;
    }
    public void setReporterUsername(String reporterUsername) {
        this.reporterUsername = reporterUsername;
    }
    public Report() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
