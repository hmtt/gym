package uk.co.hmtt.gym.app.model;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class BookingForm {

    private String exclusionDate;
    private boolean booked;

    public String getExclusionDate() {
        return exclusionDate;
    }

    public void setExclusionDate(String exclusionDate) {
        this.exclusionDate = exclusionDate;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }
}
