
package fees_management_system;
import java.awt.Color;
import javax.swing.JOptionPane;

// sql package classes
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
/**
 *
 * @author rajan
 */
public class UpdateFeesDetails extends javax.swing.JFrame // Inline import 
{

    /**
     * Creates new form AddFees
     */
    public UpdateFeesDetails() {
        initComponents();
        displayCash();
        fillCourse();
        int reciept = getRecieptNo();
        txtReciept.setText(Integer.toString(reciept));
        getDetails();
    }
    
    public void displayCash(){
        
        lblDDNo.setVisible(false);
        txtDDNo.setVisible(false);
        
        lblChequeNo.setVisible(false);
        txtChequeNo.setVisible(false);
        
        lblBankName.setVisible(false);
        txtBankName.setVisible(false);     
        
    }
    
    public boolean validation(){
        if(txtRecieved.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Please enter the field of Recieved from");
            return false;
        }
        if(dateChooser.getDate() == null){
            JOptionPane.showMessageDialog(this, "Please choose the date");
            return false;
        }
        if(comboPaymentMode.getSelectedItem().toString().equalsIgnoreCase("Card")){
            if(txtBankName.getText().equals("")){
                JOptionPane.showMessageDialog(this, "Please Enter the Bank Name");
                return false;
            }
        }
        if(comboPaymentMode.getSelectedItem().toString().equalsIgnoreCase("dd")){
                if(txtDDNo.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(this, "Please enter the DD No.");
                    return false;
                }
                if(txtBankName.getText().equals(""))
                {
                  JOptionPane.showMessageDialog(this,"Please enter the Bank Name"); 
                  return false;
                }              
        }
        if(comboPaymentMode.getSelectedItem().toString().equalsIgnoreCase("cheque")){
            if(txtChequeNo.getText().equals(""))
            {
                JOptionPane.showMessageDialog(this, "Please enter the Cheque no.");
                return false;
            }
            if(txtBankName.getText().equals("")){
                JOptionPane.showMessageDialog(this, "Please enter the bank name");
                return false;
            }
        }
        if(txtRs.getText().equals(""))  // txtRs.getText().matches("0-9+") returns boolean values        
        {
            JOptionPane.showMessageDialog(this, "Please enter the amount");
            return false;
        }
        
        
        if(txtYearFrom.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Please enter the session");
            return false;
        }
        if(txtRollNo.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Please enter the session");
            return false;
        }
        
        return true;
    }
    
    
    public void fillCourse(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/fees_management","root", "0170");
//            Connection con = DBConnection.getMysqlConnection();
            
            PreparedStatement pst = con.prepareStatement("Select cname from course");
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()){
                comboCourse.addItem(rs.getString("cname"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public int getRecieptNo(){
        
        int recieptNo = 0;
        try{
            Connection con = DBConnection.getMysqlConnection();
            PreparedStatement pst = con.prepareStatement("select max(reciept_no) from fees_details");
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()){
                recieptNo = rs.getInt(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return recieptNo+1;
    }
    
    
    public String updateData(){
        
        String status = "";
        
        int recieptNo = Integer.parseInt(txtReciept.getText());
        String sname = txtRecieved.getText();
        String rollNo = txtRollNo.getText();
        String paymentMode = comboPaymentMode.getSelectedItem().toString();
        String chequeNo = txtChequeNo.getText();
        String bankName = txtBankName.getText();
        String ddNo = txtDDNo.getText();
        String courseName = comboCourse.getSelectedItem().toString();
        String gstin = lblGstin.getText();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(dateChooser.getDate());
        float initialAmount = Float.parseFloat(txtRs.getText());
        float cgst = Float.parseFloat(txtCGST.getText());
        float sgst = Float.parseFloat(txtSGST.getText());
        float totalAmount = Float.parseFloat(txtTotalRs.getText());
        String amountInWord = txtRsInWord.getText();
        String remark = txtRemark.getText();
        int year1 = Integer.parseInt(txtYearFrom.getText());
        int year2 = Integer.parseInt(txtYearTo.getText());
        
        try{
           Connection con = DBConnection.getMysqlConnection();
           PreparedStatement pst = con.prepareStatement("update fees_details set student_name = ?, roll_no = ?, payment_mode = ?, "
                   + "cheque_no = ?, bank_name = ?, dd_no = ?, course_name = ?, gstin = ?, date = ?, amount = ?, cgst = ?, sgst = ?, "
                   + "total_amount = ?, total_in_words = ?, remarks = ?, year1 = ?, year2 = ? where reciept_no = ?");
           
           
           pst.setString(1, sname);
           pst.setString(2, rollNo);
           pst.setString(3, paymentMode);
           pst.setString(4, chequeNo);
           pst.setString(5, bankName);
           pst.setString(6, ddNo);
           pst.setString(7, courseName);
           pst.setString(8, gstin);
           pst.setString(9, date);
           pst.setFloat(10, initialAmount);
           pst.setFloat(11, cgst);
           pst.setFloat(12, sgst);
           pst.setFloat(13, totalAmount);
           pst.setString(14, amountInWord);
           pst.setString(15,remark);
           pst.setInt(16, year1);
           pst.setInt(17, year2);
           pst.setInt(18, recieptNo);
           
           int countRow = pst.executeUpdate();
           // executeUpdate return number of row affected
           if(countRow == 1){
               status = "success";
           }else{
               status = "failed";
           }
           
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return status;
    
    } 
    
    
    public void getDetails(){
        try{
            Connection con = DBConnection.getMysqlConnection();
            PreparedStatement pst = con.prepareStatement("select * from fees_details order by reciept_no desc limit 1");
            ResultSet rs = pst.executeQuery();
            rs.next();
            txtReciept.setText(rs.getString("reciept_no"));
            txtRecieved.setText(rs.getString("student_name"));
            txtRollNo.setText(rs.getString("roll_no"));
            comboPaymentMode.setSelectedItem(rs.getString("payment_mode"));
            txtChequeNo.setText(rs.getString("cheque_no"));
            txtBankName.setText(rs.getString("bank_name"));
            txtDDNo.setText(rs.getString("dd_no"));
            comboCourse.setSelectedItem(rs.getString("course_name"));
//            lblGstin.setText(rs.getString("gstin"));
            dateChooser.setDate(rs.getDate("date"));            
            txtRs.setText(rs.getString("amount"));
            txtCGST.setText(rs.getString("cgst"));
            txtSGST.setText(rs.getString("sgst"));
            txtTotalRs.setText(rs.getString("total_amount"));
            txtRsInWord.setText(rs.getString("Total_in_words"));
            txtRemark.setText(rs.getString("remarks"));
            txtYearFrom.setText(rs.getString("year1"));
            txtYearTo.setText(rs.getString("Year2"));
            
            
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

        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem2 = new javax.swing.JCheckBoxMenuItem();
        jPanel2 = new javax.swing.JPanel();
        txtChequeNo = new javax.swing.JTextField();
        lblGstin = new javax.swing.JLabel();
        comboPaymentMode = new javax.swing.JComboBox<>();
        lblMode = new javax.swing.JLabel();
        lblDDNo = new javax.swing.JLabel();
        lblChequeNo = new javax.swing.JLabel();
        txtDDNo = new javax.swing.JTextField();
        txtBankName = new javax.swing.JTextField();
        lbnRecieptNo4 = new javax.swing.JLabel();
        txtReciept = new javax.swing.JTextField();
        lbnRecieptNo5 = new javax.swing.JLabel();
        lbnRecieptNo6 = new javax.swing.JLabel();
        dateChooser = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        lbnRecieptNo3 = new javax.swing.JLabel();
        txtRollNo = new javax.swing.JTextField();
        lbnRecieptNo8 = new javax.swing.JLabel();
        txtHead = new javax.swing.JTextField();
        lbnRecieptNo9 = new javax.swing.JLabel();
        txtYearFrom = new javax.swing.JTextField();
        lbnRecieptNo10 = new javax.swing.JLabel();
        comboCourse = new javax.swing.JComboBox<>();
        lbnRecieptNo11 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        lbnRecieptNo12 = new javax.swing.JLabel();
        lbnRecieptNo13 = new javax.swing.JLabel();
        lbnRecieptNo14 = new javax.swing.JLabel();
        txtRecieved = new javax.swing.JTextField();
        txtRs = new javax.swing.JTextField();
        txtCGST = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        txtSGST = new javax.swing.JTextField();
        lbnRecieptNo15 = new javax.swing.JLabel();
        lbnRecieptNo16 = new javax.swing.JLabel();
        txtRsInWord = new javax.swing.JTextField();
        txtTotalRs = new javax.swing.JTextField();
        lbnRecieptNo17 = new javax.swing.JLabel();
        lbnRecieptNo18 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        lbnRecieptNo20 = new javax.swing.JLabel();
        btnPrint = new java.awt.Button();
        lbnRecieptNo19 = new javax.swing.JLabel();
        txtRemark = new java.awt.TextArea();
        detailsRecieved = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        errorRs = new javax.swing.JLabel();
        txtYearTo = new javax.swing.JTextField();
        lblBankName = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btn_home = new javax.swing.JButton();
        btn_sr = new javax.swing.JButton();
        btn_ec = new javax.swing.JButton();
        btn_cl = new javax.swing.JButton();
        btn_var = new javax.swing.JButton();
        btn_b = new javax.swing.JButton();
        btn_l = new javax.swing.JButton();

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        jCheckBoxMenuItem2.setSelected(true);
        jCheckBoxMenuItem2.setText("jCheckBoxMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(204, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtChequeNo.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txtChequeNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtChequeNoActionPerformed(evt);
            }
        });
        jPanel2.add(txtChequeNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 130, 220, 30));

        lblGstin.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblGstin.setText("D8217R563");
        jPanel2.add(lblGstin, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 70, 110, 30));

        comboPaymentMode.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        comboPaymentMode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DD", "Cash", "Card", "Cheque" }));
        comboPaymentMode.setSelectedIndex(1);
        comboPaymentMode.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        comboPaymentMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPaymentModeActionPerformed(evt);
            }
        });
        jPanel2.add(comboPaymentMode, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 80, 130, 30));

