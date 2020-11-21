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
    JdbcHelper jh = new JdbcHelper();
    
    //reads a whole file and return the contents as a string array
    private static String[] getFileContents(String file) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.split(";");
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
    private JPanel buildInputCard(ArrayList x, String table){
        JPanel inCard = new JPanel();
        JPanel top = new JPanel(new GridLayout(0,2));
        for(Object z: x){
           top.add(new JLabel(z.toString()));
           top.add(createTextField());
        }
        JButton submitBtn = new JButton("Submit");
        submitBtn.addActionListener(new SubmitListener(top, table));
        top.add(submitBtn, BorderLayout.PAGE_END);
        inCard.add(top);
        
        return inCard;
    }
    private void buildInputPanel() {
        ArrayList player = jh.getColumnNames("player");
        ArrayList coach = jh.getColumnNames("coach");
        ArrayList referee =  jh.getColumnNames("referee");
        ArrayList team =  jh.getColumnNames("team");
        ArrayList stadium = jh.getColumnNames("stadium");
        ArrayList game = jh.getColumnNames("game");
        ArrayList goal = jh.getColumnNames("goal");

        inputPanel= new JPanel(new CardLayout());
        
        playerCard = buildInputCard(player, "player");
        coachCard = buildInputCard(coach, "coach");
        refereeCard = buildInputCard(referee, "referee");
        teamCard = buildInputCard(team, "team");
        stadiumCard = buildInputCard(stadium, "stadium");
        gameCard = buildInputCard(game, "game");
        goalCard = buildInputCard(goal, "goal");
        
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
        private String table;

        public SubmitListener(JPanel card, String table) {
            this.card = card;
            this.table = table;
        }
       
        //INSERT INTO table (fields) VALUES (data)
        public void actionPerformed(ActionEvent e) {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO " + table + " (");
            ArrayList fields = GuiPrototype.this.jh.getColumnNames(table);
            for (int i = 0; i < fields.size(); i++) {
                sb.append(fields.get(i).toString());
                if (i < fields.size() - 1) {
                    sb.append(",");
                }
            }
            sb.append(") VALUES (");
            //for (Component component : card.getComponents()) {
            Component[] components = card.getComponents();
            for (int i = 0; i < components.length; i++) {
                if (components[i] instanceof JTextField) {
                    sb.append(((JTextField) components[i]).getText());
                    if (i < components.length - 2) {
                        sb.append(",");
                    }
                }
            }
            sb.append(")");
            System.out.println(sb.toString());
            GuiPrototype.this.jh.query(sb.toString());
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
            jh.closeConnection();
        }
    }

    private class QuerySender implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //read queries from file specified by the button's actioncommand
            String[] queries = GuiPrototype.getFileContents(e.getActionCommand());
            output.setText("");
            for (String query : queries) {
                String result = jh.query(query);
                output.append(result + "\n");
            }
            output.append("Success.");
        }
    }

    public static void main(String[] args) {
        GuiPrototype gp = new GuiPrototype();
    }
}
