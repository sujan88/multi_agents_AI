package agents;


/*******************************************************************************
*
*  TableauDeBord:
*  -------------
*  This class defines the Gui for the BankAgentGui. It shows how to build an
*  agent with an integrated GUI.
*
*  Version 1.0 - July 2003.
*  Authors: Ambroise Ncho, under the supervision of Professeur Jean Vaucher.
*
*  Departement d'Informatique et de Recherche Operationnelle (DIRO).
*  Universite de Montreal.
*
********************************************************************************/

import jade.gui.GuiEvent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import ontologies.Problem;
import ontologies.Schedule;
import ontologies.Session;
import ontologies.WLVocabulary;




class WLAgentGui extends javax.swing.JFrame implements ActionListener, WLVocabulary {
// --------------------------------------------------------------------------

   Object[] actions = {"","New Schedule","Percentage","Lift","Squat",
                       "Lifted Info","Summary"};
   String columns[] = {"Date","Tasks","Snatch","Clean & Jerk","ToTal"};
   final static int IN_PROCESS = 0;
   final static int WAIT_CONFIRM = 1;
   final static int IN_LINE = 2;
   private int status = IN_PROCESS;
   private java.util.List accounts = new ArrayList();
 //  private JTextField msg, snatch,clean_jerk, acInfo;
   //private JComboBox menu;
   private JList acList;
   private JTable opTable;
  // private JButton ok, cancel, quit;
   private WLAthleteAgent myAgent;
   private String agentName;


   
   
   // Variables declaration - do not modify
   private javax.swing.JTextField msg;
   private javax.swing.JButton cancel;
   private javax.swing.JTextField clean_jerk;
   private javax.swing.JLabel jLabel1,labelbacksqt,labelfrontsqt;
   private javax.swing.JLabel jLabel2;
   private javax.swing.JLabel jLabel3;
   private javax.swing.JLabel jLabel4;
   private javax.swing.JLabel jLabel5;
   private javax.swing.JLabel jLabel6;
   private javax.swing.JLabel jLabel7;
   private javax.swing.JLabel jLabel8;
   private javax.swing.JPanel jPanel1;
   private javax.swing.JScrollPane jScrollPane1;
   private javax.swing.JComboBox menu;
   private javax.swing.JButton ok;
   private javax.swing.JButton quit;
  
