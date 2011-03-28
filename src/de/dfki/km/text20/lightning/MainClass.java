/*
 * MainClass.java
 *
 * Copyright (c) 2011, Christoph Käding, DFKI. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 *
 */
package de.dfki.km.text20.lightning;

import static net.jcores.CoreKeeper.$;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.UIManager;

import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.util.JSPFProperties;
import net.xeoh.plugins.diagnosis.local.Diagnosis;
import net.xeoh.plugins.diagnosis.local.DiagnosisChannel;

import com.melloware.jintellitype.JIntellitype;

import de.dfki.km.augmentedtext.services.language.statistics.Statistics;
import de.dfki.km.text20.lightning.diagnosis.channels.tracing.LightningTracer;
import de.dfki.km.text20.lightning.gui.TraySymbol;
import de.dfki.km.text20.lightning.hotkey.Hotkey;
import de.dfki.km.text20.lightning.plugins.InternalPluginManager;
import de.dfki.km.text20.lightning.worker.FixationCatcher;
import de.dfki.km.text20.lightning.worker.clickTo.FixationEvaluator;
import de.dfki.km.text20.lightning.worker.training.PrecisionTrainer;
import de.dfki.km.text20.lightning.worker.warpMouse.WarpCommander;

/**
 * Main entry point.
 * 
 * @author Christoph Käding
 */
public class MainClass {

    /** indicates if the program should react on hotkeys */
    private boolean isActivated;

    /** indicates if normal mode or trainings mode is activated */
    private boolean isNormalMode;

    /** pluginmanager for the different methods */
    private InternalPluginManager internalPluginManager;

    /** icon which is shown in the system tray */
    private TraySymbol trayIcon;

    /** global pluinmanager which handles all plugins */
    private PluginManager pluginManager;

    /** global properties of the tool which will be stored between session*/
    private Properties properties;

    /** indicates if the JIntellytype.dll is there */
    private boolean dllStatus;

    /** logging channel */
    private DiagnosisChannel<String> channel;

    /** instance of mainclass */
    private static MainClass main;

    /** warps mouse cursor */
    private WarpCommander warper;

    /** statistics plugin */
    private Statistics statistics;

    /** necessary to identify the training data */
    private String[] trainingsSettings;

    /** collects training data */
    private PrecisionTrainer trainer;

    /** indicates if the trackinddata is valid */
    private boolean trackingValid;

    /** ding sound */
    private AudioClip soundDing;

    /** error sound */
    private AudioClip soundError;

    /** indicates if the tool is initialized successfully */
    private boolean allFine;

    /**
     * creates a new instance of the mainclass and initializes it
     * 
     * @param args
     */
    public static void main(String[] args) {
        MainClass.getInstance().init();
    }

    /**
     * all needed stuff is initialized here
     * also the application starts 
     */
    private void init() {
        // Set global look and feel. 
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.out.println("Unable to load native look and feel.\n");
        }

        // set logging properties
        final JSPFProperties props = new JSPFProperties();
        props.setProperty(Diagnosis.class, "recording.enabled", "true");
        props.setProperty(Diagnosis.class, "recording.file", "diagnosis.record");
        props.setProperty(Diagnosis.class, "recording.format", "java/serialization");
        props.setProperty(Diagnosis.class, "analysis.stacktraces.enabled", "true");
        props.setProperty(Diagnosis.class, "analysis.stacktraces.depth", "10000");

        // set statistic properties
        props.setProperty(Statistics.class, "application.id", "StatisticsTest");

        // initialize plugin manager
        this.pluginManager = PluginManagerFactory.createPluginManager(props);

