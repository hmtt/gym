package uk.co.hmtt.cukes.core.model;

/**
 * Created by swilson on 21/08/16.
 */
public class BookingTable {

    private String name;
    private String displayName;
    private String session;
    private String exclusion;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getExclusion() {
        return exclusion;
    }

    public void setExclusion(String exclusion) {
        this.exclusion = exclusion;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "BookingTable{" +
                "name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", session='" + session + '\'' +
                ", exclusion='" + exclusion + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookingTable that = (BookingTable) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (session != null ? !session.equals(that.session) : that.session != null) return false;
        return exclusion != null ? exclusion.equals(that.exclusion) : that.exclusion == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (session != null ? session.hashCode() : 0);
        result = 31 * result + (exclusion != null ? exclusion.hashCode() : 0);
        return result;
    }
}