   private java.awt.TextField acInfo;
   private javax.swing.JTextField snatch,backsqt,frontsqt;
   private javax.swing.JLayeredPane tablepanel;
   // End of variables declaration

   
   public WLAgentGui(WLAthleteAgent a) {
// ----------------------------------------  Constructor

      myAgent = a;      // Reference to class BankClientAgent
      setAgentName(myAgent.getLocalName());
      setTitle("Weightlifting Athlete Agent - " + getAgentName());
     
          tablepanel = new javax.swing.JLayeredPane();
         
          
          jLabel1 = new javax.swing.JLabel();
          jPanel1 = new javax.swing.JPanel();
          msg = new javax.swing.JTextField("Welcome "+getAgentName()+" ! . . . .   Select New Schedule.", 15);
          jLabel2 = new javax.swing.JLabel();
          snatch = new javax.swing.JTextField();
          backsqt = new javax.swing.JTextField();
          frontsqt = new javax.swing.JTextField();
          clean_jerk = new javax.swing.JTextField();
          jLabel3 = new javax.swing.JLabel();
          jLabel4 = new javax.swing.JLabel();
          jScrollPane1 = new javax.swing.JScrollPane();
         
          jLabel5 = new javax.swing.JLabel();
          jLabel6 = new javax.swing.JLabel();
          ok = new javax.swing.JButton();
          cancel = new javax.swing.JButton();
          quit = new javax.swing.JButton();
          jLabel7 = new javax.swing.JLabel();
          menu = new javax.swing.JComboBox(actions);
          jLabel8 = new javax.swing.JLabel();
          labelbacksqt = new javax.swing.JLabel();
          labelfrontsqt = new javax.swing.JLabel();
          acInfo = new java.awt.TextField();

         

          jLabel1.setBackground(new java.awt.Color(255, 204, 102));
          jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
          jLabel1.setForeground(new java.awt.Color(51, 51, 51));
          jLabel1.setText("Weightlifting Schedule Agent ");
          jLabel1.setBounds(100, 0, 250, 20);
          tablepanel.add(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

          
          tablepanel.add(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

          msg.setEditable(false);
          msg.setBackground(Color.black);
          msg.setForeground(Color.white);
          msg.setFont(new Font("Arial", Font.BOLD, 12));
          msg.setHorizontalAlignment(JTextField.CENTER);
          msg.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
          msg.setBounds(10, 50, 440, 30);
          tablepanel.add(msg, javax.swing.JLayeredPane.DEFAULT_LAYER);

          jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
          jLabel2.setText("Status");
          jLabel2.setBounds(10, 30, 60, 15);
          tablepanel.add(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);

          snatch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
          
          
         
          snatch.setBounds(150, 120, 80, 20);
          tablepanel.add(snatch, javax.swing.JLayeredPane.DEFAULT_LAYER);

          clean_jerk.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
          clean_jerk.setBounds(150, 150, 80, 20);
          tablepanel.add(clean_jerk, javax.swing.JLayeredPane.DEFAULT_LAYER);
          
          backsqt.setBounds(150, 180, 80, 20);
          tablepanel.add(backsqt, javax.swing.JLayeredPane.DEFAULT_LAYER);
          
          frontsqt.setBounds(150, 210, 80, 20);
          tablepanel.add(frontsqt, javax.swing.JLayeredPane.DEFAULT_LAYER);

          jLabel3.setText("Maximum Clean & Jerk");
          jLabel3.setBounds(10, 150, 140, 20);
          tablepanel.add(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);

          jLabel4.setText("Maximum Snatch");
          jLabel4.setBounds(10, 120, 140, 14);
          tablepanel.add(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
          
          labelbacksqt.setText("Maximum Back Squat");
          labelbacksqt.setBounds(10, 180, 140, 20);
          tablepanel.add(labelbacksqt, javax.swing.JLayeredPane.DEFAULT_LAYER);

          labelfrontsqt.setText("Maximum Front Squat");
          labelfrontsqt.setBounds(10, 210, 140, 20);
          tablepanel.add(labelfrontsqt, javax.swing.JLayeredPane.DEFAULT_LAYER);
          
          acList = new JList();
          acList.setVisibleRowCount(5);
          acList.setFixedCellHeight(18);
          acList.setFont(new Font("Arial", Font.PLAIN, 12));
          acList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
          JScrollPane scroll = new JScrollPane(acList);
          scroll.setPreferredSize(new Dimension(160,60));
          jScrollPane1.setViewportView(scroll);

          jScrollPane1.setBounds(250, 120, 200, 130);
          tablepanel.add(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);

          jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
          jLabel5.setText("Menu");
          jLabel5.setBounds(10, 240, 40, 15);
          tablepanel.add(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);

          jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
          jLabel6.setText("Enter Your Maximum Lifts");
          jLabel6.setBounds(10, 100, 180, 15);
          tablepanel.add(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);

          quit.setText("QUIT");
          quit.setBounds(400, 380, 80, 30);
          quit.setToolTipText("Stop agent and exit");
          quit.addActionListener(this);
          tablepanel.add(quit, javax.swing.JLayeredPane.DEFAULT_LAYER);

          ok.setText("OK");
          ok.setBounds(400, 303, 80, 30);
          ok.setToolTipText("Submit operation");
          ok.addActionListener(this);
          tablepanel.add(ok, javax.swing.JLayeredPane.DEFAULT_LAYER);

          cancel.setText("Cancel");
          cancel.setBounds(400, 340, 80, 30);
          cancel.setToolTipText("Submit operation");
          cancel.setEnabled(false);
          cancel.addActionListener(this);
          
          
         
          
          tablepanel.add(cancel, javax.swing.JLayeredPane.DEFAULT_LAYER);

          jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
          jLabel7.setText("Schedules");
          jLabel7.setBounds(250, 100, 70, 15);
          tablepanel.add(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);

           menu.setBounds(50, 240, 130, 20);
          tablepanel.add(menu, javax.swing.JLayeredPane.DEFAULT_LAYER);

          jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
          jLabel8.setText("Sessions");
          jLabel8.setBounds(0, 270, 70, 15);
          tablepanel.add(jLabel8, javax.swing.JLayeredPane.DEFAULT_LAYER);

          jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
          jPanel1.setBounds(0, 300, 390, 140);
          jPanel1.setLayout(new BorderLayout(0,0));
          Object obj[][] = new Object[0][columns.length];
          TableModel model = new TableDataModel(obj, columns);
          opTable = new JTable(model);
          opTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
          opTable.setPreferredScrollableViewportSize(new Dimension(320,100));
          opTable.setFont(new Font("Arial", Font.PLAIN, 11));
          jPanel1.add(new JScrollPane(opTable), BorderLayout.SOUTH);
         
          
          
          acInfo.setBounds(80, 270, 300, 20);
          acInfo.setBackground(Color.black);
          acInfo.setForeground(Color.white);
          acInfo.setEditable(false);
          acInfo.setFont(new Font("Arial", Font.PLAIN, 11));
          tablepanel.add(acInfo, javax.swing.JLayeredPane.DEFAULT_LAYER);
           
          JLabel backLable= new  JLabel();
          backLable.setBounds(490,1, 150, 441);
          backLable.setBorder(javax.swing.border.LineBorder.createBlackLineBorder());
          backLable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/back.jpg")));
          tablepanel.add(backLable);
          

          setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
          javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
          getContentPane().setLayout(layout);
          layout.setHorizontalGroup(
              layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(layout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(tablepanel, javax.swing.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)
                  .addContainerGap())
          );
          layout.setVerticalGroup(
              layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(layout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(tablepanel, javax.swing.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
                  .addContainerGap())
          );
     

 

   pack();
      
      
  
      addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            shutDown();
         }
      });

      //setSize(500, 350);
      setResizable(false);
      Rectangle r = getGraphicsConfiguration().getBounds();
      setLocation(r.x + (r.width - getWidth())/2,
                  r.y + (r.height - getHeight())/2);
   }

 
   public void actionPerformed(ActionEvent ae) {
// ---------------------------------------------

      if (ae.getSource() == quit) {
         shutDown();
      }
      else if (ae.getSource() == ok) {

         if (menu.getSelectedItem().equals(actions[0])) {
            alertInfo("Select an action in the menu");
         }
         else if (menu.getSelectedItem().equals(actions[1])) {
            GuiEvent ge = new GuiEvent(this, NEW_SCHEDULE);
            myAgent.postGuiEvent(ge);
            
         }
         else if (menu.getSelectedItem().equals(actions[2]) ||
                  menu.getSelectedItem().equals(actions[3])) {
            if (accounts.isEmpty()) {
               alertInfo("No Schedule currently available");
               return;
            }
            if (acList.getSelectedIndex() == -1) {
               alertInfo("Select a Schedule");
               return;
            }
            if (snatch.getText().length() == 0) {
               alertInfo("Enter a maximum snatch");
               snatch.requestFocus();
               return;
            }
            if (clean_jerk.getText().length() == 0) {
                alertInfo("Enter a maximum clean & jerk");
                clean_jerk.requestFocus();
                return;
             }
            
            try {
               float _snatch = Float.parseFloat(snatch.getText());
               float _clean_jerk = Float.parseFloat(clean_jerk.getText());
               int type = menu.getSelectedItem().equals(actions[2])?
            		   PERCENTAGE : LIFT;
               GuiEvent ge = new GuiEvent(this, type);
               ge.addParameter(accounts.get(acList.getSelectedIndex()));
               ge.addParameter(new Float(_snatch));
               ge.addParameter(new Float(_clean_jerk));
               myAgent.postGuiEvent(ge);
            }
            catch (Exception ex) { alertInfo("Invalid input. Operation aborted!"); }
         }
         else if (menu.getSelectedItem().equals(actions[4])) {
        	 if (acList.getSelectedIndex() == -1) {
                 alertInfo("Select a Schedule");
                 return;
              }
        	 
        	 if (backsqt.getText().length() == 0) {
                 alertInfo("Enter a maximum Back Squat");
                 backsqt.requestFocus();
                 return;
              }
              if (frontsqt.getText().length() == 0) {
                  alertInfo("Enter a maximum Front Squat");
                  frontsqt.requestFocus();
                  return;
               }
              
              try {
                  float _backsquat = Float.parseFloat(backsqt.getText());
                  float _frontsquat = Float.parseFloat(frontsqt.getText());
                  int type =SQUAT;
                  GuiEvent ge = new GuiEvent(this, type);
                  //schedule index no in schedule list
                  ge.addParameter(accounts.get(acList.getSelectedIndex()));
                  ge.addParameter(new Float(_backsquat));
                  ge.addParameter(new Float(_frontsquat));
                  myAgent.postGuiEvent(ge);
               }
               catch (Exception ex) { alertInfo("Invalid input. Schedule aborted!"); }
        	 
         }
         else if (menu.getSelectedItem().equals(actions[5]) ||
                  menu.getSelectedItem().equals(actions[6]) &&
                  status != IN_LINE) {
            if (accounts.isEmpty()) {
               alertInfo("No account currently available");
               return;
            }
            if (acList.getSelectedIndex() == -1) {
               alertInfo("Select an account");
               return;
            }
            int type = menu.getSelectedItem().equals(actions[5])?
                       LIFTED : TASKS;
            if (status == IN_PROCESS) {
               if (type == LIFTED)
                  alertInfo("Do you need Squats for  shcedule "+acList.getSelectedIndex()+" ?");
               else alertInfo("Summary of your schedules. Continue?");
               status = WAIT_CONFIRM;
               cancel.setEnabled(true);
               return;
            }
            if (status == WAIT_CONFIRM) {
               status = IN_LINE;
               cancel.setEnabled(false);
               GuiEvent ge = new GuiEvent(this, type);
               ge.addParameter(accounts.get(acList.getSelectedIndex()));
               myAgent.postGuiEvent(ge);
            }
         }
      }
      else if (ae.getSource() == cancel && status != IN_LINE) {
         status = IN_PROCESS;
         cancel.setEnabled(false);
         msg.setText("Operation canceled!");
      }
   }

   void shutDown() {
// -----------------  Control the closing of this gui

      int rep = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?",
                                              myAgent.getLocalName(),
                                              JOptionPane.YES_NO_CANCEL_OPTION);
      if (rep == JOptionPane.YES_OPTION) {
         GuiEvent ge = new GuiEvent(this, myAgent.QUIT);
         myAgent.postGuiEvent(ge);
      }
   }

