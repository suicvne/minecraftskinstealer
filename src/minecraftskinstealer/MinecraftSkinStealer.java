/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minecraftskinstealer;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.PrintStream;
import java.util.Set;
import javax.swing.UIManager;
import com.apple.eawt.*;

/**
 *
 * @author mike
 */
public class MinecraftSkinStealer 
{
    public String version = "1.0.2";
    public static String staticVersion = "1.0.2";
    public static String systemLookAndFeel = null;
    public boolean isOSX = false;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        if(args.length > 0)
        {
            String par = args[0];
            if(par.contains("nogui"))
            {
                System.out.printf("Minecraft Skin Stealer v%s\nby Luigifan\n", staticVersion);
                System.out.printf("Enter username to download: ");
                String input = System.console().readLine();
                downloadSkin(input);
            }
            else
            {
                System.out.printf("Invalid parameter '%s'!\n", args[0]);
            }
        }
        else
        {
            MinecraftSkinStealer prog = new MinecraftSkinStealer();
            prog.runGUI();
        }
    }
    
    public void runGUI()
    {
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Minecraft Skin Stealer");
        com.apple.eawt.Application app = new com.apple.eawt.Application();
        app.setDockIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("res/icon.png")));
            OsCheck.OSType osType = OsCheck.getOperatingSystemType();
            switch(osType)
            {
                case Windows:
                    try
                    {
                        systemLookAndFeel = UIManager.getSystemLookAndFeelClassName();
                    }
                    catch(Exception ex)
                    {
                        systemLookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
                    }
                    break;
                case MacOS:
                    try
                    {
                        systemLookAndFeel = UIManager.getSystemLookAndFeelClassName();
                        isOSX = true;
                    }
                    catch(Exception ex)
                    {
                        systemLookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
                    }
                    break;
                case Linux:
                    try
                    {
                        systemLookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
                    }
                    catch(Exception ex)
                    {
                        systemLookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
                    }
                    break;
                case Other:
                    systemLookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
                    break;
            }
            setLookAndFeel(systemLookAndFeel);
            MainWindow mw = new MainWindow();
            if(isOSX)
                mw = new MainWindow(true);
            else
                mw = new MainWindow();
            mw.setVisible(true);
    }
    
    private void setAppleSpecifics() 
    {
        com.apple.eawt.Application app = new com.apple.eawt.Application();
        app.setDockIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("res/icon.png")));
        app.setDockIconBadge("test");
    }
    
    private static void reRunMain()
    {
        
    }
    
    private static void setLookAndFeel(String lookAndFeelClass) 
    {
        try
        {
            UIManager.setLookAndFeel(lookAndFeelClass);
        }
        catch(Exception ex)
        {
            System.out.printf("Unable to set look and feel to '%s'\nException output: %s", lookAndFeelClass, ex.getMessage());
        }
    }

    /**
     * 
     * @param input the username to put in to try and download
     */
    private static void downloadSkin(String input) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
