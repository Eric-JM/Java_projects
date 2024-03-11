import java.awt.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.util.*;


import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;

public class prevent_vtech   extends JFrame implements ActionListener  {

    private Connection conn;
private Statement stat;
private ResultSet rs;
private ResultSetTableModel model;
private JTable affich, formated;


 private javax.swing.JLabel allcontracts;
    private javax.swing.JPanel contains_buttons;
    private javax.swing.JPanel contains_table;
    private javax.swing.JLabel endcontracts;
 private JScrollPane table_scroll;
 private javax.swing.JComboBox jComboBox1;
 private javax.swing.JLabel jLabel1;


   
   
private int flag=0;
  private javax.swing.JButton alerts;

    private javax.swing.JButton displayall;
    private javax.swing.JTextField enter_imm;
    private javax.swing.JLabel imm;
    private javax.swing.JButton renew_v;
private String[] columnNames ={"Immatriculation", "Genre", "Marque", "Type", "Carrosse", "CU", "Fin de validité"};
private int location=0;
private  java.sql.Date date_toincr, newvisdate;
    private Object[][] liste;

public prevent_vtech()

   {

    int i;

    forbg framebg=new forbg();
         setContentPane(framebg);



    try
      {
         conn = getConnection();
         DatabaseMetaData meta = conn.getMetaData();
         if (meta.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE))
         {
      //      scrolling = true;
           stat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);

         //      ResultSet.CONCUR_READ_ONLY);
         }
         else
         {
            stat = conn.createStatement();
   //         scrolling = false;
         }


 
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      catch (SQLException e)
      {
         e.printStackTrace();

      }




     try {
initComponents();

 rs=stat.executeQuery("select immatriculation, genre, marque, type, carrosse, cu, fin from  vtech order by immatriculation ");

      Tabledisplayer(rs);

          }

