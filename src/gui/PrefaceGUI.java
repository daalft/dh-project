package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSlider;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

import javax.swing.JSeparator;

import preface.analysis.Preface;

import java.awt.SystemColor;
import java.awt.Toolkit;
import java.io.File;

public class PrefaceGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtpath;
	private JTextField txtpathfilexml;
	private JSlider slider;
	private JTextField txtpath_1;
	private Preface preface;
	private boolean openOnComplete;
	private JFileChooser jfc;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrefaceGUI frame = new PrefaceGUI();
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
	public PrefaceGUI() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(PrefaceGUI.class.getResource("/javax/swing/plaf/basic/icons/image-delayed.png")));
		setTitle("Preface");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 535, 365);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		jfc = new JFileChooser();
		
		JLabel lblPrefaceGraphicalUser = new JLabel("Preface Graphical User Interface");
		lblPrefaceGraphicalUser.setBackground(Color.BLACK);
		lblPrefaceGraphicalUser.setForeground(SystemColor.activeCaption);
		lblPrefaceGraphicalUser.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_lblPrefaceGraphicalUser = new GridBagConstraints();
		gbc_lblPrefaceGraphicalUser.gridwidth = 3;
		gbc_lblPrefaceGraphicalUser.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrefaceGraphicalUser.gridx = 1;
		gbc_lblPrefaceGraphicalUser.gridy = 0;
		contentPane.add(lblPrefaceGraphicalUser, gbc_lblPrefaceGraphicalUser);
		
		JLabel lblSelectDirectoryContaining = new JLabel("Select directory containing chapter files");
		GridBagConstraints gbc_lblSelectDirectoryContaining = new GridBagConstraints();
		gbc_lblSelectDirectoryContaining.anchor = GridBagConstraints.WEST;
		gbc_lblSelectDirectoryContaining.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectDirectoryContaining.gridx = 1;
		gbc_lblSelectDirectoryContaining.gridy = 1;
		contentPane.add(lblSelectDirectoryContaining, gbc_lblSelectDirectoryContaining);
		
		txtpath = new JTextField();
		txtpath.setForeground(SystemColor.textText);
		GridBagConstraints gbc_txtpath = new GridBagConstraints();
		gbc_txtpath.insets = new Insets(0, 0, 5, 5);
		gbc_txtpath.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtpath.gridx = 2;
		gbc_txtpath.gridy = 1;
		contentPane.add(txtpath, gbc_txtpath);
		txtpath.setColumns(10);
		
		JButton btnSelect = new JButton("Select...");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jfc.setFileFilter(new FileFilter() {

					@Override
					public boolean accept(File f) {
						return f.isDirectory();
					}

					@Override
					public String getDescription() {
						return "";
					}
					
				});
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if (jfc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
					txtpath.setText(jfc.getSelectedFile().getPath());
				}
			}
		});
		GridBagConstraints gbc_btnSelect = new GridBagConstraints();
		gbc_btnSelect.insets = new Insets(0, 0, 5, 5);
		gbc_btnSelect.gridx = 3;
		gbc_btnSelect.gridy = 1;
		contentPane.add(btnSelect, gbc_btnSelect);
		
		JLabel lblSelectCoreferenceIndex = new JLabel("Select coreference index file");
		GridBagConstraints gbc_lblSelectCoreferenceIndex = new GridBagConstraints();
		gbc_lblSelectCoreferenceIndex.anchor = GridBagConstraints.WEST;
		gbc_lblSelectCoreferenceIndex.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectCoreferenceIndex.gridx = 1;
		gbc_lblSelectCoreferenceIndex.gridy = 2;
		contentPane.add(lblSelectCoreferenceIndex, gbc_lblSelectCoreferenceIndex);
		
		txtpathfilexml = new JTextField();
		txtpathfilexml.setForeground(SystemColor.textText);
		GridBagConstraints gbc_txtpathfilexml = new GridBagConstraints();
		gbc_txtpathfilexml.insets = new Insets(0, 0, 5, 5);
		gbc_txtpathfilexml.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtpathfilexml.gridx = 2;
		gbc_txtpathfilexml.gridy = 2;
		contentPane.add(txtpathfilexml, gbc_txtpathfilexml);
		txtpathfilexml.setColumns(10);
		
		JButton btnSelect_1 = new JButton("Select...");
		btnSelect_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jfc.setFileFilter(new FileNameExtensionFilter("XML file", new String[] {"xml"}));
				jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				if (jfc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
					txtpathfilexml.setText(jfc.getSelectedFile().getPath());
				}
			}
		});
		GridBagConstraints gbc_btnSelect_1 = new GridBagConstraints();
		gbc_btnSelect_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnSelect_1.gridx = 3;
		gbc_btnSelect_1.gridy = 2;
		contentPane.add(btnSelect_1, gbc_btnSelect_1);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.gridwidth = 3;
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.gridx = 1;
		gbc_separator.gridy = 3;
		contentPane.add(separator, gbc_separator);
		
		JLabel lblSearchWindow = new JLabel("Options");
		lblSearchWindow.setForeground(SystemColor.inactiveCaptionText);
		GridBagConstraints gbc_lblSearchWindow = new GridBagConstraints();
		gbc_lblSearchWindow.anchor = GridBagConstraints.WEST;
		gbc_lblSearchWindow.gridwidth = 3;
		gbc_lblSearchWindow.insets = new Insets(0, 0, 5, 5);
		gbc_lblSearchWindow.gridx = 1;
		gbc_lblSearchWindow.gridy = 4;
		contentPane.add(lblSearchWindow, gbc_lblSearchWindow);
		
		final JCheckBox chckbxSearchWindow = new JCheckBox("Use search window");
		
		GridBagConstraints gbc_chckbxSearchWindow = new GridBagConstraints();
		gbc_chckbxSearchWindow.anchor = GridBagConstraints.WEST;
		gbc_chckbxSearchWindow.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxSearchWindow.gridx = 1;
		gbc_chckbxSearchWindow.gridy = 5;
		contentPane.add(chckbxSearchWindow, gbc_chckbxSearchWindow);
		chckbxSearchWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean select = chckbxSearchWindow.isSelected();
				slider.setEnabled(select);
				if (select) {
					preface.setSearchWindow(slider.getValue());
				} else {
					preface.setSearchWindow(-1);
				}
			}
		});
		
		slider = new JSlider();
		slider.setPaintTrack(false);
		slider.setSnapToTicks(true);
		slider.setEnabled(false);
		slider.setMajorTickSpacing(1);
		slider.setValue(4);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMaximum(10);
		slider.setMinimum(2);
		GridBagConstraints gbc_slider = new GridBagConstraints();
		gbc_slider.gridwidth = 2;
		gbc_slider.insets = new Insets(0, 0, 5, 5);
		gbc_slider.fill = GridBagConstraints.BOTH;
		gbc_slider.gridx = 2;
		gbc_slider.gridy = 5;
		contentPane.add(slider, gbc_slider);
		
		final JCheckBox chckbxFilterStopWords = new JCheckBox("Filter stop words");
		chckbxFilterStopWords.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				preface.setUseStopwords(chckbxFilterStopWords.isSelected());
			}
		});
		GridBagConstraints gbc_chckbxFilterStopWords = new GridBagConstraints();
		gbc_chckbxFilterStopWords.anchor = GridBagConstraints.WEST;
		gbc_chckbxFilterStopWords.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxFilterStopWords.gridx = 1;
		gbc_chckbxFilterStopWords.gridy = 6;
		contentPane.add(chckbxFilterStopWords, gbc_chckbxFilterStopWords);
		
		JLabel lblChangeOutputPath = new JLabel("Change output path");
		GridBagConstraints gbc_lblChangeOutputPath = new GridBagConstraints();
		gbc_lblChangeOutputPath.insets = new Insets(0, 0, 5, 5);
		gbc_lblChangeOutputPath.anchor = GridBagConstraints.WEST;
		gbc_lblChangeOutputPath.gridx = 1;
		gbc_lblChangeOutputPath.gridy = 7;
		contentPane.add(lblChangeOutputPath, gbc_lblChangeOutputPath);
		
		txtpath_1 = new JTextField();

		txtpath_1.setForeground(SystemColor.textText);
		txtpath_1.setText("./");
		GridBagConstraints gbc_txtpath_1 = new GridBagConstraints();
		gbc_txtpath_1.insets = new Insets(0, 0, 5, 5);
		gbc_txtpath_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtpath_1.gridx = 2;
		gbc_txtpath_1.gridy = 7;
		contentPane.add(txtpath_1, gbc_txtpath_1);
		txtpath_1.setColumns(10);
		
		JButton btnSelect_2 = new JButton("Select...");
		btnSelect_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jfc.setFileFilter(new FileFilter() {

					@Override
					public boolean accept(File f) {
						return f.isDirectory();
					}

					@Override
					public String getDescription() {
						return "";
					}
					
				});
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if (jfc.showSaveDialog(null)==JFileChooser.APPROVE_OPTION) {
					String path = jfc.getSelectedFile().getPath();
					if (!path.endsWith("/"))
						path += "/";
					txtpath_1.setText(path);
				}
			}
			
		});
		
		GridBagConstraints gbc_btnSelect_2 = new GridBagConstraints();
		gbc_btnSelect_2.insets = new Insets(0, 0, 5, 5);
		gbc_btnSelect_2.gridx = 3;
		gbc_btnSelect_2.gridy = 7;
		contentPane.add(btnSelect_2, gbc_btnSelect_2);
		
		final JCheckBox chckbxOpenResultPage = new JCheckBox("Open result page on completion");
		chckbxOpenResultPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openOnComplete = chckbxOpenResultPage.isSelected();
			}
		});
		GridBagConstraints gbc_chckbxOpenResultPage = new GridBagConstraints();
		gbc_chckbxOpenResultPage.anchor = GridBagConstraints.WEST;
		gbc_chckbxOpenResultPage.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxOpenResultPage.gridx = 1;
		gbc_chckbxOpenResultPage.gridy = 8;
		contentPane.add(chckbxOpenResultPage, gbc_chckbxOpenResultPage);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start();
			}
		});
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnStart.gridwidth = 3;
		gbc_btnStart.insets = new Insets(0, 0, 0, 5);
		gbc_btnStart.gridx = 1;
		gbc_btnStart.gridy = 9;
		contentPane.add(btnStart, gbc_btnStart);
		
		preface = new Preface();
	}

	private void start () {
		File dir = new File(txtpath.getText());
		File index = new File(txtpathfilexml.getText());
		preface.setOutputDir(txtpath_1.getText());
		preface.run(dir, index);
		if (openOnComplete) {
			
		} else {
			JOptionPane.showMessageDialog(null, "Preface calculation completed.\nPlease open " + txtpath_1.getText() + "index.html");
		}
	}
}
