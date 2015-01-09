
package javaapplication1;

import com.sun.mail.smtp.SMTPTransport;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import java.io.*;
import java.security.Security;
import java.util.*;
import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;

class User implements Serializable
{
    public
        String name,tel,email,ID,password;
            String getName()
            {
                return name;
            }
            
            String getTel()
            {
                return tel;
            }
            String getEmail()
            {
                return email;
            }
            String getID()
            {
                return ID;
            }
            String getPassword()
            {
                return password;
            }
            
            void setName(String Name)
            {
                name=Name;
            }
            void setEmail(String Email)
            {
                email=Email;
            }
            void setPassword(String Password)
            {
                password=Password;
            }
            void setTel(String Tel)
            {
                tel=Tel;
            }
    
}

class Customer extends User implements Serializable
{
   public
   String city,im_ID;
   ArrayList<Item> listItem=new ArrayList<Item>();
   
   
   Customer(String name,String tel,String email,String ID,String password,String city,String im_ID)
   {
       this.name=name;
       this.tel=tel;
       this.email=email;
       this.ID=ID;
       this.password=password;
       this.city=city;
       this.im_ID=im_ID;   
   }
   
   String getCity()
   {
       return city;
   }
   String getIM()
   {
       return im_ID;
   }
   ArrayList<Item> getListItem()
   {
       return listItem;
       
   }
    void setCity(String city)
    {
       this.city=city; 
    }
    void setIM_ID(String im)
    {
       this.im_ID=im; 
    }
    void sendEmail()
    {
        
    }                  
}

class Manager extends User implements Serializable
{
    private
    String gender;
    String dob;
    String addr;
    String im_ID;
    String bio_ID;
    
    public
    Manager(String name,String tel,String email,String ID,String password,String gender,String dob,String addr, String im_ID,String bio_ID)
    {
       this.name=name;
       this.tel=tel;
       this.email=email;
       this.ID=ID;
       this.password=password;
       this.bio_ID=bio_ID;
       this.im_ID=im_ID;  
       this.gender=gender;
       this.dob=dob;
       this.addr=addr;
    }
    String getGender()
    {
        return this.gender;
    }
    String getDOB()
    {
        return this.dob;
    }
    String getAddr()
    {
        return this.addr;
    }
    String getIM()
    {
        return this.im_ID;
    }
    String getBIO()
    {
        return this.bio_ID;
    }
    boolean deleteItem()
    {
       return true; 
    }
    void setAddr(String addr)
    {
        this.addr=addr;
    }
    void setIM(String im)
    {
        this.im_ID=im;
    }
    void setBIO(String bio)
    {
        this.bio_ID=bio;
    }
    void sendEmail(Customer receiver)
    {
        
    }  
}

class Database
{
    public
    ArrayList<Category> listCategory=new ArrayList<Category>();
    ArrayList<Manager> listManager=new ArrayList<Manager>();
    ArrayList<Customer> listCustomer=new ArrayList<Customer>();
    ArrayList<Item> listItem=new ArrayList<Item>();
    ArrayList<Item> listPurchase=new ArrayList<Item>();
    
    Database()
    {
        try {
            File file = new File("Category.txt");
            if(file.exists())
            {
           InputStream file1 = new FileInputStream("Category.txt");
           InputStream buffer = new BufferedInputStream(file1);
           ObjectInput output = new ObjectInputStream(buffer);
           listCategory=(ArrayList<Category>)(ArrayList)output.readObject();
           output.close();  
            }
            
            file = new File("Manager.txt");
            if(file.exists())
            {
           InputStream file1 = new FileInputStream("Manager.txt");
           InputStream buffer = new BufferedInputStream(file1);
           ObjectInput output = new ObjectInputStream(buffer);
           listManager=(ArrayList<Manager>)(ArrayList)output.readObject();
           output.close();  
            }
            
            file = new File("Customer.txt");
            if(file.exists())
            {
           InputStream file1 = new FileInputStream("Customer.txt");
           InputStream buffer = new BufferedInputStream(file1);
           ObjectInput output = new ObjectInputStream(buffer);
           listCustomer=(ArrayList<Customer>)(ArrayList)output.readObject();
           output.close();  
            }
            
            file = new File("Item.txt");
            if(file.exists())
            {
           InputStream file1 = new FileInputStream("Item.txt");
           InputStream buffer = new BufferedInputStream(file1);
           ObjectInput output = new ObjectInputStream(buffer);
           listItem=(ArrayList<Item>)(ArrayList)output.readObject();
           output.close();  
            }
            
            file = new File("Purchase.txt");
            if(file.exists())
            {
           InputStream file1 = new FileInputStream("Purchase.txt");
           InputStream buffer = new BufferedInputStream(file1);
           ObjectInput output = new ObjectInputStream(buffer);
           listPurchase=(ArrayList<Item>)(ArrayList)output.readObject();
           output.close();  
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Trouble in opening the File");
        }

      
    }
    
     boolean validateEmail(String email)
        {
           int flag=0;
           int j=0;
           for(j=0;j<email.length();j++)
           {
               if(email.charAt(j)=='@')
                   flag=1;
               if(email.charAt(j)=='.'&&flag==1)
                   return true;
           }
           return false;
        }
    
     void changeStatus(String ID,int status)
     {
         int i,j,n;
         String seller="";
         //Now from this ID I will search for its status
         for(i=0;i<listItem.size();i++)
            if(listItem.get(i).ID.equals(ID))
                break;
         
        //in listItem
        listItem.get(i).status=status; //4 signifies this item has been sold out
        
        //In listCustomer
        n=0;
        seller=listItem.get(i).ID_seller;
        for(j=0;j<listCustomer.size();j++)
            if(listCustomer.get(j).ID.equals(seller))
                {
                    n=j;
                    break;
                }
        for(j=0;j<listCustomer.get(n).listItem.size();j++)
            if(listCustomer.get(n).listItem.get(j).ID.equals(ID))
                break;
               
        if(j!=listCustomer.get(n).listItem.size())
            listCustomer.get(n).listItem.get(j).status=status;
        else
            JOptionPane.showMessageDialog(null,"How can it happen");
            
        //in listItem
        for(i=0;i<listCategory.size();i++)
            {
                for(j=0;j<listCategory.get(i).itemList.size();j++)
                   {
                      if(listCategory.get(i).itemList.get(j).ID.equals(ID))
                         listCategory.get(i).itemList.get(j).status=status; 
                   }
               }
               //*****Changing of status DONE***********
    }
            
    void deleteItem(String ID)
    {   
         int i,j,n=0;
         String seller;
         
        for(i=0;i<listItem.size();i++)
            if(listItem.get(i).ID.equals(ID))
                break;

                    //In customer
               seller=listItem.get(i).ID_seller;
               listItem.remove(i);
               
               for(j=0;j<listCustomer.size();j++)
                   if(listCustomer.get(j).ID.equals(seller))
                   {
                       n=j;
                       break;
                   }
               
               for(j=0;j<listCustomer.get(n).listItem.size();j++)
                if(listCustomer.get(n).listItem.get(j).ID.equals(ID))
                    break;
               
               if(j!=listCustomer.get(n).listItem.size())
               listCustomer.get(n).listItem.remove(j);
               else
               JOptionPane.showMessageDialog(null,"How can it happen");
               
               for(i=0;i<listCategory.size();i++)
               {
                   for(j=0;j<listCategory.get(i).itemList.size();j++)
                   {
                      if(listCategory.get(i).itemList.get(j).ID.equals(ID))
                         listCategory.get(i).itemList.remove(j);
                   }
               }
                
      
    }
   
    boolean validateCustomer(String ID,String password)
    {
        int i;
        for(i=0;i<this.listCustomer.size();i++)
        {
            if(this.listCustomer.get(i).ID.equals(ID)&&this.listCustomer.get(i).password.equals(password))
                return true;
        }
        return false;
    }
    
    boolean validateManager(String ID,String password)
    {
        int i;
        for(i=0;i<this.listManager.size();i++)
        {
            if(this.listManager.get(i).ID.equals(ID)&&this.listManager.get(i).password.equals(password))
                return true;
        }
        return false;
    }
    public ArrayList<Item> search(String name,String ID)
    {
        ArrayList<Item> searchList=new ArrayList<Item>();
        int i;
        for(i=0;i<this.listItem.size();i++)
            if(name.equals(this.listItem.get(i).name)&&!(ID.equals(this.listItem.get(i).ID_seller))&&this.listItem.get(i).status!=4)
                searchList.add(this.listItem.get(i));
        return searchList;   
    }
    
    
    boolean validateDate(int day,int month,int year)
    {
      switch(month)
      {
          case 1:
          case 3:
          case 5:
          case 7:
          case 8:
          case 10:
          case 12:
              if(day>=1&&day<=31)
               return true;
              else
               return false;
              
          case 4:
          case 6:
          case 9:
          case 11:
              if(day>=1&&day<=30)
                  return true;
              else
                  return false;
             
         case 2:
             if(day>=1&&(year%4!=0&&day<=28)||(year%4==0&&day<=29))
                 return true;
              else
                  return false;
         
      } return false;
}
    
}


class Item implements Serializable
{
    public
    String category;
    float price;
    float bidprice;
    int age;
    String name;
    String city;
    String description;
    int status;
    int weight;   //1 for light and 2 for heavy
    File f;
    /*
    status is 1 for Negotiable, 2 for NotNegotiable, 3 for Approved  4 for Sold Out
    */
    String ID_buyer;    //ID of buyer
    String ID_seller;   //ID of seller
    String ID;          //Item id
    
    Item(String category,float price,int age,String name,String city,String description,int status,File f1,String ID_seller,String id,int weight)
    {
        this.category=category;
        this.name=name;
        this.price=price;
        this.age=age;
        this.city=city;
        this.description=description;
        this.status=status;
        this.ID_seller=ID_seller;
        this.ID=id;
        this.weight=weight;
        bidprice=0;
        f=f1;
    }
    
    String getCategory()
    {
        return category;
    }
  
    float getPrice()
    {
        return price;
    }
    
    void setPrice(float price)
    {
        this.price=price;
    }
    float getBidprice()
    {
        return this.bidprice;
    }
    void setBidprice(float price)
    {
        this.bidprice=price;
    }
    int getAge()
    {
        return this.age;
    }
    String getName()
    {
        return this.name;
    }
    String getCity()
    {
        return this.city;
    }
    String getDescription()
    {
        return this.description;
    }
    int getStatus()
    {
        return this.status;
    }
    void setStatus(int number)
    {
        this.status=number;
    }
    String getBuyer()
    {
        return this.ID_buyer;
    }
    String getSeller()
    {
        return this.ID_seller;
    }
    String getID()
    {
        return this.ID;
    }
    void changeCategory(String name)
    {
        this.category=name;
    }
    
}

class Category implements Serializable
{
    public
    ArrayList<Item> itemList;
    String name;        //Category name
    
    Category(String name)
    {
        this.name=name;
        itemList=new ArrayList<Item>();
    }
    ArrayList<Item> getCategory()
    {
        return itemList;
    }
}
class OutputPrinter implements Printable 
{
    private String printData;

    public OutputPrinter(String printDataIn)
    {
    this.printData = printDataIn;
    }

public int print(Graphics g, PageFormat pf, int page) throws PrinterException
{
    // Should only have one page, and page # is zero-based.
    if (page > 0)
    {
        return NO_SUCH_PAGE;
    }

    // Adding the "Imageable" to the x and y puts the margins on the page.
    // To make it safe for printing.
    Graphics2D g2d = (Graphics2D)g;
    int x = (int) pf.getImageableX();
    int y = (int) pf.getImageableY();        
    g2d.translate(x, y); 

    // Calculate the line height
    Font font = new Font("Serif", Font.PLAIN, 10);
    FontMetrics metrics = g.getFontMetrics(font);
    int lineHeight = metrics.getHeight();

    BufferedReader br = new BufferedReader(new StringReader(printData));

    // Draw the page:
    try
    {
        String line;
        // Just a safety net in case no margin was added.
        x += 50;
        y += 50;
        while ((line = br.readLine()) != null)
        {
            y += lineHeight;
            g2d.drawString(line, x, y);
        }
    }
    catch (IOException e)
    {
        // 
    }

    return PAGE_EXISTS;
}
}
public class NewJApplet extends javax.swing.JApplet {

    File f=null,f1=null,f2=null;
    
   Database data=new Database();
   
    public void init() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewJApplet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJApplet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJApplet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJApplet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the applet */
        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    initComponents();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Welcome_Panel.setVisible(true);
        Customer_Panel.setVisible(false);
        Item_Panel.setVisible(false);
        Upload_Item.setVisible(false);
        UploadedItem_Panel.setVisible(false);
        Reg_C.setVisible(false);
        Edit_C.setVisible(false);
        Manager_Panel.setVisible(false);
        Manage_Item.setVisible(false);
        Manage_Category.setVisible(false);
        Manage_Customer.setVisible(false);
        Manage_Transaction.setVisible(false);
        Reg_M.setVisible(false);
        Edit_M.setVisible(false);
    }

