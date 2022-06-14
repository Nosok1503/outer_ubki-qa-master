package ua.ubki.outertest;

import java.time.LocalDate;
import java.util.Objects;

public class InnDTO {
    private String inn;
    private LocalDate date;
    private boolean processed;

    public String getInn() {
        return inn;
    }

    public InnDTO setInn(String inn) {
        this.inn = inn;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public InnDTO setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public boolean isProcessed() {
        return processed;
    }

    public InnDTO setProcessed(boolean processed) {
        this.processed = processed;
        return this;
    }

    @Override
    public String toString() {
        return "innDTO{" +
                "inn='" + inn + '\'' +
                ", date=" + date +
                ", processed=" + processed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InnDTO innDTO = (InnDTO) o;
        return Objects.equals(inn, innDTO.inn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inn);
    }
}
