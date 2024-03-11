import com.birosoft.liquid.LiquidTabbedPaneUI;
import com.sun.rowset.*;
import java.awt.*;
import java.awt.print.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.util.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.table.*;
import javax.sql.rowset.*;
import java.text.NumberFormat;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.math.*;



/**
   This program shows how to display the result of a 
   database query in a table.  
*/
public class stock_manager 
{  
   public static void main(String[] args)
   {   
      
             JDialog.setDefaultLookAndFeelDecorated(true);
           
            JFrame frame = new ResultSetFrame();
            frame.setSize(1435, 875);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
      
  
        
   }
} 
 
/**
   This frame contains a combo box to select a database table
   and a table to show the data stored in the table
*/
class ResultSetFrame extends JFrame implements ActionListener, TreeSelectionListener,KeyListener
{   
	JMenuBar barmen;
	JMenu menu_fichier, menu_option,menu_aide;
	JMenuItem cont_menu_fichier, cont_menu_option1, cont_menu_option2, aide,apropos,unicity,cont_menu_option3;
	int flag_dont_leave_blank=0;
	

	
   public ResultSetFrame()
   {  
      setTitle("Gestion stock");
   setFocusable(true);
      barmen=new JMenuBar();
	  menu_fichier=new JMenu("Fichier");
          menu_option=new JMenu("Options");
          menu_aide=new JMenu("Aide");
	  cont_menu_fichier=new JMenuItem("Quitter");
          cont_menu_option1=new JMenuItem("Modifier une cellule");
          cont_menu_option2=new JMenuItem("Modifier/Supprimer une ligne");
          cont_menu_option3=new JMenuItem("Modifier/Ajouter/Supprimer un camion");
         unicity=new JMenuItem("Tester l'unicité des réferences");
          aide=new JMenuItem("Guide");
          apropos=new JMenuItem("A propos");
          menu_aide.add(aide);
          menu_aide.add(apropos);
      menu_fichier.add(cont_menu_fichier);
      menu_option.add(cont_menu_option1);
      menu_option.add(cont_menu_option2);
      menu_option.add(cont_menu_option3);
      menu_option.add(unicity);
      barmen.add(menu_fichier);
      barmen.add(menu_option);
      barmen.add(menu_aide);
      setJMenuBar(barmen);
    
      cont_menu_fichier.addActionListener(this);
      cont_menu_option1.addActionListener(this);
      cont_menu_option2.addActionListener(this);
      cont_menu_option3.addActionListener(this);
     unicity.addActionListener(this);
      aide.addActionListener(this);
      apropos.addActionListener(this);
	   
      coul=Color.BLACK;
      JLabel etiqu_seek=new JLabel("Réference:");
      JLabel etiqu_ref_sortie=new JLabel("Réference:");
      JLabel etiqu_ref_entree=new JLabel("Réference:");
      JLabel etiqu_ref_nv=new JLabel("Réference:");
      
     JLabel etiquette2=new JLabel("Sorties:");
     JLabel etiquette3=new JLabel("Entrées:");
     JLabel etiquette4=new JLabel("Designation:");
     JLabel etiquette5=new JLabel("Quantité:");
     JLabel etiquette6=new JLabel("Valeur:");
     JLabel numcamion_lb=new JLabel("Numéro camion:");
     
     
     fournisseur_lb=new JLabel("Fournisseur:");
     seuil_lb=new JLabel("Seuil critique:");
     
     etat_stock=new JLabel();
     
    erreur=new JLabel();
    champ_vide=new JLabel();
    champ_vide.setText("VEUILLEZ TAPER LA REFERENCE");
    champ_vide.setFont(new Font("Arial",Font.BOLD,20));
    erreur.setText("PIECE NON REPERTORIEE DANS LE STOCK");
    
    erreur.setFont(new Font("Arial",Font.BOLD,20));
    
     
     p = new JPanel();
     p.setLayout(new FlowLayout());
     
     
     
    
     JPanel panel_title=new JPanel();
     panel_title.setPreferredSize(new Dimension(1260,60));
     JLabel label_title=new JLabel("GESTION DU STOCK");
     label_title.setFont(new Font("Arial", Font.BOLD,28));
     label_title.setForeground(coul.BLUE);
     panel_title.setLayout(new FlowLayout(FlowLayout.CENTER,10,12));
     panel_title.add(label_title, BorderLayout.CENTER);
     
     panel_title.setBackground(coul.LIGHT_GRAY);
     panel_title.setBorder(BorderFactory.createRaisedBevelBorder());
     JPanel panel1=new JPanel();
          panel2=new JPanel();
     JPanel panel3=new JPanel();
     JPanel panel4=new JPanel();
     panel5=new JPanel();
     panel6=new JPanel();
     panel6.setPreferredSize(new Dimension(1369,451));
     panel6.setLayout(new GridLayout());
     
      /* find all tables in the database and add them to
         a combo box
      */
      panel1.setBorder(BorderFactory.createRaisedBevelBorder());
     panel3.setBorder(BorderFactory.createRaisedBevelBorder());
     
    panel1.setBackground(Color.CYAN);
  panel3.setBackground(Color.yellow);
  
  panel4.setBorder(BorderFactory.createRaisedBevelBorder());
  panel4.setBackground(Color.lightGray);
  
  panel5.setBorder(BorderFactory.createRaisedBevelBorder());
  panel5.setBackground(Color.lightGray);
     
    
      new_pc=new JButton("Inserer nouvelle piece");
      design_nv=new JTextField(18);
      design_nv.setFont(new Font("Arial",Font.BOLD,14));
      ref_nv=new JTextField(15);
      ref_nv.setFont(new Font("Arial",Font.BOLD,14));
      quantite_nv=new JTextField(5);
      quantite_nv.setFont(new Font("Arial",Font.BOLD,14));
      fournisseur_nv=new JTextField(15);
      fournisseur_nv.setFont(new Font("Arial",Font.BOLD,14));
      seuil=new JTextField(5);
      seuil.setFont(new Font("Arial",Font.BOLD,14));
      numerocamion=new JTextField(8);
      numerocamion.setFont(new Font("Arial",Font.BOLD,14));
      
     toutlestock=new JButton("Afficher tous le stock");
     recherche=new JButton("Rechercher");





     Deduire=new JButton("Mettre à jour sorties");
     Ajouter=new JButton("Mettre à jour entrées");
     filtre=new JTextField(10);
     filtre.setFont(new Font("Arial",Font.BOLD,14));
     filtre.addKeyListener(this);
     reference_entree=new JTextField(12);
     reference_entree.setFont(new Font("Arial",Font.BOLD,14));
     sortie=new JTextField(5);
     sortie.setFont(new Font("Arial",Font.BOLD,14));
     entree=new JTextField(5);
     entree.setFont(new Font("Arial",Font.BOLD,14));
     reference_sortie=new JTextField(10);
     reference_sortie.setFont(new Font("Arial",Font.BOLD,14));
     valeur_piece=new JTextField(10);
     valeur_piece.setFont(new Font("Arial",Font.BOLD,14));
      tableNames = new JComboBox();
      verif_stock=new JButton("Afficher la valeur du stock ");
      nbr_piece_critique=new JButton("Dépenses immediates");
      nbr_piece_critique.addActionListener(this);
      verif_stock.addActionListener(this);
      recherche.addActionListener(this);
      Deduire.addActionListener(this);
      toutlestock.addActionListener(this);
      new_pc.addActionListener(this);
      Ajouter.addActionListener(this);
 

      
      panel1.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
  //    panel1.setPreferredSize(new Dimension(500,60));
      panel1.add(etiqu_seek);
      panel1.add(filtre);
     panel1.add(recherche);
     panel1.add(toutlestock);
     
     panel2.setLayout(new FlowLayout());
     panel2.setOpaque(false);
     
     p.add(panel_title);
     p.add(panel1);
     
     
   // p.add(panel2);
    
     
     panel3.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
     panel3.add(etiqu_ref_sortie);
     panel3.add(reference_sortie);
     panel3.add(etiquette2);
     panel3.add(sortie);
      panel3.add(numcamion_lb);
     panel3.add(numerocamion);

     panel3.add(Deduire);
     panel3.add(etiqu_ref_entree);
     panel3.add(reference_entree);
     panel3.add(etiquette3);
     panel3.add(entree);
     panel3.add(Ajouter);
   
 p.add(panel3);  
 panel3.setLocation(0,300);
 
 panel4.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
 panel4.add(etiquette4);
 panel4.add(design_nv);
 panel4.add(etiqu_ref_nv);
 panel4.add(ref_nv);
 panel4.add(fournisseur_lb);
 panel4.add(fournisseur_nv);
 
 
 
 panel4.add(etiquette5);
 panel4.add(quantite_nv);
 
 
 panel5.setLayout(new FlowLayout(FlowLayout.CENTER,10,8));
 
 panel5.add(etiquette6);
 panel5.add(valeur_piece);
 panel5.add(seuil_lb);
 panel5.add(seuil);
 panel5.add(new_pc);
 
JTabbedPane misc_pane=new JTabbedPane();
 misc_pane.setUI(new LiquidTabbedPaneUI());
 p.add(panel4);
 p.add(panel5);
 panel2.setLayout(new FlowLayout(FlowLayout.CENTER,100,10));
 panel2.setPreferredSize(new Dimension(900,40));

 verif_stock.setPreferredSize(new Dimension(200,25));
 nbr_piece_critique.setPreferredSize(new Dimension(260,25));
 panel2.add(nbr_piece_critique);
 panel2.add(verif_stock);
 
 p.add(panel2);
 
 p.add(panel6);
      
     p.setBackground(Color.blue);
      
   //   add(p);                          //  *********************---------------------------------*******************************
     
    
   
                     
                      //  *********************---------------------------------*******************************

      try
      {  
         conn = getConnection();
         DatabaseMetaData meta = conn.getMetaData();
         if (meta.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE))
         {
            scrolling = true;  
            stat = conn.createStatement();
            stat2=conn.createStatement();
         //      ResultSet.CONCUR_READ_ONLY);
         }
         else
         {
            stat = conn.createStatement();
            scrolling = false;
         }
//         ResultSet tables = meta.getTables(null, null, null, new String[] { "TABLE" });
 //       while (tables.next())
//           tableNames.addItem(tables.getString(3));
//         tables.close();
  //       
   rs=stat.getResultSet();
   rs2=stat.getResultSet();
 
      }
      catch (IOException e)
      {  
         e.printStackTrace();
      }
      catch (SQLException e)
      {  JOptionPane.showMessageDialog(this,   e.getMessage() , "ERREUR A LA CONNECTION",JOptionPane.ERROR_MESSAGE);
         System.exit(0);
         e.printStackTrace();
      }
    
    
      initComponents2();
misc_pane.add("Gestion du stock",p);
misc_pane.add("Dépenses par camion", pan_for_depdetail);
    add(misc_pane); 
    
    
    
