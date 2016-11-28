package Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Nadin on 21/11/16.
 */
public class Conversation implements Serializable {
    private String clientName;
    private ArrayList<String> history = new ArrayList<>();
    private String status = "Em Aberto";
    private int room;

    public Conversation(String client, int room){
        this.clientName=client;
        this.room = room;
    }

    public void addMessage(String message){
        history.add(message);
    }

    public void statusOpen(){
        this.status = "Em Aberto";
    }
    public void statusClosed(){
        this.status = "Fechado";
    }

    public int getRoom() {
        return room;
    }

    public String getClientName() {
        return clientName;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<String> getHistory() {
        return history;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
