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

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;
import Semantic_Search.dataAccess.Get_OWL_object;

/**
 *
 * @author eugene
 */
class UI_URI_form extends javax.swing.JDialog {
    private javax.swing.JTextField jTextField1;
    /**
     * Creates new form НовыйJFrame1
     */
    public UI_URI_form(Frame owner) {
        super(owner, true);
        initComponents();
    }

                             
    private void initComponents() {

        JPanel jPanel1 = new JPanel();
        jTextField1 = new javax.swing.JTextField();
        JButton jButton1 = new JButton();
        JLabel jLabel1 = new JLabel();
        ResourceBundle SemanticResUI = ResourceBundle.getBundle("SemanticResUI");
       setDefaultCloseOperation(HIDE_ON_CLOSE);
         setResizable(false);

        jTextField1.setText("");

        jButton1.setText("Ok");
        jButton1.addActionListener(this::jButton1ActionPerformed);
        jTextField1.addActionListener(this::jButton1ActionPerformed);

        jLabel1.setText(SemanticResUI.getString("SelURI"));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(148, 148, 148)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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

    }                     

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        ResourceBundle SemanticResUI = ResourceBundle.getBundle("SemanticResUI");
        String http = jTextField1.getText();
        if((!http.startsWith("http" + ":")) & (!http.startsWith("https" + ":"))) {
            jTextField1.setBorder(BorderFactory.createLineBorder(Color.RED));
           jTextField1.setText(SemanticResUI.getString("OntologyHTTP"));
        }
        else {
            jTextField1.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            UI_Progress_Bar bar = new UI_Progress_Bar();


                SwingWorker swg = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        UI_Main_Window.loaded = new Get_OWL_object().Get_OWL_object(http);
                        bar.setVisible(false);
                        return null;

                    }
                };

                bar.setVisible(true);

                 swg.execute();

            UI_Main_Window.jTextField5.setText(http);

        this.setVisible(false);
        }
    }                                        

              
}
