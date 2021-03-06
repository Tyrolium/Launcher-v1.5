package fr.tyrolium.maxime.launcher;

import static fr.theshark34.swinger.Swinger.drawFullsizedImage;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import fr.theshark34.openauth.AuthenticationException;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.openlauncherlib.util.ramselector.RamSelector;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.colored.SColoredBar;
import fr.theshark34.swinger.colored.SColoredButton;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

@SuppressWarnings("serial")
public class LauncherPanel extends JPanel implements SwingerEventListener {
	
	  
	private Image background = Swinger.getResource("background.png");
	    
	private Saver saver = new Saver(new File(Launcher.TY_DIR, "launcher.properties"));
	    
	public JTextField usernameField = new JTextField(this.saver.get("username"));
	public JPasswordField passwordField = new JPasswordField();
	
	private STexturedButton connBtn = new STexturedButton(Swinger.getResource("conn.png"));
	private STexturedButton inscBtn = new STexturedButton(Swinger.getResource("insc.png"));
	private STexturedButton siteBtn = new STexturedButton(Swinger.getResource("site.png"));
	private STexturedButton boutBtn = new STexturedButton(Swinger.getResource("bout.png"));
	private STexturedButton discordBtn = new STexturedButton(Swinger.getResource("disc.png"));
	private STexturedButton ytBtn = new STexturedButton(Swinger.getResource("yt.png"));
	private STexturedButton quitBtn = new STexturedButton(Swinger.getResource("quit.png"));
	private STexturedButton hideBtn = new STexturedButton(Swinger.getResource("hide.png"));
	private STexturedButton ramButton = new STexturedButton(Swinger.getResource("ram.png"));
	
	private SColoredBar progressBar = new SColoredBar(new Color(255, 255, 255, 15));
	private JLabel infoLabel = new JLabel("Clique sur Jouer !", SwingConstants.CENTER);
	private RamSelector ramselector = new RamSelector(new File(Launcher.TY_DIR, "ram.txt"));
	
	public LauncherPanel() {
	    this.setLayout(null);
	    
	    usernameField.setForeground(Color.BLACK);
	    usernameField.setCaretColor(Color.BLACK);
	    usernameField.setFont(usernameField.getFont().deriveFont(20F));
	    usernameField.setOpaque(true);
	    usernameField.setBorder(null);
	    usernameField.setBounds(178, 151, 255, 42);
	    this.add(usernameField);
	    
	    passwordField.setForeground(Color.BLACK);
	    passwordField.setCaretColor(Color.BLACK);
	    passwordField.setFont(passwordField.getFont().deriveFont(20F));
	    passwordField.setOpaque(true);
	    passwordField.setBorder(null);
	    passwordField.setBounds(178, 229, 255, 42);
	    this.add(passwordField);	    	    
	    
	    progressBar.setBounds(12, 593, 951, 20);
	    this.add(progressBar);
	    
	    infoLabel.setForeground(Color.WHITE);
	    infoLabel.setFont(usernameField.getFont());
	    infoLabel.setBounds(12, 560, 951, 25);
	    this.add(infoLabel);
	    
	    quitBtn.setBounds(916, 15);
	    quitBtn.addEventListener(this);
	    this.add(quitBtn);
	    
	    hideBtn.setBounds(852, 15);
	    hideBtn.addEventListener(this);
	    this.add(hideBtn);
	    
	    connBtn.setBounds(176, 310);
	    connBtn.addEventListener(this);
	    this.add(connBtn);	    	    
	    
	    this.inscBtn.setBounds(176, 400);
        this.inscBtn.addEventListener(this);
        this.add(this.inscBtn);
        
        this.siteBtn.setBounds(652, 130);
        this.siteBtn.addEventListener(this);
        this.add(this.siteBtn);
        
        this.boutBtn.setBounds(652, 220);
        this.boutBtn.addEventListener(this);
        this.add(this.boutBtn);
        
        this.ytBtn.setBounds(652, 310);
        this.ytBtn.addEventListener(this);
        this.add(this.ytBtn);
	    
	    this.discordBtn.setBounds(652, 400);
        this.discordBtn.addEventListener(this);
        this.add(this.discordBtn);
        
        this.ramButton.setBounds(788, 15);
        this.ramButton.addEventListener(this);
        this.add(this.ramButton);
                
	}
	
