import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GuiPrototype {
    private JPanel buttonPanel, inputPanel;
    private JTextArea output;
    
    //reads a whole file and return the contents as a string
    private static String getFileContents(String file) {
        String content = "";
        try {
            text = new String(Files.readAllBytes(Paths.get(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
    
    private void buildButtonPanel() {
        JButton createTablesBtn = new JButton("Create tables");
        JButton initTablesBtn = new JButton("Populate tables");
        JButton dropTablesBtn = new JButton("Drop tables");
        JButton advQueriesBtn = new JButton("Run advanced queries");
        JButton simpQueriesBtn = new JButton("Run simple queries");
        
        QuerySender qs = new QuerySender();
        createTablesBtn.addActionListener(qs);
        initTablesBtn.addActionListener(qs);
        dropTablesBtn.addActionListener(qs);
        advQueriesBtn.addActionListener(qs);
        simpQueriesBtn.addActionListener(qs);
        
        //qs will read the queries from the filenames given here
        //and send them to the db
        createTablesBtn.setActionCommand("create_tables.txt");
        initTablesBtn.setActionCommand("populate_tables.txt");
        dropTablesBtn.setActionCommand("drop_tables.txt");
        advQueriesBtn.setActionCommand("adv_queries.txt");
        simpQueriesBtn.setActionCommand("simp_queries.txt");

        buttonPanel.add(createTablesBtn);
        buttonPanel.add(initTablesBtn);
        buttonPanel.add(dropTablesBtn);
        buttonPanel.add(advQueriesBtn);
        buttonPanel.add(simpQueriesBtn);
    }

    /*public void buildInputPanel() {
        //this panel will have fields for inputting data into a table
        //it will be a cardlayout to switch "scenes" depending on the table
        //either a dropdown or multiple buttons to select which table
    }*/

    public GuiPrototype() {
        JFrame frame = new JFrame("Soccer League DBMS");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);

        buildButtonPanel();
        frame.add(buttonPanel);

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

    private class QuerySender implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //read queries from file specified by the button's actioncommand
            String queries = GuiPrototype.readFileContents(e.getActionCommand());
            //send the queries string to the db
            //output the result into the JTextArea output
        }
    }
}