        lblMode.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblMode.setText("Mode of Payment :");
        jPanel2.add(lblMode, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 150, 30));

        lblDDNo.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblDDNo.setText("DD No. :");
        jPanel2.add(lblDDNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 110, 30));

        lblChequeNo.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblChequeNo.setText("Cheque No. :");
        jPanel2.add(lblChequeNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 110, 30));

        txtDDNo.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txtDDNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDDNoActionPerformed(evt);
            }
        });
        jPanel2.add(txtDDNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 130, 220, 30));

        txtBankName.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txtBankName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBankNameActionPerformed(evt);
            }
        });
        jPanel2.add(txtBankName, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 180, 500, 30));

        lbnRecieptNo4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbnRecieptNo4.setText("Reciept No. :  MCU -");
        jPanel2.add(lbnRecieptNo4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 170, 30));

        txtReciept.setEditable(false);
        txtReciept.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txtReciept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRecieptActionPerformed(evt);
            }
        });
        jPanel2.add(txtReciept, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, 130, 30));

        lbnRecieptNo5.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbnRecieptNo5.setText("Fee Date :");
        jPanel2.add(lbnRecieptNo5, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 30, 90, 30));

        lbnRecieptNo6.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbnRecieptNo6.setText("GSTIN No. :");
        jPanel2.add(lbnRecieptNo6, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 70, 110, 30));
        jPanel2.add(dateChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 30, 190, 30));

        jPanel3.setBackground(new java.awt.Color(204, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbnRecieptNo3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbnRecieptNo3.setText("To");
        jPanel3.add(lbnRecieptNo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 60, 30, 30));

        txtRollNo.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txtRollNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRollNoActionPerformed(evt);
            }
        });
        jPanel3.add(txtRollNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 110, 90, 30));

        lbnRecieptNo8.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbnRecieptNo8.setText("Recieved From :");
        jPanel3.add(lbnRecieptNo8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 130, 30));

        txtHead.setEditable(false);
        txtHead.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txtHead.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtHead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHeadActionPerformed(evt);
            }
        });
        jPanel3.add(txtHead, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 190, 470, 30));

        lbnRecieptNo9.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbnRecieptNo9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbnRecieptNo9.setText("Remarks:");
        jPanel3.add(lbnRecieptNo9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 390, 70, 30));

        txtYearFrom.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txtYearFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtYearFromActionPerformed(evt);
            }
        });
        jPanel3.add(txtYearFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 60, 90, 30));

        lbnRecieptNo10.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbnRecieptNo10.setText("The following payment for the year :");
        jPanel3.add(lbnRecieptNo10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 280, 30));

        comboCourse.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        comboCourse.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        comboCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCourseActionPerformed(evt);
            }
        });
        jPanel3.add(comboCourse, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 110, 170, 30));

        lbnRecieptNo11.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbnRecieptNo11.setText("Roll No.");
        jPanel3.add(lbnRecieptNo11, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 110, 70, 30));
        jPanel3.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 1110, -1));
        jPanel3.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 1110, 10));

        lbnRecieptNo12.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbnRecieptNo12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbnRecieptNo12.setText("Amount");
        jPanel3.add(lbnRecieptNo12, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 150, 70, 30));

        lbnRecieptNo13.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbnRecieptNo13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbnRecieptNo13.setText("Receiver Signature");
        jPanel3.add(lbnRecieptNo13, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 480, 230, 30));

        lbnRecieptNo14.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbnRecieptNo14.setText("Course :");
        jPanel3.add(lbnRecieptNo14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 70, 30));

        txtRecieved.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txtRecieved.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRecievedActionPerformed(evt);
            }
        });
        jPanel3.add(txtRecieved, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 230, 30));

        txtRs.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txtRs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRsActionPerformed(evt);
            }
        });
        txtRs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRsKeyPressed(evt);
            }
        });
        jPanel3.add(txtRs, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 190, 230, 30));

        txtCGST.setEditable(false);
        txtCGST.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txtCGST.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCGSTActionPerformed(evt);
            }
        });
        jPanel3.add(txtCGST, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 240, 230, 30));
        jPanel3.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 313, 1110, -1));

        txtSGST.setEditable(false);
        txtSGST.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txtSGST.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSGSTActionPerformed(evt);
            }
        });
        jPanel3.add(txtSGST, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 280, 230, 30));

        lbnRecieptNo15.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbnRecieptNo15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbnRecieptNo15.setText("Head");
        jPanel3.add(lbnRecieptNo15, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 150, 70, 30));

        lbnRecieptNo16.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbnRecieptNo16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbnRecieptNo16.setText("CGST   9%");
        jPanel3.add(lbnRecieptNo16, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 240, 100, 30));

        txtRsInWord.setEditable(false);
        txtRsInWord.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txtRsInWord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRsInWordActionPerformed(evt);
            }
        });
        jPanel3.add(txtRsInWord, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 490, 30));

        txtTotalRs.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txtTotalRs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalRsActionPerformed(evt);
            }
        });
        jPanel3.add(txtTotalRs, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 320, 230, 30));

        lbnRecieptNo17.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbnRecieptNo17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbnRecieptNo17.setText("Sr.No.");
        jPanel3.add(lbnRecieptNo17, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, 70, 30));

        lbnRecieptNo18.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbnRecieptNo18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbnRecieptNo18.setText("SGST   9%");
        jPanel3.add(lbnRecieptNo18, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 280, 100, 30));
        jPanel3.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 480, 230, 20));

        lbnRecieptNo20.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbnRecieptNo20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbnRecieptNo20.setText("Total Rs.");
        jPanel3.add(lbnRecieptNo20, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 320, 100, 30));

        btnPrint.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrint.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnPrint.setLabel("Print");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });
        jPanel3.add(btnPrint, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 550, 90, 30));

        lbnRecieptNo19.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbnRecieptNo19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbnRecieptNo19.setText("Total Rs. In Words :");
        jPanel3.add(lbnRecieptNo19, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, 170, 30));
        jPanel3.add(txtRemark, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 420, 500, 120));
        txtRemark.getAccessibleContext().setAccessibleDescription("Write something");

        detailsRecieved.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        detailsRecieved.setForeground(new java.awt.Color(204, 0, 0));
        jPanel3.add(detailsRecieved, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 20, 200, 20));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 0, 0));
        jLabel1.setText("*");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel1.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel1MouseExited(evt);
            }
        });
        jLabel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLabel1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jLabel1KeyReleased(evt);
            }
        });
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, 10, 10));

        errorRs.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        errorRs.setForeground(new java.awt.Color(204, 0, 0));
        jPanel3.add(errorRs, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 220, 230, -1));

        txtYearTo.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txtYearTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtYearToActionPerformed(evt);
            }
        });
        jPanel3.add(txtYearTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 60, 90, 30));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 1110, 590));

        lblBankName.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblBankName.setText("Bank Name  :");
        jPanel2.add(lblBankName, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, 110, 30));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 0, 1110, 810));

        jPanel1.setBackground(new java.awt.Color(52, 151, 217));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_home.setBackground(new java.awt.Color(52, 151, 219));
        btn_home.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btn_home.setForeground(new java.awt.Color(255, 255, 255));
        btn_home.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/image/home.png"))); // NOI18N
        btn_home.setText("Home");
        btn_home.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 153, 255), null, null, new java.awt.Color(0, 102, 255)));
        btn_home.setMargin(new java.awt.Insets(0, 0, 1, 5));
        btn_home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_homeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_homeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_homeMouseExited(evt);
            }
        });
        btn_home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_homeActionPerformed(evt);
            }
        });
        jPanel1.add(btn_home, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 260, 70));

        btn_sr.setBackground(new java.awt.Color(52, 151, 219));
        btn_sr.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btn_sr.setForeground(new java.awt.Color(255, 255, 255));
        btn_sr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/image/search2.png"))); // NOI18N
        btn_sr.setText("Search Record");
        btn_sr.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 204), null, null, new java.awt.Color(0, 102, 255)));
        btn_sr.setMargin(new java.awt.Insets(0, 0, 1, 5));
        btn_sr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_srMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_srMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_srMouseExited(evt);
            }
        });
        btn_sr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_srActionPerformed(evt);
            }
        });
        jPanel1.add(btn_sr, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, 260, 70));

        btn_ec.setBackground(new java.awt.Color(52, 151, 219));
        btn_ec.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btn_ec.setForeground(new java.awt.Color(255, 255, 255));
        btn_ec.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/image/edit2.png"))); // NOI18N
        btn_ec.setText("Edit Course");
        btn_ec.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 204), null, null, new java.awt.Color(0, 102, 255)));
        btn_ec.setMargin(new java.awt.Insets(0, 0, 1, 5));
        btn_ec.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_ecMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_ecMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_ecMouseExited(evt);
            }
        });
        btn_ec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ecActionPerformed(evt);
            }
        });
        jPanel1.add(btn_ec, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 260, 260, 70));

        btn_cl.setBackground(new java.awt.Color(52, 151, 219));
        btn_cl.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btn_cl.setForeground(new java.awt.Color(255, 255, 255));
        btn_cl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/image/list_1.png"))); // NOI18N
        btn_cl.setText("Course List");
        btn_cl.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 204), null, null, new java.awt.Color(0, 102, 255)));
        btn_cl.setMargin(new java.awt.Insets(0, 0, 1, 5));
        btn_cl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_clMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_clMouseExited(evt);
            }
        });
        btn_cl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clActionPerformed(evt);
            }
        });
        jPanel1.add(btn_cl, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 380, 260, 70));

        btn_var.setBackground(new java.awt.Color(52, 151, 219));
        btn_var.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btn_var.setForeground(new java.awt.Color(255, 255, 255));
        btn_var.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/image/view all record.png"))); // NOI18N
        btn_var.setText("View All Record");
        btn_var.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 204), null, null, new java.awt.Color(0, 102, 255)));
        btn_var.setMargin(new java.awt.Insets(0, 0, 1, 5));
        btn_var.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_varMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_varMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_varMouseExited(evt);
            }
        });
        btn_var.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_varActionPerformed(evt);
            }
        });
        jPanel1.add(btn_var, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 490, 260, 70));

        btn_b.setBackground(new java.awt.Color(52, 151, 219));
        btn_b.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btn_b.setForeground(new java.awt.Color(255, 255, 255));
        btn_b.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/image/left-arrow.png"))); // NOI18N
        btn_b.setText("Back");
        btn_b.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 204), null, null, new java.awt.Color(0, 102, 255)));
        btn_b.setMargin(new java.awt.Insets(0, -10, 1, 25));
        btn_b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_bMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_bMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_bMouseExited(evt);
            }
        });
        btn_b.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_bActionPerformed(evt);
            }
        });
        jPanel1.add(btn_b, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 590, 260, 70));

        btn_l.setBackground(new java.awt.Color(52, 151, 219));
        btn_l.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btn_l.setForeground(new java.awt.Color(255, 255, 255));
        btn_l.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/image/logout.png"))); // NOI18N
        btn_l.setText("Log Out");
        btn_l.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 204), null, null, new java.awt.Color(0, 102, 255)));
        btn_l.setMargin(new java.awt.Insets(0, 0, 1, 5));
        btn_l.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_lMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_lMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_lMouseExited(evt);
            }
        });
        btn_l.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lActionPerformed(evt);
            }
        });
        jPanel1.add(btn_l, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 690, 260, 70));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 810));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtChequeNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtChequeNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtChequeNoActionPerformed

    private void txtDDNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDDNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDDNoActionPerformed

    private void txtBankNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBankNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBankNameActionPerformed

    private void txtRecieptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRecieptActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRecieptActionPerformed

    private void txtRollNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRollNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRollNoActionPerformed

    private void txtHeadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHeadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHeadActionPerformed

    private void txtYearFromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtYearFromActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtYearFromActionPerformed

    private void comboPaymentModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPaymentModeActionPerformed
        if(comboPaymentMode.getSelectedIndex() == 0)
        // comboPaymentMode.getSElectedItem().equals("DD");
        {
            lblDDNo.setVisible(true);
            txtDDNo.setVisible(true);
            
            lblBankName.setVisible(true);
            txtBankName.setVisible(true);
            
            lblChequeNo.setVisible(false);
            txtChequeNo.setVisible(false);
            
        }
        
        if(comboPaymentMode.getSelectedIndex() == 1)
        // comboPaymentMode.getSElectedItem().equals("Cash");
        {
            lblDDNo.setVisible(false);
            txtDDNo.setVisible(false);
            
            lblBankName.setVisible(false);
            txtBankName.setVisible(false);
            
            lblChequeNo.setVisible(false);
            txtChequeNo.setVisible(false);
            
        }
        if(comboPaymentMode.getSelectedIndex() == 2)
        // comboPaymentMode.getSElectedItem().equals("Card");
        {
            lblDDNo.setVisible(false);
            txtDDNo.setVisible(false);
            
            lblBankName.setVisible(true);
            txtBankName.setVisible(true);
            
            lblChequeNo.setVisible(false);
            txtChequeNo.setVisible(false);
            
        }
        
        if(comboPaymentMode.getSelectedIndex() == 3)
        // comboPaymentMode.getSElectedItem().equals("Cheque");
        {
            lblDDNo.setVisible(false);
            txtDDNo.setVisible(false);
            
            lblBankName.setVisible(true);
            txtBankName.setVisible(true);
            
            lblChequeNo.setVisible(true);
            txtChequeNo.setVisible(true);
            
        }
    }//GEN-LAST:event_comboPaymentModeActionPerformed

    private void comboCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCourseActionPerformed
        txtHead.setText(comboCourse.getSelectedItem().toString());
    }//GEN-LAST:event_comboCourseActionPerformed

    private void txtRecievedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRecievedActionPerformed
        
    }//GEN-LAST:event_txtRecievedActionPerformed

    private void txtRsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRsActionPerformed
        float rs =Float.parseFloat(txtRs.getText());
        
        float cgst = (float)(rs*(0.09));
        Float sgst = (float)(rs*(0.09));
        
        txtCGST.setText(Float.toString(cgst));
        txtSGST.setText(sgst.toString());
        
        float total = rs + cgst + sgst;
        
        txtTotalRs.setText(Float.toString(total));
        
        txtRsInWord.setText(NumberToWordsConverter.convert((int)total));
        
    }//GEN-LAST:event_txtRsActionPerformed

    private void txtCGSTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCGSTActionPerformed
        
    }//GEN-LAST:event_txtCGSTActionPerformed

    private void txtSGSTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSGSTActionPerformed
        
    }//GEN-LAST:event_txtSGSTActionPerformed

    private void txtRsInWordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRsInWordActionPerformed
       
    }//GEN-LAST:event_txtRsInWordActionPerformed

    private void txtTotalRsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalRsActionPerformed
        
    }//GEN-LAST:event_txtTotalRsActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        if(validation()){
            String result = updateData();
            
            if(result.equals("success")){
                JOptionPane.showMessageDialog(this, "Record Successfully updated");
                PrintReciept pr = new PrintReciept();
                pr.setVisible(true);
                this.dispose();
            }
            else{
                JOptionPane.showMessageDialog(this, "Record updation failes");
            }
        }
    }//GEN-LAST:event_btnPrintActionPerformed

    private void jLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseEntered
        detailsRecieved.setText("Name of the Student");
    }//GEN-LAST:event_jLabel1MouseEntered

    private void jLabel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseExited
        detailsRecieved.setText("");
    }//GEN-LAST:event_jLabel1MouseExited

    private void txtRsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRsKeyPressed
