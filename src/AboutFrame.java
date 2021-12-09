import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AboutFrame {
    private JButton backButton;
	private JPanel about;
	private JFrame frame;

    public AboutFrame(){
        frame = new JFrame("About");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        backButton = new JButton("Close");
        about = new JPanel();
        backButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
            }
        });
        frame.getContentPane().setBackground(Color.WHITE);
        about.setBackground(Color.WHITE);
        frame.setResizable(true);

        JLabel description = new JLabel("<html><h2>Built by</h2>"+
        "    <ol>"+
        "        <li>Alif Adrian Anzary - 5025201274</li>"+
        "        <li>Benedictus Bimo C W - 5025201097</li>"+
        "        <li>Eldenabih tavirazin lutvie - 5025201213</li>"+
        "        <li>Mochamad Revanza Kurniawan - 5025201145</li>"+
        "    </ol>"+
        "    <h2>Description</h2>"+
        "    <p>The Haunted Maze adalah game puzzle labirin klasik yang bertipe single player dan bisa dimainkan oleh segala usia mulai dari anak-anak, remaja hingga dewasa. "+
        "        Pemain harus menentukan jalan yang tepat untuk keluar dari labirin yang berliku dan banyak jalan buntu juga hantu yang berkeliaran. "+
        "        Untuk membuka pintu keluar labirin, pemain membutuhkan sebuah item kunci yang terdapat di suatu tempat dalam labirin. "+
        "        Terdapat juga beberapa item yang membantu player seperti pedang untuk mengalahkan hantu dan es untuk membekukan hantu beberapa saat. "+
        "        Aplikasi ini adalah game edukasi yang diinovasikan dengan menambah unsur petualangan (adventure) di dalamnya sehingga tidak membosankan.</p>"+
        "        "+
        "    <p>Permainan ini dimainkan di PC dengan menggunakan control button keyboard yakni 'W', 'A', 'S', 'D' atau Arrow Button untuk menjalankan player. "+
        "        Game ini dilengkapi dengan level up system yang akan menjadi daya tarik bagi pemain untuk mengasah logika dan keterampilan dalam menyelesaikan variasi tantangan.</p>");
        
        // GridBagConstraints c = new GridBagConstraints();

        // c.fill = GridBagConstraints.HORIZONTAL;
        // c.gridx = 0;
        // c.gridy = 0;
        about.add(description, BorderLayout.PAGE_START);

        // c.gridy= 2;
		// c.anchor = GridBagConstraints.PAGE_END;
		// c.insets = new Insets(0,0,0,0);
		// this.backButton.setEnabled(true);
		// about.add(backButton, BorderLayout.PAGE_END);
		
		// c.gridx= 0;
		frame.add(about);

		// c.gridy = 1;
		frame.pack();
		frame.setVisible(false);
        frame.setSize(300, 300);
    }

    public void setVisible(boolean b) {
        frame.setVisible(b);
    }
}
