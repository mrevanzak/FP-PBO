import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
        about = new JPanel(new GridBagLayout());
        backButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
            }
        });
        frame.getContentPane().setBackground(Color.WHITE);
        about.setBackground(Color.WHITE);
        frame.setResizable(true);
        frame.setLayout(new GridBagLayout());

        JLabel description = new JLabel("<html><h2>Built by</h2>"+
        "    <ol>"+
        "        <li>Alif Adrian Anzary - 5025201274</li>"+
        "        <li>Benedictus Bimo C W - 5025201097</li>"+
        "        <li>Eldenabih tavirazin lutvie - 5025201213</li>"+
        "        <li>Mochamad Revanza Kurniawan - 5025201145</li>"+
        "    </ol>"+
        "    <h2>Description</h2>"+
        "    <p style=\"text-indent: 30px;\">The Haunted Maze adalah game puzzle labirin klasik yang bertipe single player dan bisa dimainkan oleh segala usia mulai dari anak-anak, remaja hingga dewasa. "+
        "        Pemain harus menentukan jalan yang tepat untuk keluar dari labirin yang berliku dan banyak jalan buntu juga hantu yang berkeliaran. "+
        "        Untuk membuka pintu keluar labirin, pemain membutuhkan sebuah item kunci yang terdapat di suatu tempat dalam labirin. "+
        "        Terdapat juga beberapa item yang membantu player seperti pedang untuk mengalahkan hantu dan es untuk membekukan hantu beberapa saat. "+
        "        Aplikasi ini adalah game edukasi yang diinovasikan dengan menambah unsur petualangan (adventure) di dalamnya sehingga tidak membosankan.</p>"+
        "        "+
        "    <p style=\"text-indent: 30px;\">Permainan ini dimainkan di PC dengan menggunakan control button keyboard yakni 'W', 'A', 'S', 'D' atau Arrow Button untuk menjalankan player. "+
        "        Game ini dilengkapi dengan level up system yang akan menjadi daya tarik bagi pemain untuk mengasah logika dan keterampilan dalam menyelesaikan variasi tantangan.</p>");
            
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.CENTER;
        c.ipady = 90;
        c.ipadx = 220;
        about.add(description, c);


        c.ipady = 0;
        c.ipadx = 0;
        c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(0,0,0,0);
		this.backButton.setEnabled(true);
        about.add(backButton, c);
		
		frame.add(about);
		frame.pack();
        frame.setSize(500, 650);
		frame.setVisible(false);
    }

    public void setVisible(boolean b) {
        frame.setVisible(b);
    }
}
