package com.comtrade.view;

import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.comtrade.constants.DbConstraints;
import com.comtrade.constants.HttpConstraints;
import com.comtrade.controller.FrontController;
import com.comtrade.database.entities.Restaurant;
import com.comtrade.database.entities.User;
import com.comtrade.transfer.TransferClass;
import com.comtrade.view.admin.WarehousesPanel;
import com.comtrade.view.common.ArcticlesPanel;
import com.comtrade.view.common.BillPanel;
import com.comtrade.view.common.RecipesPanel;
import com.comtrade.view.common.RestaurantTablePanel;
import com.comtrade.view.common.UsersPanel;

import javax.swing.JComboBox;

public class RestaurantManager extends JFrame {
	
	private JPanel contentPane;
	private JTextField tfLoginUsername;
	private JTextField tfLoginUsersurname;
	private JPasswordField pfLoginPassword;
	private JTextField tfRegistrationUserName;
	private JTextField tfRegistrationSurname;
	private JPasswordField pfPassword;
	private JPasswordField pfConfirmPassword;
	private User currentUser;
	private String pathToImage;
	private Restaurant restaurant;
	private List<Restaurant> restaurants;
	private String[] restaurantNames;
	private UsersPanel usersAdminPanel;
	private WarehousesPanel warehousesPanel;
	private ArcticlesPanel arcticlesPanel;
	private RecipesPanel recipesPanel;
	private RestaurantTablePanel restaurantTablePanel;
	private BillPanel billPanel;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RestaurantManager frame = new RestaurantManager();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RestaurantManager() {
		
		currentUser = new User();
		restaurant = new Restaurant();
		
		pathToImage = System.getProperty("user.dir");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 885, 599);
		contentPane = new JPanel();
		setResizable(false);
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		JPanel loginPanel = new JPanel();
		loginPanel.setBackground(Color.WHITE);
		contentPane.add(loginPanel, "loginPanel");
		loginPanel.setLayout(null);
		
		JPanel adminPanel = new JPanel();
		adminPanel.setBackground(Color.WHITE);
		contentPane.add(adminPanel, "adminPanel");
		adminPanel.setLayout(null);
		
		JPanel uperAdminPanel = new JPanel();
		uperAdminPanel.setLayout(null);
		uperAdminPanel.setBackground(SystemColor.activeCaption);
		uperAdminPanel.setBounds(0, 0, 869, 70);
		adminPanel.add(uperAdminPanel);
		
		JPanel lowerAdminPanel = new JPanel();
		lowerAdminPanel.setBackground(Color.WHITE);
		lowerAdminPanel.setBounds(0, 69, 869, 492);
		adminPanel.add(lowerAdminPanel);
		lowerAdminPanel.setLayout(new CardLayout(0, 0));
		
		JPanel userPanel = new JPanel();
		userPanel.setBackground(Color.WHITE);
		contentPane.add(userPanel, "userPanel");
		userPanel.setLayout(null);
		
		JPanel uperUserPanel = new JPanel();
		uperUserPanel.setLayout(null);
		uperUserPanel.setBackground(SystemColor.activeCaption);
		uperUserPanel.setBounds(0, 0, 869, 70);
		userPanel.add(uperUserPanel);
		
		
		JPanel lowerUserPanel = new JPanel();
		lowerUserPanel.setBackground(Color.WHITE);
		lowerUserPanel.setBounds(0, 69, 869, 492);
		userPanel.add(lowerUserPanel);
		lowerUserPanel.setLayout(new CardLayout(0, 0));
		
		JPanel uperLoginPanel = new JPanel();
		uperLoginPanel.setBackground(SystemColor.activeCaption);
		uperLoginPanel.setBounds(0, 0, 869, 70);
		loginPanel.add(uperLoginPanel);
		uperLoginPanel.setLayout(null);
		
		tfLoginUsername = new JTextField();
		tfLoginUsername.setBounds(327, 19, 95, 20);
		uperLoginPanel.add(tfLoginUsername);
		
