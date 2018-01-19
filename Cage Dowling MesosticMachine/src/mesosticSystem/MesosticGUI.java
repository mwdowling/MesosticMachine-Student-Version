package mesosticSystem;

import java.awt.EventQueue;



import javax.swing.JOptionPane;
import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Font;
import javax.swing.JTextArea;

/**
 * @author Martin Dowling
 * *       
 * 		   This class was developed using tutorials by the following YouTube
 *         authors: Mahendra Gossai:
 *         www.youtube.com/channel/UCjP-Zudf3-h6tqreONSP5GQ Stephen Andy Wicks:
 *         www.youtube.com/watch?v=XXkq73u9Uqg Cyan Code:
 *         www.youtube.com/watch?v=8GX_jDthfbM&t=2s Vincent Aliquo:
 *         www.youtube.com/watch?v=9VrtranTJnc&index=8&list=PLDFUMXKzFYdDH7y6tfzSsp9xn-T7ROQTd&t=7s
 * 
 * This class is a WindowBuilder application using Absolute Layout and
 * CardLayout 
 *         
 * Within a JFrame lies a navigable hierarchy of JPanel "panels":
 * (1) A Welcome panel with links to setup, run, and view cards
 * (2) A Setup panel for choosing the project directory, mesostic row, and
 *         target chapter 
 * (3) Run cards to call methods in the system's core functional objects 
 * (4) A view card to view files produced by the run functions
 * 
 *         This class was developed using tutorials by the following YouTube
 *         authors: Mahendra Gossai:
 *         www.youtube.com/channel/UCjP-Zudf3-h6tqreONSP5GQ Stephen Andy Wicks:
 *         www.youtube.com/watch?v=XXkq73u9Uqg Cyan Code:
 *         www.youtube.com/watch?v=8GX_jDthfbM&t=2s Vincent Aliquo:
 *         www.youtube.com/watch?v=9VrtranTJnc&index=8&list=PLDFUMXKzFYdDH7y6tfzSsp9xn-T7ROQTd&t=7s
 */

public class MesosticGUI {

	// the frame within which all panels are placed
	private JFrame frame;

	// the welcome panel
	private JPanel panelWelcome;

	// the setup panel and its sub-panels 
	private JPanel panelSetup;
	private JPanel panelSetUpMesosticRow;
	private JPanel panelSetupInfo;
	
	//objects to set the directory and target chapter
	private JFileChooser fileChooserDirectory;
	private String directory = "C:\\Users\\";
	private JFileChooser fileChooserChapter;
	private String chapterFileAddress;
	
	//objects to set the mesostic row in its sub-panel
	private String mesosticRowFileAddress = directory + "\\MesoticRow.txt";
	private JTextField mesosticRowtextField;
	private String mesosticRow;
	
	//the run and finish panels and sub-panel
	private JPanel panelRun;
	private JPanel panelRunMesostics;
	private JPanel panelFinish;
	
	//reference strings for files created in the run and finish panels
	
	//mesostics output files:
	private String allMesosticsFileAddress = directory + "\\AllMesostics.txt";
	private String allMesosticsWithAdjacentFileAddress = directory + "\\AllMesosticsPlusAdjacent.txt";
	private String allMesosticsFinalCenteredFileAddress = directory + "\\AllMesosticsCentred.txt";
	private String allMesosticsCollatedFileAddress = directory + "\\AllMesosticsCollated.txt";
	
	//sounds files:
	private String soundFileAddress = directory + "\\Sounds and Places\\OEDSounds Final Tabs.txt";
	private String chapterSoundsFileAddress = directory + "\\Sounds and Places\\ChapterSounds.txt";
	private String chapterSoundsSentencesFileAddress = directory + "Sounds and Places\\ChapterSoundsSentences.txt";
	
	//places files :
	private String notPlaceFileAddress = directory + "\\Sounds and Places\\Not A Place.txt";
	private String chapterPlacesFileAddress = directory + "\\Sounds and Places\\ChapterPlaces.txt";
	private String chapterPlacesSentencesFileAddress = directory + "\\Sounds and Places\\ChapterPlacesSentences.txt";
	
