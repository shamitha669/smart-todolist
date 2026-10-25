import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SmartTodoGUI extends JFrame implements ActionListener {

    JLabel titleLabel, taskLabel, priorityLabel;
    JTextField taskField;
    JComboBox<String> priorityBox;
    JButton addButton, completeButton, deleteButton;
    DefaultListModel<String> taskList;
    JList<String> list;
    JScrollPane scrollPane;

    public SmartTodoGUI() {

        setTitle("Smart To-Do Task Manager");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        titleLabel = new JLabel("SMART TO-DO TASK MANAGER");
        titleLabel.setBounds(70, 20, 380, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 19));
        add(titleLabel);

        taskLabel = new JLabel("Enter Task:");
        taskLabel.setBounds(30, 80, 100, 25);
        add(taskLabel);

        taskField = new JTextField();
        taskField.setBounds(130, 80, 300, 25);
        add(taskField);

        priorityLabel = new JLabel("Priority:");
        priorityLabel.setBounds(30, 120, 100, 25);
        add(priorityLabel);

        String[] priorities = {"High", "Medium", "Low"};

        priorityBox = new JComboBox<>(priorities);
        priorityBox.setBounds(130, 120, 150, 25);
        add(priorityBox);

        addButton = new JButton("Add Task");
        addButton.setBounds(30, 165, 120, 30);
        addButton.addActionListener(this);
        add(addButton);

        completeButton = new JButton("Complete");
        completeButton.setBounds(170, 165, 120, 30);
        completeButton.addActionListener(this);
        add(completeButton);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(310, 165, 120, 30);
        deleteButton.addActionListener(this);
        add(deleteButton);

        taskList = new DefaultListModel<>();
        list = new JList<>(taskList);

        scrollPane = new JScrollPane(list);
        scrollPane.setBounds(30, 220, 400, 200);
        add(scrollPane);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == addButton) {

            String task = taskField.getText().trim();
            String priority = (String) priorityBox.getSelectedItem();

            if (task.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a task!");
            } else {
                taskList.addElement(task + " - Priority: " + priority);
                taskField.setText("");

                sortTasks();

                JOptionPane.showMessageDialog(this,
                        "Task added successfully!");
            }
        }

        else if (e.getSource() == completeButton) {

            int index = list.getSelectedIndex();

            if (index != -1) {
                String task = taskList.get(index);

                if (!task.startsWith("[Completed] ")) {
                    taskList.set(index, "[Completed] " + task);
                    JOptionPane.showMessageDialog(this,
                            "Task completed!");
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Task is already completed!");
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please select a task!");
            }
        }

        else if (e.getSource() == deleteButton) {

            int index = list.getSelectedIndex();

            if (index != -1) {
                taskList.remove(index);
                JOptionPane.showMessageDialog(this,
                        "Task deleted!");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please select a task!");
            }
        }
    }

    void sortTasks() {

        java.util.ArrayList<String> tasks =
                new java.util.ArrayList<>();

        for (int i = 0; i < taskList.size(); i++) {
            tasks.add(taskList.get(i));
        }

        tasks.sort((a, b) ->
                getPriority(a) - getPriority(b));

        taskList.clear();

        for (String task : tasks) {
            taskList.addElement(task);
        }
    }

    int getPriority(String task) {

        if (task.contains("Priority: High"))
            return 1;

        if (task.contains("Priority: Medium"))
            return 2;

        return 3;
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
                new SmartTodoGUI());
    }
}