   /* 

      addWindowListener(new
         WindowAdapter()
         {
            public void windowClosing(WindowEvent event)
            {  
                  
                   
            try { 
                Process pr1=Runtime.getRuntime().exec("cmd /c mysqldump -u root -h 169.254.236.144 -prien stock > "+
                        "D:\\sqldumprecovery\\stock.sql");
                 
                rs=stat.executeQuery("select * from gestion_stock order by designation");
            model=new ResultSetTableModel(rs);
            affich=new Displayquery(model);
            
          ExcelExporter  exporteddocument =new ExcelExporter();
            exporteddocument.exportTable(affich, new File("D:/Documents "+"and "+"Settings"+"/USER/"+"Mes "+"Documents/"+"sauvegarde/stock.xls"));
          
            }
            
            catch (SQLException coco)    {  coco.printStackTrace(); }
            catch (IOException zz)   { zz.printStackTrace();
            
            }  
               
               
               try
               {
                  if (conn != null) conn.close();
               }
               catch (SQLException e)
               {
                  e.printStackTrace();
               }                 
                   
               
            }
            
         }); */ 
  

   };

   /**
      Gets a connection from the properties specified in
      the file database.properties.
      @return the database connection
    */
   public void actionPerformed(ActionEvent event)
   { 
   	
   	
   	if(event.getSource()==cont_menu_fichier)
   		
   	{ System.exit(0); }
   	
      try
      {  
         if (scrollPane != null) remove(scrollPane);
         String tableName = (String) tableNames.getSelectedItem();
         if (rs != null) rs.close();
   
       //String query="select * from "+ tableName;
         
   
              if (event.getSource()==recherche)
              {    String theone = (String) filtre.getText();
      
        String query="select * from gestion_stock where Designation regexp "+"\""+theone+"\""+" or"+" reference regexp "+"\""+theone+"\"";

             
             rs=stat.executeQuery(query);
              
          if(rs.last()==false) {   JOptionPane.showMessageDialog(this,erreur,"ERREUR" , JOptionPane.ERROR_MESSAGE); }
          else Tabledisplayer(rs);
              
            
              }
              
             
              
             else  if (event.getSource()==Deduire) {
                 boolean checknumber=false;
              	JLabel message1=new JLabel("STOCK INFERIEUR AU NOMBRE DE SORTIES");
                JLabel message2=new JLabel("STOCK INFERIEUR AU NOMBRE DE SORTIES ET CAMION INEXISTANT");
                JLabel message3= new JLabel("CAMION INEXISTANT");
                 message1.setFont(new Font("Arial",Font.BOLD,20));
                 message2.setFont(new Font("Arial",Font.BOLD,20));
                 message3.setFont(new Font("Arial",Font.BOLD,20));
                 String p_sorties=(String)sortie.getText();
                 
              	String refsortie=(String)reference_sortie.getText();
                 String num_cam=(String)numerocamion.getText();
              	String query_deduct="update gestion_stock set Quantite_en_stock=Quantite_en_stock-"+"\""+p_sorties+"\""+"where Reference="+"\""+refsortie+"\"";
	       Float neg_comparator;
                rs=stat.executeQuery("select Quantite_en_stock from gestion_stock where Reference= "+"\""+refsortie+"\"");
           
                     rs.absolute(1);
               neg_comparator=Float.parseFloat(rs.getObject(1).toString());
                 rs=stat.executeQuery("select valeur from gestion_stock where Reference= "+"\""+refsortie+"\"");
             rs.absolute(1);
          all_expenses=(Float.parseFloat(rs.getObject(1).toString()))*Float.parseFloat(p_sorties);
               rs=stat.executeQuery("select Designation from gestion_stock where Reference= "+"\""+refsortie+"\"");
                 rs.absolute(1);
               String des_sortie=rs.getObject(1).toString();
               
               int childcount=camion.getChildCount();
               
               for(childcount_increment=0; childcount_increment<childcount;childcount_increment++)
                   
               {      if ((camion.getChildAt(childcount_increment).toString()).equals(num_cam))
                              
                   checknumber=true;
                      
                
               }
               
               
      
               if((neg_comparator<Float.parseFloat(p_sorties))&&checknumber==true)
               { JOptionPane.showMessageDialog(this,message1, "ERREUR", JOptionPane.ERROR_MESSAGE); }
               
               
               else     if((neg_comparator<Float.parseFloat(p_sorties))&&(checknumber==false))
              {   JOptionPane.showMessageDialog(this,message2, "ERREUR", JOptionPane.ERROR_MESSAGE); }
               
               else    if((neg_comparator>=Float.parseFloat(p_sorties))&&(checknumber==true))
                     { 
               stat.executeUpdate(query_deduct);
           
          String automated_expense="insert into depenses_camions_detail "+"(Designation, Quantite,num_camion,Depenses,Periode,Date_sortie) values "+
                      "("+"\""+des_sortie+"\""+","+Float.parseFloat(p_sorties) +","  +"\""+num_cam+"\""+","+all_expenses+","+"\""+getmonth()+"\""+", date_format(curdate(), '%Y-%m-%d')" + ")";

                   
                      
                      stat.executeUpdate(automated_expense); 
                    reference_sortie.setText(null);  
                    sortie.setText(null);
                     numerocamion.setText(null);
                      
               }
             
               else if(neg_comparator>=Float.parseFloat(p_sorties)&&checknumber==false)
               {     if(num_cam.equals("cf")==true)
                         
                     { stat.executeUpdate(query_deduct);
             
              
              stat.executeUpdate("insert into cout_fixe "+"(Designation, Quantite, Cout_fixe, Periode, Date_sortie) values "+"("+"\""+des_sortie+"\""+","+ Float.parseFloat(p_sorties)+","+
                      all_expenses+","+"\""+getmonth()+"\""+ ", date_format(curdate(), '%Y-%m-%d')" +  ")");
               reference_sortie.setText(null);  
              sortie.setText(null);
               numerocamion.setText(null);
              
               }
                     
                     else if(num_cam.equals("pv")==true)
                     {    stat.executeUpdate(query_deduct);
              
                
               stat.executeUpdate("insert into vente  "+"(Designation, Quantite,Valeur, Periode, Date_sortie) values "+"("+"\""+des_sortie+"\""+
              ","+Float.parseFloat(p_sorties)+","+  all_expenses+","+"\""+getmonth()+"\""+ ", date_format(curdate(), '%Y-%m-%d')" + ")");
                     
                  reference_sortie.setText(null); 
                   sortie.setText(null);
               numerocamion.setText(null);
                  
                  
                     } 
                     
                     
                     else  JOptionPane.showMessageDialog(this, message3,"ERREUR",JOptionPane.ERROR_MESSAGE);
                         
             }
               
               
             
               
             }
              
      
             else  if (event.getSource()==Ajouter) {
              	String p_entree=(String)entree.getText();
              	String refentree=(String)reference_entree.getText();
              	String query_add="update gestion_stock set Quantite_en_stock=Quantite_en_stock+"+"\""+p_entree+"\""+"where Reference="+"\""+refentree+"\"";
				
			stat.executeUpdate(query_add);
			reference_entree.setText(null);     
			if(stat.getUpdateCount()==0) {JOptionPane.showMessageDialog(this, "PIECE NON REPERTORIEE DANS LE STOCK","ERREUR", JOptionPane.ERROR_MESSAGE);
             
             }
             }
             
              
            else  if (event.getSource()==toutlestock) {
             	String queryall="select  * from gestion_stock";
               
         
              	rs=stat.executeQuery(queryall);
                Tabledisplayer(rs);
 
      
            }
            
            else if (event.getSource()==new_pc) {
            	String contenu_design_nv=(String)design_nv.getText();
            	String contenu_ref_nv=(String)ref_nv.getText();
            	String contenu_fournisseur_nv=(String)fournisseur_nv.getText();
            	String contenu_quantit_nv=(String)quantite_nv.getText();
            	String contenu_valeur=(String)valeur_piece.getText();
            	String contenu_seuil=(String)seuil.getText();
            if((contenu_ref_nv.compareTo("")==0)||(contenu_design_nv.compareTo("")==0)||(contenu_quantit_nv.compareTo("")==0)||(contenu_valeur.compareTo("")==0)||(contenu_fournisseur_nv.compareTo("")==0)||(contenu_seuil.compareTo("")==0))
            	
            	
            	
            	{JOptionPane.showMessageDialog(this, "INSEREZ LE DETAIL","ERREUR", JOptionPane.ERROR_MESSAGE);
            	
            	flag_dont_leave_blank=1; }
            	
            	if(flag_dont_leave_blank!=1)
            	{	String query_insert="insert into gestion_stock (designation,reference,Fournisseur,quantite_en_stock,valeur,Seuil_critique) values ("+"\""+contenu_design_nv+"\""+","+"\""+contenu_ref_nv+"\""+","+"\""+contenu_fournisseur_nv+"\""+","+"\""+contenu_quantit_nv+"\""+","+"\""+contenu_valeur+"\""+","+"\""+contenu_seuil+"\""+")";           
            	System.out.println(query_insert);
            	stat.executeUpdate(query_insert); 
            	ref_nv.setText("");
                design_nv.setText("");
                quantite_nv.setText("");
                valeur_piece.setText("");
                seuil.setText("");
                
                }
            	
            	flag_dont_leave_blank=0;
            	
            }
            
            else if (event.getSource()==verif_stock) {
            	
            	rs=stat.executeQuery("select * from gestion_stock order by Designation");
                
                model=new ResultSetTableModel(rs);
                affich=new Displayquery(model);
  	        Stockpile_calc();
            	
            }
            
            
            
            else if(event.getSource()==nbr_piece_critique) {
     String    checkcforcam=(String)(numerocamion.getText());
            String immed;
if(checkcforcam.equals(""))
{
    immed="insert into cout_fixe "+"(Designation, Quantite, Cout_fixe, Periode, Date_sortie) values "+"("+"\""+ design_nv.getText()+"\""+","+ Float.parseFloat(quantite_nv.getText())+","+
                      valeur_piece.getText()+","+"\""+getmonth()+"\""+ ", date_format(curdate(), '%Y-%m-%d')" +  ")";
    stat.executeUpdate(immed);
    design_nv.setText(null);
    quantite_nv.setText(null);
    valeur_piece.setText(null);
      
    
            }
         
            else   if(event.getSource()==cont_menu_option1)
            {

                        initComponents();
           jDialog1.setSize(490,320);
             jDialog1.setLocationRelativeTo(this); 
             jDialog1.setVisible(true);
       
            }  
         
            else if (event.getSource()==jButton1) 
                
            {  String colonne_combo=(String)jComboBox1.getSelectedItem();
               String id_to_modify=(String)jTextField1.getText();
               String data_to_modify=(String)jTextField2.getText();
               String overallquery="update gestion_stock set "+colonne_combo+"="+"\""+data_to_modify+"\""+" where id="+"\""+id_to_modify+"\"";
          
              stat.executeUpdate(overallquery);
               jDialog1.setVisible(false);
            }
         
         
            else if (event.getSource()==jButton2)
            { jDialog1.setVisible(false);
              jTextField1.setText(null);
              jTextField2.setText(null);
            }
         
         
            else if (event.getSource()==cont_menu_option2)
            { initComponents();
                jDialog2.setSize(800,320);
             jDialog2.setLocationRelativeTo(this); 
             jDialog2.setVisible(true); }
         
         
            else if(event.getSource()==modify_oneline)
            { String cont_line_tomodif=(String)line_to_modifyjtxt.getText();
              String cont_design_modifoneline=(String)modif_online_design.getText();
              String cont_ref_tomodifoneline=(String)modif_oneline_ref.getText();
              String cont_four_tomodifoneline=(String)modif_oneline_fourn.getText();
              String cont_quant_tomodifoneline=(String)modif_oneline_quant.getText();
              String cont_val_tomodifoneline=(String)modif_oneline_val.getText();
              String cont_seuil_tomodifoneline=(String)modif_oneline_seuil.getText();
              String line_update_query="update gestion_stock "+" set Designation="+"\""+cont_design_modifoneline+"\""+" , Reference="+"\""+cont_ref_tomodifoneline+"\""+" , Fournisseur="+"\""+cont_four_tomodifoneline+"\""+" , Quantite_en_stock="+cont_quant_tomodifoneline+" , Valeur="+cont_val_tomodifoneline+" , Seuil_critique="+cont_seuil_tomodifoneline+" where id="+cont_line_tomodif;
                      
                stat.executeUpdate(line_update_query);
         jDialog2.setVisible(false);
            }
         
            else if((event.getSource()==cancel_modify_oneline)||(event.getSource()==cancelsuppress))

            { line_to_modifyjtxt.setText(null);
              modif_online_design.setText(null);
              modif_oneline_ref.setText(null);
              modif_oneline_fourn.setText(null);
              modif_oneline_quant.setText(null);
              modif_oneline_val.setText(null);
              modif_oneline_seuil.setText(null);
              linetosuppress.setText(null);
            
                jDialog2.setVisible(false);
               
                
            }
         
         
            else if((event.getSource()==cancel_modifnum)||(event.getSource()==canceladdnewtruck)||(event.getSource()==canceldeletetruck))
         
            { camiontomodif.setText(null);
              modifiedcam.setText(null);
              newbrandcam.setText(null);
              trucktodelete.setText(null);
              modiftree_dial.setVisible(false); 
                
            }  
                
            else if(event.getSource()==suppressline)
            { String cont_line_tosuppr=(String)linetosuppress.getText();
              int line_tosuppress=Integer.parseInt(cont_line_tosuppr);
              String suppressline_query="delete from gestion_stock where id="+line_tosuppress;
              stat.executeUpdate(suppressline_query);
              jDialog2.setVisible(false);
              
            }
         
      
           else if(event.getSource()==apropos)
            {
               initComponents();
               dialog_apropos.setSize(440, 410);
               dialog_apropos.setLocationRelativeTo(this);
               dialog_apropos.setVisible(true); }
         
           else if(event.getSource()==OK_toclose_apropos)
           { dialog_apropos.setVisible(false); }
       
                        
           else if (event.getSource()==aide)
           {               
               initComponents();
             Helpcontents.setBackground(Color.red);
             dial_aide.setSize(840, 600);
             dial_aide.setLocationRelativeTo(this);
             dial_aide.setVisible(true);  
           }
         
         
           else if (event.getSource()==display_truck_dep)
         
           {  String cont_num_cam=(String)enternumcam.getText();
              String which_month=month_chooser.getSelectedItem().toString();
              String which_year=year_chooser.getSelectedItem().toString();
               JLabel message_notruck= new JLabel("CAMION INEXISTANT");
                 message_notruck.setFont(new Font("Arial",Font.BOLD,20));
              boolean checknumber2=false;
                int childcount2=camion.getChildCount();
               
               for(childcount_increment2=0; childcount_increment2<childcount2;childcount_increment2++)
                   
               {      if ((camion.getChildAt(childcount_increment2).toString()).equals(cont_num_cam))
                              
                   checknumber2=true;
                      
                
               }
              
              if(checknumber2==false)     { JOptionPane.showMessageDialog(this,message_notruck, "ERREUR", JOptionPane.ERROR_MESSAGE); }
              
              else {
              
               
                if (flag_disp_dep!=0) { contains_only_table.remove(scrollpane2);
    flag_disp_dep=0; }
              
                exquery( "select Designation, Quantite, Depenses, DATE_FORMAT(Date_sortie , '%d-%b-%Y')   as Date  from depenses_camions_detail where num_camion= "+
                      "\""+cont_num_cam+"\""+" AND Periode= "+"\""+which_month+which_year+"\"");
              
              model2=new ResultSetTableModel(rs2);
    affich2=new Displayquery(model2);
    scrollpane2=new JScrollPane(affich2);
   
    contains_only_table.setLayout(new GridLayout());
 
   depcamionmensuel.setSize(230, 80);
   depcamionmensuel.setHorizontalAlignment(SwingConstants.CENTER);
    
    scrollpane2.setPreferredSize(new Dimension(300,500));
     
 contains_only_table.add(scrollpane2); 
     
    collectdeptotalcamion=total_truck_dep();
     flag_disp_dep=1;
     depcamionmensuel.setOpaque(true);
     depcamionmensuel.setBorder(BorderFactory.createRaisedBevelBorder());
    depcamionmensuel.setBackground(Color.yellow);
     depcamionmensuel.setFont(new Font("Arial", Font.BOLD,18));
     depcamionmensuel.setText("TOTAL : "+NumberFormat.getInstance().format(collectdeptotalcamion)+" Ar");
 
    contains_table_and_buttons.add(depcamionmensuel);
    which_cam_lb.setMinimumSize(new Dimension(390,40));
    which_cam_lb.setOpaque(true);
    which_cam_lb.setBackground(Color.yellow);
    which_cam_lb.setFont(new Font("Arial", Font.BOLD,18));
    which_cam_lb.setBorder(BorderFactory.createRaisedBevelBorder());
    which_cam_lb.setText(cont_num_cam);
    which_cam_lb.setHorizontalAlignment(SwingConstants.CENTER);
    
   
     validate();  
  
 try { stat.getMoreResults(Statement.KEEP_CURRENT_RESULT);    ; }
catch(SQLException u) {  u.printStackTrace(); }
              }
           }
         
         
         
           else if (event.getSource()==unicity)
           {int m,n,h,flag_unicity=0;
             JLabel testpasse= new JLabel("TOUTES LES REFERENCES SONT UNIQUES");
                 testpasse.setFont(new Font("Arial",Font.BOLD,20));
                  JLabel notunicity= new JLabel();
                 notunicity.setFont(new Font("Arial",Font.BOLD,20));
            String comp1,comp2;
            rs=stat.executeQuery("select Reference from gestion_stock");
       rs.last();
        h=rs.getRow();
        rs.absolute(1);
      
        for(m=1;m<=h;m++)
        {  
             for(n=m+1;n<=h;n++)
                 
             {  rs.absolute(m);  
                comp1=rs.getObject(1).toString();
                
                rs.absolute(n);
                comp2=rs.getObject(1).toString();
          
         
           if(comp1.equals(comp2)) { 
               
                 notunicity.setText("<html>ATTENTION, DES REFERENCES SONT DUPLIQUEES<br><br>"+comp1+"</html>");
               JOptionPane.showMessageDialog(this,notunicity,"Unicité",
                   JOptionPane.ERROR_MESSAGE);
  
           flag_unicity=1;
               }
                 
        }
          
        
           }
        
        if(flag_unicity!=1)
        JOptionPane.showMessageDialog(this,testpasse, "Unicité", JOptionPane.INFORMATION_MESSAGE);  flag_unicity=0;
        
           }
         
         
         
           else if(event.getSource()==cont_menu_option3)
           {  modiftree_dial.setSize(500, 350);
              modiftree_dial.setLocationRelativeTo(this);
              modiftree_dial.setVisible(true);
               
         
           }
         
           else if(event.getSource()==OK_modifnum)
         
           {    String cont_cam_tomodif=camiontomodif.getText();
                String cont_newtrucknum=modifiedcam.getText();
                
                 JLabel message_notruck2= new JLabel("CAMION INEXISTANT");
                 message_notruck2.setFont(new Font("Arial",Font.BOLD,20));
              boolean checknumber3=false;
                int childcount3=camion.getChildCount();
               
               for(int childcount_increment3=0; childcount_increment3<childcount3;childcount_increment3++)
                   
               {      if ((camion.getChildAt(childcount_increment3).toString()).equals(cont_cam_tomodif))
                              
                   checknumber3=true;
                      
                
               }
              
              if(checknumber3==false)     { JOptionPane.showMessageDialog(this,message_notruck2, "ERREUR", JOptionPane.ERROR_MESSAGE); }
              
              else {
                    
                    stat.executeUpdate("update immatric_cam set Numero= "+"\""+cont_newtrucknum+"\""+" where Numero= "+"\""+cont_cam_tomodif+"\"");
                    modiftree_dial.setVisible(false);
   camiontomodif.setText(null);
   modifiedcam.setText(null);
                     camion =new DefaultMutableTreeNode("CAMIONS");

  DefaultMutableTreeNode  divers =new DefaultMutableTreeNode("DIVERS"); 
  childcf=new DefaultMutableTreeNode("COUTS FIXES");
    childpv=new DefaultMutableTreeNode("PIECES VENDUES");
    
     divers.add(childcf);
   divers.add(childpv);
    camion.add(divers);
 
 try {    rs=stat.executeQuery("select Numero from immatric_cam order by Numero");   
    
    
 rs.last();
      tableng=rs.getRow();
      rs.absolute(1);
       
 liste=new String[tableng];
       
       for(int h=0; h<liste.length;h++)
       { rs.absolute(h+1);
           liste[h]=new String(rs.getObject(1).toString());     
            
    }  
 }
    
   catch(SQLException sqex)  {  sqex.printStackTrace(); } 
    
   
    DefaultMutableTreeNode[] treearray=new DefaultMutableTreeNode[liste.length];
    
    for (int v=0;v<liste.length;v++)

{      treearray[v]=new DefaultMutableTreeNode(liste[v]);

camion.add(treearray[v]);
}
    

    truck_tree=new JTree(camion);
                   truck_tree.addTreeSelectionListener(this);
    scroll_tree.setViewportView(truck_tree);
    splitthem.setLeftComponent(scroll_tree);
    checknumber3=false;
             validate();       
              }
            
         
           }      
      
      
           else if(event.getSource()==addnewtruck)
         
           {String cont_newtruck=newbrandcam.getText();
         
          JLabel message_notruck3= new JLabel("CAMION DEJA EXISTANT");
                 message_notruck3.setFont(new Font("Arial",Font.BOLD,20));
              boolean checknumber4=false;
                int childcount4=camion.getChildCount();
               
               for(int childcount_increment4=0; childcount_increment4<childcount4;childcount_increment4++)
                   
               {      if ((camion.getChildAt(childcount_increment4).toString()).equals(cont_newtruck))
                              
                   checknumber4=true;
                      
                
               }
                
               if(checknumber4==true)     { JOptionPane.showMessageDialog(this,message_notruck3, "ERREUR", JOptionPane.ERROR_MESSAGE); checknumber4=false;}
              
              else {
              stat.executeUpdate("insert into immatric_cam (Numero) values "+"("+"\""+cont_newtruck+"\""+")");
                 modiftree_dial.setVisible(false);
                 newbrandcam.setText(null);
                 
  
                     camion =new DefaultMutableTreeNode("CAMIONS");

  DefaultMutableTreeNode  divers =new DefaultMutableTreeNode("DIVERS"); 
  childcf=new DefaultMutableTreeNode("COUTS FIXES");
    childpv=new DefaultMutableTreeNode("PIECES VENDUES");
    
     divers.add(childcf);
   divers.add(childpv);
    camion.add(divers);
 
 try {    rs=stat.executeQuery("select Numero from immatric_cam");   
    
    
 rs.last();
      tableng=rs.getRow();
      rs.absolute(1);
       
 liste=new String[tableng];
       
       for(int h=0; h<liste.length;h++)
       { rs.absolute(h+1);
           liste[h]=new String(rs.getObject(1).toString());     
            
    }  
 }
    
   catch(SQLException sqex)  {  sqex.printStackTrace(); } 
    
   
    DefaultMutableTreeNode[] treearray=new DefaultMutableTreeNode[liste.length];
    
    for (int v=0;v<liste.length;v++)

{      treearray[v]=new DefaultMutableTreeNode(liste[v]);

camion.add(treearray[v]);
}
    

    truck_tree=new JTree(camion);
          truck_tree.addTreeSelectionListener(this);          
    scroll_tree.setViewportView(truck_tree);
    splitthem.setLeftComponent(scroll_tree);
    
             validate();       
              }
            
              
              
              }
      
    
      
          else if(event.getSource()==okdeletetruck)
           {  String cont_trucktodelete=trucktodelete.getText();
              
               JLabel message_notruck4= new JLabel("CAMION INEXISTANT");
                 message_notruck4.setFont(new Font("Arial",Font.BOLD,20));
              boolean checknumber5=false;
                int childcount5=camion.getChildCount();
               
               for(int childcount_increment5=0; childcount_increment5<childcount5;childcount_increment5++)
                   
               {      if ((camion.getChildAt(childcount_increment5).toString()).equals(cont_trucktodelete))
                              
                   checknumber5=true;
                      
                
               }
                
                if(checknumber5==false)     { JOptionPane.showMessageDialog(this,message_notruck4, "ERREUR", JOptionPane.ERROR_MESSAGE); } 
               
               
                else  { 
                    
                    stat.executeUpdate("delete from immatric_cam where Numero= "+"\""+cont_trucktodelete+"\"");
                      modiftree_dial.setVisible(false);
   trucktodelete.setText(null);
   
                     camion =new DefaultMutableTreeNode("CAMIONS");

  DefaultMutableTreeNode  divers =new DefaultMutableTreeNode("DIVERS"); 
  childcf=new DefaultMutableTreeNode("COUTS FIXES");
    childpv=new DefaultMutableTreeNode("PIECES VENDUES");
    
     divers.add(childcf);
   divers.add(childpv);
    camion.add(divers);
 
 try {    rs=stat.executeQuery("select Numero from immatric_cam");   
    
    
 rs.last();
      tableng=rs.getRow();
      rs.absolute(1);
       
 liste=new String[tableng];
       
       for(int h=0; h<liste.length;h++)
       { rs.absolute(h+1);
           liste[h]=new String(rs.getObject(1).toString());     
            
    }  
 }
    
   catch(SQLException sqex)  {  sqex.printStackTrace(); } 
    
   
    DefaultMutableTreeNode[] treearray=new DefaultMutableTreeNode[liste.length];
    
    for (int v=0;v<liste.length;v++)

{      treearray[v]=new DefaultMutableTreeNode(liste[v]);

camion.add(treearray[v]);
}
    

    truck_tree=new JTree(camion);
      truck_tree.addTreeSelectionListener(this);              
    scroll_tree.setViewportView(truck_tree);
    splitthem.setLeftComponent(scroll_tree);
    
             validate();       
             checknumber5=false;
                    
             }
               
            }  
      
      
            }
      
      }
 
              catch (SQLException e)
      {  
            String err_mess=e.toString();
            JOptionPane.showMessageDialog(this, err_mess,"ERREUR", JOptionPane.ERROR_MESSAGE);

         e.printStackTrace();
      }
      
      
   }

   
   
   
