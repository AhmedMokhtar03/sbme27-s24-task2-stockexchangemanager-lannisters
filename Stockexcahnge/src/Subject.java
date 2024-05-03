package Stockexcahnge.src;

public interface Subject {
    void Subsrcribe (Observer observer);
    void unSubsrcribe (Observer observer);
    void notifyAllSubscribers();
}
