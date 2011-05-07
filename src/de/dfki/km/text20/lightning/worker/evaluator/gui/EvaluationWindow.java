/*
 * Created by JFormDesigner on Fri Mar 04 14:58:19 CET 2011
 */

package de.dfki.km.text20.lightning.worker.evaluator.gui;

import java.awt.*;
import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;

/**
 * @author Ralf Biedert
 */
@SuppressWarnings("all")
public class EvaluationWindow {
    public EvaluationWindow() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        mainFrame = new JFrame();
        dialogPane = new JPanel();
        labelDescription = new JLabel();
        comboBoxTextPlugin = new JComboBox();
        separator2 = new JSeparator();
        labelDimension = new JLabel();
        spinnerDimension = new JSpinner();
        separator3 = new JSeparator();
        checkBoxImages = new JCheckBox();
        buttonTextConfig = new JButton();
        labelInterval = new JLabel();
        spinnerInterval = new JSpinner();
        checkBoxSummary = new JCheckBox();
        labelThresh = new JLabel();
        spinnerThresh = new JSpinner();
        scrollPane1 = new JScrollPane();
        listFiles = new JList();
        buttonSelect = new JButton();
        buttonRemove = new JButton();
        scrollPane2 = new JScrollPane();
        listDetectors = new JList();
        checkBoxConfiguration = new JCheckBox();
        buttonConfiguration = new JButton();
        separator1 = new JSeparator();
        buttonStart = new JButton();
        progressBar = new JProgressBar();

        //======== mainFrame ========
        {
            mainFrame.setTitle("Project Lightning (Desktop) Evaluator");
            mainFrame.setResizable(false);
            Container mainFrameContentPane = mainFrame.getContentPane();
            mainFrameContentPane.setLayout(new BorderLayout());

            //======== dialogPane ========
            {
                dialogPane.setBorder(Borders.DIALOG_BORDER);
                dialogPane.setLayout(new GridBagLayout());
                ((GridBagLayout)dialogPane.getLayout()).columnWidths = new int[] {80, 10, 80, 10, 80, 10, 80, 10, 80, 10, 80, 0};
                ((GridBagLayout)dialogPane.getLayout()).rowHeights = new int[] {0, 10, 20, 10, 0, 10, 0, 10, 100, 10, 0, 10, 100, 10, 0, 10, 5, 10, 0, 0};
                ((GridBagLayout)dialogPane.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 1.0E-4};
                ((GridBagLayout)dialogPane.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

                //---- labelDescription ----
                labelDescription.setText("text");
                dialogPane.add(labelDescription, new GridBagConstraints(0, 0, 11, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
                dialogPane.add(comboBoxTextPlugin, new GridBagConstraints(0, 2, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //---- separator2 ----
                separator2.setOrientation(SwingConstants.VERTICAL);
                dialogPane.add(separator2, new GridBagConstraints(3, 2, 1, 5, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                    new Insets(0, 0, 0, 0), 0, 0));

                //---- labelDimension ----
                labelDimension.setText("Dimension");
                dialogPane.add(labelDimension, new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //---- spinnerDimension ----
                spinnerDimension.setModel(new SpinnerNumberModel(200, 0, 50000, 1));
                dialogPane.add(spinnerDimension, new GridBagConstraints(6, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //---- separator3 ----
                separator3.setOrientation(SwingConstants.VERTICAL);
                dialogPane.add(separator3, new GridBagConstraints(7, 2, 1, 5, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                    new Insets(0, 0, 0, 0), 0, 0));

                //---- checkBoxImages ----
                checkBoxImages.setText("write images");
                dialogPane.add(checkBoxImages, new GridBagConstraints(8, 2, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //---- buttonTextConfig ----
                buttonTextConfig.setText("Configuration");
                dialogPane.add(buttonTextConfig, new GridBagConstraints(0, 4, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //---- labelInterval ----
                labelInterval.setText("Interval");
                dialogPane.add(labelInterval, new GridBagConstraints(4, 4, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //---- spinnerInterval ----
                spinnerInterval.setModel(new SpinnerNumberModel(1, 1, null, 1));
                dialogPane.add(spinnerInterval, new GridBagConstraints(6, 4, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //---- checkBoxSummary ----
                checkBoxSummary.setText("write summary");
                dialogPane.add(checkBoxSummary, new GridBagConstraints(8, 4, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //---- labelThresh ----
                labelThresh.setText("Threshold");
                dialogPane.add(labelThresh, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
                dialogPane.add(spinnerThresh, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //======== scrollPane1 ========
                {

                    //---- listFiles ----
                    listFiles.setVisibleRowCount(3);
                    scrollPane1.setViewportView(listFiles);
                }
                dialogPane.add(scrollPane1, new GridBagConstraints(0, 8, 11, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //---- buttonSelect ----
                buttonSelect.setText("Select");
                dialogPane.add(buttonSelect, new GridBagConstraints(0, 10, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //---- buttonRemove ----
                buttonRemove.setText("Remove");
                dialogPane.add(buttonRemove, new GridBagConstraints(4, 10, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //======== scrollPane2 ========
                {
                    scrollPane2.setViewportView(listDetectors);
                }
                dialogPane.add(scrollPane2, new GridBagConstraints(0, 12, 11, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //---- checkBoxConfiguration ----
                checkBoxConfiguration.setText("Configuration");
                dialogPane.add(checkBoxConfiguration, new GridBagConstraints(0, 14, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //---- buttonConfiguration ----
                buttonConfiguration.setText("Configuration");
                dialogPane.add(buttonConfiguration, new GridBagConstraints(4, 14, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
                dialogPane.add(separator1, new GridBagConstraints(0, 16, 11, 1, 0.0, 0.0,
                    GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));

                //---- buttonStart ----
                buttonStart.setText("text");
                dialogPane.add(buttonStart, new GridBagConstraints(0, 18, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
                dialogPane.add(progressBar, new GridBagConstraints(4, 18, 7, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            mainFrameContentPane.add(dialogPane, BorderLayout.CENTER);
            mainFrame.setSize(505, 455);
            mainFrame.setLocationRelativeTo(mainFrame.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    protected JFrame mainFrame;
    private JPanel dialogPane;
    protected JLabel labelDescription;
    protected JComboBox comboBoxTextPlugin;
    private JSeparator separator2;
    protected JLabel labelDimension;
    protected JSpinner spinnerDimension;
    private JSeparator separator3;
    protected JCheckBox checkBoxImages;
    protected JButton buttonTextConfig;
    protected JLabel labelInterval;
    protected JSpinner spinnerInterval;
    protected JCheckBox checkBoxSummary;
    protected JLabel labelThresh;
    protected JSpinner spinnerThresh;
    private JScrollPane scrollPane1;
    protected JList listFiles;
    protected JButton buttonSelect;
    protected JButton buttonRemove;
    private JScrollPane scrollPane2;
    protected JList listDetectors;
    protected JCheckBox checkBoxConfiguration;
    protected JButton buttonConfiguration;
    private JSeparator separator1;
    protected JButton buttonStart;
    protected JProgressBar progressBar;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
