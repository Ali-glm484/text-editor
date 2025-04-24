package editor;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileManager {
    private String currentFile;
    private final JTextArea textArea;

    public FileManager(JTextArea textArea) {
        this.textArea = textArea;
    }

    public void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            currentFile = selectedFile.getAbsolutePath();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(selectedFile),
                            StandardCharsets.UTF_8
                    )
            )) {

                StringBuilder content = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }

                textArea.setText(content.toString());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "خطا در باز کردن فایل!");
            }
        }
    }

    public void saveFile() {
        if (currentFile == null || currentFile.isEmpty()) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new File("untitled.txt"));
            int result = fileChooser.showSaveDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                if(!selectedFile.getName().contains(".")) {
                    selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
                }

                currentFile = selectedFile.getAbsolutePath();
            } else {
                return;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(currentFile),
                        StandardCharsets.UTF_8
                )
        )) {

            writer.write(textArea.getText());
            JOptionPane.showMessageDialog(null, "فایل با موفقیت ذخیره شد!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "خطا در ذخیره فایل!");
        }
    }

    public void newFile() {
        currentFile = null;
        textArea.setText("");
    }
}