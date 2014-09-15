/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minecraftskinstealer;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import static minecraftskinstealer.OsCheck.OSType.Linux;
import static minecraftskinstealer.OsCheck.OSType.Other;

/**
 *
 * @author mike
 */
public class MainWindow extends javax.swing.JFrame {
    private boolean _isOSX = false;
    private boolean test = true;
    BufferedImage img;
    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        this.img = null;
        initComponents();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("res/icon.png")));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        if(!_isOSX)
        {
            aboutLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            aboutLabel.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    AboutDialog ab = new AboutDialog();
                    ab.setVisible(true);
                }
            });
        }
    }
    public MainWindow(boolean isOSX) {
        this.img = null;
        _isOSX = isOSX;
        
        initComponents();
        if(isOSX)
            setAppleSpecifics();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
    }
    
    private void setAppleSpecifics()
    {
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Minecraft Skin Stealer");
        com.apple.eawt.Application app = new com.apple.eawt.Application();
        app.setDockIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("res/icon.png")));
        try
        {OSXAdapter.setAboutHandler(this, getClass().getDeclaredMethod("showAbout", (Class[])null));}
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
                    
    }
    
    //For OS X Use Only
    public void showAbout()
    {
        AboutDialog ab = new AboutDialog();
        ab.setVisible(true);
    }
    
    public void downloadSkin()
    {
        String content = this.userNameTextBox.getText();
        if(!content.trim().isEmpty())
        {
            try
            {
                String urll = String.format("http://s3.amazonaws.com/MinecraftSkins/%s.png", content);
                URL url = new URL(urll);
                this.urlTextBox.setText(urll);
                this.img = ImageIO.read(url);
                this.skinPreview.setIcon(new ImageIcon(img));
                this.saveSkinButton.setEnabled(true);
                this.resetViewButton.setEnabled(true);
            }
            catch(Exception ex)
            {
                this.urlTextBox.setText("");
                JOptionPane.showMessageDialog(this, 
                        ex.getMessage(), 
                        "Error Downloading Skin", 
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, 
                    "Please type in a username!", 
                    "Information", 
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    public void resetSkinView()
    {
        this.img = null;
        this.userNameTextBox.setText("");
        this.urlTextBox.setText("");
        this.skinPreview.setIcon(null);
        this.resetViewButton.setEnabled(false);
        this.saveSkinButton.setEnabled(false);
    }
    public void saveSkin()
    {
        String filetoSaveTo = null;
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Images", "png");
        FilenameFilter awtFilter = new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
               String lowercaseName = name.toLowerCase();
				if (lowercaseName.endsWith(".png")) {
					return true;
				} else {
					return false;
				}
            }
        };
        OsCheck.OSType ostype = OsCheck.getOperatingSystemType();
        if(_isOSX || ostype == Linux || ostype == Other)
        {
            FileDialog fd = new FileDialog(this, "Save Skin", FileDialog.SAVE);
            fd.setDirectory(System.getProperty("java.home"));
            fd.setFile(this.userNameTextBox.getText());
            fd.setFilenameFilter(awtFilter);
            fd.setVisible(true);
            if(fd.getFile() != null)
                filetoSaveTo = fd.getDirectory() + fd.getFile();
            else
                   filetoSaveTo = fd.getFile();
        }
        else
        {
            JFileChooser fc = new JFileChooser();
            fc.setSelectedFile(new File(this.userNameTextBox.getText()));
            fc.setDialogTitle("Save Skin");
            fc.setFileFilter(filter);
            int returnval = fc.showSaveDialog(this);
            if(returnval == 0)
            {
                filetoSaveTo = fc.getSelectedFile().getAbsolutePath().toString();
            }
        }
        if(filetoSaveTo != null)
            save(filetoSaveTo);
    }
    
    private void save(String file)
    {
        if(!file.endsWith("png"))
            file = file + ".png";
        
        File files = new File(file);
        try
        {
            ImageIO.write(this.img, "png", files);
            String message = String.format("Saved to %s successfully!", file);
            JOptionPane.showMessageDialog(this, message, "Information",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error Saving Skin", JOptionPane.ERROR_MESSAGE);
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

        skinPreview = new javax.swing.JLabel();
        userNameTextBox = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        downloadSkinButton = new javax.swing.JButton();
        resetViewButton = new javax.swing.JButton();
        saveSkinButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        urlTextBox = new javax.swing.JTextField();
        aboutLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Minecraft Skin Stealer");

        skinPreview.setName("skinPreviewLabel"); // NOI18N

        userNameTextBox.setText("MrMiketheripper");
        userNameTextBox.setName("userNameTextField"); // NOI18N

        jLabel2.setText("Username to Download");

        downloadSkinButton.setText("Download Skin");
        downloadSkinButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadSkinButtonActionPerformed(evt);
            }
        });

        resetViewButton.setText("Reset View");
        resetViewButton.setEnabled(false);
        resetViewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetViewButtonActionPerformed(evt);
            }
        });

        saveSkinButton.setText("Save...");
        saveSkinButton.setEnabled(false);
        saveSkinButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveSkinButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("URL (in case it doesn't load)");

        urlTextBox.setEditable(false);
        urlTextBox.setName("urlTextField"); // NOI18N

        aboutLabel.setText("v1.0.2.1");
        aboutLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userNameTextBox)
                    .addComponent(urlTextBox)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(aboutLabel)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(downloadSkinButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(resetViewButton, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(saveSkinButton)))
                        .addGap(0, 13, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(149, 149, 149)
                .addComponent(skinPreview, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(skinPreview, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userNameTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(downloadSkinButton)
                    .addComponent(resetViewButton)
                    .addComponent(saveSkinButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(urlTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(aboutLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void downloadSkinButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadSkinButtonActionPerformed
        downloadSkin();
    }//GEN-LAST:event_downloadSkinButtonActionPerformed

    private void resetViewButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetViewButtonActionPerformed
        resetSkinView();
    }//GEN-LAST:event_resetViewButtonActionPerformed

    private void saveSkinButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveSkinButtonActionPerformed
        saveSkin();
    }//GEN-LAST:event_saveSkinButtonActionPerformed

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
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel aboutLabel;
    private javax.swing.JButton downloadSkinButton;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton resetViewButton;
    private javax.swing.JButton saveSkinButton;
    private javax.swing.JLabel skinPreview;
    private javax.swing.JTextField urlTextBox;
    private javax.swing.JTextField userNameTextBox;
    // End of variables declaration//GEN-END:variables
}