	// the view panel
	private JPanel panelView;
	//private JFileChooser fileChooserOpen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MesosticGUI window = new MesosticGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Application constructor with its huge initialize method.
	 */
	public MesosticGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame. 
	 */
	private void initialize() {

		// intialize the frame
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 600);// make it bigger!
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));

		// initialize the panels
		panelWelcome = new JPanel();
		panelSetup = new JPanel();
		panelSetUpMesosticRow = new JPanel();// sub-panel of Setup
		panelSetupInfo = new JPanel();
		panelRun = new JPanel();// run the main functions of the system
		panelRunMesostics = new JPanel();
		panelFinish = new JPanel();
		panelView = new JPanel();
		
		//add the panels to the frame
		frame.getContentPane().add(panelWelcome, "name_66837443980463");
		frame.getContentPane().add(panelSetup, "name_66843595235424");
		frame.getContentPane().add(panelSetUpMesosticRow, "name_2855160376467");
		frame.getContentPane().add(panelSetupInfo, "name_9389352877922");
		frame.getContentPane().add(panelRun, "name_67343460305734");
		frame.getContentPane().add(panelRunMesostics, "name_15678440809264");
		frame.getContentPane().add(panelFinish, "name_6222578596776");
		frame.getContentPane().add(panelView, "name_67348905703949");
		
		/*
		 * START OF WELCOME PANEL CODE 
		 * Welcome panel is the GUI home 
		 * All "done" buttons return here
		 */

		//welcome panel label and buttons
		panelWelcome.setLayout(null);
		panelWelcome.setVisible(true);// default state has this panel visible
		JLabel lblWelcome = new JLabel("Welcome to the MesosticMachine");
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblWelcome.setBounds(133, 12, 638, 64);
		panelWelcome.add(lblWelcome);

		// the Setup button on Welcome panel makes the Setup panel visible
		JButton btnWelcomeSetup = new JButton("Setup");
		btnWelcomeSetup.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnWelcomeSetup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelSetup.setVisible(true);
				panelWelcome.setVisible(false);
			}
		});
		btnWelcomeSetup.setBounds(365, 110, 160, 64);
		panelWelcome.add(btnWelcomeSetup);

		// the Run button on Welcome panel makes the Run panel visible
		JButton btnWelcomeRun = new JButton("Run");
		btnWelcomeRun.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnWelcomeRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelRun.setVisible(true);
				panelWelcome.setVisible(false);
			}
		});
		btnWelcomeRun.setBounds(365, 205, 160, 64);
		panelWelcome.add(btnWelcomeRun);

		// The View button on Welcome panel makes the View panel visible
		JButton btnWelcomeView = new JButton("View");
		btnWelcomeView.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnWelcomeView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelView.setVisible(true);
				panelWelcome.setVisible(false);
			}
		});
		btnWelcomeView.setBounds(365, 392, 161, 64);
		panelWelcome.add(btnWelcomeView);
		
		// The Finish button on Welcome panel makes the Finish panel visible
		JButton btnWelcomeFinish = new JButton("Finish");
		btnWelcomeFinish.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnWelcomeFinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelFinish.setVisible(true);
				panelWelcome.setVisible(false);
			}
		});
		btnWelcomeFinish.setBounds(365, 297, 161, 64);
		panelWelcome.add(btnWelcomeFinish);
		

		/*
		 * END OF WELCOME PANELCODE; START OF SETUP PANEL CODE 
		 * 
		 * The Setup panel assigns the project file directory, the mesostic row, the target
		 * chapter file, and the output files. 
		 * 
		 * These assignments are required inputs 
		 * into the Run and Finish panel functions
		 */

		//the Setup panel and its label
		panelSetup.setLayout(null);
		panelSetup.setVisible(false);// default = not visible
		JLabel lblSetup = new JLabel("Setup Your Project");
		lblSetup.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblSetup.setBounds(267, 21, 353, 56);
		panelSetup.add(lblSetup);

		/* 
		 * the Set Project Folder button does a lot of work:
		 * It opens up a "directories only" JFileChooser.
		 * Once the directory has been chosen,
		 * it assigns all the output files created by the system 
		 * to the directory
		 * and it appends the directory to the SetupInfo panel text area
		 */
		
		// the SetupInfo sub-panel and its label
		panelSetupInfo.setLayout(null);
		JLabel lblSetupInfo = new JLabel("Your Setup Info");
		lblSetupInfo.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblSetupInfo.setBounds(278, 51, 291, 51);
		panelSetupInfo.add(lblSetupInfo);

		// add a text area to the SetupInfo panel
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textArea.setRows(3);
		textArea.setBounds(51, 160, 787, 234);
		panelSetupInfo.add(textArea);
		textArea.setEditable(false);

		//the SetProjectFolder button opens a filechooser
		JButton btnSetProjectFolder = new JButton("Set Project Folder");
		btnSetProjectFolder.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnSetProjectFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				panelSetup.setVisible(false);
				fileChooserDirectory = new JFileChooser();
				fileChooserDirectory.setBounds(0, 0, 438, 241);
				fileChooserDirectory.setPreferredSize(new java.awt.Dimension(800,600));
				fileChooserDirectory.setCurrentDirectory(new File(directory));
				fileChooserDirectory.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooserDirectory.setDialogTitle("Set the Directory");

				if (fileChooserDirectory.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

					// choose the project directory
					directory = fileChooserDirectory.getSelectedFile().getAbsolutePath();
					//put setup info into the text area of the SetupInfo panel
					textArea.append("Project folder: " + directory + "\n\n");
					
					//assign the file paths for mesostic row and output 
					mesosticRowFileAddress = directory + "\\MesoticRow.txt";
					allMesosticsFileAddress = directory + "\\AllMesostics.txt";
					allMesosticsWithAdjacentFileAddress = directory + "\\AllMesosticsPlusAdjacent.txt";
					allMesosticsFinalCenteredFileAddress = directory + "\\AllMesosticsFinalCentered.txt";
					allMesosticsCollatedFileAddress = directory + "\\AllMesosticsCollated.txt";
					
					
					//assign the file paths for the sounds and places
					soundFileAddress = directory + "\\Sounds and Places\\OEDSounds Final.txt";
					chapterSoundsFileAddress = directory + "\\Sounds and Places\\ChapterSounds.txt";
					chapterSoundsSentencesFileAddress = directory + "\\Sounds and Places\\ChapterSoundsSentences.txt";
					notPlaceFileAddress = directory + "\\Sounds and Places\\Not A Place.txt";
					chapterPlacesFileAddress = directory + "\\Sounds and Places\\ChapterPlaces.txt";
					chapterPlacesSentencesFileAddress = directory + "\\Sounds and Places\\ChapterPlacesSentences.txt";
					
					panelSetup.setVisible(true);
					panelWelcome.setVisible(false);

				} else if (fileChooserDirectory.showOpenDialog(null) == JFileChooser.CANCEL_OPTION) {
					fileChooserDirectory.setVisible(false);
					panelSetup.setVisible(false);

				}
			}
		});
		btnSetProjectFolder.setBounds(239, 98, 409, 74);
		panelSetup.add(btnSetProjectFolder);

		/* 
		 *  the Set Chapter File button opens up a "txt file only" JFileChooser
		 * 	which opens the directory set by fileChooserDirectory above
		 */
		JButton btnSetChapterFile = new JButton("Set Chapter File");
		btnSetChapterFile.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnSetChapterFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				panelSetup.setVisible(false);
				fileChooserChapter = new JFileChooser();
				fileChooserChapter.setBounds(0, 0, 438, 241);
				fileChooserChapter.setCurrentDirectory(new File(directory));
				FileNameExtensionFilter filter = new FileNameExtensionFilter("txt file", "txt");
				fileChooserChapter.setFileFilter(filter);

				if (fileChooserChapter.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					// assign the chosen file path to its address variable
					chapterFileAddress = fileChooserChapter.getSelectedFile().getAbsolutePath();
					//put setup info into the text area of the SetupInfo panel
					textArea.append("Chapter file: " + chapterFileAddress+ "\n\n");
					panelSetup.setVisible(true);
					panelWelcome.setVisible(false);

				}

				else if (fileChooserChapter.showOpenDialog(null) == JFileChooser.CANCEL_OPTION) {
					fileChooserDirectory.setVisible(false);
					panelSetup.setVisible(false);
					panelWelcome.setVisible(true);
				}
			}
		});
		btnSetChapterFile.setBounds(239, 193, 409, 80);
		panelSetup.add(btnSetChapterFile);

		// the Set Mesostic Row Button makes the SetMesosticRow panel visible
		JButton btnSetMesosticRow = new JButton("Set Mesostic Row");
		btnSetMesosticRow.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnSetMesosticRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelSetUpMesosticRow.setVisible(true);
				panelSetup.setVisible(false);
			}
		});
		btnSetMesosticRow.setBounds(239, 294, 409, 80);
		panelSetup.add(btnSetMesosticRow);

		//the SetMesosticRow panel and its label
		panelSetUpMesosticRow.setLayout(null);
		panelSetUpMesosticRow.setVisible(false);// default = not visible
		JLabel lblTypeYourMesostic = new JLabel("Type your mesostic row in the box:");
		lblTypeYourMesostic.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblTypeYourMesostic.setBounds(88, 84, 712, 59);
		panelSetUpMesosticRow.add(lblTypeYourMesostic);

		// add a text field for entering the mesostic row 
		mesosticRowtextField = new JTextField();
		mesosticRowtextField.setFont(new Font("Tahoma", Font.PLAIN, 21));
		mesosticRowtextField.setColumns(10);
		mesosticRowtextField.setBounds(86, 241, 715, 59);
		panelSetUpMesosticRow.add(mesosticRowtextField);

		/* 
		 * submit button on the SetMesosticRow panel
		 * assigns the mesosticRowFileAddress 
		 * and writes the mesostic row to a file
		 */
		JButton btnMesosticRowSubmit = new JButton("submit");
		btnMesosticRowSubmit.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnMesosticRowSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// save input as formatted string
				mesosticRow = mesosticRowtextField.getText().toLowerCase().replaceAll("\\s+", "");
				//put setup info into the text area of the SetupInfo panel
				textArea.append("Mesostic Row: " + mesosticRow+ "\n\n");
				
				// save the mesosticRow to a file
				try (PrintWriter out = new PrintWriter(mesosticRowFileAddress)) {
					out.println(mesosticRow);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				// convert mesosticRow to array of letters
				String[] mesosticRowArray = mesosticRow.split("");
				//convert mesosticRow to HashMap
				Map<Integer, String> syllableRepositoryDir = new HashMap<>();
			
				// create a syllable repository file for each letter in mesosticRow
				for (int i = 0; i < mesosticRowArray.length; i++) {
					String mesosticLetterFileAddress = directory + "\\Syllable Repositories\\MesoticLetter"
							+ (i+1) + ".txt";
					syllableRepositoryDir.put(i, mesosticLetterFileAddress); 
					try (PrintWriter out = new PrintWriter(mesosticLetterFileAddress)) {
						out.println(i+1 + mesosticRowArray[i]);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				// return to Welcome panel
				panelSetUpMesosticRow.setVisible(false);
				panelSetup.setVisible(true);
				panelWelcome.setVisible(false);

			}
		});
		btnMesosticRowSubmit.setBounds(352, 312, 183, 59);
		panelSetUpMesosticRow.add(btnMesosticRowSubmit);
		
		//The SetupInfo button makes the SetUpInfo sub-panel visible
		JButton btnSetupInfo = new JButton("Your Setup Info");
		btnSetupInfo.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnSetupInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelSetupInfo.setVisible(true);
				panelSetup.setVisible(false);				
			}
		});	
		btnSetupInfo.setBounds(239, 398, 409, 74);
		panelSetup.add(btnSetupInfo);
		
		// The "ok" button on the SetupInfo panel returns to the Welcome panel
		JButton btnpanelSetupOK = new JButton("ok");
		btnpanelSetupOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelSetupInfo.setVisible(false);
				panelWelcome.setVisible(true);
			}
		});
		btnpanelSetupOK.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnpanelSetupOK.setBounds(384, 444, 78, 61);
		panelSetupInfo.add(btnpanelSetupOK);

		// all the done buttons make the Welcome panel visible
		JButton btnSetupDone = new JButton("Done");
		btnSetupDone.setFont(new Font("Tahoma", Font.PLAIN, 21));
		btnSetupDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelSetup.setVisible(false);
				panelWelcome.setVisible(true);
			}
		});
		btnSetupDone.setBounds(770, 477, 97, 53);
		panelSetup.add(btnSetupDone);
				
		/*
		 * END OF SETUP CODE; BEGIN RUN CODE
		 */
		
		// the run panel and its label
		panelRun.setLayout(null);
		panelRun.setVisible(false);// default = not visible
		JLabel lblWelcomeToRun = new JLabel("Run Your Project");
		lblWelcomeToRun.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblWelcomeToRun.setBounds(284, 21, 319, 62);
		panelRun.add(lblWelcomeToRun);

		// the Make Mesostics button makes the mesosticMaker sub-panel visible
		JButton btnMakeMesostics = new JButton("Make Mesostics");
		btnMakeMesostics.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnMakeMesostics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelRunMesostics.setVisible(true);
				panelRun.setVisible(false);
			}
		});
		btnMakeMesostics.setBounds(275, 104, 342, 52);
		panelRun.add(btnMakeMesostics);

		//the MesosticMaker sub-panel and its label
		panelRunMesostics.setLayout(null);
		JLabel lblMakeMesostics = new JLabel("Make Mesostics");
		lblMakeMesostics.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblMakeMesostics.setBounds(297, -5, 293, 69);
		panelRunMesostics.add(lblMakeMesostics);

		//CODE FOR MESOSTICMAKER SUB-PANEL FUNCTIONS
		
		/*
		 * this button initialises the "shorter" mesostic maker 
		 * that uses a syllable repository to create a shorter mesostic set
		 */
		JButton btnShorterVersion = new JButton("Shorter version");
		btnShorterVersion.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnShorterVersion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					MesosticMakerShorter mms = new MesosticMakerShorter(directory, mesosticRowFileAddress, chapterFileAddress,
							allMesosticsFileAddress);
					mms.runMaker();
				} catch (IOException e1) {
					e1.printStackTrace();
					//redirect user to the setup window
					JOptionPane.showMessageDialog(null, "An error has occurred."
							+ "\nSetup may be incorrect."
							+ "\nClose programme and return to Setup");
				} catch (InterruptedException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Internet connection interrupted"
							+ "\nClose programme and start over");
				}
			}
		});
		btnShorterVersion.setBounds(478, 169, 318, 74);
		panelRunMesostics.add(btnShorterVersion);

		/*
		 * this button initialises the "longer" mesostic maker that does not use
		 * a syllable repository to filter words
		 */
		JButton btnLongerVersion = new JButton("Longer version");
		btnLongerVersion.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnLongerVersion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					MesosticMakerLonger mml = new MesosticMakerLonger(mesosticRowFileAddress, chapterFileAddress,
							allMesosticsFileAddress);
					mml.runMaker();
				} catch (IOException e1) {
					e1.printStackTrace();
					//redirect user to the setup window
					JOptionPane.showMessageDialog(null, "An error has occurred."
							+ "\nSetup may be incorrect."
							+ "\nClose programme and return to Setup");

				} 
			}
		});
		btnLongerVersion.setBounds(130, 169, 307, 74);
		panelRunMesostics.add(btnLongerVersion);
		
		// the done button makes the Run panel visible again
		JButton btnRunMakeMesosticsDone = new JButton("Done");
		btnRunMakeMesosticsDone.setFont(new Font("Tahoma", Font.PLAIN, 21));
		btnRunMakeMesosticsDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelRunMesostics.setVisible(false);
				panelRun.setVisible(true);
			}
		});
		btnRunMakeMesosticsDone.setBounds(736, 485, 141, 35);
		panelRunMesostics.add(btnRunMakeMesosticsDone);

		// this button calls the getSounds object
		JButton btnGetSounds = new JButton("Get Sounds");
		btnGetSounds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					SoundGetter sg = new SoundGetter(chapterFileAddress, soundFileAddress, chapterSoundsFileAddress, chapterSoundsSentencesFileAddress);
					sg.wordGetter();
					sg.sentenceGetter();
					
				} catch (IOException e1) {
					e1.printStackTrace();
					//redirect user to the setup window
					JOptionPane.showMessageDialog(null, "An error has occurred."
							+ "\nSetup may be incorrect."
							+ "\nClose programme and return to Setup");
				}
			}
		});
		btnGetSounds.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnGetSounds.setBounds(318, 167, 252, 61);
		panelRun.add(btnGetSounds);

		// this button calls the getPlaces object
		JButton btnGetPlaces = new JButton("Get Places");
		btnGetPlaces.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					PlaceGetter pg = new PlaceGetter(chapterFileAddress, notPlaceFileAddress, chapterPlacesFileAddress, chapterPlacesSentencesFileAddress);
					pg.wordGetter();
					pg.sentenceGetter();
					
				} catch (IOException e1) {
					e1.printStackTrace();
					//redirect user to the setup window
					JOptionPane.showMessageDialog(null, "An error has occurred."
							+ "\nSetup may be incorrect."
							+ "\nClose programme and return to Setup");
				}

			}
		});
		btnGetPlaces.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnGetPlaces.setBounds(318, 250, 252, 56);
		panelRun.add(btnGetPlaces);

		// all the done buttons make the welcome panel visible
		JButton btnRunDone = new JButton("Done");
		btnRunDone.setFont(new Font("Tahoma", Font.PLAIN, 21));
		btnRunDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelRun.setVisible(false);
				panelWelcome.setVisible(true);
			}
		});
		btnRunDone.setBounds(744, 478, 123, 42);
		panelRun.add(btnRunDone);

		//END OF RUN PANELS CODE; BEGINNING OF FINISH PANEL CODE 
	
		// the Finish panel and its label
		panelFinish.setLayout(null);
		JLabel lblWelcomeToFinish = new JLabel("Finish Your Project Files");
		lblWelcomeToFinish.setBounds(224, 65, 442, 51);
		lblWelcomeToFinish.setFont(new Font("Tahoma", Font.PLAIN, 42));
		panelFinish.add(lblWelcomeToFinish);
	
		// this button calls the AdjacentWordAdder object
		JButton btnAddWords = new JButton("Add Adjacent Words");
		btnAddWords.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					MesosticFinisherAdjacentWordAdder mawa = new MesosticFinisherAdjacentWordAdder(chapterFileAddress, allMesosticsFileAddress, allMesosticsWithAdjacentFileAddress);
					mawa.runMaker();
				} catch (IOException e1) {
					e1.printStackTrace();
					//redirect user to the setup window
					JOptionPane.showMessageDialog(null, "An error has occurred."
							+ "\nSetup may be incorrect."
							+ "\nClose programme and return to Setup");
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					//redirect user to the setup window
					JOptionPane.showMessageDialog(null, "The programme has been interrupted."
							+ "\nClose programme and return to Setup");
				}
				
			}
		});
		btnAddWords.setBounds(240, 181, 409, 61);
		btnAddWords.setFont(new Font("Tahoma", Font.PLAIN, 42));
		panelFinish.add(btnAddWords);
		
		//this button calls the MesosticCentredLines object
		JButton btnCentreMesosticLines = new JButton("Centre Mesostic Lines");
		btnCentreMesosticLines.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				
				try {
					MesosticFinisherCentered mfc = new MesosticFinisherCentered(allMesosticsWithAdjacentFileAddress, allMesosticsFinalCenteredFileAddress);
					mfc.runMaker();
				} catch (IOException e1) {
					e1.printStackTrace();
					//redirect user to the setup window
					JOptionPane.showMessageDialog(null, "An error has occurred."
							+ "\nSetup may be incorrect."
							+ "\nClose programme and return to Setup");
				} catch (InterruptedException e1) {
					e1.printStackTrace();
					//redirect user to the setup window
					JOptionPane.showMessageDialog(null, "An error has occurred."
							+ "\nSetup may be incorrect."
							+ "\nClose programme and return to Setup");
				}			
			}
		});
		btnCentreMesosticLines.setBounds(228, 307, 434, 61);
		btnCentreMesosticLines.setFont(new Font("Tahoma", Font.PLAIN, 42));
		panelFinish.add(btnCentreMesosticLines);
		
		//this button calls the Collation object
		JButton btnCollate = new JButton("Collate Sounds and Places");
		btnCollate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				MesosticFinisherCollated mmc = new MesosticFinisherCollated(allMesosticsFileAddress, chapterSoundsFileAddress, chapterPlacesFileAddress, allMesosticsCollatedFileAddress);
				try {
					mmc.runMaker();
				} catch (IOException | InterruptedException e1) {
					//redirect user to the setup window
					JOptionPane.showMessageDialog(null, "An error has occurred."
							+ "\nSetup may be incorrect."
							+ "\nClose programme and return to Setup");
					e1.printStackTrace();
				}
				
			}
		});
		btnCollate.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnCollate.setBounds(187, 433, 516, 61);
		panelFinish.add(btnCollate);
		
		//all done buttons return to Welcome panel
		JButton btnFinishDone = new JButton("Done");
		btnFinishDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelFinish.setVisible(false);
				panelWelcome.setVisible(true);	
			}
		});
		btnFinishDone.setBounds(777, 498, 82, 36);
		btnFinishDone.setFont(new Font("Tahoma", Font.PLAIN, 21));
		panelFinish.add(btnFinishDone);
		
		//END OF FINISH PANEL CODE; BEGINNING OF VIEW PANELS CODE
		
		/*
		 * The View card has one button 
		 * that opens up a "txt file only" filechooser on the directory 
		 * and opens the selected file with notepad.exe
		 * 
		 * NOTE:
		 * THE LOCATION OF notepad.exe IS HARD CODED HERE 
		 * THE USER'S COMPUTER MAY STORE notepad.exe SOMEWHERE ELSE 
		 * OR THE USER MAY WISH TO USE A DIFFERENT PROGRAM To OPEN FILES
		 * 
		 */
		
		// initialize the View panel and its label
		panelView.setLayout(null);
		panelView.setVisible(false);// default = not visible
		JLabel lblWelcomeToView = new JLabel("Open a file in your directory");
		lblWelcomeToView.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblWelcomeToView.setBounds(172, 0, 544, 63);
		panelView.add(lblWelcomeToView);

		/* 
		 * The Open button calls a fileChooser 
		 * which uses notepad.exe to open the selected file
		 */
		JButton btnOpen = new JButton("Open");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				panelView.setVisible(false);
				JFileChooser fileChooserOpen = new JFileChooser();
				fileChooserOpen.setBounds(0, 0, 438, 241);
				fileChooserOpen.setCurrentDirectory(new File(directory));
				FileNameExtensionFilter filter = new FileNameExtensionFilter("txt file", "txt");
				fileChooserOpen.setFileFilter(filter);

				if (fileChooserOpen.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

					String selected = fileChooserOpen.getSelectedFile().getAbsolutePath();
					Runtime runtime = Runtime.getRuntime();
					try {
						runtime.exec("C:\\windows\\system32\\notepad.exe" + " " + selected);
					} catch (IOException e1) {
						
						e1.printStackTrace();
					}
					panelSetup.setVisible(false);

				}

				else if (fileChooserChapter.showOpenDialog(null) == JFileChooser.CANCEL_OPTION) {
					
					fileChooserDirectory.setVisible(false);
					panelView.setVisible(false);
					panelWelcome.setVisible(true);
				
				}
			}
		});
		btnOpen.setFont(new Font("Tahoma", Font.PLAIN, 42));
		btnOpen.setBounds(339, 160, 159, 93);
		panelView.add(btnOpen);

		// all the done buttons make the welcome panel visible
		JButton btnViewDone = new JButton("Done");
		btnViewDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelView.setVisible(false);
				panelWelcome.setVisible(true);
			}
		});
		btnViewDone.setBounds(651, 485, 143, 35);
		panelView.add(btnViewDone);

	}
}