	@Override
	public void onEvent (SwingerEvent e){
		Desktop desktop;	
		if(e.getSource() == connBtn)	{
			
			setFieldsEnabled(false);
			
			if(usernameField.getText().replaceAll(" ", "").length() == 0 || passwordField.getText().length() == 0) {
				JOptionPane.showMessageDialog(this,  "Erreur, veuillez entrer un pseudo et un mot de passe vaides.");
				setFieldsEnabled(true);
				return;
			}
			try {
				  String email = usernameField.getText();
				  String pass = passwordField.getText();
			      String url = "jdbc:mysql://54.37.8.211:3306/tyroliumdb";
			      String user = "remote-user";
			      String passwords = "ab";
			      String sql = "SELECT * FROM users WHERE email = '"+ email +"'";	

			      Connection conn = DriverManager.getConnection(url, user, passwords);
			      System.out.println("----------|BASE DONNER [GOOD]|----------");
			      
				      try {
				        
					      Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					      ResultSet result = state.executeQuery(sql);
					      if (result.next() == true) {
					    	  System.out.println("[EMAIL = CORRECTE]");
					    	 
					    	  String spass = result.getString("password");
					    	  if(spass.equals(pass)){ 
					    		  setFieldsEnabled(true);
					    		  System.out.println("[MDP = CORRECTE]");
					    		  System.out.println(" ");
					    		  System.out.println("Recuperation pseudo = "+ result.getString("pseudo"));
					    		  System.out.println(" ");
					    		  
					    		  Thread t = new Thread() {
										@Override
										public void run() {
											try {
												Launcher.auth(result.getString("pseudo"));
											} catch (AuthenticationException | SQLException e) {
												System.out.println("----------------------Erreur Auth-----------------------");
											}
											
											try {
												Launcher.update();
											} catch (Exception e) {
												Launcher.interruptThread();
												System.out.println("-----------------------Erreur Uptade---------------------");
											}
											
											try {
												Launcher.launch();
											} catch (LaunchException e) {
												System.out.println("--------------------Erreur Lancement-----------------------");
											}
										}
									};
									t.start();
						    	  
					    	  }	else {
					    		  System.out.println("[MDP = INCORRECTE]");
					    		  JOptionPane.showMessageDialog(this,  "Votre Mots de passe est incorrecte");
									setFieldsEnabled(true);
									return;			    	  
					    	  }
					      } else {
					    	  System.out.println("[EMAIL = INCORRECTE]");
					    	  JOptionPane.showMessageDialog(this,  "Votre email "+ email +" n'est pas dans notre base de donn�");
								setFieldsEnabled(true);
								return;
					      }
				      	  System.out.println("----------|REQUETE [GOOD]|----------");
				      } catch (Exception e2) {
				    	  e2.printStackTrace();
				    	  System.out.println("----------|REQUETE [ERREUR]|----------");
				    	  JOptionPane.showMessageDialog(this,  "Une erreur de requete entre votre ordinateur et notre base de donn� est survenu");
							setFieldsEnabled(true);
							return;
				      }
			      
			    	} catch (Exception e3) {
				      e3.printStackTrace();
				      System.out.println("----------|BASE DONNER [ERREUR]|----------");
				      JOptionPane.showMessageDialog(this,  "Une erreur entre votre ordinateur et notre base de donn� est survenu");
						setFieldsEnabled(true);
						return;
				    }	  
		}
		else if (e.getSource() == this.inscBtn) {
            desktop = Desktop.getDesktop();

            try {
                desktop.browse((new URL("http://tyrolium.fr/ServerMC/Profil")).toURI());
            } catch (MalformedURLException var10) {
                var10.printStackTrace();
            } catch (IOException var11) {
                var11.printStackTrace();
            } catch (URISyntaxException var12) {
                var12.printStackTrace();
            }
        }
		else if (e.getSource() == this.siteBtn) {
            desktop = Desktop.getDesktop();

            try {
                desktop.browse((new URL("http://tyrolium.fr/ServerMC/")).toURI());
            } catch (MalformedURLException var10) {
                var10.printStackTrace();
            } catch (IOException var11) {
                var11.printStackTrace();
            } catch (URISyntaxException var12) {
                var12.printStackTrace();
            }
        }
		else if (e.getSource() == this.boutBtn) {
            desktop = Desktop.getDesktop();

            try {
                desktop.browse((new URL("http://tyrolium.tebex.io")).toURI());
            } catch (MalformedURLException var10) {
                var10.printStackTrace();
            } catch (IOException var11) {
                var11.printStackTrace();
            } catch (URISyntaxException var12) {
                var12.printStackTrace();
            }
        }
		else if (e.getSource() == this.discordBtn) {
            desktop = Desktop.getDesktop();

            try {
                desktop.browse((new URL("http://discord.gg/WPu4KMf")).toURI());
            } catch (MalformedURLException var10) {
                var10.printStackTrace();
            } catch (IOException var11) {
                var11.printStackTrace();
            } catch (URISyntaxException var12) {
                var12.printStackTrace();
            }
        }
		else if (e.getSource() == this.ytBtn) {
            desktop = Desktop.getDesktop();

            try {
                desktop.browse((new URL("https://www.youtube.com/channel/UCZTbdKAcFw2xrLFjO9WpYYw/")).toURI());
            } catch (MalformedURLException var10) {
                var10.printStackTrace();
            } catch (IOException var11) {
                var11.printStackTrace();
            } catch (URISyntaxException var12) {
                var12.printStackTrace();
            }
        }
		else if(e.getSource() == quitBtn)
			System.exit(0);
		else if(e.getSource() == hideBtn)
			LauncherFrame.getInstance().setState(JFrame.ICONIFIED);
		else if(e.getSource() == ramButton)
			ramselector.display();
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		drawFullsizedImage(graphics, this, background);
	}
	
	private void setFieldsEnabled(boolean enabled) {
		usernameField.setEnabled(enabled);
		passwordField.setEnabled(enabled);
		connBtn.setEnabled(enabled);
	}
	
	public SColoredBar getProgressBar()	{
		return progressBar;
		
	}
	
	public void setInfoText(String text) {
		infoLabel.setText(text);
	}
	
	public RamSelector getRamSelector() {
		return ramselector;
	}
	

}