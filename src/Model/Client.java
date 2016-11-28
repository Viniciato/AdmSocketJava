package Model;

import java.io.Serializable;

/**
 * Created by Nadin on 08/11/16.
 */
public class Client implements Serializable{
    private int room;
    private String name;


    public Client(int room, String name){
        this.room = room;
        this.name = name;

    }

    public String getRoom() {
        return String.valueOf(room);
    }

    public void setRoom(int room) {
        this.room = room;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
