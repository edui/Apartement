package id.co.kurindo.apartment.helper;

import id.co.kurindo.apartment.model.Issue;
import id.co.kurindo.apartment.model.Room;

/**
 * Created by DwiM on 5/13/2017.
 */

public class ViewHelper {
    private static ViewHelper instance;
    private Issue issue;

    public static ViewHelper getInstance() {
        return instance;
    }

    private Room room;

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public Issue getIssue() {
        return issue;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
