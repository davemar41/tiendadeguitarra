import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Ventana extends JFrame{
    private JTextField codigoedit;
    private JLabel Codigo;
    private JTextField modeloedit;
    private JTextField tipoguitaredit;
    private JTextField Precioedit;
    private JButton aceptarButton;
    private JButton borrarButton;
    private JTextArea textArea1;
    private JButton listar;
    private JPanel Panel1;
    private JButton Reset;
    private JButton Update;
    private JButton buscarButton;
    private String micodigo;
    private int codigonum;
    private String mimodelo;
    private String mitipoguitar;
    private String miprecio;
    private Double precio2;
    private String driver="com.mysql.jbc.driver";
    public  String bbdd="jbdc:mysql//localhost/mitienda2:3306/tiendaguitar";
    ArrayList miarray= new ArrayList();


    public  String usuario = "root";
    public String password="";




    public Ventana() {
        setContentPane(Panel1);

        aceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            Extraigodatos();

                try {
                    meConecto();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }


        });
        listar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    descargoInfo();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                textArea1.setText(miarray.toString());


            }
        });
        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        Reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                codigoedit.setText("");
                modeloedit.setText("");
                tipoguitaredit.setText("");
                textArea1.setText("");
                Precioedit.setText("");

            }
        });
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    buscoPorCodigo();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }


            }
        });
        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    borro();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            }
        });
        Update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    update();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }
    public void Extraigodatos(){
        micodigo=codigoedit.getText();
        codigonum=Integer.parseInt(micodigo);
        mimodelo=modeloedit.getText();

        mitipoguitar=tipoguitaredit.getText();
        miprecio=Precioedit.getText();
        precio2=Double.parseDouble(miprecio);

    }
    public void meConecto()throws Exception{
        //.forName("com.mysql.jbc.driver");
        System.out.println(codigonum+mimodelo+mitipoguitar+precio2 );
        Connection conexion= DriverManager.getConnection("jdbc:mysql://localhost:3306/mitienda2", "root","");
        Statement stato=conexion.createStatement();
        String sql="INSERT INTO mitienda2.tiendaguitar (codigo,nombremodelo,tipodeguitarra,precio) values ('" +codigonum+"','"+mimodelo+"','"+mitipoguitar+"','"+precio2 +"')";
        stato.executeUpdate(sql);
        //conexion.close();


    }public void descargoInfo()throws Exception{
        Connection conexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/mitienda2", "root","");
        Statement result=conexion.createStatement();
        ResultSet misdatos=result.executeQuery("Select * from tiendaguitar");
        while(misdatos.next()){
            System.out.println(misdatos.getInt(1)+misdatos.getString(2)+misdatos.getString(3)+misdatos.getDouble(4));
            String milista=misdatos.getInt(1)+misdatos.getString(2)+misdatos.getString(3)+misdatos.getDouble(4);

            miarray.add(String.valueOf(misdatos.getInt(1)));
            miarray.add(misdatos.getString(2));
            miarray.add(misdatos.getString(3));
            miarray.add(String.valueOf(misdatos.getDouble(4)));
            //conexion.close();


            //milista="";


        }


    }
    public void buscoPorCodigo()throws Exception{
        micodigo=codigoedit.getText();
        codigonum=Integer.parseInt(micodigo);
        Connection conexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/mitienda2", "root","");
        Statement result=conexion.createStatement();
        ResultSet misdatos=result.executeQuery("Select * from tiendaguitar where codigo="+codigonum);
        while(misdatos.next()){
            textArea1.setText(String.valueOf(misdatos.getInt(1))+misdatos.getString(2)+misdatos.getString(3)+
                    String.valueOf(misdatos.getDouble(4)));
            System.out.println(String.valueOf(misdatos.getInt(1))+misdatos.getString(2)+misdatos.getString(3)+
                    String.valueOf(misdatos.getDouble(4)));
        }


    }public void borro()throws Exception{
        micodigo=codigoedit.getText();
        codigonum=Integer.parseInt(micodigo);
        Connection conexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/mitienda2", "root","");
        Statement result=conexion.createStatement();
       result.executeUpdate("delete  from tiendaguitar where codigo="+codigonum);
        textArea1.setText("datos borrados '\n'"+miarray.toString() );

    }public void update()throws Exception{
        micodigo=codigoedit.getText();
        codigonum=Integer.parseInt(micodigo);
        Connection conexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/mitienda2", "root","");
        Statement result=conexion.createStatement();
        result.executeUpdate("update  tiendaguitar set precio="+Precioedit.getText()+" where codigo="+codigonum);
        textArea1.setText("datos actualizados '\n'"+miarray.toString() );
        miarray.clear();

    }



}
