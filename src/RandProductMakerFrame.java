import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandProductMakerFrame extends JFrame
{
    String name, description, id;
    double price;
    static RandomAccessFile randomAccessFile;
    String road = "src/ProductInfo.txt";
    File preferredFile = new File(road);
    int records = 0;
    JPanel PanelM, PanelP, PanelC;
    JLabel labelName, labelDesc, labelid, labelprice, labelrecCount;
    static JTextField nameTF, descTF, idTF, costTF, recCountTF;
    JButton terminateBtn;
    JButton addBtn;
    JButton eraseBtn;
    public RandProductMakerFrame() {

        setTitle("The Machine Product Creator");
        setSize(1080, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createGUI();

        setVisible(true);
    }

    private void createGUI()
    {
        PanelM = new JPanel();
        PanelP = new JPanel();
        PanelC = new JPanel();

        PanelM.add(PanelP);
        PanelM.add(PanelC);

        add(PanelM);

        createPanelP();
        createPanelC();
    }

    private void createPanelP() {
        PanelP.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 1), "Product Information"));
        PanelP.setLayout(new GridLayout(3, 4));

        labelName = new JLabel("Name: ", JLabel.RIGHT);
        labelName.setSize(15,20);
        labelName.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,20));
        nameTF = new JTextField(10);
        nameTF.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));

        labelDesc = new JLabel("Description: ", JLabel.RIGHT);
        labelDesc.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,20));
        descTF = new JTextField(15);
        descTF.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));


        labelid = new JLabel("ID: ", JLabel.RIGHT);
        labelid.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,20));
        idTF = new JTextField(8);
        idTF.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));

        labelprice = new JLabel("Cost", JLabel.RIGHT);
        labelprice.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,20));
        costTF = new JTextField(5);
        costTF.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));

        labelrecCount = new JLabel("Record Count: ", JLabel.RIGHT);
        recCountTF = new JTextField(5);
        labelrecCount.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,20));
        recCountTF.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        recCountTF.setEditable(false);

        PanelP.add(labelName);
        PanelP.add(nameTF);
        PanelP.add(labelDesc);
        PanelP.add(descTF);
        PanelP.add(labelid);
        PanelP.add(idTF);
        PanelP.add(labelprice);
        PanelP.add(costTF);
        PanelP.add(labelrecCount);
        PanelP.add(recCountTF);

    }

    private void createPanelC() {
        PanelC.setLayout(new GridLayout(1, 3));
        addBtn = new JButton("Add");
        addBtn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,18));
        addBtn.addActionListener((ActionEvent ae) ->{
            if(inputValid()){
                name = namePadding(nameTF.getText());
                description = descPadding(descTF.getText());
                id = idPadding(idTF.getText());
                price = Double.parseDouble(costTF.getText());
                try {
                    randomAccessFile = new RandomAccessFile(preferredFile, "rw");
                    randomAccessFile.seek(randomAccessFile.length());
                    randomAccessFile.writeBytes(id + ", " + name + ", " + description + ", " + String.format("%.2f", price) + "\n");
                    randomAccessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                records++;
                recCountTF.setText(String.valueOf(records));
                nameTF.setText(null);
                descTF.setText(null);
                idTF.setText(null);
                costTF.setText(null);
                JOptionPane.showMessageDialog(this, "Record has been written to file", "Success!", JOptionPane.INFORMATION_MESSAGE);
            }
            else if (!inputValid()){
                JOptionPane.showMessageDialog(this, "Invalid Input", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        eraseBtn = new JButton("Erase");
        eraseBtn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,18));
        eraseBtn.addActionListener((ActionEvent ae) ->{
            nameTF.setText(null);
            descTF.setText(null);
            idTF.setText(null);
            costTF.setText(null);
            recCountTF.setText(null);
            JOptionPane.showMessageDialog(this, "Form Cleared!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        terminateBtn = new JButton("Quit");
        terminateBtn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,18));
        terminateBtn.addActionListener((ActionEvent ae) -> System.exit(0));
        PanelC.add(addBtn);
        PanelC.add(eraseBtn);
        PanelC.add(terminateBtn);
    }
    public static String namePadding(String name){
        do{
            name += " ";
        }while(name.length() < 35);
        return name;
    }
    public static String descPadding(String desc){
        do{
            desc += " ";
        }while(desc.length() < 75);
        return desc;
    }
    public static String idPadding(String id){
        do{
            id += " ";
        }while(id.length() < 6);
        return id;
    }
    public static boolean inputValid(){
        String name = nameTF.getText();
        String desc = descTF.getText();
        String id = idTF.getText();
        double cost = Double.parseDouble(costTF.getText());
        if(name.length() <= 35 && desc.length() <= 75 && id.length() <= 6 && costTF != null){
            return true;
        }
        else{
            return false;
        }
    }
}