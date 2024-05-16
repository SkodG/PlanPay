package pp.projects.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pp.projects.controller.ConsoleControllerImpl;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.Font;
import java.awt.Color;
import java.awt.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class ConsolleView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnCalendar;
	private JButton btnServices;
	private JButton btnTransactions;
	private JLabel lbTransactions;
	private JLabel lbUsername;
	private JLabel lbImp;
	private JLabel lblmporto;
	private ImageIcon icon;
	private JButton btnObjectives;
	private int count;
	
	private ConsoleControllerImpl controller;
	private CalendarView calendarView;
	
	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public ConsolleView(ConsoleControllerImpl c) throws IOException {
		setTitle("CONSOLLE");
		this.controller = c;
		this.count = 0;
		this.calendarView = new CalendarView(c);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 736, 490);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 245, 245));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lbUsername = new JLabel("");
		lbUsername.setFont(new Font("Calibri", Font.PLAIN, 38));
		lbUsername.setBounds(31, 24, 461, 42);
		contentPane.add(lbUsername);
		
		btnServices = new JButton("SERVIZI");
		btnServices.setFont(new Font("Calibri", Font.PLAIN, 36));
		btnServices.setBounds(31, 165, 307, 176);
		contentPane.add(btnServices);
		
		lbImp = new JLabel("Importo:");
		lbImp.setFont(new Font("Calibri", Font.PLAIN, 33));
		lbImp.setBounds(31, 76, 134, 42);
		contentPane.add(lbImp);
		
		lblmporto = new JLabel("0,00 €");
		lblmporto.setFont(new Font("Calibri", Font.PLAIN, 34));
		lblmporto.setBounds(228, 76, 122, 42);
		contentPane.add(lblmporto);
		
		JList listTransactions = new JList();
		listTransactions.setBounds(31, 433, 661, 249);
		listTransactions.setVisible(false);
		
		// TO DO: controllare che vengano visualizzate. Impostare metodo di visualizzazione:
		// *data, testo, importo
		contentPane.add(listTransactions);
		
		//listTransactions.setToolTipText(controller.getAllTransactions().toString());
		
		lbTransactions = new JLabel("Transazioni");
		lbTransactions.setFont(new Font("Calibri", Font.PLAIN, 36));
		lbTransactions.setBounds(31, 385, 597, 42);
		contentPane.add(lbTransactions);
		
		btnCalendar = new JButton();
		btnCalendar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calendarView.setVisible(true);
			}
		});
		try {
			btnCalendar.setIcon(new ImageIcon(this.getClass().getResource("/images/calendar.png")));
		} catch (Exception ex) {
		    System.out.println(ex);
		}
		btnCalendar.setBounds(558, 24, 134, 94);	
		contentPane.add(btnCalendar);
		
		btnTransactions = new JButton();
		try {
		    btnTransactions.setIcon(new ImageIcon(this.getClass().getResource("/images/freccia.png")));
		} catch (Exception ex) {
		    System.out.println(ex);
		}
		btnTransactions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				count += 1;
				if (count % 2 == 0) {
					listTransactions.setVisible(false);
					setBounds(100, 100, 736, 490);
				} else {
					listTransactions.setVisible(true);
					setBounds(100, 100, 736, 722);
				}
			}
		});
		btnTransactions.setBounds(630, 385, 62, 42);
		contentPane.add(btnTransactions);
		
		btnObjectives = new JButton("OBBIETTIVI");
		btnObjectives.setFont(new Font("Calibri", Font.PLAIN, 36));
		btnObjectives.setBounds(385, 165, 307, 176);
		contentPane.add(btnObjectives);
		
		// WindowListener per gestire l'evento "windowOpened"
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                lbUsername.setText(c.setNameController().toUpperCase());
            }
        });
	}
}