//        if(txtRs.getText().matches("0-9+")== false){
//            errorRs.setText("Please enter valid amount");
//        }
    }//GEN-LAST:event_txtRsKeyPressed

    private void jLabel1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel1KeyPressed
       
    }//GEN-LAST:event_jLabel1KeyPressed

    private void jLabel1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel1KeyReleased
        
    }//GEN-LAST:event_jLabel1KeyReleased

    private void txtYearToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtYearToActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtYearToActionPerformed

    private void btn_homeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_homeMouseClicked
        Home hm = new Home();
        hm.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_btn_homeMouseClicked

    private void btn_homeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_homeMouseEntered
        Color clr = new Color(56,149,242);
        btn_home.setBackground(clr);
    }//GEN-LAST:event_btn_homeMouseEntered

    private void btn_homeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_homeMouseExited
        Color clr = new Color(52,151,217);
        btn_home.setBackground(clr);
    }//GEN-LAST:event_btn_homeMouseExited

    private void btn_homeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_homeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_homeActionPerformed

    private void btn_srMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_srMouseClicked
        SearchRecord sr = new SearchRecord();
        sr.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_srMouseClicked

    private void btn_srMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_srMouseEntered
        Color clr = new Color(56,149,242);
        btn_sr.setBackground(clr);
    }//GEN-LAST:event_btn_srMouseEntered

    private void btn_srMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_srMouseExited
        Color clr = new Color(52,151,217);
        btn_sr.setBackground(clr);
    }//GEN-LAST:event_btn_srMouseExited

    private void btn_srActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_srActionPerformed
        SearchRecord sr = new SearchRecord();
        sr.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_srActionPerformed

    private void btn_ecMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ecMouseClicked
        EditCourse ec = new EditCourse();
        ec.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_ecMouseClicked

    private void btn_ecMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ecMouseEntered
        Color clr = new Color(56,149,242);
        btn_ec.setBackground(clr);
    }//GEN-LAST:event_btn_ecMouseEntered

    private void btn_ecMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ecMouseExited
        Color clr = new Color(52,151,217);
        btn_ec.setBackground(clr);
    }//GEN-LAST:event_btn_ecMouseExited

    private void btn_ecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ecActionPerformed
        EditCourse ec = new EditCourse();
        ec.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_ecActionPerformed

    private void btn_clMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_clMouseEntered
        Color clr = new Color(56,149,242);
        btn_cl.setBackground(clr);
    }//GEN-LAST:event_btn_clMouseEntered

    private void btn_clMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_clMouseExited
        Color clr = new Color(52,151,217);
        btn_cl.setBackground(clr);
    }//GEN-LAST:event_btn_clMouseExited

    private void btn_clActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clActionPerformed
        ViewCourse vc = new ViewCourse();
        vc.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_clActionPerformed

    private void btn_varMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_varMouseClicked
        ViewAllRecord var = new ViewAllRecord();
        var.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_varMouseClicked

    private void btn_varMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_varMouseEntered
        Color clr = new Color(56,149,242);
        btn_var.setBackground(clr);
    }//GEN-LAST:event_btn_varMouseEntered

    private void btn_varMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_varMouseExited
        Color clr = new Color(52,151,217);
        btn_var.setBackground(clr);
    }//GEN-LAST:event_btn_varMouseExited

    private void btn_varActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_varActionPerformed
        ViewAllRecord var = new ViewAllRecord();
        var.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_varActionPerformed

    private void btn_bMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_bMouseClicked
        Home hm = new Home();
        hm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_bMouseClicked

    private void btn_bMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_bMouseEntered
        Color clr = new Color(56,149,242);
        btn_b.setBackground(clr);
    }//GEN-LAST:event_btn_bMouseEntered

    private void btn_bMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_bMouseExited
        Color clr = new Color(52,151,217);
        btn_b.setBackground(clr);
    }//GEN-LAST:event_btn_bMouseExited

    private void btn_bActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bActionPerformed
        Home h = new Home();
        h.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_bActionPerformed

    private void btn_lMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_lMouseClicked
        LogIn lg = new LogIn();
        lg.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_lMouseClicked

    private void btn_lMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_lMouseEntered
        Color clr = new Color(56,149,242);
        btn_l.setBackground(clr);
    }//GEN-LAST:event_btn_lMouseEntered

    private void btn_lMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_lMouseExited
        Color clr = new Color(52,151,217);
        btn_l.setBackground(clr);
    }//GEN-LAST:event_btn_lMouseExited

    private void btn_lActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lActionPerformed
        LogIn li = new LogIn();
        li.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_lActionPerformed

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
            java.util.logging.Logger.getLogger(UpdateFeesDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdateFeesDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdateFeesDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateFeesDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UpdateFeesDetails().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button btnPrint;
    private javax.swing.JButton btn_b;
    private javax.swing.JButton btn_cl;
    private javax.swing.JButton btn_ec;
    private javax.swing.JButton btn_home;
    private javax.swing.JButton btn_l;
    private javax.swing.JButton btn_sr;
    private javax.swing.JButton btn_var;
    private javax.swing.JComboBox<String> comboCourse;
    private javax.swing.JComboBox<String> comboPaymentMode;
    private com.toedter.calendar.JDateChooser dateChooser;
    private javax.swing.JLabel detailsRecieved;
    private javax.swing.JLabel errorRs;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lblBankName;
    private javax.swing.JLabel lblChequeNo;
    private javax.swing.JLabel lblDDNo;
    private javax.swing.JLabel lblGstin;
    private javax.swing.JLabel lblMode;
    private javax.swing.JLabel lbnRecieptNo10;
    private javax.swing.JLabel lbnRecieptNo11;
    private javax.swing.JLabel lbnRecieptNo12;
    private javax.swing.JLabel lbnRecieptNo13;
    private javax.swing.JLabel lbnRecieptNo14;
    private javax.swing.JLabel lbnRecieptNo15;
    private javax.swing.JLabel lbnRecieptNo16;
    private javax.swing.JLabel lbnRecieptNo17;
    private javax.swing.JLabel lbnRecieptNo18;
    private javax.swing.JLabel lbnRecieptNo19;
    private javax.swing.JLabel lbnRecieptNo20;
    private javax.swing.JLabel lbnRecieptNo3;
    private javax.swing.JLabel lbnRecieptNo4;
    private javax.swing.JLabel lbnRecieptNo5;
    private javax.swing.JLabel lbnRecieptNo6;
    private javax.swing.JLabel lbnRecieptNo8;
    private javax.swing.JLabel lbnRecieptNo9;
    private javax.swing.JTextField txtBankName;
    private javax.swing.JTextField txtCGST;
    private javax.swing.JTextField txtChequeNo;
    private javax.swing.JTextField txtDDNo;
    private javax.swing.JTextField txtHead;
    private javax.swing.JTextField txtReciept;
    private javax.swing.JTextField txtRecieved;
    private java.awt.TextArea txtRemark;
    private javax.swing.JTextField txtRollNo;
    private javax.swing.JTextField txtRs;
    private javax.swing.JTextField txtRsInWord;
    private javax.swing.JTextField txtSGST;
    private javax.swing.JTextField txtTotalRs;
    private javax.swing.JTextField txtYearFrom;
    private javax.swing.JTextField txtYearTo;
    // End of variables declaration//GEN-END:variables
}