        // add plugins at classpath
        try {
            this.pluginManager.addPluginsFrom(new URI("classpath://*"));
            this.pluginManager.addPluginsFrom(new File("plugins/").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // initialize other variables
        this.allFine = false;
        this.statistics = this.pluginManager.getPlugin(Statistics.class); // FIXME: sth strange with statistic
        this.channel = this.pluginManager.getPlugin(Diagnosis.class).channel(LightningTracer.class);
        this.properties = new Properties();
        this.internalPluginManager = new InternalPluginManager(this.pluginManager);
        this.trayIcon = new TraySymbol();
        this.dllStatus = this.checkDll();
        this.isActivated = true;
        this.isNormalMode = true;

        // indicate start
        System.out.println("\nSession started.\n");
        this.channel.status("Session started.");

        // Creates classes which are needed for the three parts (clicking, warping and training) of this tool.
        FixationEvaluator fixationEvaluator = new FixationEvaluator();
        this.trainer = new PrecisionTrainer();
        this.warper = new WarpCommander();
        Thread warpThread = new Thread(this.warper);
        Thread evaluationThread = new Thread(fixationEvaluator);

        // main component which listen on trackingevents
        FixationCatcher fixationCatcher = new FixationCatcher(fixationEvaluator, this.trainer);

        // check if all things are fine
        if (this.dllStatus) {

            // initialize hotkeys
            Hotkey.init(fixationEvaluator, this.trainer);

            // load sounds
            this.soundDing = Applet.newAudioClip(MainClass.class.getResource("resources/ding.wav"));
            this.soundError = Applet.newAudioClip(MainClass.class.getResource("resources/error.wav"));

            if (fixationCatcher.getStatus()) {

                // start listening
                warpThread.start();
                evaluationThread.start();
                fixationCatcher.startCatching();
                this.warper.start();

                // indicate success
                this.showTrayMessage("Initializing successful.");
                System.out.println("\nInitializing successful.\n");
                this.channel.status("Initializing successful.");

                // update status
                this.allFine = true;
            }
        }
    }

    /**
     * gives an object of the mainclass back
     * 
     * @return a singleton instance
     */
    public static MainClass getInstance() {
        if (main == null) {
            main = new MainClass();
        }
        return main;
    }

    /**
     * adds the given text to the statistics which are provided by plugin
     * 
     * @param text
     */
    public void addToStatistic(String text) {
        //        this.statistics.
    }

    /**
     * provides internal plugin manager
     * 
     * @return internal plugin manager
     */
    public InternalPluginManager getInternalPluginManager() {
        return this.internalPluginManager;
    }

    /**
     * provides the logging channel
     * 
     * @return the diagnosis channel
     */
    public DiagnosisChannel<String> getChannel() {
        return this.channel;
    }

    /**
     * Indicates if the program should react on hotkeys. 
     * 
     * @return true is activated
     */
    public boolean isActivated() {
        return this.isActivated;
    }

    /**
     * refreshes warper
     */
    public void refreshWarper() {
        this.warper.stop();
        this.warper.start();
    }

    /**
     * Toggles between active and inactive status.
     */
    public void toggleStatus() {
        if (this.isActivated) {

            // show change in tray
            this.showTrayMessage("Status: tool is now deactivated");
            this.trayIcon.setDeactivatedIcon();

            // deactivate warper
            this.warper.stop();

            // change status
            this.isActivated = false;

        } else {

            // show change in tray
            this.showTrayMessage("Status: tool is now activated");
            this.trayIcon.setActivatedIcon();

            // activate warper
            this.warper.start();

            // change status
            this.isActivated = true;
        }
    }

    /**
     * @return the status allFine
     */
    public boolean isAllFine() {
        return this.allFine;
    }

    /**
     * Shuts down the application
     */
    public void exit() {

        // store properties to a file
        this.properties.writeProperties();

        // close plugins
        this.internalPluginManager.getCurrentMouseWarper().stop();
        this.internalPluginManager.getCurrentSaliencyDetector().stop();

        // make the trainer known that the training is over
        this.trainer.leaveTraining();

        if (this.dllStatus) {
            // deactivate the hotkeys
            JIntellitype.getInstance().cleanUp();

            // deactivate warper
            this.warper.stop();
        }

        // removes icon from system tray
        this.trayIcon.remove();

        // disables plugins
        this.pluginManager.shutdown();

        this.channel.status("Session closed.");
        System.out.println("\nSession closed.");

        // close the tool
        System.exit(0);
    }

    /**
     * Indicates if the tool is in normal or trainings mode.
     * 
     * @return true if is in normal mode 
     */
    public boolean isNormalMode() {
        return this.isNormalMode;
    }

    /**
     * Toggles mode between normal an trainings mode.
     */
    public void toggleMode() {
        if (this.isNormalMode) {

            // shows change in tray and console
            this.showTrayMessage("Modus: trainings mode activated");

            // change mode
            this.isNormalMode = false;

            // deactivate warper
            this.warper.stop();

        } else {

            // shows change in tray and console
            this.showTrayMessage("Modus: normal mode activated");

            // make the trainer known that the training is over
            this.trainer.leaveTraining();

            // change mode
            this.isNormalMode = true;

            // activate warper
            this.warper.start();
        }
    }

    /**
     * shows the given text as popup on the tray icon
     * 
     * @param caption
     * @param text
     */
    public void showTrayMessage(String text) {
        this.trayIcon.showMessage("Project Lightning (Desktop)", text);
    }

    /**
     * returns global used pluginmanager, because only one of them should be used at the same time
     * 
     * @return pluginManager
     */
    public PluginManager getPluginManager() {
        return this.pluginManager;
    }

    /**
     * resets trainer, used to notify new user names
     * 
     * @param name
     */
    public void resetTrainer(String name) {
        this.trainer.leaveTraining();
    }

    /**
     * returns the actual properties which includes all configurations which can be changed in the gui
     * 
     * @return current properties object
     */
    public Properties getProperties() {
        return this.properties;
    }

    /**
     * @return the trainingsSettings
     *      0 = user name
     *      1 = screen brightness
     *      2 = setting brightness
     */
    public String[] getTrainingsSettings() {
        if (this.trainingsSettings == null) {
            this.trainingsSettings = new String[3];
            this.trainingsSettings[0] = "DefaultUser";
            this.trainingsSettings[1] = "not choosen";
            this.trainingsSettings[2] = "not choosen";
        }
        return this.trainingsSettings;
    }

    /**
     * @param settings 
     *      0 = user name
     *      1 = screen brightness
     *      2 = setting brightness
     */
    public void setTrainingsSettings(String[] settings) {
        this.trainingsSettings = settings;
    }

    /**
     * @return the trackingValid
     */
    public boolean isTrackingValid() {
        return this.trackingValid;
    }

    /**
     * @param trackingValid the trackingValid to set
     */
    public void setTrackingValid(boolean trackingValid) {
        this.trackingValid = trackingValid;
    }

    /**
     * plays the ding sound
     */
    public void playDing() {
        if (!this.properties.isSoundActivated()) return;
        this.soundDing.play();
    }

    /**
     * plays the error sound
     */
    public void playError() {
        if (!this.properties.isSoundActivated()) return;
        this.soundError.play();
    }

    /**
     * Checks if the required JIntellitype.dll is placed in the windows directory.
     * If it is not there it tries to unzip it into the System32 directory.
     * If this fails, a message is displayed and the dll is unziped into the "." directory.
     * 
     * @return true if it is there or the copy was successful 
     */
    // FIXME: works only with admin rights
    private boolean checkDll() {

        // create target file
        File destination = new File(System.getenv("SYSTEMROOT") + "/System32/JIntellitype.dll");

        // check if it is already the
        if (!destination.exists()) {

            System.out.println("\nJIntellytype.dll was not found.");

            // try to unzip it to the windows directory
            $(MainClass.class.getResourceAsStream("resources/JIntellitype.zip")).zipstream().unzip(System.getenv("SYSTEMROOT") + "/System32/");

            if (destination.exists()) {
                System.out.println("... but is now placed.\n");
                // return successful
                return true;
            }

            // try to unzip it to "."
            $(MainClass.class.getResourceAsStream("resources/JIntellitype.zip")).zipstream().unzip(".");

            // indicate error 
            String msg = new String("Initializing failed. A necessary DLL-file, 'JIntellitype.dll', could not be copied into your " + destination.getParent() + " directory. Please do it by yourself or run Project Lightning (Desktop) with granted administration rights.");
            this.showTrayMessage(msg);
            System.out.println("\n" + msg + "\n");
            this.channel.status(msg);

            // return not successful
            return false;
        }

        // return successful
        return true;
    }
}
