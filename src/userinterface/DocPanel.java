package userinterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import catchdata.Catcher;

public class DocPanel extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	GridBagConstraints c = new GridBagConstraints();
	JTextField ffURL = new JTextField(
			"https://build.liferay.com/2/view/test-portal-fixpack-frontend-tomcat-mysql%28ee-6.2.10_6.2.10.9%29/");
	JTextField buildNum = new JTextField("#number");
	JTextField begin_line = new JTextField("3");
	JTextField buildNumList = new JTextField("#number/#number/(the last \""
			+ "/" + "\" is important)");
	JTextField startComponent = new JTextField("portal-component-name");
	JTextField endComponent = new JTextField("portal-component-name");
	JTextField freecase = new JTextField("name/name/(the last \"" + "/"
			+ "\" is important)");
	JButton run_button = new JButton("Start");
	JComboBox<String> b_model = new JComboBox<String>();
	JComboBox<String> c_model = new JComboBox<String>();
	JComboBox<String> os = new JComboBox<String>();
	JPanel mainPanel = new JPanel();
	JPanel detail_panel = new JPanel();
	ImageIcon background = new ImageIcon();

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				DocPanel doc = new DocPanel();
				doc.init();
				doc.changeComponentModel();
				doc.start();
				doc.set_OS();

			}
		});
	}

	class main_Panel extends JPanel {
		Image img;

		public main_Panel() {
			background = new ImageIcon("avatar.png");
			img = background.getImage();
			System.out.println(background.getIconHeight() + "-"
					+ background.getIconWidth());
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			g.drawImage(img, 0, 0, null);
		}

	}

	public void init() {
		setTitle("GoogleDocGenarator");
		setLocation(300, 300);
		setSize(500, 300);
		setResizable(false);
		setPreferredSize(new Dimension(250, 250));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		mainPanel.setBackground(Color.white);
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.setBorder(BorderFactory.createTitledBorder("V15"));

		add(mainPanel);

		b_model.addItem("Simple Mode");
		b_model.addItem("Mutiple Mode(default mode only)");
		c_model.addItem("Default Mode");
		c_model.addItem("Break Mode");
		c_model.addItem("Free Mode");
		os.addItem("Windows");
		os.addItem("Linux");

		// 0,0
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.weighty = 0;
		mainPanel.add(new JLabel("OS Environment:"), c);
		
		// 1,0
		c.gridx = 1;
		mainPanel.add(os, c);
		
		//	0,1
		c.gridx=0;
		c.gridy = 1;
		mainPanel.add(new JLabel("Job URL:"), c);

		// 1,1
		c.gridx = 1;
		c.weightx = 1;
		mainPanel.add(ffURL, c);

		// 0,2
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 0;
		mainPanel.add(new JLabel("Build Model:"), c);

		// 1,2
		JPanel model_panel = new JPanel();
		model_panel.setLayout(new GridBagLayout());
		model_panel.setBackground(Color.white);
		c.gridx = 0;
		c.gridy = 0;
		model_panel.add(b_model, c);
		c.gridx = 2;
		c.weightx = 1;
		model_panel.add(new JLabel(), c);
		c.gridx = 3;
		c.weightx = 0;
		model_panel.add(run_button, c);
		c.gridx = 1;
		c.gridy = 2;
		mainPanel.add(model_panel, c);

		// 0,3
		c.gridx = 0;
		c.gridy = 3;
		c.weightx = 0;
		mainPanel.add(new JLabel("Component Model:"), c);

		// 1,3
		c.gridx = 1;
		c.gridy = 3;
		mainPanel.add(c_model, c);

		// 0,4
		c.gridx = 0;
		c.gridy = 4;
		c.weightx = 0;
		mainPanel.add(new JLabel("Model Details:"), c);

		// 1,4

		detail_panel.setLayout(new GridBagLayout());
		// c.gridx=0;
		c.gridy = 0;
		detail_panel.add(new JLabel("Build Number(Simple):"), c);
		c.gridx = 1;
		c.weightx = 1;
		detail_panel.add(buildNum, c);
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0;
		detail_panel.add(new JLabel("Begin-Line:"), c);
		c.gridx = 1;
		c.weightx = 1;
		detail_panel.add(begin_line, c);
		c.gridx = 1;
		c.gridy = 4;
		c.weighty = 1;
		mainPanel.add(detail_panel, c);
	}

	public void changeComponentModel() {
		c_model.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// detail_panel
				if (c_model.getSelectedIndex() == 0) {
					if (b_model.getSelectedIndex() == 0) {
						defaultMode_s();
					} else if (b_model.getSelectedIndex() == 1) {
						defaultMode_m();
					}
				} else if (c_model.getSelectedIndex() == 1) {
					breakMode();
				} else if (c_model.getSelectedIndex() == 2) {
					freeMode();
				}
			}
		});
	}

	private void init_model() {
		Catcher.setModel_b("false");
		Catcher.setModel_f("false");
		Catcher.setModel_m("false");
	}
	
	public void set_OS(){
		os.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				if (os.getSelectedIndex() == 0) {
//					System.out.println("windows");
					Catcher.setOs(os.getItemAt(0));
				} else if (os.getSelectedIndex() == 1) {
//					System.out.println("linux");
					Catcher.setOs(os.getItemAt(1));
				}
			}
		});
	}
	
	public void start() {
		run_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				

				if (c_model.getSelectedIndex() == 0) {
					if (b_model.getSelectedIndex() == 0) {
						init_model();
						Catcher.setUrl(ffURL.getText());
						Catcher.setBeginLine(Integer.parseInt(begin_line
								.getText()));
						Catcher.setBuildNum(buildNum.getText());

					} else if (b_model.getSelectedIndex() == 1) {
						init_model();
						Catcher.setUrl(ffURL.getText());
						Catcher.setBeginLine(Integer.parseInt(begin_line
								.getText()));
						Catcher.setBuildNumlist_parameter(buildNumList
								.getText());
						Catcher.setModel_m("true");
					}
				} else if (c_model.getSelectedIndex() == 1) {
					init_model();
					Catcher.setUrl(ffURL.getText());
					Catcher.setBuildNum(buildNum.getText());
					Catcher.setStartName(startComponent.getText());
					Catcher.setEndName(endComponent.getText());
					Catcher.setModel_b("true");
				} else if (c_model.getSelectedIndex() == 2) {
					init_model();
					Catcher.setUrl(ffURL.getText());
					Catcher.setBuildNum(buildNum.getText());
					Catcher.setFree_cases(freecase.getText());
					Catcher.setModel_f("true");
				}
				Catcher.start();
			}
		});
	}

	private void freeMode() {
		b_model.setSelectedIndex(0);
		detail_panel.removeAll();
		detail_panel.revalidate();
		c.gridx = 0;
		c.gridy = 0;
		detail_panel.add(new JLabel("Build Number(Simple):"), c);
		c.gridx = 1;
		c.weightx = 1;
		detail_panel.add(buildNum, c);
		c.gridx = 0;
		c.gridy = 1;
		detail_panel.add(new JLabel("Free Component List:"), c);
		c.gridx = 1;
		c.weightx = 1;
		detail_panel.add(freecase, c);
	}

	private void breakMode() {
		b_model.setSelectedIndex(0);
		detail_panel.removeAll();
		detail_panel.revalidate();
		c.gridx = 0;
		c.gridy = 0;
		detail_panel.add(new JLabel("Build Number(Simple):"), c);
		c.gridx = 1;
		c.weightx = 1;
		detail_panel.add(buildNum, c);
		c.gridx = 0;
		c.gridy = 1;
		detail_panel.add(new JLabel("Start Component:"), c);
		c.gridx = 1;
		c.weightx = 1;
		detail_panel.add(startComponent, c);
		c.gridx = 0;
		c.gridy = 2;
		detail_panel.add(new JLabel("End Component:"), c);
		c.gridx = 1;
		c.weightx = 1;
		detail_panel.add(endComponent, c);
	}

	private void defaultMode_m() {
		detail_panel.removeAll();
		detail_panel.revalidate();
		c.gridx = 0;
		c.gridy = 0;
		detail_panel.add(new JLabel("Build Number(Mutiple):"), c);
		c.gridx = 1;
		c.weightx = 1;
		detail_panel.add(buildNumList, c);
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0;
		detail_panel.add(new JLabel("Begin-Line:"), c);
		c.gridx = 1;
		c.weightx = 1;
		detail_panel.add(begin_line, c);
		detail_panel.revalidate();
	}

	private void defaultMode_s() {
		detail_panel.removeAll();
		detail_panel.revalidate();
		c.gridx = 0;
		c.gridy = 0;
		detail_panel.add(new JLabel("Build Number(Simple):"), c);
		c.gridx = 1;
		c.weightx = 1;
		detail_panel.add(buildNum, c);
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0;
		detail_panel.add(new JLabel("Begin-Line:"), c);
		c.gridx = 1;
		c.weightx = 1;
		detail_panel.add(begin_line, c);
		detail_panel.revalidate();
	}
}
