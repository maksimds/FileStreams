import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.stream.Stream;

public class RandProductSearchFrame extends JFrame {
    JPanel panelM, PanelS, PanelT, PanelC;
    JTextField searchTF;
    JTextArea jTextArea;
    JScrollPane pane;
    JLabel searchLabel;
    JButton searchBut, quitBut;
    String res;
    ArrayList<String> arrayList = new ArrayList<>();
    StringBuffer stringBuffer;
    Stream<String> stream;
    String road = "src/ProductInfo.txt";
    File preferredFile = new File(road);
    public RandProductSearchFrame(){

        setTitle("Product at Random Search");
        setSize(980, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createGUI();

        setVisible(true);

    }

    private void createGUI() {
        panelM = new JPanel();
        PanelT = new JPanel();
        PanelS = new JPanel();
        PanelC = new JPanel();

        panelM.setLayout(new BorderLayout());
        panelM.add(PanelT, BorderLayout.NORTH);
        panelM.add(PanelS, BorderLayout.CENTER);
        panelM.add(PanelC,BorderLayout.SOUTH);

        add(panelM);

        createpanelNorth();
        createpanelCenter();
        createpanelSouth();
    }

    private void createpanelNorth() {
        searchLabel = new JLabel("Search Product Name: ");
        searchLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        searchTF = new JTextField("", 20);
        searchTF.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        PanelT.add(searchLabel);
        PanelT.add(searchTF);
    }

    private void createpanelCenter() {
        jTextArea = new JTextArea(30, 60);
        jTextArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        jTextArea.setEditable(false);
        pane = new JScrollPane(jTextArea);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        PanelS.add(pane);
    }

    private void createpanelSouth() {
        quitBut = new JButton("Quit");
        quitBut.addActionListener((ActionEvent ae) ->{
            System.exit(0);
        });
        searchBut = new JButton("Search");
        searchBut.addActionListener((ActionEvent ae ) ->{
            res = searchTF.getText();
            if(res.isEmpty()){
                JOptionPane.showMessageDialog(this, "Search field is empty, cannot search!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else{
                stringBuffer = new StringBuffer();
                try {
                    RandomAccessFile randomAccessFile = new RandomAccessFile(preferredFile, "rw");
                    while(randomAccessFile.getFilePointer() < randomAccessFile.length()){
                        arrayList.add(randomAccessFile.readLine() + "\n");
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            stream = arrayList.stream().filter(s -> s.contains(res));
            stream.forEach(s -> jTextArea.append(s));
        });
        PanelC.add(searchBut);
        PanelC.add(quitBut);
    }
}