public void  Stockpile_calc()
{
      
         nombre_lignes=affich.getRowCount();
         
         for(k=0;k<nombre_lignes;k++)
    	
   { valeur_totale=valeur_totale+((Float.parseFloat(affich.getValueAt(k,4).toString()))*(Float.parseFloat(affich.getValueAt(k,5).toString()))); 
   
   nombre_total_pieces=nombre_total_pieces+(Float.parseFloat(affich.getValueAt(k,4).toString()));
   
   
   }
       
 
   String affichage_du_total="<html>"+"<font size=5>"+"Nombre total de pièces  : "+nombre_total_pieces+"<br>"+"<br>"+"Valeur totale  du stock : "+NumberFormat.getInstance().format(valeur_totale)+" Ar"+"</font>"+"</html>"; 
   etat_stock.setText(affichage_du_total);
   etat_stock.setVerticalAlignment(SwingConstants.CENTER);
   etat_stock.setHorizontalAlignment(JLabel.CENTER);
   etat_stock.setPreferredSize(new Dimension(350,400));
 
   
   JOptionPane.showMessageDialog(affich,etat_stock,"Etat stock", JOptionPane.PLAIN_MESSAGE);
  valeur_totale=0;
   nombre_total_pieces=0;
  Tabledisplayer(rs);

 
}

