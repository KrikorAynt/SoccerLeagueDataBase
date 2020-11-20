import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class GuiPrototype {
    //these are instance variables so they are accessible by the actionlisteners
    private JFrame frame;
    private JTextArea output;
    private JPanel inputPanel;
    private JPanel playerCard ;
    private JPanel coachCard ;
    private JPanel refereeCard ;
    private JPanel teamCard ;
    private JPanel stadiumCard ;
    private JPanel gameCard;
    private JPanel goalCard ;
    
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
    private JTextField createTextField() {
        JTextField tf = new JTextField();
        
        return tf;
    }

    private JPanel buildButtonPanel() {
        JPanel buttonPanel = new JPanel(new BorderLayout());
        JPanel top = new JPanel(new GridLayout(0,1));

        QuerySender qs = new QuerySender();
        TableSwitcher ts = new TableSwitcher();
        top.add(createButton("Create tables", "create_tables.txt", qs));
        top.add(createButton("Populate tables", "populate_tables.txt", qs));
        top.add(createButton("Drop tables", "drop_tables.txt", qs));
        top.add(createButton("Run advanced queries", "adv_queries.txt", qs));
        top.add(createButton("Run simple queries", "simp_queries.txt", qs));
        top.add(createButton("Create Player", "playerCard", ts));
        top.add(createButton("Create Coach", "coachCard", ts));
        top.add(createButton("Create Referee", "refereeCard", ts));
        top.add(createButton("Create Team", "teamCard", ts));
        top.add(createButton("Create Stadium", "stadiumCard", ts));
        top.add(createButton("Create Game", "gameCard", ts));
        top.add(createButton("Create Goal", "goalCard", ts));

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
    private JPanel buildInputCard(ArrayList x){
        JPanel inCard = new JPanel();
        JPanel top = new JPanel(new GridLayout(0,2));
        for(Object z: x){
           top.add(new JLabel(z.toString()));
           top.add(createTextField());
        }
        JButton submitBtn = new JButton("Submit");
        submitBtn.addActionListener(new SubmitListener(top));
        top.add(submitBtn, BorderLayout.PAGE_END);
        inCard.add(top);
        
        return inCard;
    }
    private void buildInputPanel() {
        ArrayList player = new ArrayList(Arrays.asList("First Name", "Last Name", "Jersey Number"));
        ArrayList coach = new ArrayList(Arrays.asList("First Name", "Last Name", "Team Name"));
        ArrayList referee = new ArrayList(Arrays.asList("First Name", "Last Name"));
        ArrayList team = new ArrayList(Arrays.asList("Team Name", "Jersey Color"));
        ArrayList stadium = new ArrayList(Arrays.asList("Stadium Name", "Team Name"));
        ArrayList game = new ArrayList(Arrays.asList("Team 1", "Team 2", "Referee"));
        ArrayList goal = new ArrayList(Arrays.asList("Player ID", "Time Scored", "GameID"));
        
        inputPanel= new JPanel(new CardLayout());
        
         playerCard = buildInputCard(player);
         coachCard = buildInputCard(coach);
         refereeCard = buildInputCard(referee);
         teamCard = buildInputCard(team);
         stadiumCard = buildInputCard(stadium);
         gameCard = buildInputCard(game);
         goalCard = buildInputCard(goal);
        
        inputPanel.add(playerCard,"playerCard");
        inputPanel.add(coachCard,"coachCard");
        inputPanel.add(refereeCard,"refereeCard");
        inputPanel.add(teamCard,"teamCard");
        inputPanel.add(stadiumCard,"stadiumCard");
        inputPanel.add(gameCard,"gameCard");
        inputPanel.add(goalCard,"goalCard");
        
        
        
    }

    public GuiPrototype() {
        frame = new JFrame("Soccer League DBMS");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        
        JPanel buttonPanel = buildButtonPanel();
        frame.add(buttonPanel, BorderLayout.LINE_START);
        
        JPanel outPanel = buildOutputPanel();
        frame.add(outPanel, BorderLayout.CENTER);
        
        buildInputPanel();
        frame.add(inputPanel, BorderLayout.LINE_END);

        frame.pack();
        frame.setVisible(true);
    }

    private class SubmitListener implements ActionListener {
        private JPanel card;

        public SubmitListener(JPanel card) {
            this.card = card;
        }
        
        public void actionPerformed(ActionEvent e) {
            
            for (Component component : card.getComponents()) {
            if (component instanceof JTextField) {
                System.out.println(((JTextField) component).getText());
            }
        }
        }
    }
    
    //may change depending on how the input panel is implemented
    private class TableSwitcher implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            CardLayout cl = (CardLayout)(inputPanel.getLayout());
            cl.show(inputPanel, e.getActionCommand());
        }
    }

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
