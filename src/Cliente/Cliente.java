package Cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Cliente implements Runnable {
    String host, mensajeRecivir, mensajeMandar;
    int port;

    DataOutputStream out;
    DataInputStream in;

    Thread thread;

    Socket socket;

    Cliente(String host, int port) {
        this.host = host;
        this.port = port;

        mensajeMandar = "";
        mensajeRecivir = "";

        thread = new Thread(this);
        thread.start();
    }

    public static void main(String[] args) {
        Cliente c = new Cliente("127.0.0.1", 5000);
        while(true){
            if(c.getMensajeRecivir()!=null||c.getMensajeRecivir()!=""){
                c.setMensajeMandar("awa");
            }
            
            System.out.println(c.getMensajeRecivir());
        }
    }

    @Override
    public void run() {
        try {
            socket = new Socket(host, port);

            while (true) {
                mandarMensaje(mensajeMandar);
                mensajeRecivir = recivirMensaje();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMensajeMandar(String mensajeMandar) {
        this.mensajeMandar = mensajeMandar;
    }

    public String getMensajeRecivir() {
        return mensajeRecivir;
    }

    void mandarMensaje(String mensaje) {
        try {
            out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(mensaje);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String recivirMensaje(){
        String mensaje = null;
        try {
            in = new DataInputStream(socket.getInputStream());
            mensaje = in.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mensaje;
    }
}
