package Servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

class Servidor implements Runnable {
    int puerto;
    ServerSocket servidor;

    ArrayList<Socket> clientes;

    DataOutputStream out;
    DataInputStream in;

    Socket sc;
    Thread thread;

    Servidor(int puerto) {
        this.puerto = puerto;
        clientes = new ArrayList<>();
        thread = new Thread(this);
        thread.start();
    }

    public static void main(String[] args) {
        Servidor s = new Servidor(5000);
        while (true) {
            System.out.print("\n");
            // s.mandarMensaje(s.recivirMensaje());
            s.recivirMandar();
        }
    }

    @Override
    public void run() {
        try {

            servidor = new ServerSocket(puerto);

            System.out.println("Server Iniciado");
            while (true) {
                sc = servidor.accept();
                clientes.add(sc);
                System.out.println("Cliente conected");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void mandarMensaje(String mensaje) {

        for (int i = 0; i < clientes.size(); i++) {
            System.out.println(mensaje);
            try {
                out = new DataOutputStream(clientes.get(i).getOutputStream());

                out.writeUTF(mensaje);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    String recivirMensaje() {
        String resultado = null;
        for (Socket cliente : clientes) {
            try {
                in = new DataInputStream(cliente.getInputStream());
                resultado = in.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultado;
    }

    void recivirMandar() {
        String resultado = "";
        for (int i = 0; i < clientes.size(); i++) {
            try {
                in = new DataInputStream(clientes.get(i).getInputStream());
                resultado = in.readUTF();

                for (int e = 0; e < clientes.size(); e++) {
                    // if (i != e) {
                    System.out.println("mensaje: " + resultado);
                    out = new DataOutputStream(clientes.get(e).getOutputStream());
                    out.writeUTF(resultado);
                    // }
                }
            } catch (IOException o) {
                o.printStackTrace();
            }
        }
    }
}