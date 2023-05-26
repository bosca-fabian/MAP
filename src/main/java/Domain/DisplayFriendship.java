package Domain;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;

public class DisplayFriendship {
    private final ObjectProperty<String> user = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> friendship = new SimpleObjectProperty<>();

    public DisplayFriendship(String user, LocalDate friendship) {
        this.user.set(user);
        this.friendship.set(friendship);
    }

    public DisplayFriendship(String user){
        this.user.set(user);
    }

    public String getUser() {
        return user.get();
    }

    public ObjectProperty<String> userProperty() {
        return user;
    }

    public void setUser(String user) {
        this.user.set(user);
    }

    public LocalDate getFriendship() {
        return friendship.get();
    }

    public ObjectProperty<LocalDate> friendshipProperty() {
        return friendship;
    }

    public void setFriendship(LocalDate friendship) {
        this.friendship.set(friendship);
    }
}
