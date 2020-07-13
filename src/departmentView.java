/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Imrul
 */
public class departmentView extends javax.swing.JFrame {

    String flags=null;
    /**
     * Creates new form regAdmin
     */
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    
    //2 switches to visible text in password field.
    int swt=0;
    int swt2=0;
    
    String userAdmin;
    String passAdmin;
    public departmentView() {
        initComponents();
    }
    
    String ImgPath = null;
    //...............cut

    //Extend Construction made from dashboard form
    public departmentView(String sendUser, String sendPassword, String flag) 
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        initComponents();
        conn = uapDB.connectDB(); 
//        addCourses.setFocusable(true);         //it is used to unfocus initially
        userAdmin=sendUser;
        passAdmin=sendPassword;
        System.out.println(sendUser);
        System.out.println(sendPassword);
        
        flags=flag;
        //panel fals/true
        if(flag.equals("add"))
        {
            addCourses.setFocusable(true);         //it is used to unfocus initially
            System.out.println("addition");
            addCourses.setVisible(true);
            viewCourses.setVisible(false);
        }
        else if(flag.equals("CSE"))
        {
            search.setFocusable(true);
            deptTAG.setText("CSE");
            System.out.println("CSE");
            addCourses.setVisible(false);
            viewCourses.setVisible(true);
            Show_Courses_In_JTable();         //showing CSE Table.
        }
        else if(flag.equals("EEE"))
        {
            deptTAG.setText("EEE");
            System.out.println("EEE");
            addCourses.setVisible(false);
            viewCourses.setVisible(true);
            Show_Courses_In_JTable();         //showing EEE Table.
        }
        else if(flag.equals("CE"))
        {
            System.out.println("CE");
        }
        else if(flag.equals("BBA"))
        {
            System.out.println("BBA");
        }
        else if(flag.equals("LAW"))
        {
            System.out.println("LAW");
        }
        else if(flag.equals("ENG"))
        {
            System.out.println("ENG");
        }
        
    }
    
    
    
    //__________________________________________________________________CSE____________________________________
    // FUNCTION FOR SHOWING FULL TABLE
    public ArrayList<viewCourses> getCourses()
    {
            ArrayList<viewCourses> viewCoursesList = new ArrayList<viewCourses>();
            //Connection con = getConnection();
            String query = null;

            if(flags.equals("CSE"))
            {
                query = "SELECT * FROM cse";
            }
            else if(flags.equals("EEE"))
            {
                query = "SELECT * FROM eee";
            }
            
            Statement st;
            ResultSet rs;
        try { 
            st = conn.createStatement();
            rs = st.executeQuery(query);
            viewCourses view;
            
            while(rs.next())
            {
                view = new viewCourses(rs.getString("course_code"),rs.getString("department"),rs.getString("credit"));
                viewCoursesList.add(view);
            }
        } catch (SQLException ex) {
            Logger.getLogger(departmentView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return viewCoursesList;
    }
    
    //FUNCTION FOR SEARCHED ROWS OF TABLE #########################################################3##################
    public ArrayList<viewCourses> searchList()
    {
            ArrayList<viewCourses> viewCoursesList = new ArrayList<viewCourses>(); 
       try { 
            PreparedStatement Statement = null;
           
            if(flags.equals("CSE"))
            {
                Statement = conn.prepareStatement("SELECT * from cse WHERE  course_code like ?");
            }
            else if(flags.equals("EEE"))
            {
                Statement = conn.prepareStatement("SELECT * from eee WHERE  course_code like ?");
                
            }
           
                      
            String name=search.getText();
            Statement.setString(1,"%" + name + "%");
            ResultSet rs = Statement.executeQuery();
            viewCourses view;
            while(rs.next())
            {
                view = new viewCourses(rs.getString("course_code"),rs.getString("department"),rs.getString("credit"));
                viewCoursesList.add(view);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(departmentView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return viewCoursesList;
    }
    
    
    // 2 Populate the JTable
    // METHOD FOR FULL TABLE TO BE CALLED
    public void Show_Courses_In_JTable()
    {
        
        ArrayList<viewCourses> list = getCourses();
        DefaultTableModel model = (DefaultTableModel) viewCoursesTable.getModel();
        //clear J table
        model.setRowCount(0);
        Object[] row  = new Object[3];
        for(int i = 0 ; i < list.size() ; i++)
        {
            //row[0] = list.get(i).getId();
            row[0] = list.get(i).getCourse_code();
            row[1] = list.get(i).getDepartment();
            row[2] = list.get(i).getCredit();
            model.addRow(row);
        }
    }
    
    
    //METHOD FOR SEARCHED ROWS TO BE CALLED ##########################################################################
    public void Searched_Courses_In_JTable()
    {
        
        ArrayList<viewCourses> list = searchList();
        DefaultTableModel model = (DefaultTableModel) viewCoursesTable.getModel();
        //clear J table
        model.setRowCount(0);
        Object[] row  = new Object[5];
        for(int i = 0 ; i < list.size() ; i++)
        {
            //row[0] = list.get(i).getId();
            row[0] = list.get(i).getCourse_code();
            row[1] = list.get(i).getDepartment();
            row[2] = list.get(i).getCredit();
            model.addRow(row);
        }
    }
    public void ShowItem(int index)
    {
        test.setText(getCourses().get(index).getCourse_code());
    }
    //_____________________________________________________________________CSE_______________________________
 
    
    //Checking empty inputs
    public boolean checkInputs()
    {
        if(courseField.getText().trim().toLowerCase().equals("type course code") ) 
        {
            return false;
        }
        else
        {
            try {
                return true;
            } catch (Exception ex) {
                return false;
            }
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

        addCourses = new javax.swing.JPanel();
        kGradientPanel1 = new keeptoo.KGradientPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        courseField = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        kGradientPanel2 = new keeptoo.KGradientPanel();
        jLabel11 = new javax.swing.JLabel();
        bba = new javax.swing.JRadioButton();
        cse = new javax.swing.JRadioButton();
        eee = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        one = new javax.swing.JRadioButton();
        two = new javax.swing.JRadioButton();
        three = new javax.swing.JRadioButton();
        four = new javax.swing.JRadioButton();
        viewCourses = new javax.swing.JPanel();
        kGradientPanel3 = new keeptoo.KGradientPanel();
        deptTAG = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        kGradientPanel4 = new keeptoo.KGradientPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        viewCoursesTable = new javax.swing.JTable();
        search = new javax.swing.JTextField();
        test = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(300, 100));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        addCourses.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Add Course");

        jLabel9.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("UAP");

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add_32px.png"))); // NOI18N

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 525, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        addCourses.add(kGradientPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 860, 63));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Credit:");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, 100, 30));

        courseField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        courseField.setForeground(new java.awt.Color(204, 204, 204));
        courseField.setText("Type course code");
        courseField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 153)));
        courseField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                courseFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                courseFieldFocusLost(evt);
            }
        });
        courseField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                courseFieldMouseClicked(evt);
            }
        });
        courseField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                courseFieldActionPerformed(evt);
            }
        });
        jPanel2.add(courseField, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 110, 30));

        jButton2.setBackground(new java.awt.Color(0, 0, 204));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Cancel");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 390, 80, 30));

        jButton3.setBackground(new java.awt.Color(0, 0, 204));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Registration");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, 100, 30));

        javax.swing.GroupLayout kGradientPanel2Layout = new javax.swing.GroupLayout(kGradientPanel2);
        kGradientPanel2.setLayout(kGradientPanel2Layout);
        kGradientPanel2Layout.setHorizontalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        kGradientPanel2Layout.setVerticalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel2.add(kGradientPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 440, 860, 10));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Course Code:");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 100, 30));

        bba.setBackground(new java.awt.Color(255, 255, 255));
        bba.setText("BBA");
        bba.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bbaActionPerformed(evt);
            }
        });
        jPanel2.add(bba, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 160, 60, -1));

        cse.setBackground(new java.awt.Color(255, 255, 255));
        cse.setText("CSE");
        cse.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cseActionPerformed(evt);
            }
        });
        jPanel2.add(cse, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, 60, -1));

        eee.setBackground(new java.awt.Color(255, 255, 255));
        eee.setText("EEE");
        eee.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        eee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eeeActionPerformed(evt);
            }
        });
        jPanel2.add(eee, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 60, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Department:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, 100, 30));

        one.setBackground(new java.awt.Color(255, 255, 255));
        one.setText("1.5");
        one.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oneActionPerformed(evt);
            }
        });
        jPanel2.add(one, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, -1, -1));

        two.setBackground(new java.awt.Color(255, 255, 255));
        two.setText("2");
        two.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                twoActionPerformed(evt);
            }
        });
        jPanel2.add(two, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 240, -1, -1));

        three.setBackground(new java.awt.Color(255, 255, 255));
        three.setText("3");
        three.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                threeActionPerformed(evt);
            }
        });
        jPanel2.add(three, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 240, -1, -1));

        four.setBackground(new java.awt.Color(255, 255, 255));
        four.setText("4");
        four.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fourActionPerformed(evt);
            }
        });
        jPanel2.add(four, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 240, -1, -1));

        addCourses.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 840, 450));

        getContentPane().add(addCourses, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 510));

        viewCourses.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        kGradientPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        deptTAG.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        deptTAG.setForeground(new java.awt.Color(255, 255, 255));
        deptTAG.setText("CSE/EEE");
        kGradientPanel3.add(deptTAG, new org.netbeans.lib.awtextra.AbsoluteConstraints(56, 0, 242, 63));

        jLabel12.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("UAP");
        kGradientPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(823, 0, 37, -1));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/view_32px.png"))); // NOI18N
        kGradientPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 11, -1, 41));

        viewCourses.add(kGradientPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 860, 63));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton4.setBackground(new java.awt.Color(0, 0, 204));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Cancel");
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 390, 80, 30));

        javax.swing.GroupLayout kGradientPanel4Layout = new javax.swing.GroupLayout(kGradientPanel4);
        kGradientPanel4.setLayout(kGradientPanel4Layout);
        kGradientPanel4Layout.setHorizontalGroup(
            kGradientPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        kGradientPanel4Layout.setVerticalGroup(
            kGradientPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel3.add(kGradientPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 440, 860, 10));

        viewCoursesTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        viewCoursesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Course Code", "Department", "Credit"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        viewCoursesTable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        viewCoursesTable.setFocusable(false);
        viewCoursesTable.setGridColor(new java.awt.Color(0, 0, 102));
        viewCoursesTable.setShowVerticalLines(false);
        viewCoursesTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewCoursesTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(viewCoursesTable);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 300, 330));

        search.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        search.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(51, 0, 153)));
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });
        search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                searchKeyTyped(evt);
            }
        });
        jPanel3.add(search, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 300, 30));

        test.setText("*****");
        jPanel3.add(test, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 110, 100, 40));

        viewCourses.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 840, 450));

        getContentPane().add(viewCourses, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 510));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void courseFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_courseFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_courseFieldActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:   
        dashboard c = null;
        try {
            c = new dashboard(userAdmin,passAdmin);
        } catch (SQLException ex) {
            Logger.getLogger(regAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if(checkInputs())
        {
            InputStream img = null;
            try{
                String s=new String();
                String c= courseField.getText();
                String d= department;
                String cc = credit;
                // the mysql insert statement
                String query = null;
                if(department.equals("CSE"))
                {
                    query = "INSERT into cse(course_code, department, credit) values (?, ?, ?)";
                }
                else if(department.equals("EEE"))
                {
                    query = "INSERT into eee(course_code, department, credit) values (?, ?, ?)";
                }
                else if(department.equals("BBA"))
                {
                    query = "INSERT into bba(course_code, department, credit) values (?, ?, ?)";
                }
                
                // create the mysql insert preparedstatement
                pst = conn.prepareStatement(query);
                pst.setString(1, c);
                pst.setString(2, d);
                pst.setString(3, cc);
                     
                pst.execute();
                System.out.println("Successful");
                
                    
                ///*****************************************************************************
                
                courseField.setText("Type course code");
                courseField.setForeground(new Color(204,204,204));
                cse.setSelected(false);
                eee.setSelected(false);
                bba.setSelected(false);
                
                one.setSelected(false);
                two.setSelected(false);
                three.setSelected(false);
                four.setSelected(false);
            }
            catch(Exception e)
            {
                System.out.println("Unsuccessful");
                
            }
            finally
            {
            try{
                rs.close();
                pst.close();
               }
                catch(Exception e){
                }
            }            
      }
      else
      {
          System.out.println("One or more field is empty");  
      }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

    }//GEN-LAST:event_formWindowOpened

    private void courseFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_courseFieldMouseClicked

    }//GEN-LAST:event_courseFieldMouseClicked

    private void courseFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_courseFieldFocusGained
        // TODO add your handling code here:
         if(courseField.getText().trim().toLowerCase().equals("type course code")){
            courseField.setText("");
            courseField.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_courseFieldFocusGained

    private void courseFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_courseFieldFocusLost
        // TODO add your handling code here:
         if(courseField.getText().trim().equals("")){
            courseField.setText("Type course code");
            courseField.setForeground(new Color(204,204,204));
        }
    }//GEN-LAST:event_courseFieldFocusLost

    private void eeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eeeActionPerformed
        // TODO add your handling code here:
        department="EEE";
        cse.setSelected(false);
        eee.setSelected(true);
        bba.setSelected(false);
    }//GEN-LAST:event_eeeActionPerformed

    private void bbaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bbaActionPerformed
        // TODO add your handling code here:
        department="BBA";
        cse.setSelected(false);
        eee.setSelected(false);
        bba.setSelected(true);
    }//GEN-LAST:event_bbaActionPerformed

    private void cseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cseActionPerformed
        // TODO add your handling code here:
        department="CSE";
        cse.setSelected(true);
        eee.setSelected(false);
        bba.setSelected(false);
    }//GEN-LAST:event_cseActionPerformed

    private void oneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oneActionPerformed
        // TODO add your handling code here:
        credit="1.5";
        one.setSelected(true);
        two.setSelected(false);
        three.setSelected(false);
        four.setSelected(false);
    }//GEN-LAST:event_oneActionPerformed

    private void twoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_twoActionPerformed
        // TODO add your handling code here:
        credit="2";
        one.setSelected(false);
        two.setSelected(true);
        three.setSelected(false);
        four.setSelected(false);
    }//GEN-LAST:event_twoActionPerformed

    private void threeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_threeActionPerformed
        // TODO add your handling code here:
        credit="3";
        one.setSelected(false);
        two.setSelected(false);
        three.setSelected(true);
        four.setSelected(false);
    }//GEN-LAST:event_threeActionPerformed

    private void fourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fourActionPerformed
        // TODO add your handling code here:
        credit="4";
        one.setSelected(false);
        two.setSelected(false);
        three.setSelected(false);
        four.setSelected(true);
    }//GEN-LAST:event_fourActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        dashboard c = null;
        try {
            c = new dashboard(userAdmin,passAdmin);
        } catch (SQLException ex) {
            Logger.getLogger(regAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void viewCoursesTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewCoursesTableMouseClicked
        // TODO add your handling code here:
        int index= viewCoursesTable.getSelectedRow();
        ShowItem(index);
    }//GEN-LAST:event_viewCoursesTableMouseClicked

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchActionPerformed

    private void searchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchKeyPressed

    private void searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchKeyReleased
        // TODO add your handling code here:
        Searched_Courses_In_JTable();
    }//GEN-LAST:event_searchKeyReleased

    private void searchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_searchKeyTyped

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
            java.util.logging.Logger.getLogger(regAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(regAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(regAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(regAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>


        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new regAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel addCourses;
    private javax.swing.JRadioButton bba;
    private javax.swing.JTextField courseField;
    private javax.swing.JRadioButton cse;
    private javax.swing.JLabel deptTAG;
    private javax.swing.JRadioButton eee;
    private javax.swing.JRadioButton four;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private keeptoo.KGradientPanel kGradientPanel1;
    private keeptoo.KGradientPanel kGradientPanel2;
    private keeptoo.KGradientPanel kGradientPanel3;
    private keeptoo.KGradientPanel kGradientPanel4;
    private javax.swing.JRadioButton one;
    private javax.swing.JTextField search;
    private javax.swing.JLabel test;
    private javax.swing.JRadioButton three;
    private javax.swing.JRadioButton two;
    private javax.swing.JPanel viewCourses;
    private javax.swing.JTable viewCoursesTable;
    // End of variables declaration//GEN-END:variables
    private String department;
    private String credit;
}
