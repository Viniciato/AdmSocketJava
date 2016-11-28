package Controller;

import Model.Conversation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Nadin on 08/11/16.
 */
public class InitialWindowController {
    private Socket socket;
    @FXML private AnchorPane window;
    @FXML private TableView<Conversation> conversationsTable;
    @FXML private TextField textLabel;
    @FXML private TextArea messagesLabel;
    private BufferedWriter bufferedWriter;
    private Thread t;

    @FXML void updateAll(ActionEvent event) {
        try {
            updateTable();
            escutar();
            messagesLabel.clear();
        }catch (Exception e){
        System.out.println(e);}

    }

    @FXML void enterRoom(ActionEvent event) throws Exception{
        enterInRoom();
    }

    @FXML void exit(ActionEvent event) throws IOException{

    }


    @FXML void getHistory(ActionEvent event) throws Exception{
        getHistory();
        escutar();
    }

    @FXML void sendMessage(ActionEvent event) throws IOException{
        ArrayList<String> array = new ArrayList<>();
        array.add("9999");
        array.add(textLabel.getText());
        array.add("Atendente");
        bufferedWriter.write(array.toString()+"\r\n");
        messagesLabel.appendText("Atendente" + " -> " + textLabel.getText()+"\r\n");
        bufferedWriter.flush();
        textLabel.setText("");
    }

    public void getHistory() throws Exception{
        t.stop();
        Conversation conv = conversationsTable.getSelectionModel().getSelectedItem();
        if(conv == null)
            System.out.println("Erro selecionar Tabela");
        else {
            t.stop();
            messagesLabel.clear();
            ArrayList<String> array = new ArrayList<>();
            array.add("8888");
            array.add("7777");
            array.add(String.valueOf(conv.getRoom()));
            bufferedWriter.write(array.toString()+"\r\n");
            System.out.println(array);
            bufferedWriter.flush();
            Thread.sleep(1000);

            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            InputStream is = socket.getInputStream();
            ObjectInputStream input = new ObjectInputStream(is);
            ArrayList<String> history = (ArrayList<String>) input.readObject();
            for (String msg : history){
                messagesLabel.appendText(msg);
            }
        }

    }


    public void escutar(){
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                try {
                    InputStream in = socket.getInputStream();
                    InputStreamReader inr = new InputStreamReader(in);
                    BufferedReader bfr = new BufferedReader(inr);
                    String msg = "";
                    while(!"Sair".equalsIgnoreCase(msg)){
                        if(bfr.ready()){
                            msg = bfr.readLine();
                            msg = msg.replace("[", "");
                            msg = msg.replace("]", "");
                            String[] ary = msg.split(",");

                            if(msg.equals("Sair"))
                                messagesLabel.appendText("Servidor caiu");
                            else
                                messagesLabel.appendText(ary[0]+"\r\n");
                        }
                    }
                }catch (Exception e){
                    System.out.println(e);
                }
                return null;
            }
        };

        t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    public void updateTable() throws Exception{

        ObservableList<Conversation> conversations = FXCollections.observableArrayList(getConversations());
        conversationsTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("clientName"));
        conversationsTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("room"));
        conversationsTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("status"));
        conversationsTable.setItems(conversations);
    }

    public ArrayList<Conversation> getConversations() throws Exception{
            t.stop();
                ArrayList<String> array = new ArrayList<>();
                array.add("8888");
                array.add("9783940");
                bufferedWriter.write(array.toString()+"\r\n");
                System.out.println(array);
                bufferedWriter.flush();
                Thread.sleep(1000);

                OutputStream outputStream = socket.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(outputStream);
                InputStream is = socket.getInputStream();
                ObjectInputStream input = new ObjectInputStream(is);

                ArrayList<Conversation> conversations = (ArrayList<Conversation>) input.readObject();
                System.out.println("terminou");
                System.out.println(conversations);
                return conversations;
    }

    public void enterInRoom() throws Exception{
        Conversation conv = conversationsTable.getSelectionModel().getSelectedItem();
        if(conv == null)
            System.out.println("Erro selecionar Tabela");
        else
        {
            ArrayList<String> array = new ArrayList<>();
            array.add("8888");
            array.add("chSt");
            array.add(String.valueOf(conv.getRoom()));
            OutputStream outStream = socket.getOutputStream();
            Writer writer = new OutputStreamWriter(outStream);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(array.toString()+"\r\n");
            bufferedWriter.flush();

            Thread.sleep(2000);

        }
    }
    public void setSocket(Socket socket) throws IOException {
        this.socket = socket;
        OutputStream outStream = socket.getOutputStream();
        Writer writer = new OutputStreamWriter(outStream);
        this.bufferedWriter = new BufferedWriter(writer);
        escutar();
    }
}