		JLabel label = new JLabel("User name");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.WHITE);
		label.setBounds(327, 39, 95, 20);
		uperLoginPanel.add(label);
		
		tfLoginUsersurname = new JTextField();
		tfLoginUsersurname.setBounds(457, 19, 95, 20);
		uperLoginPanel.add(tfLoginUsersurname);
		
		JLabel label_1 = new JLabel("User surname");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setForeground(Color.WHITE);
		label_1.setBounds(457, 39, 95, 20);
		uperLoginPanel.add(label_1);
		
		pfLoginPassword = new JPasswordField();
		pfLoginPassword.setBounds(587, 19, 95, 20);
		uperLoginPanel.add(pfLoginPassword);
		
		JLabel label_2 = new JLabel("User password");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setForeground(Color.WHITE);
		label_2.setBounds(587, 39, 95, 20);
		uperLoginPanel.add(label_2);
		
		JLabel lblWrongCredentials = new JLabel("");
		lblWrongCredentials.setHorizontalAlignment(SwingConstants.CENTER);
		lblWrongCredentials.setVerticalAlignment(SwingConstants.TOP);
		lblWrongCredentials.setForeground(Color.RED);
		lblWrongCredentials.setBounds(587, 88, 225, 20);
		loginPanel.add(lblWrongCredentials);
		
		Button loginButton = new Button("Log In");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String userName = tfLoginUsername.getText();
				String userSurname = tfLoginUsersurname.getText();
				String password = String.copyValueOf(pfLoginPassword.getPassword());
				
				User loginUser = new User();
				loginUser.setUserName(userName);
				loginUser.setUserSurname(userSurname);
				loginUser.setPassword(password);
				try {
					TransferClass transfer = FrontController.getInstance().execute(
							TransferClass.create(loginUser, HttpConstraints.GET_ONE, DbConstraints.USER));
					User user = (User) transfer.getResponse();
					if(user != null) {
						
						currentUser = user;
						
						if(user.isAdmin()) {
							contentPane.add(adminPanel, "adminPanel");
							CardLayout card = (CardLayout)contentPane.getLayout();
							card.show(contentPane, "adminPanel");
							
							usersAdminPanel = new UsersPanel(currentUser, pathToImage, contentPane, lowerAdminPanel, warehousesPanel, arcticlesPanel, recipesPanel, restaurantTablePanel, billPanel, adminPanel);
							lowerAdminPanel.add(usersAdminPanel, "usersAdminPanel");
							
							CardLayout card1 = (CardLayout)lowerAdminPanel.getLayout();
							card1.show(lowerAdminPanel, "usersAdminPanel");
						}else {
							contentPane.add(userPanel, "userPanel");
							CardLayout card = (CardLayout)contentPane.getLayout();
							card.show(contentPane, "userPanel");
							
							usersAdminPanel = new UsersPanel(currentUser, pathToImage, contentPane, lowerUserPanel, warehousesPanel, arcticlesPanel, recipesPanel, restaurantTablePanel, billPanel, userPanel);
							lowerUserPanel.add(usersAdminPanel, "usersAdminPanel");
							
							CardLayout card1 = (CardLayout)lowerUserPanel.getLayout();
							card1.show(lowerUserPanel, "usersAdminPanel");
						}
						lblWrongCredentials.setText("");
						tfLoginUsername.setText("");
						tfLoginUsersurname.setText("");
						pfLoginPassword.setText("");
						loginUser.setUserName(null);
						loginUser.setUserSurname(null);
						loginUser.setPassword(null);
					}else {
						lblWrongCredentials.setText("Wrong credentials! Try again.");
					}
					
				}catch (ClassNotFoundException e) {
					e.printStackTrace();
				}catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		loginButton.setForeground(Color.WHITE);
		loginButton.setBackground(SystemColor.activeCaption);
		loginButton.setBounds(716, 29, 95, 20);
		uperLoginPanel.add(loginButton);
		
		JLabel label_3 = new JLabel("Users");
		label_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Component[] components = lowerUserPanel.getComponents();

				for(int i = 0; i < components.length; i++) {
					lowerUserPanel.remove(components[i]);
				}
				
				usersAdminPanel = new UsersPanel(currentUser, pathToImage, contentPane, lowerUserPanel, warehousesPanel, arcticlesPanel, recipesPanel, restaurantTablePanel, billPanel, userPanel);
				lowerUserPanel.add(usersAdminPanel, "usersAdminPanel");
				
				CardLayout card = (CardLayout)lowerUserPanel.getLayout();
				card.show(lowerUserPanel, "usersAdminPanel");
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				label_3.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				label_3.setForeground(Color.WHITE);
			}
		});
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setForeground(Color.WHITE);
		label_3.setBounds(771, 27, 75, 14);
		uperUserPanel.add(label_3);
		
		JLabel label_6 = new JLabel("Arcticles & Menus");
		label_6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Component[] components = lowerUserPanel.getComponents();

				for(int i = 0; i < components.length; i++) {
					lowerUserPanel.remove(components[i]);
				}
				
				arcticlesPanel = new ArcticlesPanel(currentUser, pathToImage, lowerUserPanel);
				lowerUserPanel.add(arcticlesPanel, "arcticlesPanel");
				
				CardLayout card = (CardLayout)lowerUserPanel.getLayout();
				card.show(lowerUserPanel, "arcticlesPanel");
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				label_6.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				label_6.setForeground(Color.WHITE);
			}
		});
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setForeground(Color.WHITE);
		label_6.setBounds(651, 27, 110, 14);
		uperUserPanel.add(label_6);
		
		JLabel label_7 = new JLabel("Recipes");
		label_7.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				Component[] components = lowerUserPanel.getComponents();

				for(int i = 0; i < components.length; i++) {
					lowerUserPanel.remove(components[i]);
				}
				
				recipesPanel = new RecipesPanel(currentUser, pathToImage, lowerUserPanel);
				lowerUserPanel.add(recipesPanel, "recipesPanel");
				
				CardLayout card = (CardLayout)lowerUserPanel.getLayout();
				card.show(lowerUserPanel, "recipesPanel");
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				label_7.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				label_7.setForeground(Color.WHITE);
			}
		});
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		label_7.setForeground(Color.WHITE);
		label_7.setBounds(564, 27, 75, 14);
		uperUserPanel.add(label_7);
		
		JLabel label_10 = new JLabel("Tables");
		label_10.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Component[] components = lowerUserPanel.getComponents();

				for(int i = 0; i < components.length; i++) {
					lowerUserPanel.remove(components[i]);
				}
				
				restaurantTablePanel = new RestaurantTablePanel(currentUser, pathToImage, lowerUserPanel, billPanel);
				lowerUserPanel.add(restaurantTablePanel, "restaurantTablePanel");
				
				CardLayout card = (CardLayout)lowerUserPanel.getLayout();
				card.show(lowerUserPanel, "restaurantTablePanel");
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				label_10.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				label_10.setForeground(Color.WHITE);
			}
		});
		label_10.setHorizontalAlignment(SwingConstants.CENTER);
		label_10.setForeground(Color.WHITE);
		label_10.setBounds(479, 27, 75, 14);
		uperUserPanel.add(label_10);
		
		JLabel lblNewLabel = new JLabel("Users");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Component[] components = lowerAdminPanel.getComponents();

				for(int i = 0; i < components.length; i++) {
					lowerAdminPanel.remove(components[i]);
				}
				
				usersAdminPanel = new UsersPanel(currentUser, pathToImage, contentPane, lowerAdminPanel, warehousesPanel, arcticlesPanel, recipesPanel, restaurantTablePanel, billPanel, adminPanel);
				lowerAdminPanel.add(usersAdminPanel, "usersAdminPanel");
				
				CardLayout card = (CardLayout)lowerAdminPanel.getLayout();
				card.show(lowerAdminPanel, "usersAdminPanel");
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblNewLabel.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblNewLabel.setForeground(Color.WHITE);
			}
		});
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(780, 27, 55, 14);
		uperAdminPanel.add(lblNewLabel);
		
		JLabel lblWarehouse = new JLabel("Warehouse");
		lblWarehouse.setHorizontalAlignment(SwingConstants.CENTER);
		lblWarehouse.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Component[] components = lowerAdminPanel.getComponents();

				for(int i = 0; i < components.length; i++) {
					lowerAdminPanel.remove(components[i]);
				}
				
				warehousesPanel = new WarehousesPanel(currentUser, pathToImage, lowerAdminPanel);
				lowerAdminPanel.add(warehousesPanel, "warehousesPanel");
				
				CardLayout card = (CardLayout)lowerAdminPanel.getLayout();
				card.show(lowerAdminPanel, "warehousesPanel");
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblWarehouse.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblWarehouse.setForeground(Color.WHITE);
			}
		});
		lblWarehouse.setForeground(Color.WHITE);
		lblWarehouse.setBounds(695, 27, 75, 14);
		uperAdminPanel.add(lblWarehouse);
		
		JLabel lblArcticles = new JLabel("Arcticles & Menus");
		lblArcticles.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				Component[] components = lowerAdminPanel.getComponents();

				for(int i = 0; i < components.length; i++) {
					lowerAdminPanel.remove(components[i]);
				}
				
				arcticlesPanel = new ArcticlesPanel(currentUser, pathToImage, lowerAdminPanel);
				lowerAdminPanel.add(arcticlesPanel, "arcticlesPanel");
				
				CardLayout card = (CardLayout)lowerAdminPanel.getLayout();
				card.show(lowerAdminPanel, "arcticlesPanel");
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblArcticles.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblArcticles.setForeground(Color.WHITE);
			}
		});
		lblArcticles.setHorizontalAlignment(SwingConstants.CENTER);
		lblArcticles.setForeground(Color.WHITE);
		lblArcticles.setBounds(575, 27, 110, 14);
		uperAdminPanel.add(lblArcticles);
		
		JLabel lblRecipes = new JLabel("Recipes");
		lblRecipes.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				Component[] components = lowerAdminPanel.getComponents();

				for(int i = 0; i < components.length; i++) {
					lowerAdminPanel.remove(components[i]);
				}
				
				recipesPanel = new RecipesPanel(currentUser, pathToImage, lowerAdminPanel);
				lowerAdminPanel.add(recipesPanel, "recipesPanel");
				
				CardLayout card = (CardLayout)lowerAdminPanel.getLayout();
				card.show(lowerAdminPanel, "recipesPanel");
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblRecipes.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblRecipes.setForeground(Color.WHITE);
			}
		});
		lblRecipes.setHorizontalAlignment(SwingConstants.CENTER);
		lblRecipes.setForeground(Color.WHITE);
		lblRecipes.setBounds(498, 27, 67, 14);
		uperAdminPanel.add(lblRecipes);
		
		JLabel lblTables = new JLabel("Tables");
		lblTables.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Component[] components = lowerAdminPanel.getComponents();

				for(int i = 0; i < components.length; i++) {
					lowerAdminPanel.remove(components[i]);
				}
				
				restaurantTablePanel = new RestaurantTablePanel(currentUser, pathToImage, lowerAdminPanel, billPanel);
				lowerAdminPanel.add(restaurantTablePanel, "restaurantTablePanel");
				
				CardLayout card = (CardLayout)lowerAdminPanel.getLayout();
				card.show(lowerAdminPanel, "restaurantTablePanel");
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblTables.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblTables.setForeground(Color.WHITE);
			}
		});
		lblTables.setHorizontalAlignment(SwingConstants.CENTER);
		lblTables.setForeground(Color.WHITE);
		lblTables.setBounds(427, 27, 61, 14);
		uperAdminPanel.add(lblTables);
		
		JLabel lblBills = new JLabel("Bills");
		lblBills.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				/*Component[] components = lowerAdminPanel.getComponents();

				for(int i = 0; i < components.length; i++) {
					if(components[i] != billPanel)
						lowerAdminPanel.remove(components[i]);
				}*/
				if(billPanel != null) {
					lowerAdminPanel.remove(billPanel);
	            }
				billPanel = new BillPanel(currentUser, pathToImage, lowerAdminPanel, null);
				lowerAdminPanel.add(billPanel, "billPanel");
				
				CardLayout card = (CardLayout)lowerAdminPanel.getLayout();
				card.show(lowerAdminPanel, "billPanel");
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblBills.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblBills.setForeground(Color.WHITE);
			}
		});
		lblBills.setHorizontalAlignment(SwingConstants.CENTER);
		lblBills.setForeground(Color.WHITE);
		lblBills.setBounds(362, 27, 55, 14);
		uperAdminPanel.add(lblBills);
		
		JLabel label_4 = new JLabel("User name:");
		label_4.setBounds(327, 119, 95, 20);
		loginPanel.add(label_4);
		
		tfRegistrationUserName = new JTextField();
		tfRegistrationUserName.setBounds(457, 119, 95, 20);
		loginPanel.add(tfRegistrationUserName);
		
		JLabel label_5 = new JLabel("User surname:");
		label_5.setBounds(587, 119, 95, 20);
		loginPanel.add(label_5);
		
		tfRegistrationSurname = new JTextField();
		tfRegistrationSurname.setBounds(717, 119, 95, 20);
		loginPanel.add(tfRegistrationSurname);
		
		JLabel lblUserSurameMessage = new JLabel("");
		lblUserSurameMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserSurameMessage.setVerticalAlignment(SwingConstants.TOP);
		lblUserSurameMessage.setForeground(Color.RED);
		lblUserSurameMessage.setBounds(587, 149, 225, 20);
		loginPanel.add(lblUserSurameMessage);
		
		JLabel lblUserNameMessage = new JLabel("");
		lblUserNameMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserNameMessage.setVerticalAlignment(SwingConstants.TOP);
		lblUserNameMessage.setForeground(Color.RED);
		lblUserNameMessage.setBounds(327, 149, 225, 20);
		loginPanel.add(lblUserNameMessage);
		
		JLabel label_8 = new JLabel("Password:");
		label_8.setBounds(327, 179, 95, 20);
		loginPanel.add(label_8);
		
		pfPassword = new JPasswordField();
		pfPassword.setBounds(457, 179, 95, 20);
		loginPanel.add(pfPassword);
		
		JLabel label_9 = new JLabel("Confirm password:");
		label_9.setBounds(587, 179, 111, 20);
		loginPanel.add(label_9);
		
		JLabel lblConfirmPasswordMessage = new JLabel("");
		lblConfirmPasswordMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfirmPasswordMessage.setVerticalAlignment(SwingConstants.TOP);
		lblConfirmPasswordMessage.setForeground(Color.RED);
		lblConfirmPasswordMessage.setBounds(587, 209, 225, 20);
		loginPanel.add(lblConfirmPasswordMessage);
		
		JLabel lblPasswordMessage = new JLabel("");
		lblPasswordMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblPasswordMessage.setVerticalAlignment(SwingConstants.TOP);
		lblPasswordMessage.setForeground(Color.RED);
		lblPasswordMessage.setBounds(327, 209, 225, 20);
		loginPanel.add(lblPasswordMessage);
		
		pfConfirmPassword = new JPasswordField();
		pfConfirmPassword.setBounds(717, 179, 95, 20);
		loginPanel.add(pfConfirmPassword);
		
		try {
			TransferClass transfer = FrontController.getInstance().execute(
					TransferClass.create(restaurant, HttpConstraints.GET, DbConstraints.RESTAURANT));
			restaurants = (List<Restaurant>) transfer.getResponse();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		restaurantNames = new String[restaurants.size()+1];
		restaurantNames[0] = "";
		for(int i = 0; i < restaurants.size(); i++) {
			restaurantNames[i+1] = restaurants.get(i).getRestaurantName();
		}
		
		JComboBox comboBox = new JComboBox(restaurantNames);
		comboBox.setBounds(457, 239, 95, 20);
		loginPanel.add(comboBox);
		
		JLabel lblRestaurant = new JLabel("Restaurant:");
		lblRestaurant.setBounds(327, 239, 95, 20);
		loginPanel.add(lblRestaurant);
		
		JLabel lblRestaurantMessage = new JLabel("");
		lblRestaurantMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblRestaurantMessage.setVerticalAlignment(SwingConstants.TOP);
		lblRestaurantMessage.setForeground(Color.RED);
		lblRestaurantMessage.setBounds(327, 269, 225, 20);
		loginPanel.add(lblRestaurantMessage);
		
		Button btnRegistration = new Button("Registration");
		btnRegistration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String userName = tfRegistrationUserName.getText();
				String userSurname = tfRegistrationSurname.getText();
				String password = String.copyValueOf(pfPassword.getPassword());
				String confirmPassword = String.copyValueOf(pfConfirmPassword.getPassword());
				
				boolean userNameConfirm, userSurnameConfirm, passwordConfirm, confirmPasswordConfirm, confirmRestaurant;
				
				if(userName.length() == 0) {
					lblUserNameMessage.setText("User name is required field!");
					userNameConfirm = false;
				}else {
					lblUserNameMessage.setText("");
					userNameConfirm = true;
				}
				
				if(userSurname.length() == 0) {
					lblUserSurameMessage.setText("User surname is required field!");
					userSurnameConfirm = false;
				}else {
					lblUserSurameMessage.setText("");
					userSurnameConfirm = true;
				}
				
				if(password.length() < 6 || password == null) {
					lblPasswordMessage.setText("Password length must be 6 or higher!");
					passwordConfirm = false;
				}else {
					lblPasswordMessage.setText("");
					passwordConfirm = true;
				}
				
				if(!password.equals(confirmPassword)) {
					lblConfirmPasswordMessage.setText("Passwords do not match!");
					confirmPasswordConfirm = false;
				}else if(confirmPassword.isEmpty()) {
					lblConfirmPasswordMessage.setText("Confirm password is required field!");
					confirmPasswordConfirm = false;
				}else {
					lblConfirmPasswordMessage.setText("");
					confirmPasswordConfirm = true;
				}
				
				if(comboBox.getSelectedItem().equals("")) {
					lblRestaurantMessage.setText("Restaurant is required!");
					confirmRestaurant = false;
				}else {
					lblRestaurantMessage.setText("");
					confirmRestaurant = true;
				}
				
				if(userNameConfirm && userSurnameConfirm && passwordConfirm && confirmPasswordConfirm && confirmRestaurant) {
					
					User newUser = new User();
					newUser.setAdmin(false);
					newUser.setPassword(confirmPassword);
					newUser.setUserName(userName);
					newUser.setUserSurname(userSurname);
					newUser.setRestaurant(restaurants.get(comboBox.getSelectedIndex()-1));
					try {
						TransferClass transfer = FrontController.getInstance().execute(
								TransferClass.create(newUser, HttpConstraints.POST, DbConstraints.USER));
						JOptionPane.showMessageDialog(null, transfer.getMessage());
					}catch (ClassNotFoundException e) {
						e.printStackTrace();
					}catch (IOException e) {
						e.printStackTrace();
					}
					newUser = null;
					tfRegistrationUserName.setText("");
					tfRegistrationSurname.setText("");
					pfPassword.setText("");
					pfConfirmPassword.setText("");
					comboBox.setSelectedItem("");
				}
				
			}
		});
		btnRegistration.setForeground(Color.WHITE);
		btnRegistration.setBackground(SystemColor.activeCaption);
		btnRegistration.setBounds(717, 239, 95, 20);
		loginPanel.add(btnRegistration);
		
	}
}
