/*
 * Copyright (C) 2016
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package Semantic_Search.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import Semantic_Search.SearchEngine.LuceneIndexer;
import Semantic_Search.SearchEngine.LuceneSearcher;

import java.util.ResourceBundle;

import Semantic_Search.dataAccess.Get_OWL_object;
import org.semanticweb.owlapi.model.OWLOntology;


public class UI_Main_Window extends JFrame {

    private final ResourceBundle SemanticResUI = ResourceBundle.getBundle("SemanticResUI");
    static OWLOntology loaded = null;

  private final JMenuBar menuBar = new JMenuBar();
  private final JMenu fileMenu =  new JMenu(SemanticResUI.getString("file"));
  private final JMenu OpenMenu = new JMenu(SemanticResUI.getString("open"));
  private final JMenu Inform = new JMenu(SemanticResUI.getString("About"));
    private final JMenu AnalyzerType = new JMenu(SemanticResUI.getString("AnalyzerType"));
  private final JMenuItem XMLopen = new JMenuItem(SemanticResUI.getString("collection"));
  private final JMenuItem Httpopen = new JMenuItem(SemanticResUI.getString("URI"));
 // private final JMenuItem OWLopen = new JMenuItem(SemanticResUI.getString("OwlDir"));
    private final JMenuItem OWLoneopen = new JMenuItem(SemanticResUI.getString("OWLone"));
  private final JMenuItem exitItem = new JMenuItem(SemanticResUI.getString("Exit"));

    private final JMenuItem about = new JMenuItem(SemanticResUI.getString("AboutProg"));
    private final static JRadioButtonMenuItem AnalyzerStdItem = new JRadioButtonMenuItem("English Analyzer");
    final public static JRadioButtonMenuItem AnalyzerRuItem = new JRadioButtonMenuItem("Russian Analyzer");
  
  private final JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
  private final JScrollPane  jScrollPane2 = new javax.swing.JScrollPane();
  private final JPanel  jPanel1 = new javax.swing.JPanel();
  private final JTextField  jTextField3 = new javax.swing.JTextField();
  private final JButton jButton1 = new javax.swing.JButton();
  private final  JLabel jLabel1 = new  javax.swing.JLabel();
  private final JLabel jLabel2 = new  javax.swing.JLabel();
  public static final JTextArea jTextField5 = new javax.swing.JTextArea();
  private static final JTextArea jTextField4 = new javax.swing.JTextArea();
  private final JLabel jLabel3 = new javax.swing.JLabel();
  private final JLabel jLabel4 = new javax.swing.JLabel();
  private final JLabel jLabel5 = new javax.swing.JLabel();
  public static final JLabel jLabel6 =new javax.swing.JLabel();
  public static final JLabel jLabel7 =new javax.swing.JLabel();

 
 
  

//Construct the frame
  
  public UI_Main_Window() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
      
    }
    catch(Exception ignored) {
    }
  }


  //Component initialization
  private void jbInit() {


    //Set components font
      fileMenu.setFont(new java.awt.Font("Verdana", Font.PLAIN, 11));
      OpenMenu.setFont(new java.awt.Font("Verdana", Font.PLAIN, 11));
      XMLopen.setFont(new java.awt.Font("Verdana", Font.PLAIN, 11));
      Inform.setFont(new java.awt.Font("Verdana", Font.PLAIN, 11));
      AnalyzerType.setFont(new java.awt.Font("Verdana", Font.PLAIN, 11));
      AnalyzerStdItem.setFont(new java.awt.Font("Verdana", Font.PLAIN, 11));
     // OWLopen.setFont(new java.awt.Font("Verdana", Font.PLAIN, 11));
      AnalyzerStdItem.setSelected(true);
      AnalyzerRuItem.setFont(new java.awt.Font("Verdana", Font.PLAIN, 11));
      OWLoneopen.setFont(new java.awt.Font("Verdana", Font.PLAIN, 11));
      Httpopen.setFont(new java.awt.Font("Verdana", Font.PLAIN, 11));
      exitItem.setFont(new java.awt.Font("Verdana", Font.PLAIN, 11));
      about.setFont(new java.awt.Font("Verdana", Font.PLAIN, 11));

    //Add menus
    fileMenu.add(OpenMenu);
    OpenMenu.add(XMLopen);
    //OpenMenu.add(OWLopen);
    OpenMenu.add(OWLoneopen);
    OpenMenu.add(Httpopen);
    fileMenu.addSeparator();
    fileMenu.add(exitItem);
    AnalyzerType.add(AnalyzerStdItem);
    AnalyzerType.add(AnalyzerRuItem);
    Inform.add(about);

    //Add Listeners
    XMLopen.addActionListener( new FrameGUI_Directory_adapterXML(this));
  //  OWLopen.addActionListener(this::OpenDirectoryOWL_actionPerformed);
    OWLoneopen.addActionListener(this::OWLfile_ActionPerformed);
    Httpopen.addActionListener(this::Http_ActionPerformed);
    about.addActionListener(this::About_ActionPerformed);
    AnalyzerStdItem.addActionListener(this::AnalyzerStd_actionPerformed);
    AnalyzerRuItem.addActionListener(this::AnalyzerRU_actionPerformed);
    exitItem.addActionListener(this::ExitMenu_actionPerformed);

    //Add Bars
    menuBar.add(fileMenu);
    menuBar.add(AnalyzerType);
    menuBar.add(Inform);

    this.setJMenuBar(menuBar);
    this.setMinimumSize(new java.awt.Dimension(677, 566));
    this.setSize(new java.awt.Dimension(334, 172));
    this.setTitle(SemanticResUI.getString("title"));

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);



      jLabel6.setText("");
      jLabel6.setToolTipText(SemanticResUI.getString("ResultDocs_tooltip"));
      jLabel6.setVerticalAlignment(javax.swing.SwingConstants.TOP);
      jScrollPane1.setViewportView(jLabel6);

      jLabel7.setText("");
      jLabel7.setToolTipText(SemanticResUI.getString("OwlRes"));
      jLabel7.setVerticalAlignment(javax.swing.SwingConstants.TOP);
      jScrollPane2.setViewportView(jLabel7);

        jLabel1.setLabelFor(jScrollPane1);
        jLabel1.setText(SemanticResUI.getString("SearchResults"));
        jLabel2.setLabelFor(jScrollPane2);
        jLabel2.setText(SemanticResUI.getString("OWLresults"));
        jLabel5.setText(SemanticResUI.getString("SelectedDirectory"));
        jLabel3.setText("Doc:");
        jLabel4.setText("OWL:");
              


        jTextField3.setText("");
        jTextField3.setToolTipText(SemanticResUI.getString("EnterSearchReq"));
        jTextField3.addActionListener(new FrameGUI_buttonSearch_actionAdapter(this));
        jButton1.setText(SemanticResUI.getString("SearchButton"));
        jButton1.addActionListener(new FrameGUI_buttonSearch_actionAdapter(this));
        
        
          
        jTextField4.setEditable(false);
        jTextField4.setBackground(new java.awt.Color(237, 237, 237));
        jTextField4.setFont(new java.awt.Font("Dialog", Font.PLAIN, 12)); // NOI18N
        jTextField4.setText(""); //Current selected folders: \n
        jTextField4.setBorder(null);
        jTextField4.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        jTextField4.setForeground(new java.awt.Color(102, 102, 255));
        jTextField4.setFocusable(false);
       
        jTextField5.setEditable(false);
        jTextField5.setBackground(new java.awt.Color(237, 237, 237));
        jTextField5.setForeground(new java.awt.Color(102, 102, 255));
        jTextField5.setFont(new java.awt.Font("Dialog", Font.PLAIN, 12)); // NOI18N
        jTextField5.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        jTextField5.setText("");
        jTextField5.setFocusable(false);
        jTextField5.setBorder(null);
        
        jLabel3.setForeground(new java.awt.Color(0,153,102));

        jLabel4.setForeground(new java.awt.Color(0,153,102));


      javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
      jPanel1.setLayout(jPanel1Layout);
      jPanel1Layout.setHorizontalGroup(
              jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                      .addGroup(jPanel1Layout.createSequentialGroup()
                              .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                      .addGroup(jPanel1Layout.createSequentialGroup()
                                              .addGap(16, 16, 16)
                                              .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                      .addComponent(jScrollPane1)
                                                      .addComponent(jLabel1))
                                              .addGap(18, 18, 18)
                                              .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                      .addComponent(jLabel2)
                                                      .addComponent(jScrollPane2)))
                                      .addGroup(jPanel1Layout.createSequentialGroup()
                                              .addContainerGap(133, Short.MAX_VALUE)
                                              .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                      .addComponent(jLabel5)
                                                      .addGroup(jPanel1Layout.createSequentialGroup()
                                                              .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                              .addComponent(jButton1))
                                                      .addGroup(jPanel1Layout.createSequentialGroup()
                                                              .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                      .addComponent(jLabel3)
                                                                      .addComponent(jLabel4))
                                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                              .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                      .addComponent(jTextField5)
                                                                      .addComponent(jTextField4))))
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 307, Short.MAX_VALUE)))
                              .addGap(17, 17, 17))
      );
      jPanel1Layout.setVerticalGroup(
              jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                              .addComponent(jLabel5)
                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                              .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                      .addComponent(jTextField4)
                                      .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                              .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                      .addComponent(jTextField5)
                                      .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                              .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                      .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                      .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                              .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                      .addComponent(jLabel1)
                                      .addComponent(jLabel2))
                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                              .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                      .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE)
                                      .addComponent(jScrollPane1))
                              .addContainerGap())
      );

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
              layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                      .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1006, Short.MAX_VALUE)
      );
      layout.setVerticalGroup(
              layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                      .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 802, Short.MAX_VALUE)
      );
      pack();

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    this.setVisible(true);




  }
  //Overridden so we can exit when window is closed
  @Override
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      System.exit(0);
    }
  }

  void buttonSearch_actionPerformed(ActionEvent e) throws Exception {
      jLabel7.setText("");
      jLabel6.setText("");
      if((jTextField3.getText().isEmpty()) | (jTextField3.getText().equalsIgnoreCase(SemanticResUI.getString("EnterSearchReq")))) {

         jTextField3.setBorder(BorderFactory.createLineBorder(Color.RED));
          jTextField3.setText(SemanticResUI.getString("EnterSearchReq"));



      }
        else {
          jTextField3.setBorder(BorderFactory.createLineBorder(Color.GRAY));

          new LuceneSearcher().luceneSearcher(jTextField3.getText(), loaded);

          }
        
        }
      


private  void About_ActionPerformed(ActionEvent e) {
 new UI_About(this).setVisible(true);

}




private  void OWLfile_ActionPerformed(ActionEvent e) {
    UI_Progress_Bar bar = new UI_Progress_Bar();
    String Path = this.mGetFilesPath(true);
    UI_Main_Window.jTextField5.setText(Path);
    if (!Path.equals("Nothing was selected")) {
        SwingWorker swg = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                loaded = new Get_OWL_object().Get_OWL_object(Path);
                bar.setVisible(false);
                return null;
            }

        };

        bar.setVisible(true);

        swg.execute();

    }

}

  private void Http_ActionPerformed(java.awt.event.ActionEvent evt) {
      new UI_URI_form(this).setVisible(true);

    }               
  

  private void ExitMenu_actionPerformed(ActionEvent e){System.exit(0);}



  void OpenDirectoryXML_actionPerformed(ActionEvent e) {
      UI_Progress_Bar bar = new UI_Progress_Bar();
      String Path = this.mGetFilesPath(false);
      UI_Main_Window.jTextField4.setText(Path);
      if (!Path.equals("Nothing was selected")) {
          SwingWorker swg = new SwingWorker() {
              @Override
              protected Object doInBackground() throws Exception {
                  //noinspection AccessStaticViaInstance
                  new LuceneIndexer().luceneIndexer(Path);
                  bar.setVisible(false);
                  return null;

              }
          };

          bar.setVisible(true);

          swg.execute();

      }
  }





  private void AnalyzerStd_actionPerformed(ActionEvent e) {

     AnalyzerRuItem.setSelected(false);

      if (!jTextField4.getText().isEmpty() && AnalyzerStdItem.isSelected()) {

          UI_Progress_Bar bar = new UI_Progress_Bar();

          SwingWorker swg = new SwingWorker() {
              @Override
              protected Object doInBackground() throws Exception {
                  //noinspection AccessStaticViaInstance
                  new LuceneIndexer().luceneIndexer(jTextField4.getText());
                  bar.setVisible(false);
                  return null;

              }
          };

          bar.setVisible(true);

           swg.execute();
      }



      AnalyzerStdItem.setEnabled(true);

      AnalyzerStdItem.setSelected(true);




 }

    private void AnalyzerRU_actionPerformed(ActionEvent e) {

        AnalyzerStdItem.setSelected(false);
        if (!jTextField4.getText().isEmpty() && AnalyzerRuItem.isSelected()) {
            UI_Progress_Bar bar = new UI_Progress_Bar();

            SwingWorker swg = new SwingWorker() {
                @Override
                protected Object doInBackground() throws Exception {
                    //noinspection AccessStaticViaInstance
                    new LuceneIndexer().luceneIndexer(jTextField4.getText());
                    bar.setVisible(false);
                    return null;

                }
            };

            bar.setVisible(true);

            swg.execute();


        }
        AnalyzerRuItem.setEnabled(true);
        AnalyzerRuItem.setSelected(true);

    }




    private String mGetFilesPath (boolean file) {

        String resultSel = "Nothing was selected";
        String UserDirectory = System.getProperty("user.home");
        JFileChooser fc = new JFileChooser(UserDirectory);
        fc.setDialogType(JFileChooser.OPEN_DIALOG);
        if (!file){
            fc.setDialogTitle("Open a Collection folder");
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        } else {
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fc.setDialogTitle("Open an OWL ontology");
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "OWL ontology (*.owl)", "owl");
            fc.setFileFilter(filter);
        }
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            File SelFolder = fc.getSelectedFile();

            resultSel = SelFolder.toString();

        }
        return  resultSel;
    }

   
}

class FrameGUI_buttonSearch_actionAdapter implements java.awt.event.ActionListener {
  private final UI_Main_Window adaptee;

  FrameGUI_buttonSearch_actionAdapter(UI_Main_Window adaptee) {
    this.adaptee = adaptee;
  }
  @Override
  public void actionPerformed(ActionEvent e) {

      try {
          adaptee.buttonSearch_actionPerformed(e);
      } catch (Exception e1) {
          e1.printStackTrace();
      }

  }
}



class FrameGUI_Directory_adapterXML implements java.awt.event.ActionListener {
  private final UI_Main_Window adaptee;

  FrameGUI_Directory_adapterXML(UI_Main_Window adaptee) {
    this.adaptee = adaptee;
  }
  @Override
  public void actionPerformed(ActionEvent e) {
      try {
          adaptee.OpenDirectoryXML_actionPerformed(e);
      } catch (Exception e1) {
          e1.printStackTrace();
      }
  }
}




