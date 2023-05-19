
package fees_management_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author rajan
 */
public class SignUp extends javax.swing.JFrame {

    String fname, lname, uname, pass, cnf_pass, contact;
           Date dob;
           int id = 0;
    
    public SignUp() {
        initComponents();
    }

    public int getId(){
        
        ResultSet rs = null;
        try{
           Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/fees_management","root","0170");
            String sql = "select max(id) from signup"; 
            Statement st = con.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                id = rs.getInt(1);
                id++;
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return id;
    }
    
    
    boolean validation(){
           
           
           fname = txt_fname.getText();
           lname = txt_lname.getText();
           uname = txt_uname.getText();
           pass = txt_pass.getText();
           cnf_pass = txt_cnf_pass.getText();
           dob = txt_dob.getDate();
           contact = txt_contact.getText();
           
           if(fname.equals("")){
               JOptionPane.showMessageDialog(this,"Please enter First Name");
               return false;
           }
           if(lname.equals("")){
               JOptionPane.showMessageDialog(this,"Please enter Last Name");
               return false;
           }
           if(uname.equals("")){
               JOptionPane.showMessageDialog(this,"Please enter the user name");
               return false;
           }
           if(pass.equals("")){
               JOptionPane.showMessageDialog(this,"Please enter the password");
               return false;
           }
           if(cnf_pass.equals("")){
               JOptionPane.showMessageDialog(this,"Please enter the confirm password");
               return false;
           }
           if(!cnf_pass.equals(pass)){
               JOptionPane.showMessageDialog(this,"Password does not match");
               return false;
           }
           if(dob == null){
               JOptionPane.showMessageDialog(this,"Please enter the Date of Birth");
               return false;
           }
           if(contact.equals("")){
               JOptionPane.showMessageDialog(this,"Please enter contact number");
               return false;
           }
           
           
           return true;
           
       }
       
       
       public void checkPassword(){
        pass = txt_pass.getText();
        if(pass.length()<8){
            pass_error.setText("Password should be greater than 8 digit");
        }
        else{
            pass_error.setText("");
        }
    }
//    public void matchPassword(){
//           cnf_pass = txt_cnf_pass.getText();
//           pass = txt_pass.getText();
//           if(cnf_pass.equals(pass)){
//               cnf_pass_error.setText("");
//           }
//           else{
//               cnf_pass_error.setText("Password does not match");
//           }
//    }
    
    public void checkContactNo(){
        contact = txt_contact.getText();
        if(contact.length()==10){
            contact_error.setText("");
        }
        else{
            contact_error.setText("Enter a valid number.");
        }
    }
    
    void insertDetails(){
       // dob = txt_dob.getDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String myDob = format.format(dob);
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/fees_management","root","0170");
           // System.out.println(con);
            String sql = "insert into signup values(?,?,?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            
            stmt.setInt(1,getId());
            stmt.setString(2, fname);
            stmt.setString(3, lname);
            stmt.setString(4, uname);
            stmt.setString(5, pass);
            //stmt.setString(6, cnf_pass);
            stmt.setString(6, myDob);
            stmt.setString(7, contact);
            int i = stmt.executeUpdate();
            
            if(i>0) // if above query is executed seccessfully it will enter the if statement 
            {
               JOptionPane.showMessageDialog(this, "Record Inserted");
            }
            else{
                JOptionPane.showMessageDialog(this, "Record not Inserted"); 
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_fname = new java.awt.TextField();
        txt_lname = new java.awt.TextField();
        txt_uname = new java.awt.TextField();
        txt_dob = new com.toedter.calendar.JDateChooser();
        txt_contact = new java.awt.TextField();
        txt_pass = new javax.swing.JPasswordField();
        txt_cnf_pass = new javax.swing.JPasswordField();
        pass_error = new javax.swing.JLabel();
        cnf_pass_error = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        contact_error = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(51, 151, 217));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel1.setText("First Name :");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 100, -1));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel2.setText("Last Name :");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 77, 122, -1));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel3.setText("Username :");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 121, 122, -1));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel4.setText("Password :");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 168, 122, -1));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel5.setText("Confirm Password :");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 208, -1, -1));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel6.setText("Date of Birth :");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 252, -1, -1));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel7.setText("Contact No. :");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 292, 122, -1));

        txt_fname.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_fname.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jPanel2.add(txt_fname, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, 167, 25));

        txt_lname.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jPanel2.add(txt_lname, new org.netbeans.lib.awtextra.AbsoluteConstraints(223, 77, 167, 25));

        txt_uname.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jPanel2.add(txt_uname, new org.netbeans.lib.awtextra.AbsoluteConstraints(223, 121, 167, 25));

        txt_dob.setDateFormatString("dd-MM-yyyy");
        txt_dob.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jPanel2.add(txt_dob, new org.netbeans.lib.awtextra.AbsoluteConstraints(223, 252, 167, 25));

        txt_contact.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_contact.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_contactKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_contactKeyReleased(evt);
            }
        });
        jPanel2.add(txt_contact, new org.netbeans.lib.awtextra.AbsoluteConstraints(223, 292, 167, 25));

        txt_pass.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_pass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_passKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_passKeyReleased(evt);
            }
        });
        jPanel2.add(txt_pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(223, 165, 167, 25));

        txt_cnf_pass.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_cnf_pass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_cnf_passKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_cnf_passKeyReleased(evt);
            }
        });
        jPanel2.add(txt_cnf_pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(223, 205, 167, 25));

        pass_error.setFont(new java.awt.Font("Lucida Fax", 0, 12)); // NOI18N
        pass_error.setForeground(new java.awt.Color(255, 0, 0));
        jPanel2.add(pass_error, new org.netbeans.lib.awtextra.AbsoluteConstraints(223, 199, 242, -1));

        cnf_pass_error.setFont(new java.awt.Font("Lucida Fax", 0, 12)); // NOI18N
        cnf_pass_error.setForeground(new java.awt.Color(255, 0, 0));
        jPanel2.add(cnf_pass_error, new org.netbeans.lib.awtextra.AbsoluteConstraints(223, 242, 167, 4));

        jButton1.setBackground(new java.awt.Color(51, 151, 217));
        jButton1.setFont(new java.awt.Font("Lucida Fax", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(51, 0, 51));
        jButton1.setText("Back");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(129, 339, -1, -1));

        jButton2.setBackground(new java.awt.Color(51, 151, 217));
        jButton2.setFont(new java.awt.Font("Lucida Fax", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 51, 51));
        jButton2.setText("SignUp");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(269, 339, -1, -1));

        contact_error.setFont(new java.awt.Font("Lucida Fax", 0, 12)); // NOI18N
        contact_error.setForeground(new java.awt.Color(255, 0, 0));
        jPanel2.add(contact_error, new org.netbeans.lib.awtextra.AbsoluteConstraints(223, 327, 161, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(56, 117, -1, 380));

        jLabel8.setFont(new java.awt.Font("Lucida Fax", 1, 24)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Welcome to SignUp with MCU");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 26, 574, -1));

        jLabel9.setFont(new java.awt.Font("Lucida Fax", 1, 20)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("SignUp Form");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 74, 574, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 580, 520));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        LogIn li = new LogIn();   
        if(validation()){
                insertDetails();
                li.setVisible(true);
                this.dispose();
        }
       
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txt_passKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_passKeyPressed
        checkPassword();
    }//GEN-LAST:event_txt_passKeyPressed

    private void txt_passKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_passKeyReleased
        checkPassword();
    }//GEN-LAST:event_txt_passKeyReleased

    private void txt_cnf_passKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cnf_passKeyPressed
        
    }//GEN-LAST:event_txt_cnf_passKeyPressed

    private void txt_cnf_passKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cnf_passKeyReleased
       
    }//GEN-LAST:event_txt_cnf_passKeyReleased

    private void txt_contactKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_contactKeyPressed
       checkContactNo();
    }//GEN-LAST:event_txt_contactKeyPressed

    private void txt_contactKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_contactKeyReleased
        checkContactNo();
    }//GEN-LAST:event_txt_contactKeyReleased

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        LogIn lg = new LogIn();
        lg.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1MouseClicked
                                      

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SignUp().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel cnf_pass_error;
    private javax.swing.JLabel contact_error;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel pass_error;
    private javax.swing.JPasswordField txt_cnf_pass;
    private java.awt.TextField txt_contact;
    private com.toedter.calendar.JDateChooser txt_dob;
    private java.awt.TextField txt_fname;
    private java.awt.TextField txt_lname;
    private javax.swing.JPasswordField txt_pass;
    private java.awt.TextField txt_uname;
    // End of variables declaration//GEN-END:variables
}