public float total_truck_dep()
{int nb_lignes_depcamions=0;
 float deptotalcamion=0;
 int m;
 
 nb_lignes_depcamions=affich2.getRowCount();
        
  for(m=0;m<nb_lignes_depcamions;m++)
  {  deptotalcamion=deptotalcamion+(Float.parseFloat(affich2.getValueAt(m,2).toString())); }
 
 return deptotalcamion;
 
}




      
  

public void Tabledisplayer(ResultSet rs_todisp)
{  if(flag!=0) { panel6.remove(scrollPane); flag=0; }
            
              model=new ResultSetTableModel(rs_todisp);
                affich=new Displayquery(model);
               scrollPane=new JScrollPane(affich);
    
              panel6.add(scrollPane);
               flag=1;
               validate();
 
              
               

 try { stat.getMoreResults(Statement.KEEP_CURRENT_RESULT);    ; }
catch(SQLException ut) { ut.printStackTrace();  }

}
   
   public static Connection getConnection()
      throws SQLException, IOException
   {  
      String driv="org.gjt.mm.mysql.Driver";
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

  
//  String drivers = props.getProperty("jdbc.drivers");
   //   if (drivers != null) System.setProperty("jdbc.drivers", drivers);
 //   String url ="jdbc:mysql://192.168.0.2/stock";
 String url="jdbc:mysql://localhost/stock";
 // String url="jdbc:mysql://localhost/stock_local";
      String username = "root";
      String password ="rien";

      return DriverManager.getConnection(url, username, password);
   }
   
   
   
    private void initComponents() {

       jDialog1 = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jDialog2 = new javax.swing.JDialog();
        tabpaneformodif = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
         line_to_modify = new javax.swing.JLabel();
        line_to_modifyjtxt = new javax.swing.JTextField();
        modif_online_design = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        modif_oneline_ref = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        modif_oneline_fourn = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        modif_oneline_quant = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        modif_oneline_val = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        modif_oneline_seuil = new javax.swing.JTextField();
        modify_oneline = new javax.swing.JButton();
        cancel_modify_oneline = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        linetosuppress = new javax.swing.JTextField();
        suppressline = new javax.swing.JButton();
        cancelsuppress = new javax.swing.JButton();
        dialog_apropos=new JDialog();
        panel_apropos=new JPanel();
        label_apropos=new JLabel();
        label_image=new JLabel();
        OK_toclose_apropos=new JButton();
         dial_aide = new javax.swing.JDialog();
        scroll_foraide = new javax.swing.JScrollPane();
        Helpcontents = new javax.swing.JLabel();
        
        jButton1.addActionListener(this);
      jButton2.addActionListener(this);
      
      modify_oneline.addActionListener(this);
      cancel_modify_oneline.addActionListener(this);
      suppressline.addActionListener(this);
      cancelsuppress.addActionListener(this);
      OK_toclose_apropos.addActionListener(this);
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Modification d'une cellule"));
   dialog_apropos.setTitle("A propos de gestion_stock");

        jLabel1.setText("Colonne:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Designation", "Reference", "Fournisseur", "Quantite_en_stock", "Valeur", "Seuil_critique" }));

        jLabel2.setText("id:");

        jLabel3.setText("Donnée:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(65, Short.MAX_VALUE))
        );

        jButton1.setText("OK");

        jButton2.setText("Annuler");

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialog1Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(50, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(73, 73, 73))))
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        
        jLabel4.setText("Designation:");

        jLabel5.setText("Reference:");

        jLabel6.setText("Fournisseur:");

        jLabel7.setText("Quantité:");

        jLabel8.setText("Valeur:");

        jLabel9.setText("Seuil:");

        modify_oneline.setText("Modifier");

        cancel_modify_oneline.setText("Annuler");

        line_to_modify.setText("Ligne:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(modify_oneline))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(line_to_modify)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(line_to_modifyjtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(modif_online_design, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(67, 67, 67))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(modif_oneline_quant, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(179, 179, 179)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(modif_oneline_ref, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(modif_oneline_val, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(46, 46, 46)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(modif_oneline_fourn, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(cancel_modify_oneline)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel9)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(modif_oneline_seuil, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(82, 82, 82)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(line_to_modify)
                    .addComponent(line_to_modifyjtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(modif_online_design, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(modif_oneline_ref, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(modif_oneline_fourn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(modif_oneline_quant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(modif_oneline_val, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(modif_oneline_seuil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(82, 82, 82)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(modify_oneline)
                    .addComponent(cancel_modify_oneline))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        tabpaneformodif.addTab("Modification", jPanel2);

        jLabel10.setText("Numéro de ligne:");

        suppressline.setText("Supprimer");

        cancelsuppress.setText("Annuler");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(suppressline, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(linetosuppress, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(98, 98, 98)
                        .addComponent(cancelsuppress, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)))
                .addContainerGap(333, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(suppressline, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(101, 101, 101)
                        .addComponent(cancelsuppress, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(linetosuppress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(55, Short.MAX_VALUE))
        );

        tabpaneformodif.addTab("Suppression", jPanel3);

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabpaneformodif, javax.swing.GroupLayout.DEFAULT_SIZE, 796, Short.MAX_VALUE)
        );
        jDialog2Layout.setVerticalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabpaneformodif, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
        
        panel_apropos.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        java.net.URL impath=getClass().getResource("dsc00071.jpg");
        ImageIcon pict=new ImageIcon(impath);
        label_image.setIcon(pict);
     
        label_apropos.setFont(new java.awt.Font("Arial", 0, 18));
        label_apropos.setText("<html>\n\n\nLogiciel de gestion de stock <br>\n© Copyright 2008  Seraly Ferid Transport\n\n</html>");

        javax.swing.GroupLayout panel_aproposLayout = new javax.swing.GroupLayout(panel_apropos);
        panel_apropos.setLayout(panel_aproposLayout);
        panel_aproposLayout.setHorizontalGroup(
            panel_aproposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_aproposLayout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addGroup(panel_aproposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_aproposLayout.createSequentialGroup()
                        .addComponent(label_apropos)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_aproposLayout.createSequentialGroup()
                        .addComponent(label_image, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67))))
        );
        panel_aproposLayout.setVerticalGroup(
            panel_aproposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_aproposLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(label_image)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_apropos, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );

        OK_toclose_apropos.setText("OK");

        javax.swing.GroupLayout dialog_aproposLayout = new javax.swing.GroupLayout(dialog_apropos.getContentPane());
        dialog_apropos.getContentPane().setLayout(dialog_aproposLayout);
        dialog_aproposLayout.setHorizontalGroup(
            dialog_aproposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialog_aproposLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(panel_apropos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dialog_aproposLayout.createSequentialGroup()
                .addContainerGap(186, Short.MAX_VALUE)
                .addComponent(OK_toclose_apropos, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(180, 180, 180))
        );
        dialog_aproposLayout.setVerticalGroup(
            dialog_aproposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialog_aproposLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(panel_apropos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(OK_toclose_apropos)
                .addContainerGap())
        );
        
        Helpcontents.setText("<HTML> <HEAD> <TITLE></TITLE>  </HEAD> <BODY LANG=\"fr-FR\" DIR=\"LTR\">" +
                " <P ALIGN=CENTER STYLE=\"margin-bottom: 0cm; background: #00ffff; border: 1.10pt double #000000; padding: 0.05cm\"> " +
                "<FONT FACE=\"Arial, sans-serif\"><FONT SIZE=12><B>RUBRIQUE D'AIDE</B></FONT></FONT></P> <P ALIGN=CENTER STYLE=\"margin-bottom: 0cm\">" +
                "<BR> </P> <P ALIGN=CENTER STYLE=\"margin-bottom: 0cm\"><BR> </P> <P ALIGN=CENTER STYLE=\"margin-bottom: 0cm\"><BR>" +
                " </P> <font size=4>  <P ALIGN=LEFT STYLE=\"margin-bottom: 0cm\"><FONT COLOR=\"#0000ff\"><FONT FACE=\"Arial, sans-serif\">" +
                "<B>1. <U>Lecture dans le  stock</U></B></FONT></FONT></P> <P ALIGN=LEFT STYLE=\"margin-bottom: 0cm; text-decoration: none\">" +
                "<FONT FACE=\"Arial, sans-serif\">Le bouton &laquo;&nbsp;Afficher tout le stock&nbsp;&raquo; permet de lister la totalit&eacute; de tout le stock.</FONT>" +
                "</P> <P ALIGN=LEFT STYLE=\"margin-bottom: 0cm; text-decoration: none\">" +
                "<FONT FACE=\"Arial, sans-serif\">Le bouton &laquo;&nbsp;Rechercher&nbsp;&raquo; permet" +
                " d'afficher les articles correspondant aux mots-cl&eacute; entr&eacute;s dans la" +
                "case &laquo;&nbsp;R&eacute;f&eacute;rence&nbsp;&raquo;</FONT></P> " +
                "<P ALIGN=LEFT STYLE=\"margin-bottom: 0cm; text-decoration: none\"><BR> </P> " +
                "<P ALIGN=LEFT STYLE=\"margin-bottom: 0cm; text-decoration: none\"><FONT COLOR=\"#0000ff\">" +
                "<FONT FACE=\"Arial, sans-serif\"><B>2. <U>Entr&eacute;es - Sorties</U></B></FONT></FONT></P> " +
                "</BODY> </HTML>");
        scroll_foraide.setViewportView(Helpcontents);

        javax.swing.GroupLayout dial_aideLayout = new javax.swing.GroupLayout(dial_aide.getContentPane());
        dial_aide.getContentPane().setLayout(dial_aideLayout);
        dial_aideLayout.setHorizontalGroup(
            dial_aideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll_foraide, javax.swing.GroupLayout.DEFAULT_SIZE, 771, Short.MAX_VALUE)
        );
        dial_aideLayout.setVerticalGroup(
            dial_aideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll_foraide, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
        );



        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

      
    }// </editor-fold>
    
    
      private void initComponents2() {
Calendar totime=Calendar.getInstance();
          
          
   camion =new DefaultMutableTreeNode("CAMIONS");

  DefaultMutableTreeNode  divers =new DefaultMutableTreeNode("DIVERS"); 
  childcf=new DefaultMutableTreeNode("COUTS FIXES");
    childpv=new DefaultMutableTreeNode("PIECES VENDUES");
    childreport=new DefaultMutableTreeNode("RESULTAT MENSUEL");
  
    
     divers.add(childcf);
   divers.add(childpv);
   divers.add(childreport);
  
    camion.add(divers);
 
 try {    rs=stat.executeQuery("select Numero from immatric_cam order by Numero");   
    
    
 rs.last();
      tableng=rs.getRow();
      rs.absolute(1);
       
 liste=new String[tableng];
       
       for(int h=0; h<liste.length;h++)
       { rs.absolute(h+1);
           liste[h]=new String(rs.getObject(1).toString());     
            
    }  
 }
    
   catch(SQLException sqex)  {  sqex.printStackTrace(); } 
    
   
    DefaultMutableTreeNode[] treearray=new DefaultMutableTreeNode[liste.length];
    
    for (int v=0;v<liste.length;v++)

{      treearray[v]=new DefaultMutableTreeNode(liste[v]);

camion.add(treearray[v]);
}
    

    truck_tree=new JTree(camion);
        
        pan_for_depdetail = new javax.swing.JPanel();
        dep_cam_lb = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        splitthem = new javax.swing.JSplitPane();
        scroll_tree = new javax.swing.JScrollPane(truck_tree);
    
        contains_table_and_buttons = new javax.swing.JPanel();
        numcam_lb = new javax.swing.JLabel();
        enternumcam = new javax.swing.JTextField();
      
        enternumcam.setFont(new Font("Arial",Font.BOLD,14));
        enternumcam.addKeyListener(this);
        display_truck_dep = new javax.swing.JButton();
        display_truck_dep.addActionListener(this);
        contains_only_table = new javax.swing.JPanel();
        contains_total = new javax.swing.JPanel();
dep_cam_lb.setOpaque(true);
          dep_cam_lb.setBackground(new java.awt.Color(204, 204, 204));
         dep_cam_lb.setForeground(new java.awt.Color(51, 51, 255));
           dep_cam_lb.setFont(new java.awt.Font("Arial", 1, 28));
        dep_cam_lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dep_cam_lb.setText("DEPENSES PAR CAMION");
        dep_cam_lb.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        depcamionmensuel = new javax.swing.JLabel();
        which_cam_lb = new javax.swing.JLabel();
        splitthem.setLeftComponent(scroll_tree);

       contains_table_and_buttons.setBackground(new java.awt.Color(0,234,204));
  contains_only_table.setPreferredSize(new java.awt.Dimension(600, 500));
        numcam_lb.setFont(new java.awt.Font("Arial", 1, 12));
        numcam_lb.setText("Numero du camion:");

        display_truck_dep.setText("Afficher");
     
        display_truck_dep.setMinimumSize(new Dimension(100, 30));
        truck_tree.addTreeSelectionListener(this);
       
        scroll_tree.setMinimumSize(new Dimension(205,23));
       pan_for_depdetail.setOpaque(true);
       pan_for_depdetail.setBackground(Color.BLUE);
       String[] the_months = { "Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Decembre" };
     String[] the_years={  "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018" } ;
 month_chooser=new JComboBox(the_months);
 System.out.println(totime.MONTH);
 month_chooser.setSelectedIndex(totime.get(Calendar.MONTH));
 JLabel month_lb=new JLabel("Mois: ");
 month_lb.setFont(new java.awt.Font("Arial", 1, 12));
 
 JLabel year_lb=new JLabel("Année: ");
 year_lb.setFont(new java.awt.Font("Arial", 1, 12));
 
 
 year_chooser=new JComboBox(the_years);

 year_chooser.setSelectedIndex(getcurrentyear());
 
 
  modiftree_dial = new javax.swing.JDialog();
        tab_modiftree = new javax.swing.JTabbedPane();
        modiftree = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        camiontomodif = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        modifiedcam = new javax.swing.JTextField();
        OK_modifnum = new javax.swing.JButton();
        cancel_modifnum = new javax.swing.JButton();
        addtree = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        newbrandcam = new javax.swing.JTextField();
        addnewtruck = new javax.swing.JButton();
        canceladdnewtruck = new javax.swing.JButton();
        deletetree = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        trucktodelete = new javax.swing.JTextField();
        okdeletetruck = new javax.swing.JButton();
        canceldeletetruck = new javax.swing.JButton();
 
 
   jLabel12.setText("Numéro du camion à modifier: ");
    jLabel13.setText("Nouveau numéro: ");

        OK_modifnum.setText("Modifier");
        OK_modifnum.addActionListener(this);
        addnewtruck.addActionListener(this);
        okdeletetruck.addActionListener(this);
        cancel_modifnum.addActionListener(this);
        canceladdnewtruck.addActionListener(this);
        canceldeletetruck.addActionListener(this);
        
        

        cancel_modifnum.setText("Annuler");
        
       
      javax.swing.GroupLayout contains_only_tableLayout = new javax.swing.GroupLayout(contains_only_table);
        contains_only_table.setLayout(contains_only_tableLayout);
        contains_only_tableLayout.setHorizontalGroup(
            contains_only_tableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 699, Short.MAX_VALUE)
        );
        contains_only_tableLayout.setVerticalGroup(
            contains_only_tableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 341, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout contains_table_and_buttonsLayout = new javax.swing.GroupLayout(contains_table_and_buttons);
        contains_table_and_buttons.setLayout(contains_table_and_buttonsLayout);
         contains_table_and_buttonsLayout.setHorizontalGroup(
        contains_table_and_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contains_table_and_buttonsLayout.createSequentialGroup()
                .addGroup(contains_table_and_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contains_table_and_buttonsLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(contains_only_table, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(contains_table_and_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(contains_table_and_buttonsLayout.createSequentialGroup()
                                .addGap(88, 88, 88)
                                .addComponent(depcamionmensuel, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(contains_table_and_buttonsLayout.createSequentialGroup()
                                .addGap(108, 108, 108)
                                .addComponent(which_cam_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(contains_table_and_buttonsLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(numcam_lb)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(enternumcam, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)
                        .addComponent(display_truck_dep)
                        .addGap(100, 100,100)
                        .addComponent(month_lb)
                        .addGap(10, 10, 10)
                        .addComponent(month_chooser,javax.swing.GroupLayout.PREFERRED_SIZE,100,javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47,47,47)
                        .addComponent(year_lb)
                        .addGap(10,10,10)
                        .addComponent(year_chooser,javax.swing.GroupLayout.PREFERRED_SIZE,100,javax.swing.GroupLayout.PREFERRED_SIZE)
                        ))
                .addContainerGap(184, Short.MAX_VALUE))
        );
        contains_table_and_buttonsLayout.setVerticalGroup(
            contains_table_and_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contains_table_and_buttonsLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(contains_table_and_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numcam_lb)
                    .addComponent(enternumcam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(display_truck_dep)
                    .addComponent(month_lb)
                    .addComponent(month_chooser)
                    .addComponent(year_lb)
                    .addComponent(year_chooser)
                    )
                .addGap(37, 37, 37)
                .addComponent(contains_only_table, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contains_table_and_buttonsLayout.createSequentialGroup()
                .addContainerGap(177, Short.MAX_VALUE)
                .addComponent(which_cam_lb)
                .addGap(76, 76, 76)
                .addComponent(depcamionmensuel, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(237, 237, 237))
        );
        splitthem.setRightComponent(contains_table_and_buttons);

      
          javax.swing.GroupLayout pan_for_depdetailLayout = new javax.swing.GroupLayout(pan_for_depdetail);
        pan_for_depdetail.setLayout(pan_for_depdetailLayout);
        pan_for_depdetailLayout.setHorizontalGroup(
            pan_for_depdetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1278, Short.MAX_VALUE)
            .addGroup(pan_for_depdetailLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dep_cam_lb, javax.swing.GroupLayout.DEFAULT_SIZE, 1258, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(splitthem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1278, Short.MAX_VALUE)
        );
        
        
        
        pan_for_depdetailLayout.setVerticalGroup(
            pan_for_depdetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_for_depdetailLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(dep_cam_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(splitthem, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
                .addContainerGap())
        );   
     
      
        
        
          javax.swing.GroupLayout modiftreeLayout = new javax.swing.GroupLayout(modiftree);
        modiftree.setLayout(modiftreeLayout);
        modiftreeLayout.setHorizontalGroup(
            modiftreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modiftreeLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(modiftreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, modiftreeLayout.createSequentialGroup()
                        .addComponent(OK_modifnum)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 256, Short.MAX_VALUE)
                        .addComponent(cancel_modifnum)
                        .addGap(84, 84, 84))
                    .addGroup(modiftreeLayout.createSequentialGroup()
                        .addGroup(modiftreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(modiftreeLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(modifiedcam, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(modiftreeLayout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(camiontomodif, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(264, Short.MAX_VALUE))))
        );
        modiftreeLayout.setVerticalGroup(
            modiftreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modiftreeLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(modiftreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(camiontomodif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addGroup(modiftreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(modifiedcam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addGroup(modiftreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancel_modifnum)
                    .addComponent(OK_modifnum, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37))
        );

        tab_modiftree.addTab("Modification", modiftree);

        jLabel14.setText("Nouveau numéro: ");

        addnewtruck.setText("Ajouter");

        canceladdnewtruck.setText("Annuler");

        javax.swing.GroupLayout addtreeLayout = new javax.swing.GroupLayout(addtree);
        addtree.setLayout(addtreeLayout);
        addtreeLayout.setHorizontalGroup(
            addtreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addtreeLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(addnewtruck)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 272, Short.MAX_VALUE)
                .addComponent(canceladdnewtruck)
                .addGap(71, 71, 71))
            .addGroup(addtreeLayout.createSequentialGroup()
                .addGap(119, 119, 119)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(newbrandcam, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(272, Short.MAX_VALUE))
        );
        addtreeLayout.setVerticalGroup(
            addtreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addtreeLayout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addGroup(addtreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(newbrandcam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                .addGroup(addtreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addnewtruck)
                    .addComponent(canceladdnewtruck))
                .addGap(36, 36, 36))
        );

        tab_modiftree.addTab("Ajout", addtree);

        jLabel15.setText("Camion à supprimer: ");

        okdeletetruck.setText("Supprimer");

        canceldeletetruck.setText("Annuler");

        javax.swing.GroupLayout deletetreeLayout = new javax.swing.GroupLayout(deletetree);
        deletetree.setLayout(deletetreeLayout);
        deletetreeLayout.setHorizontalGroup(
            deletetreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deletetreeLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(okdeletetruck)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 280, Short.MAX_VALUE)
                .addComponent(canceldeletetruck)
                .addGap(61, 61, 61))
            .addGroup(deletetreeLayout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(trucktodelete, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(268, Short.MAX_VALUE))
        );
        deletetreeLayout.setVerticalGroup(
            deletetreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deletetreeLayout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addGroup(deletetreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(trucktodelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addGroup(deletetreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okdeletetruck)
                    .addComponent(canceldeletetruck))
                .addGap(33, 33, 33))
        );

        tab_modiftree.addTab("Suppression", deletetree);

        javax.swing.GroupLayout modiftree_dialLayout = new javax.swing.GroupLayout(modiftree_dial.getContentPane());
        modiftree_dial.getContentPane().setLayout(modiftree_dialLayout);
        modiftree_dialLayout.setHorizontalGroup(
            modiftree_dialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tab_modiftree, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE)
        );
        modiftree_dialLayout.setVerticalGroup(
            modiftree_dialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tab_modiftree, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        
        
        
        
        
        
        
validate();
   
      }
    
    
   
   public  String getmonth()
   {  Calendar cal=Calendar.getInstance();
    
      String monthvalue="";
       int monthnumber=cal.get(Calendar.MONTH);
       if (monthnumber==0)  monthvalue="Janvier";
   
    else if(monthnumber==1) monthvalue="Fervrier";
    else if(monthnumber==2)  monthvalue= "Mars";
    else if(monthnumber==3)  monthvalue= "Avril";
     else if(monthnumber==4)  monthvalue= "Mai";      
    else if(monthnumber==5)  monthvalue= "Juin";
    else if(monthnumber==6)  monthvalue= "Juillet";
    else if(monthnumber==7)  monthvalue= "Aout";
    else if(monthnumber==8)  monthvalue="Septembre";
    else if(monthnumber==9) monthvalue="Octobre";
    else if(monthnumber==10) monthvalue="Novembre";
    else if(monthnumber==11) monthvalue="Decembre";
    
return monthvalue+cal.get(Calendar.YEAR);
    
   }
    
 
   
   

   public JScrollPane scrollPane, scroll_foraide, scrollpane2;
   private ResultSetTableModel model, model2;
   private JComboBox tableNames, month_chooser,year_chooser;
   private ResultSet rs,rs2;
   private ResultSetMetaData forcounting;
   private Connection conn;
   private Statement stat,stat2;
   private boolean scrolling;
   private JButton recherche,Deduire, toutlestock,new_pc,Ajouter,verif_stock,nbr_piece_critique;
   private JTextField entree,filtre,sortie,reference_sortie,reference_entree,design_nv,ref_nv,quantite_nv,valeur_piece,fournisseur_nv,seuil,numerocamion;
   private JPanel panel2,p, panel5, panel6;
   private int flag=0;
   private JLabel erreur,etat_stock,champ_vide,fournisseur_lb,seuil_lb;
   private JTable table;
   private  Color coul;
     private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2,dialog_apropos, dial_aide;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
      private javax.swing.JButton cancel_modify_oneline;
    private javax.swing.JButton cancelsuppress,OK_toclose_apropos;
      private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel10, label_apropos, label_image, Helpcontents;
     private javax.swing.JLabel line_to_modify;
     private javax.swing.JTextField line_to_modifyjtxt;
      private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3,  panel_apropos;
     private javax.swing.JTextField linetosuppress;
    private javax.swing.JTextField modif_oneline_fourn;
    private javax.swing.JTextField modif_oneline_quant;
    private javax.swing.JTextField modif_oneline_ref;
    private javax.swing.JTextField modif_oneline_seuil;
    private javax.swing.JTextField modif_oneline_val;
    private javax.swing.JTextField modif_online_design;
    private javax.swing.JButton modify_oneline;
    private javax.swing.JButton suppressline;
    private javax.swing.JTabbedPane tabpaneformodif;
    
      private javax.swing.JPanel contains_only_table;
    private javax.swing.JPanel contains_table_and_buttons;
    private javax.swing.JPanel contains_total;
    private javax.swing.JLabel dep_cam_lb;
    private javax.swing.JButton display_truck_dep;
    private javax.swing.JTextField enternumcam;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel numcam_lb;
    private javax.swing.JPanel pan_for_depdetail;
    private javax.swing.JScrollPane scroll_tree;
    private javax.swing.JSplitPane splitthem;
    private JTree truck_tree;
    private  DefaultMutableTreeNode camion,childcf,childpv,childreport;
    
      private javax.swing.JButton OK_modifnum;
    private javax.swing.JButton addnewtruck;
    private javax.swing.JPanel addtree;
    private javax.swing.JTextField camiontomodif;
    private javax.swing.JButton cancel_modifnum;
    private javax.swing.JButton canceladdnewtruck;
    private javax.swing.JButton canceldeletetruck;
    private javax.swing.JPanel deletetree;
    
      private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JTextField modifiedcam;
    private javax.swing.JPanel modiftree;
    private javax.swing.JDialog modiftree_dial;
    private javax.swing.JTextField newbrandcam;
    private javax.swing.JButton okdeletetruck;
     private javax.swing.JTabbedPane tab_modiftree;
    private javax.swing.JTextField trucktodelete;
    
    
    
  
 private javax.swing.JLabel which_cam_lb;
  private  Displayquery affich, affich2;
  private int flag_disp_dep=0;
  JLabel depcamionmensuel;

   private int k, nombre_lignes=0;
   private float all_expenses,  nombre_total_pieces=0;
    float collectdeptotalcamion;
   private  double valeur_totale=0;
   private int childcount_increment, childcount_increment2;
  public String[] liste;
   public Float[]  eachtruckmonthlydep;
   public int tableng;
   
   
   

    public void valueChanged(TreeSelectionEvent e) {
 
   
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                           truck_tree.getLastSelectedPathComponent();
      
 //   String collectnumcam=(String)enternumcam.getText();
        String collectmonth=month_chooser.getSelectedItem().toString();
        String collectyear=year_chooser.getSelectedItem().toString();
        
        if(((node.getUserObject().toString().equals("DIVERS"))==false)&&((node.getUserObject().toString().equals("CAMIONS"))==false))  
        {
        
        
    
    if (flag_disp_dep!=0) { contains_only_table.remove(scrollpane2);
    flag_disp_dep=0; }
    
    if(node.getUserObject().toString().equals("COUTS FIXES"))
        
  {  if((year_chooser.getSelectedIndex()==0)||((year_chooser.getSelectedIndex()==1)&&(month_chooser.getSelectedIndex()<=3)))

       {
           exquery("select Designation,Quantite,Cout_fixe,Periode from cout_fixe where Periode= "+"\""+collectmonth+collectyear+"\"");   }
       else
        exquery("select Designation,Quantite,Cout_fixe,  DATE_FORMAT(Date_sortie , '%d-%b-%Y')   as Date from cout_fixe where  Periode= "+"\""+collectmonth+collectyear+"\"");   camdepdisplayer(node);     }

    else  if(node.getUserObject().toString().equals("PIECES VENDUES"))
         {
         if((year_chooser.getSelectedIndex()==0)||((year_chooser.getSelectedIndex()==1)&&(month_chooser.getSelectedIndex()<=3)))
         {  exquery("select Designation,Quantite,Valeur, Periode from vente where Periode= "+"\""+collectmonth+collectyear+"\""); }

         else
        exquery("select Designation,Quantite,Valeur,   DATE_FORMAT(Date_sortie , '%d-%b-%Y')   as Date  from vente where  Periode= "+"\""+collectmonth+collectyear+"\"");      camdepdisplayer(node);       }


    else  if(node.getUserObject().toString().equals("RESULTAT MENSUEL"))

    { float receiver_g=0;
      float receiver_p=0;
        String gourmand=null;
        String econome=null;
        eachtruckmonthlydep=new Float[tableng];

      for (int z=0;z<eachtruckmonthlydep.length;z++)

      {     String tamp=exquery2("select sum(depenses) from  depenses_camions_detail where num_camion="+
      "\""+liste[z]+"\""+" and Periode="+"\""+collectmonth+collectyear+"\"");

            eachtruckmonthlydep[z]=new Float(Float.parseFloat(tamp));

               }


 for(int ppt=0;ppt<eachtruckmonthlydep.length;ppt++)

 {    if(receiver_g<Math.max(receiver_g,eachtruckmonthlydep[ppt]))

      { receiver_g=Math.max(receiver_g, eachtruckmonthlydep[ppt]);
 gourmand=liste[ppt];   }
 }



    receiver_p=eachtruckmonthlydep[0];

     for(int gdf=0;gdf<eachtruckmonthlydep.length;gdf++)

 {    if((receiver_p>Math.min(receiver_p,eachtruckmonthlydep[gdf]))&&(eachtruckmonthlydep[gdf]!=0))

      { receiver_p=Math.min(receiver_p, eachtruckmonthlydep[gdf]);
 econome=liste[gdf];   }
 }

  float depdumois=sumofdep();


    JLabel resultmensuel=new JLabel();

    resultmensuel.setPreferredSize(new Dimension(420, 300));
 resultmensuel.setText("<HTML>\n\n<BODY LANG=\"fr-FR\" BGCOLOR=\"#ffff66\" DIR=\"LTR\">\n<P ALIGN=CENTER STYLE=\"margin-bottom: 0cm; background: #00ffff; border: none; padding: 0cm; text-decoration: none\">\n<FONT COLOR=\"#000000\"><FONT FACE=\"Arial, sans-serif\"><FONT SIZE=7><B><SPAN STYLE=\"background: transparent\">RESULTAT\nMENSUEL</SPAN></B></FONT></FONT></FONT></P>\n<P STYLE=\"margin-bottom: 0cm\"><BR>\n</P>\n<P STYLE=\"margin-bottom: 0cm\"><BR>\n</P>\n<P STYLE=\"margin-bottom: 0cm\"><BR>\n</P>\n<P STYLE=\"margin-bottom: 0cm\"><BR>\n</P>\n<P STYLE=\"margin-bottom: 0cm\"><FONT FACE=\"Arial, sans-serif\"><FONT SIZE=5><B>DEPENSE\nMAXIMALE : "+NumberFormat.getInstance().format(receiver_g)+" Ar"+ " ("+gourmand+")"+"</B></FONT></FONT>\n</P>\n<P STYLE=\"margin-bottom: 0cm\"><BR>\n</P>\n<P STYLE=\"margin-bottom: 0cm\"><FONT FACE=\"Arial, sans-serif\"><FONT SIZE=5><B>DEPENSE\nMINIMALE : "+NumberFormat.getInstance().format(receiver_p)+" Ar"+" ("+econome+")"+"</B></FONT></FONT>\n</P>\n<P STYLE=\"margin-bottom: 0cm\"><BR>\n</P>\n<P STYLE=\"margin-bottom: 0cm\"><FONT FACE=\"Arial, sans-serif\"><FONT SIZE=5><B>DEPENSE\nTOTALE DU MOIS : "+NumberFormat.getInstance().format(depdumois)+" Ar"+ "</B></FONT></FONT>\n</P>\n</BODY>\n</HTML>");

 JOptionPane.showMessageDialog(this, resultmensuel, "DEPENSES MENSUELLES", JOptionPane.INFORMATION_MESSAGE);



    }




    else {

      if((year_chooser.getSelectedIndex()==0)||((year_chooser.getSelectedIndex()==1)&&(month_chooser.getSelectedIndex()<=3)))
      {    exquery( "select Designation, Quantite, Depenses, Periode from depenses_camions_detail where num_camion= "+
                "\""+node.getUserObject().toString()+"\""+" AND Periode= "+"\""+collectmonth+collectyear+"\"");  }

      else

         exquery("select Designation,Quantite, Depenses,  DATE_FORMAT(Date_sortie , '%d-%b-%Y')   as Date from depenses_camions_detail  where  num_camion= "+
                "\""+node.getUserObject().toString()+"\""+" AND  Periode= "+"\""+collectmonth+collectyear+"\"");

     camdepdisplayer(node);
        }

 try { stat.getMoreResults(Statement.KEEP_CURRENT_RESULT);    ; }
catch(SQLException uz) { uz.printStackTrace();  }




        }
    }



    
    public void exquery(String quer)
    { try { stat2=conn.createStatement();
  //    System.out.println(quer);
      
       rs2=stat.executeQuery(quer); }
      
      
       catch(SQLException toto)
    {  toto.printStackTrace(); }  
            
    
}
    
    
    
  public String exquery2(String quer)
    { String theoutput = null;
      
      try { stat2=conn.createStatement();
     
      
       rs2=stat.executeQuery(quer);
      rs2.absolute(1);
      
      if(rs2.getObject(1)==null)
      { theoutput="0";   }
      
      else
      {   theoutput=rs2.getObject(1).toString();  }
      }
      
    
       catch(SQLException toto)
    {  toto.printStackTrace(); }  
            
    return theoutput;
}
  
  
  public float sumofdep()
          
  {   float contentofsum=0;  
      
      try { stat2=conn.createStatement();
     
      
       rs2=stat.executeQuery("select sum(depenses) from  depenses_camions_detail where Periode="+"\""+month_chooser.getSelectedItem().toString()+year_chooser.getSelectedItem().toString()+"\"");

       
       rs2.absolute(1);
  contentofsum=Float.parseFloat(rs2.getObject(1).toString()); 
  
  }
    
    
   catch(SQLException toto)
    {  toto.printStackTrace(); }    
    
    return contentofsum;
  }
    
    

    public void keyTyped(KeyEvent e) {
       
    }

    public void keyPressed(KeyEvent e) {
       int keycode=e.getKeyCode();
       if((keycode==KeyEvent.VK_ENTER)&&e.getSource()==filtre)
       {    try {
                String inside_seek = filtre.getText();


                rs = stat.executeQuery("select * from gestion_stock where Designation regexp " + "\"" + inside_seek + "\"" + " or" + " reference regexp " + "\"" + inside_seek + "\"");
          
              if(rs.last()==false) {   JOptionPane.showMessageDialog(this,erreur,"ERREUR" , JOptionPane.ERROR_MESSAGE); }
              else { Tabledisplayer(rs);  }
                
               
                
                
            
            } catch (SQLException ex) {
                Logger.getLogger(ResultSetFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
           
       } 
       
       else if((keycode==KeyEvent.VK_ENTER)&&(e.getSource()==enternumcam))      
       
       
       {     display_truck_dep.doClick();         }
       
       
       
       
    }

    public void keyReleased(KeyEvent e) {
        
    }
    
    
    public void camdepdisplayer(DefaultMutableTreeNode noeud)
    
    {  
            model2 = new ResultSetTableModel(rs2);
            affich2 = new Displayquery(model2);
            scrollpane2 = new JScrollPane(affich2);
affich2.sizeColumnsToFit(0);
            contains_only_table.setLayout(new GridLayout());

            depcamionmensuel.setSize(230, 80);

            depcamionmensuel.setHorizontalAlignment(SwingConstants.CENTER);

            scrollpane2.setPreferredSize(new Dimension(300, 500));

            contains_only_table.add(scrollpane2);

            collectdeptotalcamion = total_truck_dep();
            flag_disp_dep = 1;
            depcamionmensuel.setOpaque(true);
            depcamionmensuel.setBorder(BorderFactory.createRaisedBevelBorder());
            depcamionmensuel.setBackground(Color.yellow);
            depcamionmensuel.setFont(new Font("Arial", Font.BOLD, 18));
            depcamionmensuel.setText("TOTAL : " + NumberFormat.getInstance().format(collectdeptotalcamion) + " Ar");
            contains_table_and_buttons.add(depcamionmensuel);

            which_cam_lb.setMinimumSize(new Dimension(390, 40));
            which_cam_lb.setOpaque(true);
            which_cam_lb.setBackground(Color.yellow);
            which_cam_lb.setFont(new Font("Arial", Font.BOLD, 18));
            which_cam_lb.setBorder(BorderFactory.createRaisedBevelBorder());
            which_cam_lb.setText(noeud.getUserObject().toString());
            which_cam_lb.setHorizontalAlignment(SwingConstants.CENTER);
           
            validate();
    }
    
    
    public int getcurrentyear()
    {  Calendar fortheyear=Calendar.getInstance();
       int yeartamp=0;
       switch(fortheyear.get(Calendar.YEAR))
       
       {  case 2008 : yeartamp=0;
          break;
           case 2009 : yeartamp=1;
           break;
                  
           case 2010: yeartamp=2;
                  break;
           case 2011 : yeartamp=3;
                          break;
           case 2012 : yeartamp=4;
                                  break;
           case 2013 : yeartamp=5;
                       break;                   
           case 2014 : yeartamp=6;
                        break;                          
           case 2015 : yeartamp=7;
                           break;                               
           case 2016: yeartamp=8;
                          break;                                        
           case 2017: yeartamp=9;
                          break;                                                
           case 2018 : yeartamp=10;
                        break;                                                          
          
                   
           default : ; 
           
       }
       
       
       return yeartamp;
        
    }
    
    
    
        

}