   void alertInfo(String s) {
// --------------------------

      Toolkit.getDefaultToolkit().beep();
      msg.setText(s);
   }

   public void alertResponse(Object o) {
// -------------------------------------

      String s = "";
      if (o instanceof Problem) {  // This a failure response from coacher
         s = ((Problem)o).getMsg();
      }
      else if (o instanceof Schedule) {
    	  
         Schedule acc = (Schedule)o;
     
         if (updateAccount(acc)) {  //  <-- Existing schedule just updated
            s =  "Snatch ["+acc.getSnatch()+"kg]   clean & jerk ["+acc.getClean_jerk()+"kg]";
           if(acc.getType()==SQUAT)
        	   s = acc.getFrontSquat()==0.0f ?"Back Squat [ "+acc.getBackSquat()+"kg ] " : "Front Squat [ "+acc.getFrontSquat()+" kg]";
            
         }
         else {   //  <-- This is a new schedule just added
            java.util.Vector v = getAccountList();
            acList.setListData(v);
            acList.setSelectedIndex(accounts.size()-1);
            s = acc.getName() + " created!";
         }
      
      if(acc.getType()==LIFT) s= "Lifted !!!  Now your Total weight of lifted is " +acc.getLifted();
     
      }
     
      else if (o instanceof jade.util.leap.List) {  //  <-- This is a list of sessions
         displayOperations((jade.util.leap.List)o);
      }
      else if (o instanceof String) {
         s = (String)o;
      }
      msg.setText(s);
   }

