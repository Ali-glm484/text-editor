package editor;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class TextEditor extends JFrame {
    private final FileManager fileManager;
    private final JTextArea textArea;

    public TextEditor() {
        setTitle("Plain text editor");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        textArea = new JTextArea();
        fileManager = new FileManager(textArea);

        JScrollPane scrollPane = new JScrollPane(textArea);
        setJMenuBar(createMenuBar());
        add(scrollPane, BorderLayout.CENTER);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem newFile = new JMenuItem("New");
        JMenuItem open = new JMenuItem("Opening");
        JMenuItem save = new JMenuItem("Save");

        JMenu editMenu = new JMenu("Edit");
        JMenuItem cut = new JMenuItem("Cut");
        JMenuItem copy = new JMenuItem("Copy");
        JMenuItem paste = new JMenuItem("Paste");
        JMenuItem findReplace = new JMenuItem("Search and replace");

        newFile.addActionListener(e -> fileManager.newFile());
        open.addActionListener(e -> fileManager.openFile());
        save.addActionListener(e -> fileManager.saveFile());

        cut.addActionListener(e -> ActionHandlers.cut(textArea));
        copy.addActionListener(e -> ActionHandlers.copy(textArea));
        paste.addActionListener(e -> ActionHandlers.paste(textArea));
        findReplace.addActionListener(e -> {
            FindReplaceDialog dialog = new FindReplaceDialog(this, textArea);
            dialog.setVisible(true);
        });

        fileMenu.add(newFile);
        fileMenu.addSeparator();
        fileMenu.add(open);
        fileMenu.add(save);

        editMenu.add(cut);
        editMenu.add(copy);
        editMenu.add(paste);
        editMenu.addSeparator();
        editMenu.add(findReplace);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        return menuBar;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TextEditor editor = new TextEditor();
            editor.setVisible(true);
        });
    }
}