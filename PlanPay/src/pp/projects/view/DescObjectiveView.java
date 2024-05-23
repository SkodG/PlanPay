package pp.projects.view;

import javax.swing.JPanel;

import java.awt.Component;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JList;

public class DescObjectiveView extends JPanel implements ListCellRenderer<Object>{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	//TEST
	 private Random rnd;

	/**
	 * Create the panel.
	 */
	public DescObjectiveView() {
		setLayout(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(contentPane);

		
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		JButton b = new JButton();
		return this;
	}
}