   boolean updateAccount(Schedule acc) {
// ------------------------------------

      for (Iterator it = accounts.iterator(); it.hasNext();) {
         Schedule a = (Schedule)it.next();
         if (a.getId().equals(acc.getId())) {
            int i = accounts.indexOf(a);
            accounts.set(i, acc);  //  <-- Schedule already exists: update it
            return true;
         }
      }
      accounts.add(acc);  //  <-- New Schedule: add to the list of scheduleSS
      return false;
   }

   java.util.Vector getAccountList() {
// -----------------------------------

      java.util.Vector v = new java.util.Vector();
      for (int i = 0; i < accounts.size(); i++) {
         Schedule acc = (Schedule)accounts.get(i);
         v.add((i+1) + ". " + acc.getName() + "    schID: " + acc.getId());
      }
      return v;
   }

   void displayOperations(jade.util.leap.List list) {
// --------------------------------------------------

      Schedule acc = (Schedule)accounts.get(acList.getSelectedIndex());
      acInfo.setText(acc.getName() + "    schID: "+ acc.getId());
      Object[][] data = new Object[list.size()][columns.length];
      for (int i = 0; i < list.size(); i++) {
         Session op = (Session)list.get(i);
         data[i][0] = op.getDate();
         String type="";
         switch( op.getType()){
         case 1: type="new Schedule";break;
         case 2: type="percentage";break;
         case 3: type="Lift";break;
         case 4: type="Lifted";break;
         case 5: type="Task";break;
         case 6: type="Check Info";break;
         case 7: type="Squat";break;
         default : type="Unknown";
         }
         data[i][1] = type;
         if (op.getType() == LIFT ){
            data[i][2] = new Float(op.getSnatch());
         data[i][3] = new Float(op.getClean_jerk());
        
         }
         else {
        	 data[i][2] = null;
        	 data[i][3] = null;
         
         }
         data[i][4] =  new Float(op.getLifted());
      }
      TableDataModel model = (TableDataModel)opTable.getModel();
      model.setData(data);
      opTable.setModel(model);
      opTable.updateUI();
   }