            catch (SQLException coco)    {  coco.printStackTrace(); }


}



   private void initComponents() {


        contains_table = new javax.swing.JPanel();
        contains_buttons = new javax.swing.JPanel();
        displayall = new javax.swing.JButton();
        alerts = new javax.swing.JButton();
        imm = new javax.swing.JLabel();
        enter_imm = new javax.swing.JTextField();
        renew_v = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        contains_table.setBackground(new java.awt.Color(255, 153, 153));

        javax.swing.GroupLayout contains_tableLayout = new javax.swing.GroupLayout(contains_table);
        contains_table.setLayout(contains_tableLayout);
        contains_tableLayout.setHorizontalGroup(
            contains_tableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 945, Short.MAX_VALUE)
        );
        contains_tableLayout.setVerticalGroup(
            contains_tableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 442, Short.MAX_VALUE)
        );

        contains_buttons.setBackground(new java.awt.Color(0, 0, 255));

        displayall.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        displayall.setText("Afficher tout");
        displayall.addActionListener(this);

        alerts.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        alerts.setText("Alertes visite");
        alerts.addActionListener(this);

        imm.setFont(new java.awt.Font("Arial", 1, 11));
        imm.setForeground(new java.awt.Color(255, 255, 255));
        imm.setText("Immatriculation : ");

        enter_imm.setFont(new java.awt.Font("Arial", 1, 11));

        renew_v.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        renew_v.setText("Renouveller");
        renew_v.addActionListener(this);

        jComboBox1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Decembre" }));
        jComboBox1.addActionListener(this);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Par mois :");

        javax.swing.GroupLayout contains_buttonsLayout = new javax.swing.GroupLayout(contains_buttons);
        contains_buttons.setLayout(contains_buttonsLayout);
        contains_buttonsLayout.setHorizontalGroup(
            contains_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contains_buttonsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(displayall)
                .addGap(55, 55, 55)
                .addComponent(alerts, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
                .addComponent(imm)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(enter_imm, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(renew_v, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        contains_buttonsLayout.setVerticalGroup(
            contains_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contains_buttonsLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(contains_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(imm)
                    .addComponent(enter_imm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(renew_v)
                    .addComponent(alerts, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(displayall, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(contains_table, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(contains_buttons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(117, 117, 117)
                .addComponent(contains_table, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(contains_buttons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        pack();

     
    }// </editor-fold>





public void Tabledisplayer(ResultSet rs_todisp)
{       try {
            if (flag != 0) {
                contains_table.remove(table_scroll);
                flag = 0;
            }

            rs_todisp.last();
            int longr = rs_todisp.getRow();
            rs_todisp.absolute(1);

            liste = new Object[longr][7];
       

            for (int h = 0; h < longr; h++) {
              
           
 rs_todisp.absolute(h+1);

                for (int j = 0; j <7; j++) {
                     

                    if (j == 6) {
                       String formated_date = DateFormat.getDateInstance().format(rs_todisp.getDate(j+1));

                  
                        //     formated_date=formated_date.substring(8,10)+"/"+ formated_date.substring(5, 7)+"/"+ formated_date.substring(0, 4);
                        liste[h][j] = new String(formated_date);
                    } else  {
                        liste[h][j] = new String(rs_todisp.getObject(j+1).toString());
                      

                    }
                }
            }




            formated = new JTable(liste, columnNames);


            JTable fofo = new Displayquery(formated.getModel());


            table_scroll = new JScrollPane(fofo);
            table_scroll.setPreferredSize(new Dimension(938, 320));
contains_table.setLayout(new GridLayout());
 contains_table.setBackground(new java.awt.Color(255, 153, 153));
 contains_table.setPreferredSize(new Dimension(938,472));
           contains_table.add(table_scroll);
        validate();
       flag=1;
        
   

        }             catch (SQLException ex) {
            Logger.getLogger(prevent_vtech.class.getName()).log(Level.SEVERE, null, ex);
        }


}







      public static Connection getConnection()
      throws SQLException, IOException
   {
  //    String driv="org.gjt.mm.mysql.Driver";
          String driv="sun.jdbc.odbc.JdbcOdbcDriver";
   //    Properties props = new Properties();
   //   FileInputStream in = new FileInputStream("database.properties");
   //   props.load(in);
  //    in.close();

System.out.println("-> Chargement du driver...");
    try {
      Class.forName(driv).newInstance();
      System.out.println("*** Driver OK ***");
    }
    catch (Exception e) {
      System.out.println("ERREUR: Chargement impossible.\n" + e);

    }



// String url="jdbc:mysql://localhost/gestion_contrat";
String url="jdbc:odbc:DRIVER=Microsoft Access Driver (*.mdb); DBQ=visite_technique.mdb; ";
      String username = "";
      String password = "";

      return DriverManager.getConnection(url, username, password);
   }


       public void actionPerformed(ActionEvent e) {

           try {


            if(e.getSource()==displayall)       {

                rs=stat.executeQuery("select immatriculation, genre, marque, type, carrosse, cu, fin from vtech order by immatriculation ");
                Tabledisplayer(rs);
             
            }

            else if(e.getSource()==alerts)

            {

                 rs=stat.executeQuery("select immatriculation, genre, marque, type, carrosse, cu, fin from vtech where fin-Date()<=31 order by immatriculation ");
                Tabledisplayer(rs);


                

            }

            else if(e.getSource()==jComboBox1)
            {
                int selected_month=jComboBox1.getSelectedIndex();
                String querymonth;

             querymonth = "select immatriculation, genre, marque, type, carrosse, cu, fin from vtech where month(fin)="+(selected_month+1+" order by immatriculation");

             rs=stat.executeQuery(querymonth);
              Tabledisplayer(rs);

            }

            else if(e.getSource()==renew_v)

            {  String num=enter_imm.getText();
                 Calendar adder=Calendar.getInstance();
                    JLabel notfound=new JLabel();
                 notfound.setText("MATRICULE INTROUVABLE");
                 notfound.setFont(new Font("Arial", Font.BOLD,15));


      //

            rs=stat.executeQuery("select fin from vtech where immatriculation="+"\'"+num+"\'");

     
              
             if(rs.absolute(1)==true)   {
rs.absolute(1);

date_toincr=rs.getDate(1);

adder.setTime(date_toincr);
adder.add(Calendar.MONTH, 6);

    newvisdate=new java.sql.Date(adder.getTime().getTime());

    adder.setTime(newvisdate);
   

  String rnew_query="update vtech set fin=#"+newvisdate+"#  where immatriculation="+"\'"+num+"\'";


  stat.executeUpdate(rnew_query);
  enter_imm.setText(null);

         
           }
               
               else { JOptionPane.showMessageDialog(this, notfound, "erreur", JOptionPane.ERROR_MESSAGE);}

            }




            } catch (SQLException ex) {
                
               ex.printStackTrace();
            }





        }








      public static void main(String[] args)
   {   JDialog.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new prevent_vtech();
        frame.setTitle("Gestion visite technique");
            frame.setSize(1000, 748);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);


   }






}








