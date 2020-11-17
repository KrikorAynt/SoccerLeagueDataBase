import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GuiPrototype {
    //these are instance variables so they are accessible by the actionlisteners
    private JFrame frame;
    private JTextArea output;
    
    //reads a whole file and return the contents as a string
    private static String getFileContents(String file) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
    
    private JButton createButton(String text, String actioncmd, ActionListener al) {
        JButton newButton = new JButton(text);
        newButton.addActionListener(al);
        newButton.setActionCommand(actioncmd);
        return newButton;
    }

    private JPanel buildButtonPanel() {
        JPanel buttonPanel = new JPanel(new BorderLayout());
        JPanel top = new JPanel(new GridLayout(0,1));

        QuerySender qs = new QuerySender();
        top.add(createButton("Create tables", "create_tables.txt", qs));
        top.add(createButton("Populate tables", "populate_tables.txt", qs));
        top.add(createButton("Drop tables", "drop_tables.txt", qs));
        top.add(createButton("Run advanced queries", "adv_queries.txt", qs));
        top.add(createButton("Run simple queries", "simp_queries.txt", qs));

        buttonPanel.add(top, BorderLayout.PAGE_START);
        JButton exitBtn = new JButton("Exit");
        exitBtn.addActionListener(new ExitListener());
        buttonPanel.add(exitBtn, BorderLayout.PAGE_END);

        return buttonPanel;
    }

    private JPanel buildOutputPanel() {
        JPanel outPanel = new JPanel(new BorderLayout());

        output = new JTextArea(20,40);
        output.setEditable(false);
        JScrollPane sp = new JScrollPane(output);

        outPanel.add(new JLabel("Query output"), BorderLayout.PAGE_START);
        outPanel.add(sp, BorderLayout.CENTER);

        return outPanel;
    }

    /*private JPanel buildInputPanel() {
        //this panel will have fields for inputting data into a table
        //it will be a cardlayout to switch "scenes" depending on the table
        //either a dropdown or multiple buttons to select which table
    }*/

    public GuiPrototype() {
        frame = new JFrame("Soccer League DBMS");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        
        JPanel buttonPanel = buildButtonPanel();
        frame.add(buttonPanel, BorderLayout.LINE_START);
        
        JPanel outPanel = buildOutputPanel();
        frame.add(outPanel, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }
    
    /*may change depending on how the input panel is implemented

    private class TableSwitcher implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            CardLayout cl = (CardLayout)(inputPanel.getLayout());
            cl.show(inputPanel, e.getActionCommand());
        }
    }*/

    private class ExitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            GuiPrototype.this.frame.dispose();
            //TODO also disconnect from db
        }
    }

    private class QuerySender implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //read queries from file specified by the button's actioncommand
            String queries = GuiPrototype.getFileContents(e.getActionCommand());
            /* TODO: 
             * send the queries string to the db
             * output the result into the JTextArea output
             */
            System.out.println(queries); //just for testing
        }
    }

    public static void main(String[] args) {
        GuiPrototype gp = new GuiPrototype();
    }
}
