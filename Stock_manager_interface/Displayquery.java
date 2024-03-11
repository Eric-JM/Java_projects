import com.sun.rowset.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import java.util.*;
import javax.swing.table.*;
import javax.sql.rowset.*;
import java.text.NumberFormat;

class Displayquery extends JTable {
     int comm;
    ResultSetTableModel modl;
    ResultSetTableModel rst_loc;
    JTableHeader tableheader;
   
    
    public Displayquery(ResultSetTableModel rst)
    {  super(rst);
   
   
         setFont(new Font("Sans Serif", Font.BOLD,14));
         setDefaultRenderer(Object.class,new CenterTableCellRenderer());
         setDefaultRenderer(Object.class, new NumberRenderer());
         
         
         this.tableheader=new JTableHeader(); 
        this.tableheader=this.getTableHeader();
        
        this.tableheader.setFont((new Font("Arial", Font.BOLD,14)));
        this.tableheader.setForeground(Color.RED);
        this.tableheader.setBackground(Color.YELLOW);
    
  }
    
    
      public class NumberRenderer extends DefaultTableCellRenderer
   {
   	private NumberFormat formatter;
    
   	public NumberRenderer()
   	{
   		this(NumberFormat.getNumberInstance());
   	}
    
   	public NumberRenderer(NumberFormat formatter)
   	{
   		super();
   		this.formatter = formatter;
   	 setHorizontalAlignment(CENTER);
     setVerticalAlignment(CENTER);
   	}
    
    
   	public void setValue(Object value)
   	{
   		if ((value != null) && (value instanceof Number))
   		{
   			value = formatter.format(value);
   		}
    
   		super.setValue(value);
   	}
   }
      
      
       public class CenterTableCellRenderer extends DefaultTableCellRenderer {
    public CenterTableCellRenderer() {
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
        
          
    }
}
       
}


     
       
      