   public void resetStatus() {
// ---------------------------

      status = IN_PROCESS;
   }
   
   public void setAgentName(String name){
	   this.agentName=name;
   }
   public String getAgentName(){
	   return agentName;
   }
}


// =========================== External class ============================//

/* TableDataModel:
*  --------------
*  External class for the definition of the tables data model, used to
*  control the display of data within the different tables
**/
   class TableDataModel extends AbstractTableModel {
// -------------------------------------------------

   private String[] columns;
   private Object[][] data;

   public TableDataModel(Object[][] data, String[] columns) {
// ----------------------------------------------------------  Constructor
      this.data = data;
      this.columns = columns;
   }

   public int getColumnCount() {
// -----------------------------  Return the number of columns in the table
      return columns.length;
   }

   public int getRowCount() {
// --------------------------  Return the number of rows in the table
      return data.length;
   }

   public String getColumnName(int col) {
// --------------------------------------  Return the name of a column
      return columns[col];
   }

   public Object getValueAt(int row, int col) {
// --------------------------------------------  Return the value at a specific
//                                               row and column
      if ( data.length == 0 ) return null;
      return data[row][col];
   }

   public Class getColumnClass(int col) {
// --------------------------------------  Return the class of the values held
//                                         by a column
      Object o = getValueAt(0, col);
      if (o == null) return columns[col].getClass();
      return getValueAt(0, col).getClass();
   }

   public void setValueAt(Object value, int row, int col){
// ------------------------------------------------------  Set the value at a specific
//                                                         row and column
      data[row][col] = value;
   }

   public void setData(Object[][] data){
// ------------------------------------  Update the entire data in the table
      this.data = data;
   }

   Object[][] getData(){
// ---------------------  Return the entire data of the table
      return data;
   }
}// end TableDataModel
