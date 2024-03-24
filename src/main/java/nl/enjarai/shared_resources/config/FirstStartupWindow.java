package nl.enjarai.shared_resources.config;

import net.minecraft.util.Identifier;
import nl.enjarai.shared_resources.api.GameResource;
import nl.enjarai.shared_resources.api.GameResourceRegistry;
import nl.enjarai.shared_resources.util.directory.GameDirectoryProvider;
import nl.enjarai.shared_resources.util.directory.RootedGameDirectoryProvider;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.Timer;

// Base structure largely copied from oωo-sentinel
public class FirstStartupWindow {
    public static void open(SharedResourcesConfig config) throws Exception {
        // Fix AA
        System.setProperty("awt.useSystemAAFontSettings", "lcd");
        System.setProperty("swing.aatext", "true");

        // Force GTK if available
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        for (UIManager.LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
            if (!"GTK+".equals(laf.getName())) continue;
            UIManager.setLookAndFeel(laf.getClassName());
        }

        // ------
        // Window
        // ------

        JFrame window = new JFrame("Shared Resources first startup");
        window.setVisible(false);

        //noinspection ConstantConditions
        BufferedImage iconImage = ImageIO.read(FirstStartupWindow.class.getClassLoader()
                .getResourceAsStream("assets/shared-resources/icon.png"));

        window.setIconImage(iconImage);
        window.setMinimumSize(new Dimension(0, 250));
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                synchronized (FirstStartupWindow.class) {
                    FirstStartupWindow.class.notifyAll();
                }
            }
        });
        window.setLocationByPlatform(true);

        // -----
        // Title
        // -----

        JPanel titlePanel = new JPanel(new BorderLayout());

        Image smallImage = iconImage.getScaledInstance(64, 64, Image.SCALE_AREA_AVERAGING);
        JLabel titleLabel = new JLabel("<html><b>Customize<br>Shared Resources", new ImageIcon(smallImage), SwingConstants.LEFT);
        titleLabel.setFont(titleLabel.getFont().deriveFont(titleLabel.getFont().getSize() * 1.25f));
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        titleLabel.setBorder(new EmptyBorder(15, 15, 15, 15));

        titlePanel.add(titleLabel, BorderLayout.NORTH);


        JTextArea description = new JTextArea(2, 20);
        description.setText(
                "Shared Resources is available to override the location of your game directories. " +
                "\n\n" +
                "This window is provided as a convenient way to configure its settings before the game itself loads and starts populating them. " +
                "\n\n" +
                "All these settings can be changed later from the in-game config menu. " +
                "\n\n"
        );
        description.setWrapStyleWord(true);
        description.setLineWrap(true);
        description.setOpaque(false);
        description.setEditable(false);
        description.setFocusable(false);
        description.setBackground(UIManager.getColor("Label.background"));
        description.setFont(UIManager.getFont("Label.font"));
        description.setBorder(new EmptyBorder(0, 15, 15, 15));
        titlePanel.add(description, BorderLayout.CENTER);


        window.getContentPane().add(titlePanel, BorderLayout.WEST);

        // -------
        // Buttons
        // -------

        JPanel configPanel = new JPanel(new GridLayout(0, 1, 0, 5));
        configPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        configPanel.setMinimumSize(new Dimension(200, 0));

        // -------
        // Global dir selector
        // -------

        Path currentDir = Paths.get("");

        GameDirectoryProvider directoryProvider = config.getGlobalDirectory();
        if (directoryProvider instanceof RootedGameDirectoryProvider) {
            currentDir = ((RootedGameDirectoryProvider) directoryProvider).getRoot();
        }

        configPanel.add(new JLabel("Global directory:"));
        JButton globalDirButton = new JButton(currentDir.toString());

        Path finalCurrentDir = currentDir;

        globalDirButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setCurrentDirectory(finalCurrentDir.toFile());
            chooser.setDialogTitle("Select global directory");
            chooser.setApproveButtonText("Select");
            chooser.setApproveButtonToolTipText("Select this directory");
            chooser.setMultiSelectionEnabled(false);
            chooser.setDialogType(JFileChooser.CUSTOM_DIALOG);

            new SwingWorker<Integer, Void>() {
                @Override
                protected Integer doInBackground() {
                    return chooser.showOpenDialog(window);
                }

                @Override
                protected void done() {
                    try {
                        if (get() == JFileChooser.APPROVE_OPTION) {
                            File selectedFile = chooser.getSelectedFile();

                            chooser.setCurrentDirectory(selectedFile);
                            config.setGlobalDirectory(selectedFile.getAbsoluteFile().toPath());
                            globalDirButton.setText(selectedFile.getAbsolutePath());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.execute();
        });

        configPanel.add(globalDirButton);

        // -------
        // Resource toggles
        // -------

        configPanel.add(new JLabel("Enabled resources:"));

        List<Identifier> resources = new ArrayList<>(GameResourceRegistry.REGISTRY.getIds());
        Collections.sort(resources);
        resources.forEach(id -> {
            GameResource gameResource = GameResourceRegistry.REGISTRY.get(id);
            JCheckBox checkBox = new JCheckBox(gameResource.getDefaultPath().toString(), config.isEnabled(id));
            checkBox.addActionListener(e -> config.setEnabled(id, checkBox.isSelected()));
            configPanel.add(checkBox);
        });

        window.getContentPane().add(configPanel, BorderLayout.CENTER);

        // -------
        // Save and exit buttons
        // -------

        JPanel controlsPanel = new JPanel(new GridLayout(0, 1, 0, 3));
        controlsPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 0, 3, 0));

        JButton saveButton = new JButton("Save");

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(false);

        saveButton.addActionListener(e -> {
            saveButton.setEnabled(false);
            progressBar.setIndeterminate(true);

            // sleep for 1 second to make it look like it's doing something
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    window.dispose();

                    synchronized (FirstStartupWindow.class) {
                        FirstStartupWindow.class.notifyAll();
                    }
                }
            }, 1000);
        });

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));

        buttonsPanel.add(exitButton);
        buttonsPanel.add(saveButton);

        // ---------------
        // Window creation
        // ---------------

        controlsPanel.add(buttonsPanel);
        controlsPanel.add(progressBar);

        window.getContentPane().add(controlsPanel, BorderLayout.SOUTH);

        window.pack();
        window.setVisible(true);
        window.requestFocus();

        synchronized (FirstStartupWindow.class) {
            FirstStartupWindow.class.wait();
        }
    }
}
