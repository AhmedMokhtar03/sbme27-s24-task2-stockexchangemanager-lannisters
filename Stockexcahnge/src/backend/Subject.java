package backend;

public interface Subject {
    void Subsrcribe (Observer observer);
    void unSubsrcribe (Observer observer);
    void notifyAllSubscribers();
}