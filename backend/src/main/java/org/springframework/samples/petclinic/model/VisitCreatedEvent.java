package org.springframework.samples.petclinic.model;

public class VisitCreatedEvent {

    private final Integer visitId;

    public VisitCreatedEvent(Integer visitId) {
        this.visitId = visitId;
    }

    public Integer getVisitId() {
        return visitId;
    }

    @Override
    public String toString() {
        return "VisitCreatedEvent{" +
            "visitId=" + visitId +
            '}';
    }
}