    public void save()
        {
        try {
            File file = new File("Category.txt");
            if(!file.exists())
           file.createNewFile();
            
           OutputStream file1 = new FileOutputStream("Category.txt");
           OutputStream buffer = new BufferedOutputStream(file1);
           ObjectOutput output = new ObjectOutputStream(buffer);
           output.writeObject(data.listCategory);
           output.close();  
            
            
            file = new File("Manager.txt");
            if(!file.exists())
           file.createNewFile();
            
           file1 = new FileOutputStream("Manager.txt");
           buffer = new BufferedOutputStream(file1);
           output = new ObjectOutputStream(buffer);
           output.writeObject(data.listManager);
           output.close();  
            
            
            file = new File("Customer.txt");
            if(!file.exists())
           file.createNewFile();
            
           file1 = new FileOutputStream("Customer.txt");
           buffer = new BufferedOutputStream(file1);
           output = new ObjectOutputStream(buffer);
           output.writeObject(data.listCustomer);
           output.close();  
            
            
            file = new File("Item.txt");
            if(!file.exists())
           file.createNewFile();
            
           file1 = new FileOutputStream("Item.txt");
           buffer = new BufferedOutputStream(file1);
           output = new ObjectOutputStream(buffer);
           output.writeObject(data.listItem);
           output.close(); 
           
            file = new File("Purchase.txt");
            if(!file.exists())
           file.createNewFile();
            
           file1 = new FileOutputStream("Purchase.txt");
           buffer = new BufferedOutputStream(file1);
           output = new ObjectOutputStream(buffer);
           output.writeObject(data.listPurchase);
           output.close();
            
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Trouble in writing in the File");
        }
        }
    
    
    /**
     * This method is called from within the init() method to initialize the
     * form. WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Welcome_Panel = new javax.swing.JPanel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        Customer_Panel = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        CustomerName = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        Item_Panel = new javax.swing.JPanel();
        jLabel138 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel132 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jList6 = new javax.swing.JList();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList3 = new javax.swing.JList();
        jScrollPane4 = new javax.swing.JScrollPane();
        jList4 = new javax.swing.JList();
        jScrollPane5 = new javax.swing.JScrollPane();
        jList5 = new javax.swing.JList();
        jScrollPane7 = new javax.swing.JScrollPane();
        jList7 = new javax.swing.JList();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        Upload_Item = new javax.swing.JPanel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jTextField18 = new javax.swing.JTextField();
        jTextField19 = new javax.swing.JTextField();
        jTextField20 = new javax.swing.JTextField();
        jTextField21 = new javax.swing.JTextField();
        jTextField24 = new javax.swing.JTextField();
        jTextField25 = new javax.swing.JTextField();
        jLabel79 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jLabel34 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jTextField22 = new javax.swing.JTextField();
        jTextField23 = new javax.swing.JTextField();
        jLabel81 = new javax.swing.JLabel();
        UploadedItem_Panel = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        jList13 = new javax.swing.JList();
        jScrollPane9 = new javax.swing.JScrollPane();
        jList9 = new javax.swing.JList();
        jScrollPane10 = new javax.swing.JScrollPane();
        jList10 = new javax.swing.JList();
        jScrollPane11 = new javax.swing.JScrollPane();
        jList11 = new javax.swing.JList();
        jScrollPane14 = new javax.swing.JScrollPane();
        jList14 = new javax.swing.JList();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jLabel125 = new javax.swing.JLabel();
        jLabel126 = new javax.swing.JLabel();
        jLabel127 = new javax.swing.JLabel();
        jLabel135 = new javax.swing.JLabel();
        jLabel129 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        Reg_C = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel120 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jPasswordField2 = new javax.swing.JPasswordField();
        jPasswordField3 = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        Edit_C = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        jLabel92 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jTextField26 = new javax.swing.JTextField();
        jTextField28 = new javax.swing.JTextField();
        jPasswordField6 = new javax.swing.JPasswordField();
        jTextField29 = new javax.swing.JTextField();
        jTextField30 = new javax.swing.JTextField();
        jPasswordField10 = new javax.swing.JPasswordField();
        jLabel12 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        Manager_Panel = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        ManagerName = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        Manage_Item = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel109 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        jScrollPane18 = new javax.swing.JScrollPane();
        jList18 = new javax.swing.JList();
        jScrollPane8 = new javax.swing.JScrollPane();
        jList8 = new javax.swing.JList();
        jScrollPane12 = new javax.swing.JScrollPane();
        jList12 = new javax.swing.JList();
        jScrollPane15 = new javax.swing.JScrollPane();
        jList15 = new javax.swing.JList();
        jScrollPane16 = new javax.swing.JScrollPane();
        jList16 = new javax.swing.JList();
        jScrollPane17 = new javax.swing.JScrollPane();
        jList17 = new javax.swing.JList();
        jScrollPane19 = new javax.swing.JScrollPane();
        jList19 = new javax.swing.JList();
        jLabel111 = new javax.swing.JLabel();
        jLabel124 = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        jLabel113 = new javax.swing.JLabel();
        jLabel114 = new javax.swing.JLabel();
        jLabel115 = new javax.swing.JLabel();
        jLabel116 = new javax.swing.JLabel();
        jLabel117 = new javax.swing.JLabel();
        jLabel118 = new javax.swing.JLabel();
        jLabel119 = new javax.swing.JLabel();
        Manage_Category = new javax.swing.JPanel();
        jLabel122 = new javax.swing.JLabel();
        jScrollPane22 = new javax.swing.JScrollPane();
        jList22 = new javax.swing.JList();
        jLabel123 = new javax.swing.JLabel();
        jLabel128 = new javax.swing.JLabel();
        jLabel130 = new javax.swing.JLabel();
        jLabel131 = new javax.swing.JLabel();
        Manage_Customer = new javax.swing.JPanel();
        jLabel133 = new javax.swing.JLabel();
        jLabel134 = new javax.swing.JLabel();
        jScrollPane21 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel136 = new javax.swing.JLabel();
        jLabel137 = new javax.swing.JLabel();
        jLabel139 = new javax.swing.JLabel();
        jLabel140 = new javax.swing.JLabel();
        jLabel142 = new javax.swing.JLabel();
        jLabel143 = new javax.swing.JLabel();
        Manage_Transaction = new javax.swing.JPanel();
        jScrollPane20 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel144 = new javax.swing.JLabel();
        jLabel145 = new javax.swing.JLabel();
        jLabel146 = new javax.swing.JLabel();
        jLabel147 = new javax.swing.JLabel();
        jLabel151 = new javax.swing.JLabel();
        jLabel152 = new javax.swing.JLabel();
        jLabel155 = new javax.swing.JLabel();
        Reg_M = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jTextField13 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        jPasswordField4 = new javax.swing.JPasswordField();
        jPasswordField5 = new javax.swing.JPasswordField();
        jLabel8 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        Edit_M = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        jTextField33 = new javax.swing.JTextField();
        jTextField34 = new javax.swing.JTextField();
        jTextField35 = new javax.swing.JTextField();
        jTextField40 = new javax.swing.JTextField();
        jPasswordField8 = new javax.swing.JPasswordField();
        jPasswordField9 = new javax.swing.JPasswordField();
        jLabel35 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(680, 363));
        setPreferredSize(new java.awt.Dimension(680, 363));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Welcome_Panel.setMinimumSize(new java.awt.Dimension(680, 363));
        Welcome_Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        Welcome_Panel.add(jPasswordField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 60, 120, -1));

        jRadioButton1.setBackground(new java.awt.Color(255, 255, 255));
        jRadioButton1.setFont(new java.awt.Font("Algerian", 0, 11)); // NOI18N
        jRadioButton1.setForeground(new java.awt.Color(255, 102, 0));
        jRadioButton1.setText("Manager");
        jRadioButton1.setBorder(null);
        jRadioButton1.setBorderPainted(true);
        jRadioButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });
        Welcome_Panel.add(jRadioButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 130, -1, -1));

        jLabel2.setFont(new java.awt.Font("Algerian", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 102, 0));
        jLabel2.setText("Password");
        Welcome_Panel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 60, 90, -1));

        jRadioButton2.setBackground(new java.awt.Color(255, 255, 255));
        jRadioButton2.setFont(new java.awt.Font("Algerian", 0, 11)); // NOI18N
        jRadioButton2.setForeground(new java.awt.Color(255, 102, 0));
        jRadioButton2.setText("Customer");
        jRadioButton2.setBorder(null);
        jRadioButton2.setBorderPainted(true);
        jRadioButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });
        Welcome_Panel.add(jRadioButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 160, -1, -1));

        jLabel3.setFont(new java.awt.Font("Algerian", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 102, 0));
        jLabel3.setText("Username");
        Welcome_Panel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 20, -1, -1));

        jLabel4.setBackground(new java.awt.Color(102, 0, 0));
        jLabel4.setFont(new java.awt.Font("Algerian", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 102, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("SignUp");
        jLabel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        Welcome_Panel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 100, 30));

        jLabel5.setBackground(new java.awt.Color(102, 0, 0));
        jLabel5.setForeground(new java.awt.Color(255, 102, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Login");
        jLabel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        Welcome_Panel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 90, 90, 20));
        Welcome_Panel.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 20, 120, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication1/online-shop-logo-template-ai-eps-10.jpg"))); // NOI18N
        jLabel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        Welcome_Panel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 680, 360));

        getContentPane().add(Welcome_Panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 360));

        Customer_Panel.setMinimumSize(new java.awt.Dimension(680, 363));
        Customer_Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Algerian", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(204, 0, 51));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Search");
        jLabel9.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0))));
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        Customer_Panel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, 80, 30));

        jLabel21.setBackground(new java.awt.Color(102, 0, 0));
        jLabel21.setFont(new java.awt.Font("Algerian", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 102, 0));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Upload Item");
        jLabel21.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jLabel21.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel21MouseClicked(evt);
            }
        });
        Customer_Panel.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, 120, 30));

        jLabel22.setBackground(new java.awt.Color(102, 0, 0));
        jLabel22.setFont(new java.awt.Font("Algerian", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 102, 0));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("View my uploaded Item");
        jLabel22.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jLabel22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel22MouseClicked(evt);
            }
        });
        Customer_Panel.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 310, 210, 40));
        Customer_Panel.add(CustomerName, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 300, 30));

        jLabel7.setBackground(new java.awt.Color(102, 0, 0));
        jLabel7.setFont(new java.awt.Font("Algerian", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 102, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("LogOut");
        jLabel7.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        Customer_Panel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 90, -1));

        jLabel25.setBackground(new java.awt.Color(102, 0, 0));
        jLabel25.setFont(new java.awt.Font("Algerian", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 102, 0));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Edit Account");
        jLabel25.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jLabel25.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel25MouseClicked(evt);
            }
        });
        Customer_Panel.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, 130, -1));

        jTextField2.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        Customer_Panel.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 150, 140, 30));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication1/online-shop-logo-template-ai-eps-10.jpg"))); // NOI18N
        Customer_Panel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 680, 360));

        getContentPane().add(Customer_Panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 360));

        Item_Panel.setMinimumSize(new java.awt.Dimension(680, 363));
        Item_Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel138.setFont(new java.awt.Font("Algerian", 0, 14)); // NOI18N
        jLabel138.setForeground(new java.awt.Color(255, 0, 0));
        jLabel138.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel138.setText("Description");
        jLabel138.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel138.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel138.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel138MouseClicked(evt);
            }
        });
        Item_Panel.add(jLabel138, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, 120, 20));

        jLabel13.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 102, 0));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Item Name");
        Item_Panel.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 70, 20));

        jLabel26.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 102, 0));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Seller Name");
        Item_Panel.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 60, 70, 20));

        jLabel132.setFont(new java.awt.Font("Algerian", 0, 14)); // NOI18N
        jLabel132.setForeground(new java.awt.Color(255, 0, 0));
        jLabel132.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel132.setText("view image");
        jLabel132.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel132.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel132.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel132MouseClicked(evt);
            }
        });
        Item_Panel.add(jLabel132, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 110, 20));

        jLabel76.setFont(new java.awt.Font("Algerian", 0, 14)); // NOI18N
        jLabel76.setForeground(new java.awt.Color(255, 0, 0));
        jLabel76.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel76.setText("BUY");
        jLabel76.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel76.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel76.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel76MouseClicked(evt);
            }
        });
        Item_Panel.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 330, 100, 20));

        jList6.setBackground(new java.awt.Color(255, 204, 102));
        jList6.setFont(new java.awt.Font("Comic Sans MS", 1, 10)); // NOI18N
        jList6.setForeground(new java.awt.Color(102, 51, 0));
        jList6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane6.setViewportView(jList6);

        Item_Panel.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 660, 230));

        jList1.setBackground(new java.awt.Color(255, 204, 102));
        jList1.setFont(new java.awt.Font("Comic Sans MS", 1, 10)); // NOI18N
        jList1.setForeground(new java.awt.Color(102, 51, 0));
        jList1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane1.setViewportView(jList1);

        Item_Panel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, 80, 230));

        jList2.setBackground(new java.awt.Color(255, 204, 102));
        jList2.setFont(new java.awt.Font("Comic Sans MS", 1, 10)); // NOI18N
        jList2.setForeground(new java.awt.Color(102, 51, 0));
        jList2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane2.setViewportView(jList2);

        Item_Panel.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 90, 50, 230));

        jList3.setBackground(new java.awt.Color(255, 204, 102));
        jList3.setFont(new java.awt.Font("Comic Sans MS", 1, 10)); // NOI18N
        jList3.setForeground(new java.awt.Color(102, 51, 0));
        jList3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane3.setViewportView(jList3);

        Item_Panel.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, 80, 230));

        jList4.setBackground(new java.awt.Color(255, 204, 102));
        jList4.setFont(new java.awt.Font("Comic Sans MS", 1, 10)); // NOI18N
        jList4.setForeground(new java.awt.Color(102, 51, 0));
        jList4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane4.setViewportView(jList4);

        Item_Panel.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 90, 80, 230));

        jList5.setBackground(new java.awt.Color(255, 204, 102));
        jList5.setFont(new java.awt.Font("Comic Sans MS", 1, 10)); // NOI18N
        jList5.setForeground(new java.awt.Color(102, 51, 0));
        jList5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane5.setViewportView(jList5);

        Item_Panel.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 90, 70, 230));

        jList7.setBackground(new java.awt.Color(255, 204, 102));
        jList7.setFont(new java.awt.Font("Comic Sans MS", 1, 10)); // NOI18N
        jList7.setForeground(new java.awt.Color(102, 51, 0));
        jList7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane7.setViewportView(jList7);

        Item_Panel.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 90, 80, 230));

        jLabel27.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 102, 0));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Category");
        Item_Panel.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 60, 50, 20));

        jLabel28.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 102, 0));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("Bid Price");
        Item_Panel.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 60, 60, 20));

        jLabel29.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 102, 0));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("City");
        Item_Panel.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 60, 60, 20));

        jLabel31.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 102, 0));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("Item Id");
        Item_Panel.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 60, 70, 20));

        jLabel32.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 102, 0));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("Price");
        Item_Panel.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 60, 70, 20));

        jLabel33.setFont(new java.awt.Font("Algerian", 0, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 0, 0));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("Back");
        jLabel33.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel33.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel33MouseClicked(evt);
            }
        });
        Item_Panel.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 90, -1));

        jLabel36.setFont(new java.awt.Font("Algerian", 0, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 0, 0));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("Sign Out");
        jLabel36.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel36.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel36.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel36MouseClicked(evt);
            }
        });
        Item_Panel.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 10, 90, 20));

        jLabel49.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 102, 0));
        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setText("Availability");
        Item_Panel.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 60, 70, 20));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication1/online-shop-logo-template-ai-eps-10.jpg"))); // NOI18N
        Item_Panel.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 680, 360));

        getContentPane().add(Item_Panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 360));

        Upload_Item.setMinimumSize(new java.awt.Dimension(680, 363));
        Upload_Item.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel77.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(255, 102, 0));
        jLabel77.setText("* Name");
        Upload_Item.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, -1, -1));

        jLabel78.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(255, 102, 0));
        jLabel78.setText("* City");
        Upload_Item.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 130, 40, -1));
        Upload_Item.add(jTextField18, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 210, -1));
        Upload_Item.add(jTextField19, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 130, 210, -1));

        jTextField20.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField20KeyTyped(evt);
            }
        });
        Upload_Item.add(jTextField20, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 160, 210, -1));
        Upload_Item.add(jTextField21, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, 210, -1));
        Upload_Item.add(jTextField24, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 220, 210, -1));

        jTextField25.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField25KeyTyped(evt);
            }
        });
        Upload_Item.add(jTextField25, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 190, 210, -1));

        jLabel79.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(255, 102, 0));
        jLabel79.setText("* Description");
        Upload_Item.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 220, 90, -1));

        jLabel84.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(255, 102, 0));
        jLabel84.setText("* Price");
        Upload_Item.add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 160, 70, -1));

        jLabel85.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        jLabel85.setForeground(new java.awt.Color(255, 102, 0));
        jLabel85.setText("* Age");
        Upload_Item.add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, 70, -1));

        jLabel80.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(255, 102, 0));
        jLabel80.setText("* Category");
        Upload_Item.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, -1, -1));

        jLabel43.setFont(new java.awt.Font("Algerian", 1, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(204, 0, 0));
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setText("Back");
        jLabel43.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jLabel43.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel43MouseClicked(evt);
            }
        });
        Upload_Item.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 90, -1));

        jRadioButton4.setText("Heavy Item");
        jRadioButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton4MouseClicked(evt);
            }
        });
        Upload_Item.add(jRadioButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, -1, -1));

        jRadioButton3.setText("Light Item");
        jRadioButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton3MouseClicked(evt);
            }
        });
        Upload_Item.add(jRadioButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, -1, -1));

        jLabel34.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 153, 0));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("View");
        jLabel34.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel34.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel34MouseClicked(evt);
            }
        });
        Upload_Item.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 100, 50, -1));

        jLabel44.setFont(new java.awt.Font("Arabic Typesetting", 0, 18)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 102, 0));
        jLabel44.setText("   Choose Image");
        jLabel44.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel44MouseClicked(evt);
            }
        });
        Upload_Item.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 100, 110, 110));

        jLabel45.setFont(new java.awt.Font("Algerian", 1, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(204, 0, 0));
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setText("Upload");
        jLabel45.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jLabel45.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel45MouseClicked(evt);
            }
        });
        Upload_Item.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 300, 100, -1));

        jLabel46.setFont(new java.awt.Font("Algerian", 1, 14)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(204, 0, 0));
        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel46.setText("Sign Out");
        jLabel46.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jLabel46.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel46MouseClicked(evt);
            }
        });
        Upload_Item.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 10, 90, -1));

        jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication1/online-shop-logo-template-ai-eps-10.jpg"))); // NOI18N
        Upload_Item.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 680, 360));
        Upload_Item.add(jTextField22, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, 210, -1));
        Upload_Item.add(jTextField23, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 210, -1));

        jLabel81.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication1/online-shop-logo-template-ai-eps-10.jpg"))); // NOI18N
        Upload_Item.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 680, 360));

        getContentPane().add(Upload_Item, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 360));

        UploadedItem_Panel.setMinimumSize(new java.awt.Dimension(680, 363));
        UploadedItem_Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 102, 0));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Item Name");
        UploadedItem_Panel.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 70, 20));

        jList13.setBackground(new java.awt.Color(255, 204, 102));
        jList13.setFont(new java.awt.Font("Comic Sans MS", 1, 10)); // NOI18N
        jList13.setForeground(new java.awt.Color(102, 51, 0));
        jList13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane13.setViewportView(jList13);

        UploadedItem_Panel.add(jScrollPane13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 580, 230));

        jList9.setBackground(new java.awt.Color(255, 204, 102));
        jList9.setFont(new java.awt.Font("Comic Sans MS", 1, 10)); // NOI18N
        jList9.setForeground(new java.awt.Color(102, 51, 0));
        jList9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane9.setViewportView(jList9);

        UploadedItem_Panel.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 80, 70, 230));

        jList10.setBackground(new java.awt.Color(255, 204, 102));
        jList10.setFont(new java.awt.Font("Comic Sans MS", 1, 10)); // NOI18N
        jList10.setForeground(new java.awt.Color(102, 51, 0));
        jList10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane10.setViewportView(jList10);

        UploadedItem_Panel.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, 80, 230));

        jList11.setBackground(new java.awt.Color(255, 204, 102));
        jList11.setFont(new java.awt.Font("Comic Sans MS", 1, 10)); // NOI18N
        jList11.setForeground(new java.awt.Color(102, 51, 0));
        jList11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane11.setViewportView(jList11);

        UploadedItem_Panel.add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 80, 80, 230));

        jList14.setBackground(new java.awt.Color(255, 204, 102));
        jList14.setFont(new java.awt.Font("Comic Sans MS", 1, 10)); // NOI18N
        jList14.setForeground(new java.awt.Color(102, 51, 0));
        jList14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane14.setViewportView(jList14);

        UploadedItem_Panel.add(jScrollPane14, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 80, 80, 230));

        jLabel37.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 102, 0));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("Category");
        UploadedItem_Panel.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 60, 50, 20));

        jLabel38.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 102, 0));
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setText("Description");
        UploadedItem_Panel.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 60, 60, 20));

        jLabel40.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 102, 0));
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setText("Item_Id");
        UploadedItem_Panel.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 60, 70, 20));

        jLabel41.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 102, 0));
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setText("Status");
        UploadedItem_Panel.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 60, 70, 20));

        jLabel86.setFont(new java.awt.Font("Algerian", 0, 14)); // NOI18N
        jLabel86.setForeground(new java.awt.Color(255, 0, 0));
        jLabel86.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel86.setText("Back");
        jLabel86.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel86.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel86.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel86MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel86MouseEntered(evt);
            }
        });
        UploadedItem_Panel.add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 90, -1));

        jLabel83.setFont(new java.awt.Font("Algerian", 0, 14)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(255, 0, 0));
        jLabel83.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel83.setText("Sign Out");
        jLabel83.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel83.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel83.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel83MouseClicked(evt);
            }
        });
        UploadedItem_Panel.add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 10, 90, 20));

        jLabel125.setFont(new java.awt.Font("Algerian", 0, 11)); // NOI18N
        jLabel125.setForeground(new java.awt.Color(255, 0, 0));
        jLabel125.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel125.setText("DELETE ITEM");
        jLabel125.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel125.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel125MouseClicked(evt);
            }
        });
        UploadedItem_Panel.add(jLabel125, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 320, 190, 30));

        jLabel126.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel126.setForeground(new java.awt.Color(255, 102, 0));
        jLabel126.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel126.setText("Price");
        UploadedItem_Panel.add(jLabel126, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 60, 70, 20));

        jLabel127.setFont(new java.awt.Font("Algerian", 0, 14)); // NOI18N
        jLabel127.setForeground(new java.awt.Color(255, 0, 0));
        jLabel127.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel127.setText("Change Status");
        jLabel127.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel127.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel127.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel127MouseClicked(evt);
            }
        });
        UploadedItem_Panel.add(jLabel127, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 160, 30));

        jLabel135.setFont(new java.awt.Font("Algerian", 0, 14)); // NOI18N
        jLabel135.setForeground(new java.awt.Color(255, 0, 0));
        jLabel135.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel135.setText("View Image");
        jLabel135.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel135.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel135.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel135MouseClicked(evt);
            }
        });
        UploadedItem_Panel.add(jLabel135, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 320, 120, 30));

        jLabel129.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel129.setForeground(new java.awt.Color(255, 102, 0));
        jLabel129.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel129.setText("Bid Price");
        UploadedItem_Panel.add(jLabel129, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 60, 70, 20));

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication1/online-shop-logo-template-ai-eps-10.jpg"))); // NOI18N
        UploadedItem_Panel.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 680, 360));

        getContentPane().add(UploadedItem_Panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 360));

        Reg_C.setMinimumSize(new java.awt.Dimension(680, 363));
        Reg_C.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel18.setFont(new java.awt.Font("Algerian", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 0, 0));
        jLabel18.setText("Registration Portal");
        Reg_C.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 330, 30));

        jLabel53.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 102, 0));
        jLabel53.setText("* Email Adress");
        Reg_C.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, -1, -1));

        jLabel55.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 102, 0));
        jLabel55.setText("IM_ID");
        Reg_C.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 300, -1, -1));

        jLabel56.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(255, 102, 0));
        jLabel56.setText("* City");
        Reg_C.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 190, 40, -1));

        jLabel57.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(255, 102, 0));
        jLabel57.setText("* Password");
        Reg_C.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 70, -1));

        jLabel58.setFont(new java.awt.Font("Algerian", 1, 12)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(255, 51, 0));
        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel58.setText("Back");
        jLabel58.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), null, null));
        jLabel58.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel58.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel58MouseClicked(evt);
            }
        });
        Reg_C.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 90, -1));

        jLabel59.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(255, 102, 0));
        jLabel59.setText("* Confirm Password");
        Reg_C.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, 130, 20));

        jLabel60.setFont(new java.awt.Font("Algerian", 1, 12)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(255, 51, 0));
        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel60.setText("Submit");
        jLabel60.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), null, null));
        jLabel60.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel60.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel60MouseClicked(evt);
            }
        });
        Reg_C.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 160, 100, -1));

        jLabel62.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(255, 102, 0));
        jLabel62.setText("* Telephone");
        Reg_C.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 220, 70, -1));

        jLabel120.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        jLabel120.setForeground(new java.awt.Color(255, 102, 0));
        jLabel120.setText("* Name");
        Reg_C.add(jLabel120, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, -1, -1));
        Reg_C.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 300, 210, -1));
        Reg_C.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 210, -1));

        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField5KeyTyped(evt);
            }
        });
        Reg_C.add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 220, 210, -1));
        Reg_C.add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 190, 210, -1));
        Reg_C.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, 210, -1));
        Reg_C.add(jPasswordField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 160, 210, -1));
        Reg_C.add(jPasswordField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 130, 210, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("* Indicates mandatory field");
        Reg_C.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 334, 160, 20));

        jLabel63.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication1/online-shop-logo-template-ai-eps-10.jpg"))); // NOI18N
        Reg_C.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 680, 360));

        getContentPane().add(Reg_C, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 360));

        Edit_C.setMinimumSize(new java.awt.Dimension(680, 363));
        Edit_C.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel23.setFont(new java.awt.Font("Algerian", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 0, 0));
        jLabel23.setText("Edit Account");
        Edit_C.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 330, 30));

        jLabel87.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        jLabel87.setForeground(new java.awt.Color(255, 102, 0));
        jLabel87.setText("* Name");
        Edit_C.add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, -1, -1));

        jLabel88.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        jLabel88.setForeground(new java.awt.Color(255, 102, 0));
        jLabel88.setText("IM_ID");
        Edit_C.add(jLabel88, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 300, -1, -1));

        jLabel89.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        jLabel89.setForeground(new java.awt.Color(255, 102, 0));
        jLabel89.setText("* City");
        Edit_C.add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 190, 40, -1));

        jLabel90.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        jLabel90.setForeground(new java.awt.Color(255, 102, 0));
        jLabel90.setText("* Password");
        Edit_C.add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 70, -1));

        jLabel91.setFont(new java.awt.Font("Algerian", 1, 12)); // NOI18N
        jLabel91.setForeground(new java.awt.Color(255, 51, 0));
        jLabel91.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel91.setText("Back");
        jLabel91.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), null, null));
        jLabel91.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel91.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel91MouseClicked(evt);
            }
        });
        Edit_C.add(jLabel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 90, -1));

        jLabel92.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        jLabel92.setForeground(new java.awt.Color(255, 102, 0));
        jLabel92.setText("* Confirm Password");
        Edit_C.add(jLabel92, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, 130, 20));

        jLabel93.setFont(new java.awt.Font("Algerian", 1, 12)); // NOI18N
        jLabel93.setForeground(new java.awt.Color(255, 51, 0));
        jLabel93.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel93.setText("Save");
        jLabel93.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), null, null));
        jLabel93.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel93.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel93MouseClicked(evt);
            }
        });
        Edit_C.add(jLabel93, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 160, 100, -1));

        jLabel94.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        jLabel94.setForeground(new java.awt.Color(255, 102, 0));
        jLabel94.setText("* Telephone");
        Edit_C.add(jLabel94, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 220, 70, -1));
        Edit_C.add(jTextField26, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 300, 210, -1));

        jTextField28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField28MouseClicked(evt);
            }
        });
        jTextField28.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField28KeyTyped(evt);
            }
        });
        Edit_C.add(jTextField28, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 220, 210, -1));
        Edit_C.add(jPasswordField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 160, 210, -1));
        Edit_C.add(jTextField29, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 190, 210, -1));
        Edit_C.add(jTextField30, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 210, -1));
        Edit_C.add(jPasswordField10, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 130, 210, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("* Indicates mandatory field");
        Edit_C.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 334, 160, 20));

        jLabel95.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication1/online-shop-logo-template-ai-eps-10.jpg"))); // NOI18N
        Edit_C.add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 680, 360));

        getContentPane().add(Edit_C, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 360));

        Manager_Panel.setMinimumSize(new java.awt.Dimension(680, 363));
        Manager_Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Algerian", 0, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(204, 0, 0));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Manage Customer");
        jLabel11.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), new java.awt.Color(153, 0, 0)));
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        Manager_Panel.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(555, 160, 120, 30));

        jLabel30.setFont(new java.awt.Font("Algerian", 0, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(204, 0, 0));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("Manage Item");
        jLabel30.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), new java.awt.Color(153, 0, 0)));
        jLabel30.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel30MouseClicked(evt);
            }
        });
        Manager_Panel.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 100, 30));

        jLabel48.setFont(new java.awt.Font("Algerian", 0, 12)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(204, 0, 0));
        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel48.setText("Manage Category");
        jLabel48.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), new java.awt.Color(153, 0, 0)));
        jLabel48.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel48.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel48MouseClicked(evt);
            }
        });
        Manager_Panel.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 140, 30));

        jLabel50.setFont(new java.awt.Font("Algerian", 0, 12)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(204, 0, 0));
        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setText("View Transactions");
        jLabel50.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), new java.awt.Color(153, 0, 0)));
        jLabel50.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel50.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel50MouseClicked(evt);
            }
        });
        Manager_Panel.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 320, 130, 30));

        jLabel16.setFont(new java.awt.Font("Algerian", 0, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(204, 0, 0));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("LogOut");
        jLabel16.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), new java.awt.Color(153, 0, 0)));
        jLabel16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });
        Manager_Panel.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 70, 30));

        jLabel51.setFont(new java.awt.Font("Algerian", 0, 12)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(204, 0, 0));
        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setText("Edit Account");
        jLabel51.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), new java.awt.Color(153, 0, 0)));
        jLabel51.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel51.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel51MouseClicked(evt);
            }
        });
        Manager_Panel.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(573, 10, 100, 30));
        Manager_Panel.add(ManagerName, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 140, 30));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication1/online-shop-logo-template-ai-eps-10.jpg"))); // NOI18N
        Manager_Panel.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 680, 360));

        getContentPane().add(Manager_Panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 360));

        Manage_Item.setMinimumSize(new java.awt.Dimension(680, 363));
        Manage_Item.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel39.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 102, 0));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("Item Name");
        Manage_Item.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 70, 20));

        jLabel109.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel109.setForeground(new java.awt.Color(255, 102, 0));
        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel109.setText("Seller ID");
        Manage_Item.add(jLabel109, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 60, 70, 20));

        jLabel110.setFont(new java.awt.Font("Algerian", 0, 14)); // NOI18N
        jLabel110.setForeground(new java.awt.Color(255, 0, 0));
        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel110.setText("Delete Item");
        jLabel110.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel110.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel110.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel110MouseClicked(evt);
            }
        });
        Manage_Item.add(jLabel110, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 330, 130, 20));

        jList18.setBackground(new java.awt.Color(255, 204, 102));
        jList18.setFont(new java.awt.Font("Comic Sans MS", 1, 10)); // NOI18N
        jList18.setForeground(new java.awt.Color(102, 51, 0));
        jList18.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane18.setViewportView(jList18);

        Manage_Item.add(jScrollPane18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 670, 230));

        jList8.setBackground(new java.awt.Color(255, 204, 102));
        jList8.setFont(new java.awt.Font("Comic Sans MS", 1, 10)); // NOI18N
        jList8.setForeground(new java.awt.Color(102, 51, 0));
        jList8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane8.setViewportView(jList8);

        Manage_Item.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, 80, 230));

        jList12.setBackground(new java.awt.Color(255, 204, 102));
        jList12.setFont(new java.awt.Font("Comic Sans MS", 1, 10)); // NOI18N
        jList12.setForeground(new java.awt.Color(102, 51, 0));
        jList12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane12.setViewportView(jList12);

        Manage_Item.add(jScrollPane12, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 90, 50, 230));

        jList15.setBackground(new java.awt.Color(255, 204, 102));
        jList15.setFont(new java.awt.Font("Comic Sans MS", 1, 10)); // NOI18N
        jList15.setForeground(new java.awt.Color(102, 51, 0));
        jList15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane15.setViewportView(jList15);

        Manage_Item.add(jScrollPane15, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, 80, 230));

        jList16.setBackground(new java.awt.Color(255, 204, 102));
        jList16.setFont(new java.awt.Font("Comic Sans MS", 1, 10)); // NOI18N
        jList16.setForeground(new java.awt.Color(102, 51, 0));
        jList16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane16.setViewportView(jList16);

        Manage_Item.add(jScrollPane16, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 90, 80, 230));

        jList17.setBackground(new java.awt.Color(255, 204, 102));
        jList17.setFont(new java.awt.Font("Comic Sans MS", 1, 10)); // NOI18N
        jList17.setForeground(new java.awt.Color(102, 51, 0));
        jList17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane17.setViewportView(jList17);

        Manage_Item.add(jScrollPane17, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 90, 70, 230));

        jList19.setBackground(new java.awt.Color(255, 204, 102));
        jList19.setFont(new java.awt.Font("Comic Sans MS", 1, 10)); // NOI18N
        jList19.setForeground(new java.awt.Color(102, 51, 0));
        jList19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane19.setViewportView(jList19);

        Manage_Item.add(jScrollPane19, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 90, 80, 230));

        jLabel111.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel111.setForeground(new java.awt.Color(255, 102, 0));
        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel111.setText("Category");
        Manage_Item.add(jLabel111, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 60, 50, 20));

        jLabel124.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel124.setForeground(new java.awt.Color(255, 102, 0));
        jLabel124.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel124.setText("ID");
        Manage_Item.add(jLabel124, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 60, 70, 20));

        jLabel112.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel112.setForeground(new java.awt.Color(255, 102, 0));
        jLabel112.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel112.setText("Description");
        Manage_Item.add(jLabel112, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 60, 60, 20));

        jLabel113.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel113.setForeground(new java.awt.Color(255, 102, 0));
        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel113.setText("City");
        Manage_Item.add(jLabel113, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 60, 60, 20));

        jLabel114.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel114.setForeground(new java.awt.Color(255, 102, 0));
        jLabel114.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel114.setText("Availability");
        Manage_Item.add(jLabel114, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 60, 70, 20));

        jLabel115.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel115.setForeground(new java.awt.Color(255, 102, 0));
        jLabel115.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel115.setText("Price");
        Manage_Item.add(jLabel115, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 60, 70, 20));

        jLabel116.setFont(new java.awt.Font("Algerian", 0, 14)); // NOI18N
        jLabel116.setForeground(new java.awt.Color(255, 0, 0));
        jLabel116.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel116.setText("Back");
        jLabel116.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel116.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel116.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel116MouseClicked(evt);
            }
        });
        Manage_Item.add(jLabel116, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 90, -1));

        jLabel117.setText("    Image");
        Manage_Item.add(jLabel117, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 140, 60, 60));

        jLabel118.setFont(new java.awt.Font("Algerian", 0, 14)); // NOI18N
        jLabel118.setForeground(new java.awt.Color(255, 0, 0));
        jLabel118.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel118.setText("Change Category");
        jLabel118.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel118.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel118.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel118MouseClicked(evt);
            }
        });
        Manage_Item.add(jLabel118, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 330, 150, 20));

        jLabel119.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication1/online-shop-logo-template-ai-eps-10.jpg"))); // NOI18N
        Manage_Item.add(jLabel119, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 680, 360));

        getContentPane().add(Manage_Item, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 360));

        Manage_Category.setMinimumSize(new java.awt.Dimension(680, 363));
        Manage_Category.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel122.setFont(new java.awt.Font("Algerian", 0, 14)); // NOI18N
        jLabel122.setForeground(new java.awt.Color(255, 0, 0));
        jLabel122.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel122.setText("Add Category");
        jLabel122.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel122.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel122.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel122MouseClicked(evt);
            }
        });
        Manage_Category.add(jLabel122, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 330, 140, 20));

        jList22.setBackground(new java.awt.Color(255, 204, 102));
        jList22.setFont(new java.awt.Font("Comic Sans MS", 1, 10)); // NOI18N
        jList22.setForeground(new java.awt.Color(102, 51, 0));
        jList22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane22.setViewportView(jList22);

        Manage_Category.add(jScrollPane22, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, 120, 230));

        jLabel123.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel123.setForeground(new java.awt.Color(255, 102, 0));
        jLabel123.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel123.setText("Category");
        Manage_Category.add(jLabel123, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, 50, 20));

        jLabel128.setFont(new java.awt.Font("Algerian", 0, 14)); // NOI18N
        jLabel128.setForeground(new java.awt.Color(255, 0, 0));
        jLabel128.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel128.setText("Back");
        jLabel128.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel128.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel128.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel128MouseClicked(evt);
            }
        });
        Manage_Category.add(jLabel128, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 90, -1));

        jLabel130.setFont(new java.awt.Font("Algerian", 0, 14)); // NOI18N
        jLabel130.setForeground(new java.awt.Color(255, 0, 0));
        jLabel130.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel130.setText("Remove Category");
        jLabel130.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel130.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel130.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel130MouseClicked(evt);
            }
        });
        Manage_Category.add(jLabel130, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 330, 150, 20));

        jLabel131.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication1/online-shop-logo-template-ai-eps-10.jpg"))); // NOI18N
        Manage_Category.add(jLabel131, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 680, 360));

        getContentPane().add(Manage_Category, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 360));

        Manage_Customer.setMinimumSize(new java.awt.Dimension(680, 363));
        Manage_Customer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel133.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel133.setForeground(new java.awt.Color(255, 102, 0));
        jLabel133.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel133.setText("Customer Name");
        Manage_Customer.add(jLabel133, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 100, 20));

        jLabel134.setFont(new java.awt.Font("Algerian", 0, 14)); // NOI18N
        jLabel134.setForeground(new java.awt.Color(255, 0, 0));
        jLabel134.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel134.setText("Remove");
        jLabel134.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel134.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel134.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel134MouseClicked(evt);
            }
        });
        Manage_Customer.add(jLabel134, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 330, 100, 20));

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane21.setViewportView(jTextArea2);

        Manage_Customer.add(jScrollPane21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 620, 220));

        jLabel136.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel136.setForeground(new java.awt.Color(255, 102, 0));
        jLabel136.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel136.setText("Email ID");
        Manage_Customer.add(jLabel136, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 60, 60, 20));

        jLabel137.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel137.setForeground(new java.awt.Color(255, 102, 0));
        jLabel137.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel137.setText("ID");
        Manage_Customer.add(jLabel137, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 60, 60, 20));

        jLabel139.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel139.setForeground(new java.awt.Color(255, 102, 0));
        jLabel139.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel139.setText("Telephone");
        Manage_Customer.add(jLabel139, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 60, 70, 20));

        jLabel140.setFont(new java.awt.Font("Algerian", 0, 14)); // NOI18N
        jLabel140.setForeground(new java.awt.Color(255, 0, 0));
        jLabel140.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel140.setText("Back");
        jLabel140.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel140.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel140.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel140MouseClicked(evt);
            }
        });
        Manage_Customer.add(jLabel140, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 90, -1));

        jLabel142.setFont(new java.awt.Font("Algerian", 0, 14)); // NOI18N
        jLabel142.setForeground(new java.awt.Color(255, 0, 0));
        jLabel142.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel142.setText("Send Email");
        jLabel142.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel142.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel142.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel142MouseClicked(evt);
            }
        });
        Manage_Customer.add(jLabel142, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 330, 90, 20));

        jLabel143.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication1/online-shop-logo-template-ai-eps-10.jpg"))); // NOI18N
        Manage_Customer.add(jLabel143, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 680, 360));

        getContentPane().add(Manage_Customer, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 360));

        Manage_Transaction.setMinimumSize(new java.awt.Dimension(680, 363));
        Manage_Transaction.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane20.setViewportView(jTextArea1);

        Manage_Transaction.add(jScrollPane20, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 350, 210));

        jLabel144.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel144.setForeground(new java.awt.Color(255, 102, 0));
        jLabel144.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel144.setText("Item Name");
        Manage_Transaction.add(jLabel144, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, 70, 20));

        jLabel145.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel145.setForeground(new java.awt.Color(255, 102, 0));
        jLabel145.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel145.setText("Seller ID");
        Manage_Transaction.add(jLabel145, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 60, 70, 20));

        jLabel146.setFont(new java.awt.Font("Algerian", 0, 14)); // NOI18N
        jLabel146.setForeground(new java.awt.Color(255, 0, 0));
        jLabel146.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel146.setText("Print");
        jLabel146.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel146.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel146.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel146MouseClicked(evt);
            }
        });
        Manage_Transaction.add(jLabel146, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 330, 100, 20));

        jLabel147.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel147.setForeground(new java.awt.Color(255, 102, 0));
        jLabel147.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel147.setText("Buyer ID");
        Manage_Transaction.add(jLabel147, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 60, 70, 20));

        jLabel151.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel151.setForeground(new java.awt.Color(255, 102, 0));
        jLabel151.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel151.setText("Price");
        Manage_Transaction.add(jLabel151, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 60, 70, 20));

        jLabel152.setFont(new java.awt.Font("Algerian", 0, 14)); // NOI18N
        jLabel152.setForeground(new java.awt.Color(255, 0, 0));
        jLabel152.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel152.setText("Back");
        jLabel152.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel152.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel152.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel152MouseClicked(evt);
            }
        });
        Manage_Transaction.add(jLabel152, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 90, -1));

        jLabel155.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication1/online-shop-logo-template-ai-eps-10.jpg"))); // NOI18N
        Manage_Transaction.add(jLabel155, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 680, 360));

        getContentPane().add(Manage_Transaction, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 360));

        Reg_M.setMinimumSize(new java.awt.Dimension(680, 363));
        Reg_M.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel19.setFont(new java.awt.Font("Algerian", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(204, 51, 0));
        jLabel19.setText("Registration Portal");
        Reg_M.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 240, 30));

        jLabel61.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(255, 102, 0));
        jLabel61.setText("* Email ID");
        Reg_M.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, -1, -1));

        jLabel64.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(255, 102, 0));
        jLabel64.setText("* Name");
        Reg_M.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, -1, -1));

        jLabel65.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(255, 102, 0));
        jLabel65.setText("* Date of Birth");
        Reg_M.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 320, 110, -1));

        jLabel66.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(255, 102, 0));
        jLabel66.setText("* Address");
        Reg_M.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, 80, -1));

        jLabel67.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(255, 102, 0));
        jLabel67.setText("* Password");
        Reg_M.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, 110, -1));

        jLabel68.setFont(new java.awt.Font("Algerian", 0, 18)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(204, 0, 0));
        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel68.setText("Back");
        jLabel68.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jLabel68.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel68MouseClicked(evt);
            }
        });
        Reg_M.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 110, -1));

        jLabel69.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(255, 102, 0));
        jLabel69.setText("* Confirm Password");
        Reg_M.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, 140, 20));

        jLabel70.setFont(new java.awt.Font("Algerian", 0, 18)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(204, 0, 0));
        jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel70.setText("Submit");
        jLabel70.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jLabel70.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel70MouseClicked(evt);
            }
        });
        Reg_M.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 160, 110, -1));

        jLabel71.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(255, 102, 0));
        jLabel71.setText("* Telephone");
        Reg_M.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 200, 100, -1));
        Reg_M.add(jTextField8, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 260, 210, -1));
        Reg_M.add(jTextField9, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 80, 210, -1));

        jTextField10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField10KeyTyped(evt);
            }
        });
        Reg_M.add(jTextField10, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 200, 210, -1));
        Reg_M.add(jTextField11, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 170, 210, -1));
        Reg_M.add(jTextField12, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, 210, -1));
        Reg_M.add(jTextField13, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 290, 210, -1));

        jTextField14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField14KeyTyped(evt);
            }
        });
        Reg_M.add(jTextField14, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 320, 50, -1));

        jTextField15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField15KeyTyped(evt);
            }
        });
        Reg_M.add(jTextField15, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 320, 70, -1));

        jTextField16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField16KeyTyped(evt);
            }
        });
        Reg_M.add(jTextField16, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 320, 60, -1));
        Reg_M.add(jTextField17, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 230, 210, -1));
        Reg_M.add(jPasswordField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 140, 210, -1));
        Reg_M.add(jPasswordField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 110, 210, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("* Indicates mandatory field");
        Reg_M.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 160, 20));

        jLabel73.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(255, 102, 0));
        jLabel73.setText("* Bio_ID");
        Reg_M.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 260, -1, -1));

        jLabel74.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(255, 102, 0));
        jLabel74.setText("* IM_ID");
        Reg_M.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 230, -1, -1));

        jLabel75.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(255, 102, 0));
        jLabel75.setText("* Gender");
        Reg_M.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 290, -1, -1));

        jLabel72.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication1/online-shop-logo-template-ai-eps-10.jpg"))); // NOI18N
        Reg_M.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 680, 360));

        getContentPane().add(Reg_M, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 360));

        Edit_M.setMinimumSize(new java.awt.Dimension(680, 363));
        Edit_M.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel24.setFont(new java.awt.Font("Algerian", 1, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(204, 51, 0));
        jLabel24.setText("Edit Account");
        Edit_M.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 240, 30));

        jLabel97.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel97.setForeground(new java.awt.Color(255, 102, 0));
        jLabel97.setText("* Name");
        Edit_M.add(jLabel97, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, -1, -1));

        jLabel99.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel99.setForeground(new java.awt.Color(255, 102, 0));
        jLabel99.setText("* Address");
        Edit_M.add(jLabel99, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, 80, -1));

        jLabel100.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel100.setForeground(new java.awt.Color(255, 102, 0));
        jLabel100.setText("* Password");
        Edit_M.add(jLabel100, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, 110, -1));

        jLabel101.setFont(new java.awt.Font("Algerian", 0, 18)); // NOI18N
        jLabel101.setForeground(new java.awt.Color(204, 0, 0));
        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel101.setText("Back");
        jLabel101.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jLabel101.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel101MouseClicked(evt);
            }
        });
        Edit_M.add(jLabel101, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 110, -1));

        jLabel102.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel102.setForeground(new java.awt.Color(255, 102, 0));
        jLabel102.setText("* Confirm Password");
        Edit_M.add(jLabel102, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, 140, 20));

        jLabel103.setFont(new java.awt.Font("Algerian", 0, 18)); // NOI18N
        jLabel103.setForeground(new java.awt.Color(204, 0, 0));
        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel103.setText("Save");
        jLabel103.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jLabel103.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel103MouseClicked(evt);
            }
        });
        Edit_M.add(jLabel103, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 160, 110, -1));

        jLabel104.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel104.setForeground(new java.awt.Color(255, 102, 0));
        jLabel104.setText("* Telephone");
        Edit_M.add(jLabel104, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 200, 100, -1));

        jTextField33.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField33KeyTyped(evt);
            }
        });
        Edit_M.add(jTextField33, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 200, 210, -1));
        Edit_M.add(jTextField34, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 170, 210, -1));
        Edit_M.add(jTextField35, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, 210, -1));
        Edit_M.add(jTextField40, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 230, 210, -1));
        Edit_M.add(jPasswordField8, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 140, 210, -1));
        Edit_M.add(jPasswordField9, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 110, 210, -1));

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel35.setText("* Indicates mandatory field");
        Edit_M.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 160, 20));

        jLabel106.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel106.setForeground(new java.awt.Color(255, 102, 0));
        jLabel106.setText("* IM_ID");
        Edit_M.add(jLabel106, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 230, -1, -1));

        jLabel108.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication1/online-shop-logo-template-ai-eps-10.jpg"))); // NOI18N
        Edit_M.add(jLabel108, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 680, 360));

        getContentPane().add(Edit_M, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 360));
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        save();
        jRadioButton1.setSelected(false);
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        
        if(jTextField1.getText()==""||jPasswordField1.getPassword().length==0)
        {
           JOptionPane.showMessageDialog(null,"Invalid Input");
        }
        else
        {
        if(jRadioButton1.isSelected())
        {
            boolean result;
            String s1=jTextField1.getText();
            char a[]=jPasswordField1.getPassword();
            String s2=new String(a);
            
            result=data.validateManager(s1,s2);
            
            if(result==false)
               JOptionPane.showMessageDialog(null,"Invalid Input.");
            else 
                {
                    Welcome_Panel.setVisible(false);
                    Manager_Panel.setVisible(true);
                }
        }
        else
            if(jRadioButton2.isSelected())
            {
            boolean result;
            String s1=jTextField1.getText();
            char a[]=jPasswordField1.getPassword();
            String s2=new String(a);
           
            result=data.validateCustomer(s1,s2);
            if(result==false)
               JOptionPane.showMessageDialog(null,"Invalid Input.");
            else 
                {
                    Welcome_Panel.setVisible(false);
                    Customer_Panel.setVisible(true);
                    jTextField2.setText(null);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Select the Manager/Customer Button"); 
            }
        }//end else (jTextField1.getText()==""||jPasswordField1.getPassword().length==0)
        save();
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed

        jRadioButton2.setSelected(false);
        save();
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        if(jRadioButton1.isSelected()==false &&jRadioButton2.isSelected()==false)
            JOptionPane.showMessageDialog(null,"Please Select Manager or Customer.");
        else if(jRadioButton1.isSelected()==false)
        {
            Welcome_Panel.setVisible(false);
            Reg_C.setVisible(true);
        }
        else
        {
            Welcome_Panel.setVisible(false);
            Reg_M.setVisible(true);
        }
        save();
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel60MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel60MouseClicked
        String s1=new String(jPasswordField2.getPassword());
        String s2=new String(jPasswordField3.getPassword());
        if(jTextField4.getText()==null ||jTextField5.getText()==null ||jTextField6.getText()==null ||jTextField7.getText()==null || !s1.equals(s2))
        {
            JOptionPane.showMessageDialog(null,"Please Enter all the mandatory details.\nAlso check for Password.");
            jPasswordField2.setText("");
            jPasswordField3.setText("");
        }else if(!data.validateEmail(jTextField4.getText()))
        {
             JOptionPane.showMessageDialog(null,"Invalid Email");
        }
        else
        {
            String id= "C"+(data.listCustomer.size()+1);
            if(data.listCustomer.size()>0)
                id= "C"+(Integer.parseInt(data.listCustomer.get(data.listCustomer.size()-1).ID.substring(1))+1);
            Customer obj=new Customer(jTextField7.getText(),jTextField5.getText(),jTextField4.getText(),id,s1,jTextField6.getText(),jTextField3.getText());
            data.listCustomer.add(obj);
            JOptionPane.showMessageDialog(null,"Your ID is "+id);
            jTextField4.setText("");
            jTextField5.setText("");
            jTextField6.setText("");
            jTextField7.setText("");
            jTextField3.setText("");
            jPasswordField2.setText("");
            jPasswordField3.setText("");
        }
        save();
    }//GEN-LAST:event_jLabel60MouseClicked

    private void jLabel70MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel70MouseClicked
       String s1=new String(jPasswordField4.getPassword());
        String s2=new String(jPasswordField5.getPassword());
        if(jTextField8.getText()==null ||jTextField9.getText()==null ||jTextField10.getText()==null ||jTextField11.getText()==null||jTextField12.getText()==null||jTextField13.getText()==null||jTextField14.getText()==null||jTextField15.getText()==null||jTextField16.getText()==null||jTextField17.getText()==null || !s1.equals(s2))
        {
            JOptionPane.showMessageDialog(null,"Please enter all the details.\nAlso check for Password.");
            jPasswordField4.setText("");
            jPasswordField5.setText("");
        }
        else if(!data.validateDate(Integer.parseInt(jTextField14.getText()), Integer.parseInt(jTextField16.getText()),Integer.parseInt(jTextField15.getText())))
        {
            JOptionPane.showMessageDialog(null,"Invalid Date");
        }else if(!data.validateEmail(jTextField9.getText()))
        {
            JOptionPane.showMessageDialog(null,"Invalid Email");
        }
        else
        {   
            String id= "M"+(data.listManager.size()+1);
            if(data.listManager.size()>0)
                id= "M"+(Integer.parseInt(data.listManager.get(data.listManager.size()-1).ID.substring(1))+1);
            Manager obj=new Manager(jTextField12.getText(),jTextField10.getText(),jTextField9.getText(),id,s1,jTextField13.getText(),jTextField14.getText()+"/"+jTextField16.getText()+"/"+jTextField15.getText(),jTextField11.getText(),jTextField17.getText(),jTextField8.getText());
            data.listManager.add(obj);
            JOptionPane.showMessageDialog(null,"Your ID is "+id);
            //input in database.
        }
        jTextField8.setText("");
        jTextField9.setText("");
        jTextField10.setText("");
        jTextField11.setText("");
        jTextField12.setText("");
        jTextField13.setText("");
        jTextField14.setText("");
        jTextField15.setText("");
        jTextField16.setText("");
        jTextField17.setText("");
        jPasswordField4.setText("");
        jPasswordField5.setText("");
        save();
    }//GEN-LAST:event_jLabel70MouseClicked

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
        Customer_Panel.setVisible(false);
        Upload_Item.setVisible(true);
        save();
    }//GEN-LAST:event_jLabel21MouseClicked

    private void jLabel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MouseClicked
        Customer_Panel.setVisible(false);
        UploadedItem_Panel.setVisible(true);
        jList13.removeAll();
        DefaultListModel model1=new DefaultListModel();
        int i;
        String s;
        int idd=Integer.parseInt(jTextField1.getText().substring(1));
        for(i=0;i<data.listCustomer.get(idd-1).listItem.size();i++)
        {
            String av="";
            if(data.listCustomer.get(idd-1).listItem.get(i).status==1)
                av="Negotiable";
            if(data.listCustomer.get(idd-1).listItem.get(i).status==2)
                av="NotNegotiable";
            if(data.listCustomer.get(idd-1).listItem.get(i).status==3)
                av="Approved";
             if(data.listCustomer.get(idd-1).listItem.get(i).status==4)
                av="SoldOut";
             
            s=data.listCustomer.get(idd-1).listItem.get(i).name+"        "
                    +data.listCustomer.get(idd-1).listItem.get(i).category
                    +"        "+data.listCustomer.get(idd-1).listItem.get(i).description
                    +"        "+data.listCustomer.get(idd-1).listItem.get(i).price
                    +"        "+av
                    +"        "+data.listCustomer.get(idd-1).listItem.get(i).bidprice
                    +"        "+data.listCustomer.get(idd-1).listItem.get(i).ID;
            model1.addElement(s);
        }
        jList13.setModel(model1);
        save();
    }//GEN-LAST:event_jLabel22MouseClicked

    private void jLabel93MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel93MouseClicked
        /*
        Edit Customer account
        */
        int n;
        String temp_ID=jTextField1.getText();
        if(jTextField30.getText()==null ||jTextField29.getText()==null ||jTextField28.getText()==null ||jTextField26.getText()==null||jPasswordField10.getPassword().length==0)
        {
            JOptionPane.showMessageDialog(null,"No field indicated by * should be left empty");
        }else
        {
            if(new String(jPasswordField6.getPassword()).equals(new String(jPasswordField10.getPassword())))
            {
                 for(n=0;n<data.listCustomer.size();n++)
                if(data.listCustomer.get(n).ID.equals(temp_ID))
                    break;
                data.listCustomer.get(n).name=jTextField30.getText();
                data.listCustomer.get(n).city=jTextField29.getText();
                data.listCustomer.get(n).tel=jTextField28.getText(); 
                data.listCustomer.get(n).im_ID=jTextField26.getText();

                JOptionPane.showMessageDialog(null,"Information Updated successfully");
                save();// TODO add your handling code here:
            }
            else
                JOptionPane.showMessageDialog(null,"Password does not match");
        }
    }//GEN-LAST:event_jLabel93MouseClicked

    private void jLabel103MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel103MouseClicked
        /*
        Edit Manager account
        */
        int n;
        String temp_ID=jTextField1.getText();
        if(jTextField35.getText()==null ||jTextField34.getText()==null ||jTextField33.getText()==null ||jTextField40.getText()==null||jPasswordField9.getPassword().length==0)
        {
            JOptionPane.showMessageDialog(null,"No field indicated by * should be left empty");
        }else
        {
            if(new String(jPasswordField9.getPassword()).equals(new String(jPasswordField8.getPassword())))
            {
                 for(n=0;n<data.listManager.size();n++)
                if(data.listManager.get(n).ID.equals(temp_ID))
                    break;
                data.listManager.get(n).name=jTextField35.getText();
                data.listManager.get(n).addr=jTextField34.getText();
                data.listManager.get(n).tel=jTextField33.getText(); 
                data.listManager.get(n).im_ID=jTextField40.getText();
               
                JOptionPane.showMessageDialog(null,"Information Updated successfully");
                save();// TODO add your handling code here:
            }
            else
                JOptionPane.showMessageDialog(null,"Password does not match");
        }
        save();// TODO add your handling code here:
    }//GEN-LAST:event_jLabel103MouseClicked

    private void jLabel30MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel30MouseClicked
        int i;
        String s;
        jList18.removeAll();
        DefaultListModel model1=new DefaultListModel();
        ArrayList<Item>searchList=new ArrayList<Item>(data.listItem);
      
        for(i=0;i<searchList.size();i++)
        {
            String av="";
            if(searchList.get(i).status==1)
                av="Negotiable";
            if(searchList.get(i).status==2)
                av="NotNegotiable";
            if(searchList.get(i).status==3)
                av="Approved";
             if(searchList.get(i).status==4)
                av="SoldOut";
             
             
            s=searchList.get(i).name+
                    "        "+searchList.get(i).category+"        "+
                    searchList.get(i).ID_seller+"        "+
                    searchList.get(i).description+"        "+
                    searchList.get(i).price+"        "+
                    searchList.get(i).city+"        "+
                    av+"        "+
                    searchList.get(i).ID;
            model1.addElement(s);
        }
        jList18.setModel(model1);
        Manager_Panel.setVisible(false);
        Manage_Item.setVisible(true);
        save();
    }//GEN-LAST:event_jLabel30MouseClicked

    private void jLabel48MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel48MouseClicked
        jList22.removeAll();
        int i;
        DefaultListModel model1=new DefaultListModel();
        for(i=0;i<data.listCategory.size();i++)
            model1.addElement(data.listCategory.get(i).name);
        jList22.setModel(model1);
        
        System.out.print("\nSize of Category list is::"+data.listCategory.size());
        Manager_Panel.setVisible(false);
        Manage_Category.setVisible(true);
        save();
    }//GEN-LAST:event_jLabel48MouseClicked

    private void jLabel50MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel50MouseClicked
        int i;
        String s="";
        for(i=0;i<data.listPurchase.size();i++)
            s+=data.listPurchase.get(i).name+"        "
                    +data.listPurchase.get(i).ID_buyer+"        "
                    +data.listPurchase.get(i).ID_seller+
                    "        Rs."+data.listPurchase.get(i).bidprice+"\n";
        jTextArea1.setText(s);
        Manager_Panel.setVisible(false);
        Manage_Transaction.setVisible(true);
        save();
    }//GEN-LAST:event_jLabel50MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        int i;
        String s="";
        for(i=0;i<data.listCustomer.size();i++){
            s+=data.listCustomer.get(i).name+"        "
                    +data.listCustomer.get(i).email+"        "
                    +data.listCustomer.get(i).tel+"        "
                    +data.listCustomer.get(i).ID+"\n";
        }
        jTextArea2.setText(s);
        Manager_Panel.setVisible(false);
        Manage_Customer.setVisible(true);
        save();
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel51MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel51MouseClicked
        /*
        Mouse clicked on Manager Panel
        */
        String temp_pass;
        String temp_ID=jTextField1.getText();
        int n;
        for(n=0;n<data.listManager.size();n++)
            if(data.listManager.get(n).ID.equals(temp_ID))
                break;
        
        temp_pass=data.listManager.get(n).password;
        
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter your password:");
        JPasswordField pass = new JPasswordField(40);
        panel.add(label);
        panel.add(pass);
        String[] options = new String[]{"OK", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, panel, "Authorization",
                         JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                         null, options, options[1]);
        if(option == 0) // pressing OK button
            {
                char[] password = pass.getPassword();
                 if(temp_pass.equals(new String(password)))
                 {
                    Manager_Panel.setVisible(false);
                    Edit_M.setVisible(true);
                    
                    jPasswordField8.setText(temp_pass);
                    jPasswordField9.setText(temp_pass);
                    jTextField35.setText(data.listManager.get(n).name); 
                    jTextField34.setText(data.listManager.get(n).addr); 
                    jTextField33.setText(data.listManager.get(n).tel); 
                    jTextField40.setText(data.listManager.get(n).im_ID);
                 }
            
           }
        save();
        
    }//GEN-LAST:event_jLabel51MouseClicked

    private void jLabel44MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel44MouseClicked
       JFileChooser UploadFile=new JFileChooser();
       UploadFile.showOpenDialog(null);
       f=UploadFile.getSelectedFile();
       if(f==null)
           JOptionPane.showMessageDialog(null,"No File Chosen");
       BufferedImage img = null;
        try {
            img = ImageIO.read(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(jLabel44.getWidth(), jLabel44.getHeight(),Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        jLabel44.setIcon(imageIcon);
       save();
    }//GEN-LAST:event_jLabel44MouseClicked

    private void jLabel45MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel45MouseClicked
        if(jTextField18.getText()==null||jTextField19.getText()==null||jTextField20.getText()==null||jTextField21.getText()==null||jTextField25.getText()==null||jTextField24.getText()==null||(!jRadioButton3.isSelected()&&!jRadioButton4.isSelected()))
            JOptionPane.showMessageDialog(null,"Please enter all the fields");
        else
        {
            if(f==null)
                JOptionPane.showMessageDialog(null,"Please enter the image");
            else
            {
                //upload to database.
                int i,j;
                for(i=0;i<data.listCategory.size();i++)
                if(data.listCategory.get(i).name.equalsIgnoreCase(jTextField18.getText()))
                    break;
                if(i==data.listCategory.size())
                    JOptionPane.showMessageDialog(null,"Category entered is wrong.");
                else{
                int temp_id;
                String id= "$"+i+"-"+(data.listCategory.get(i).itemList.size()+1);
                if(data.listCategory.get(i).itemList.size()>0)
                    {
                        temp_id=Integer.parseInt(data.listCategory.get(i).itemList.get(data.listCategory.get(i).itemList.size()-1).ID.substring(3));
                        id= "$"+i+"-"+(temp_id+1);
                    }
                System.out.println("\nSystem Generated ID::"+id);
                if(jRadioButton3.isSelected())
                    j=1;
                else
                    j=2;
                
                Item obj=new Item(jTextField18.getText(),Float.parseFloat(jTextField20.getText()),Integer.parseInt(jTextField25.getText()),jTextField21.getText(),jTextField19.getText(),jTextField24.getText(),1,f,jTextField1.getText(),id,j);
                
                data.listItem.add(obj);
                data.listCategory.get(i).itemList.add(obj);
                String idd=jTextField1.getText();
                for(j=0;j<data.listCustomer.size();j++)
                    if(data.listCustomer.get(j).ID.equals(idd))
                        break;
                
                if(j<data.listCustomer.size())
                data.listCustomer.get(j).listItem.add(obj);
                else
                    JOptionPane.showMessageDialog(null,"No Customer ID found");
                
                jTextField18.setText("");//category
                jTextField19.setText("");//city
                jTextField20.setText("");//price
                jTextField21.setText("");//name
                jTextField25.setText("");//age
                jTextField24.setText("");//description
                jLabel44.setIcon(null);
                jRadioButton3.setSelected(false);
                jRadioButton4.setSelected(false);
                f=null;
                }
            }
        }
        save();
    }//GEN-LAST:event_jLabel45MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        
        String temp_id="",name="";
        int i,j;
        jList6.removeAll();
        String s=jTextField2.getText();
        ArrayList <Item> searchList=new ArrayList <Item> ();
        searchList=data.search(s,jTextField1.getText());
        DefaultListModel model1=new DefaultListModel();
        
        for(i=0;i<searchList.size();i++)
        {
             String av="";
            if(searchList.get(i).status==1)
                av="Negotiable";
            if(searchList.get(i).status==2)
                av="NotNegotiable";
            if(searchList.get(i).status==3)
                av="Approved";
             if(searchList.get(i).status==4)
                av="SoldOut";
            
             for(j=0;j<data.listCustomer.size();j++)
                if(searchList.get(i).ID_seller.equals(data.listCustomer.get(j).ID))
                {
                    name=data.listCustomer.get(j).name;
                    break;
                }
           s=searchList.get(i).name+"    "+searchList.get(i).category+"     "
                   +name+"     "+searchList.get(i).city+"     "
                   +searchList.get(i).price+"     "+
                   +searchList.get(i).bidprice+"     "
                   +av+"     "+searchList.get(i).ID;
           
           model1.addElement(s);
        }
        jList6.setModel(model1);
        Customer_Panel.setVisible(false);
        Item_Panel.setVisible(true);
        jTextField2.setText(null);
        save();
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel110MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel110MouseClicked
       
        String name="",ID="",seller="";
        int j=0;
        
        DefaultListModel model=(DefaultListModel)jList18.getModel();
        int selectedIndex = jList18.getSelectedIndex();
        if (selectedIndex != -1) 
        { name=model.get(selectedIndex).toString();
        
        while(name.charAt(j++)!='$');
       
        ID=name.substring(--j);
        data.deleteItem(ID);
        model.remove(selectedIndex);
        }else
            JOptionPane.showMessageDialog(null,"Please select");
        save();
    }//GEN-LAST:event_jLabel110MouseClicked

    private void jLabel118MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel118MouseClicked
        DefaultListModel model=(DefaultListModel)jList18.getModel();
        int selectedIndex = jList18.getSelectedIndex();
        
        if (selectedIndex != -1)
        {
         String name="",ID="";
         int j,i;
         name=model.get(selectedIndex).toString();
        
        j=0;
        int n;
        
        String category;
        
        while(name.charAt(j++)!='$');
       
        ID=name.substring(--j);
        
        for(i=0;i<data.listItem.size();i++)
            if(data.listItem.get(i).ID.equals(ID))
                break;
        
  
            String s=JOptionPane.showInputDialog("Enter the name of the category.");
            category=data.listItem.get(i).category;
            
            for(j=0;j<data.listCategory.size();j++)
                if(data.listCategory.get(j).name.equals(s))
                    break;
            
            if(j!=data.listCategory.size())
            {
                 //for removing from previous category
               for(n=0;n<data.listCategory.size();n++)
                if(data.listCategory.get(n).name.equals(category))
                    break;
               
               data.listCategory.get(j).itemList.remove(data.listItem.get(i));
               data.listItem.get(i).category=s;
               data.listCategory.get(j).itemList.add(data.listItem.get(i));
            }else
                JOptionPane.showMessageDialog(null,"The category given by you doesnot exist in the database");
        }else
            JOptionPane.showMessageDialog(null,"Please click on the Item");
        
        save();
    }//GEN-LAST:event_jLabel118MouseClicked

    private void jLabel122MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel122MouseClicked
        
        int i;
        DefaultListModel model=(DefaultListModel)jList22.getModel();
        String s=JOptionPane.showInputDialog("Add Category");
        
        if(!s.isEmpty())
        {
        for(i=0;i<data.listCategory.size();i++)
            if((s.equals(data.listCategory.get(i).name)))
                break;
        if(i==data.listCategory.size())
        {
            data.listCategory.add(new Category(s));
            model.addElement(s);
            jList22.setModel(model);
        }
        else
            JOptionPane.showMessageDialog(null,"Category already exists");
        }else
            JOptionPane.showMessageDialog(null,"Fill Up something");
        
        save();
    }//GEN-LAST:event_jLabel122MouseClicked

    private void jLabel130MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel130MouseClicked
        DefaultListModel model=(DefaultListModel)jList22.getModel();
        int selectedIndex = jList22.getSelectedIndex();
        if (selectedIndex != -1)
        {
            int i;
            String s=model.get(selectedIndex).toString();
            for(i=0;!(s.equals(data.listCategory.get(i).name));i++);
            if(data.listCategory.get(i).itemList.size()>0 && i<data.listCategory.size())
                JOptionPane.showMessageDialog(null,"Cannot Remove Category. Items present in the category.");
            else if(i<data.listCategory.size())
            {
                data.listCategory.remove(i);
                model.remove(selectedIndex);
            }
            else
                JOptionPane.showMessageDialog(null,"Not valid input");
        }
        save();
    }//GEN-LAST:event_jLabel130MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        Welcome_Panel.setVisible(true);
        save();Customer_Panel.setVisible(false);
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel36MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel36MouseClicked
        Welcome_Panel.setVisible(true);
        save();Item_Panel.setVisible(false);
    }//GEN-LAST:event_jLabel36MouseClicked

    private void jLabel33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseClicked
        Customer_Panel.setVisible(true);
        save();Item_Panel.setVisible(false);
    }//GEN-LAST:event_jLabel33MouseClicked

    private void jLabel46MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel46MouseClicked
        Welcome_Panel.setVisible(true);
        save();Upload_Item.setVisible(false);
    }//GEN-LAST:event_jLabel46MouseClicked

    private void jLabel43MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel43MouseClicked
        Customer_Panel.setVisible(true);
        save();Upload_Item.setVisible(false);
    }//GEN-LAST:event_jLabel43MouseClicked

    private void jLabel83MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel83MouseClicked
        Welcome_Panel.setVisible(true);
        save();UploadedItem_Panel.setVisible(false);
    }//GEN-LAST:event_jLabel83MouseClicked

    private void jLabel86MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel86MouseClicked
        Customer_Panel.setVisible(true);
        save();UploadedItem_Panel.setVisible(false);
    }//GEN-LAST:event_jLabel86MouseClicked

    private void jLabel58MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel58MouseClicked
        Welcome_Panel.setVisible(true);
        save();Reg_C.setVisible(false);
    }//GEN-LAST:event_jLabel58MouseClicked

    private void jLabel91MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel91MouseClicked
        Customer_Panel.setVisible(true);
        save();Edit_C.setVisible(false);
    }//GEN-LAST:event_jLabel91MouseClicked

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
        Welcome_Panel.setVisible(true);
        
        save();Manager_Panel.setVisible(false);
    }//GEN-LAST:event_jLabel16MouseClicked

    private void jLabel116MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel116MouseClicked
        Manage_Item.setVisible(false);
        Manager_Panel.setVisible(true);
        save();
    }//GEN-LAST:event_jLabel116MouseClicked

    private void jLabel128MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel128MouseClicked
         Manage_Category.setVisible(false);
        Manager_Panel.setVisible(true);
        save();
    }//GEN-LAST:event_jLabel128MouseClicked

    private void jLabel140MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel140MouseClicked
         Manage_Customer.setVisible(false);
        Manager_Panel.setVisible(true);
        save();
    }//GEN-LAST:event_jLabel140MouseClicked

    private void jLabel101MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel101MouseClicked
        Edit_M.setVisible(false);
        Manager_Panel.setVisible(true);
        save();
    }//GEN-LAST:event_jLabel101MouseClicked

    private void jLabel68MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel68MouseClicked
        Welcome_Panel.setVisible(true);
        Reg_M.setVisible(false);
        save();
    }//GEN-LAST:event_jLabel68MouseClicked

    private void jLabel86MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel86MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel86MouseEntered

    private void jLabel76MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel76MouseClicked
         
        String name="",ID="",seller="";
        int j=0,i,n,m;
        float bid=0;
        DefaultListModel model=(DefaultListModel)jList6.getModel();
        int selectedIndex = jList6.getSelectedIndex();
        if (selectedIndex != -1) 
            name=model.get(selectedIndex).toString();
       
        while(name.charAt(j++)!='$');
        
        ID=name.substring(--j);
        
        System.out.print("\nID="+ID);
        //Now from this ID I will search for its status
        for(i=0;i<data.listItem.size();i++)
            if(data.listItem.get(i).ID.equals(ID))
                break;
        
               m=0;
               seller=data.listItem.get(i).ID_seller;
               for(n=0;n<data.listCustomer.size();n++)
                   if(data.listCustomer.get(n).ID.equals(seller))
                   {
                       m=n;
                       break;
                   }
               
        if(i!=data.listItem.size())
        {   
            if(!(data.listItem.get(i).weight==2&&(!(data.listItem.get(i).city.equalsIgnoreCase(data.listCustomer.get(m).city)))))
            {
            if(data.listItem.get(i).status==1)
            {
               bid=Float.parseFloat(JOptionPane.showInputDialog("Enter The Bid price"));
               if(bid<=data.listItem.get(i).price&&bid>=data.listItem.get(i).bidprice)
               {
               
               /*
               Now I have to change the bid price in three places
               In listItem in Database
               In listCustomer, whoever owned this item
               In listCategory
               */
               //listItem
               data.listItem.get(i).bidprice=bid;
               
               //In listCustomer
               n=0;
               seller=data.listItem.get(i).ID_seller;
               for(j=0;j<data.listCustomer.size();j++)
                   if(data.listCustomer.get(j).ID.equals(seller))
                   {
                       n=j;
                       break;
                   }
               
               for(j=0;j<data.listCustomer.get(n).listItem.size();j++)
                if(data.listCustomer.get(n).listItem.get(j).ID.equals(ID))
                    break;
               
               if(j!=data.listCustomer.get(n).listItem.size())
               data.listCustomer.get(n).listItem.get(j).bidprice=bid; 
               else
               JOptionPane.showMessageDialog(null,"How can it happen");
               
               //change in listCategory
               for(i=0;i<data.listCategory.size();i++)
               {
                   for(j=0;j<data.listCategory.get(i).itemList.size();j++)
                   {
                      if(data.listCategory.get(i).itemList.get(j).ID.equals(ID))
                         data.listCategory.get(i).itemList.get(j).bidprice=bid; 
                   }
               }
               }else
                   JOptionPane.showMessageDialog(null,"You can bid more than the current item price\nOr less than Current bid Price");
               //****End of changing*******
            }else if(data.listItem.get(i).status==2)
            {
              n=JOptionPane.showConfirmDialog(null,"You cannot set the bid price \nThis item is now non negotiable\nDo you wanna Buy it? ");
              if(n==JOptionPane.YES_OPTION)
              {
                  String url="https://www.paypal.com/webapps/mpp/send-money-online";
                            try {
                                    java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
                                } catch (Exception ex) {  
                                        JOptionPane.showMessageDialog(null,"Cannot connect to the internet");
                                                         }
                            
                /*
                    Right now i am calling a function 
                    Change status in database changeStatus()
                */
                //Its time to change the status of the item
                           data.changeStatus(ID,4);
                           data.listItem.get(i).ID_buyer=jTextField1.getText();
                           data.listPurchase.add(data.listItem.get(i));
              }
            }else if(data.listItem.get(i).status==3)
            {
                 n=JOptionPane.showConfirmDialog(null,"Seller has Approved\n If You wanna Buy this Item Click OK");
                if(n==JOptionPane.YES_OPTION)
                    {
                        String url="https://www.paypal.com/webapps/mpp/send-money-online";
                            try {
                                    java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
                                } catch (Exception ex) {  
                                         JOptionPane.showMessageDialog(null,"Cannot connect to the internet");
                                                         }
                    }
                
                //Its time to change the status of the item
                data.changeStatus(ID,4); 
                data.listItem.get(i).ID_buyer=jTextField1.getText();
                data.listPurchase.add(data.listItem.get(i));
            
            }else if(data.listItem.get(i).status==4)
                JOptionPane.showMessageDialog(null,"Sorry!!!! This Item has been sold out");
             else
                JOptionPane.showMessageDialog(null,"Status can not be extracted::"+data.listItem.get(i).status);  
           }else
            JOptionPane.showMessageDialog(null,"Item is Heavy and you are not in that City so You cannot Buy");
        }else
         JOptionPane.showMessageDialog(null,"No Item found in ItemList of the Database");
        
        save();
    }//GEN-LAST:event_jLabel76MouseClicked

    private void jLabel152MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel152MouseClicked
        Manage_Transaction.setVisible(false);
        Manager_Panel.setVisible(true);
        save();
    }//GEN-LAST:event_jLabel152MouseClicked

    private void jLabel134MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel134MouseClicked
        String s=JOptionPane.showInputDialog("Enter ID");
        int i=0,j;
        if(data.listCustomer.size()>0)
            for(i=0;i<data.listCustomer.size();i++)
                {
                    if(s.equalsIgnoreCase(data.listCustomer.get(i).ID))
                    break;
                }
        else
                i=data.listCustomer.size();
        
        if(i<data.listCustomer.size())
        {
            for(j=0;j<data.listCustomer.get(i).listItem.size();j++)
                data.deleteItem(data.listCustomer.get(i).listItem.get(j).ID);
            
            data.listCustomer.remove(i);
            String s1="";
        for(i=0;i<data.listCustomer.size();i++)
            s1+=data.listCustomer.get(i).name+"\t\t"+data.listCustomer.get(i).email+"\t\t"+data.listCustomer.get(i).tel+"\t"+data.listCustomer.get(i).ID+"\n";
        jTextArea2.setText(s1);
            
        }
        else
            JOptionPane.showMessageDialog(null,"Wrong ID");
        save();
    }//GEN-LAST:event_jLabel134MouseClicked

    private void jLabel127MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel127MouseClicked
         /*
        Changing status
        */
        int i,j=0,n=0,option=0;
        String s="",ID="",seller_ID=jTextField1.getText();
        DefaultListModel model=(DefaultListModel)jList13.getModel();
        int selectedIndex = jList13.getSelectedIndex();
        if (selectedIndex != -1)
        {
           s=model.get(selectedIndex).toString();
           j=0;
           while(s.charAt(j++)!='$');
           ID=s.substring(--j);
           
           for(i=0;i<data.listCustomer.size();i++)
           if(data.listCustomer.get(i).ID.equals(seller_ID))
               break;
           
          for(n=0;n<data.listCustomer.get(i).listItem.size();n++)
              if(data.listCustomer.get(i).listItem.get(n).ID.equals(ID))
                  break;
          
         option=Integer.parseInt(JOptionPane.showInputDialog("Enter in integer\n1.Negotiable\n2.Not Negotiable\n3.Approved"));
         if(option==1||option==2||option==3)
         {
             data.changeStatus(ID, option);
         }else
             JOptionPane.showMessageDialog(null,"Please enter in Integer from 1-3 only");
          
           
        }else
            JOptionPane.showMessageDialog(null,"Please Select a Item");
        save();
    }//GEN-LAST:event_jLabel127MouseClicked

    private void jLabel34MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel34MouseClicked
        
        String s="";
        int i;
        
        for(i=0;i<data.listCategory.size();i++)
            s=s+data.listCategory.get(i).name+"\n";
        
        JOptionPane.showMessageDialog(null,s);
        save();
    }//GEN-LAST:event_jLabel34MouseClicked

    private void jLabel25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel25MouseClicked
        /*
        Mouse clicked on Customer Panel
        */
        String temp_pass;
        String temp_ID=jTextField1.getText();
        int n;
        for(n=0;n<data.listCustomer.size();n++)
            if(data.listCustomer.get(n).ID.equals(temp_ID))
                break;
        
        temp_pass=data.listCustomer.get(n).password;
        
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter your password:");
        JPasswordField pass = new JPasswordField(40);
        panel.add(label);
        panel.add(pass);
        String[] options = new String[]{"OK", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, panel, "Authorization",
                         JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                         null, options, options[1]);
        if(option == 0) // pressing OK button
            {
                char[] password = pass.getPassword();
                 if(temp_pass.equals(new String(password)))
                 {
                    Customer_Panel.setVisible(false);
                    
                    Edit_C.setVisible(true);
                    jPasswordField10.setText(temp_pass);
                    jPasswordField6.setText(temp_pass);
                    jTextField30.setText(data.listCustomer.get(n).name); 
                    jTextField29.setText(data.listCustomer.get(n).getCity()); 
                    jTextField28.setText(data.listCustomer.get(n).tel); 
                    jTextField26.setText(data.listCustomer.get(n).im_ID);
                 }
            
           }
        save();
    }//GEN-LAST:event_jLabel25MouseClicked

    private void jLabel132MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel132MouseClicked
        
        String name="",ID="";
        int j=0;
        
        DefaultListModel model=(DefaultListModel)jList6.getModel();
        int selectedIndex = jList6.getSelectedIndex();
        if (selectedIndex != -1) 
        { name=model.get(selectedIndex).toString();
        
        while(name.charAt(j++)!='$');
       
        ID=name.substring(--j);
        for(j=0;j<data.listItem.size();j++)
            if(data.listItem.get(j).ID.equals(ID))
                break;
        
        JLabel label = new JLabel();
        BufferedImage img = null;
        try {
            img = ImageIO.read(data.listItem.get(j).f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(img.getWidth(), img.getHeight(),Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        label.setSize(img.getWidth(),img.getHeight());
        label.setIcon(imageIcon);
        JOptionPane.showMessageDialog(null, label);
        }
        save();
        
    }//GEN-LAST:event_jLabel132MouseClicked

    private void jRadioButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton3MouseClicked
       jRadioButton4.setSelected(false);
       save();
    }//GEN-LAST:event_jRadioButton3MouseClicked

    private void jRadioButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton4MouseClicked
        jRadioButton3.setSelected(false);
        save();
    }//GEN-LAST:event_jRadioButton4MouseClicked

    private void jLabel125MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel125MouseClicked
        int j=0;
        String s="",ID="";
        DefaultListModel model=(DefaultListModel)jList13.getModel();
        int selectedIndex = jList13.getSelectedIndex();
        if (selectedIndex != -1)
        {
            s=model.get(selectedIndex).toString();
            j=0;
            while(s.charAt(j++)!='$');
            ID=s.substring(--j);
            data.deleteItem(ID);
            model.remove(selectedIndex);
        }else
        JOptionPane.showMessageDialog(null,"Please Select a Item");
        save();
    }//GEN-LAST:event_jLabel125MouseClicked

    private void jLabel135MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel135MouseClicked
        String name="",ID="";
        int j=0;
        
        DefaultListModel model=(DefaultListModel)jList13.getModel();
        int selectedIndex = jList13.getSelectedIndex();
        if (selectedIndex != -1) 
        { name=model.get(selectedIndex).toString();
        
        while(name.charAt(j++)!='$');
       
        ID=name.substring(--j);
        for(j=0;j<data.listItem.size();j++)
            if(data.listItem.get(j).ID.equals(ID))
                break;
        
        JLabel label = new JLabel();
        BufferedImage img = null;
        try {
            img = ImageIO.read(data.listItem.get(j).f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(img.getWidth(), img.getHeight(),Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        label.setSize(img.getWidth(),img.getHeight());
        label.setIcon(imageIcon);
        JOptionPane.showMessageDialog(null, label);
        }
        save();
    }//GEN-LAST:event_jLabel135MouseClicked

    private void jLabel138MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel138MouseClicked
       String name="",ID="";
        int j=0;
        
        DefaultListModel model=(DefaultListModel)jList6.getModel();
        int selectedIndex = jList6.getSelectedIndex();
        if (selectedIndex != -1) 
        { name=model.get(selectedIndex).toString();
        
        while(name.charAt(j++)!='$');
       
        ID=name.substring(--j);
        for(j=0;j<data.listItem.size();j++)
            if(data.listItem.get(j).ID.equals(ID))
                break;
        
        JOptionPane.showMessageDialog(null,""+data.listItem.get(j).description);
        }else
            JOptionPane.showMessageDialog(null,"First select an item");
        save();
    }//GEN-LAST:event_jLabel138MouseClicked

    private void jLabel142MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel142MouseClicked
        
        int n,i=0;
        String email="";
        
        String s=JOptionPane.showInputDialog("Enter ID");
        for(n=0;n<data.listCustomer.size();n++)
              if(data.listCustomer.get(n).ID.equals(s))
                  break;
        
        email=data.listCustomer.get(n).email;
        n=0;
        while(email.charAt(n++)!='@');
        email=email.substring(n);
        
        String url="https://www."+email;
                            try {
                                    java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
                                } catch (Exception ex) {  
                                        JOptionPane.showMessageDialog(null,"Cannot connect to the internet");
                                                         }
        
        
        save();
    }//GEN-LAST:event_jLabel142MouseClicked

    private void jTextField5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyTyped
        char ch=evt.getKeyChar();
        if(! Character.isDigit(ch)){
          evt.consume();
        }
    }//GEN-LAST:event_jTextField5KeyTyped

    private void jTextField28MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField28MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField28MouseClicked

    private void jTextField28KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField28KeyTyped
        char ch=evt.getKeyChar();
        if(! Character.isDigit(ch)){
          evt.consume();
        }
    }//GEN-LAST:event_jTextField28KeyTyped

    private void jTextField10KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField10KeyTyped
        char ch=evt.getKeyChar();
        if(! Character.isDigit(ch)){
          evt.consume();
        }
    }//GEN-LAST:event_jTextField10KeyTyped

    private void jTextField14KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField14KeyTyped
       char ch=evt.getKeyChar();
        if(! Character.isDigit(ch)){
          evt.consume();
        }
    }//GEN-LAST:event_jTextField14KeyTyped

    private void jTextField16KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField16KeyTyped
        char ch=evt.getKeyChar();
        if(! Character.isDigit(ch)){
          evt.consume();
        }
    }//GEN-LAST:event_jTextField16KeyTyped

    private void jTextField15KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField15KeyTyped
        char ch=evt.getKeyChar();
        if(! Character.isDigit(ch)){
          evt.consume();
        }
    }//GEN-LAST:event_jTextField15KeyTyped

    private void jTextField33KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField33KeyTyped
        char ch=evt.getKeyChar();
        if(! Character.isDigit(ch)){
          evt.consume();
        }
    }//GEN-LAST:event_jTextField33KeyTyped

    private void jTextField25KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField25KeyTyped
        char ch=evt.getKeyChar();
        if(! Character.isDigit(ch)){
          evt.consume();
        }
    }//GEN-LAST:event_jTextField25KeyTyped

    private void jTextField20KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField20KeyTyped
        char ch=evt.getKeyChar();
        if(! Character.isDigit(ch)){
          evt.consume();
        }
    }//GEN-LAST:event_jTextField20KeyTyped

    private void jLabel146MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel146MouseClicked
    String printData="ITEM NAME       BUYER ID       SELLER ID       PRICE\n"+jTextArea1.getText();
    PrinterJob job = PrinterJob.getPrinterJob();
    job.setPrintable(new OutputPrinter(printData));
    boolean doPrint = job.printDialog();
    if (doPrint)
    { 
        try 
        {
            job.print();
        }
        catch (PrinterException e)
        {
            // Print job did not complete.
        }
    }
    save();
    }//GEN-LAST:event_jLabel146MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CustomerName;
    private javax.swing.JPanel Customer_Panel;
    private javax.swing.JPanel Edit_C;
    private javax.swing.JPanel Edit_M;
    private javax.swing.JPanel Item_Panel;
    private javax.swing.JPanel Manage_Category;
    private javax.swing.JPanel Manage_Customer;
    private javax.swing.JPanel Manage_Item;
    private javax.swing.JPanel Manage_Transaction;
    private javax.swing.JLabel ManagerName;
    private javax.swing.JPanel Manager_Panel;
    private javax.swing.JPanel Reg_C;
    private javax.swing.JPanel Reg_M;
    private javax.swing.JPanel Upload_Item;
    private javax.swing.JPanel UploadedItem_Panel;
    private javax.swing.JPanel Welcome_Panel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel139;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel140;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel143;
    private javax.swing.JLabel jLabel144;
    private javax.swing.JLabel jLabel145;
    private javax.swing.JLabel jLabel146;
    private javax.swing.JLabel jLabel147;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel151;
    private javax.swing.JLabel jLabel152;
    private javax.swing.JLabel jLabel155;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JList jList1;
    private javax.swing.JList jList10;
    private javax.swing.JList jList11;
    private javax.swing.JList jList12;
    private javax.swing.JList jList13;
    private javax.swing.JList jList14;
    private javax.swing.JList jList15;
    private javax.swing.JList jList16;
    private javax.swing.JList jList17;
    private javax.swing.JList jList18;
    private javax.swing.JList jList19;
    private javax.swing.JList jList2;
    private javax.swing.JList jList22;
    private javax.swing.JList jList3;
    private javax.swing.JList jList4;
    private javax.swing.JList jList5;
    private javax.swing.JList jList6;
    private javax.swing.JList jList7;
    private javax.swing.JList jList8;
    private javax.swing.JList jList9;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField10;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JPasswordField jPasswordField3;
    private javax.swing.JPasswordField jPasswordField4;
    private javax.swing.JPasswordField jPasswordField5;
    private javax.swing.JPasswordField jPasswordField6;
    private javax.swing.JPasswordField jPasswordField8;
    private javax.swing.JPasswordField jPasswordField9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField26;
    private javax.swing.JTextField jTextField28;
    private javax.swing.JTextField jTextField29;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField30;
    private javax.swing.JTextField jTextField33;
    private javax.swing.JTextField jTextField34;
    private javax.swing.JTextField jTextField35;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField40;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}
