/*
 * EvaluationThread.java
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
 */
package de.dfki.km.text20.lightning.components.evaluator.worker;

import java.io.File;
import java.util.ArrayList;

import de.dfki.km.text20.lightning.components.evaluationmode.StorageContainer;
import de.dfki.km.text20.lightning.components.evaluator.EvaluatorMain;
import de.dfki.km.text20.lightning.components.evaluator.plugins.CoverageAnalyser;
import de.dfki.km.text20.lightning.plugins.saliency.SaliencyDetector;

/**
 * This thread runs through all given files and detectors and evaluates them.
 * 
 * @author Christoph Käding
 *
 */
public class EvaluationThread implements Runnable {

    /** list of the from listDetectors selected detectors */
    private ArrayList<SaliencyDetector> selectedDetectors;

    /** evaluation worker which runs the detectors */
    private EvaluatorWorker worker;

    /** selected *.xml files */
    private ArrayList<File> files;

    /** given main class */
    private EvaluatorMain mainClass;

    /** indicates if the thread should be stopped */
    private boolean stop;

    /** current used dimension */
    private int dimension;

    /**
     * initializes necessary variables
     * 
     * @param main
     */
    public void init(EvaluatorMain main) {
        this.mainClass = main;
        this.files = main.getFiles();
        this.selectedDetectors = main.getSelectedDetectors();
        this.worker = main.getWorker();
        this.stop = false;
        this.dimension = main.getDimension();
    }

    /**
     * stops the processing
     */
    public void stop() {
        this.stop = true;
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        // initialize Variables
        DataXMLParser dataParser = new DataXMLParser();
        PupilXMLParser pupilParser = new PupilXMLParser();
        CoverageAnalyser analyser = this.mainClass.getCoverageAnalyser();

        // run through every file ...
        for (File file : this.files) {

            System.out.println("- File " + file.getName() + " is the next one.");

            // ... read related pupil stream, ...
            pupilParser.readFile(new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("_") + 1) + "pupils.xml"), this.worker);

            // ... and every detector
            for (SaliencyDetector detector : this.selectedDetectors) {

                // start current detector
                detector.start();

                // indicate current detector
                System.out.println("- Detector: " + detector.getInformation().getDisplayName());

                // ... and through every container in it ...
                for (StorageContainer container : dataParser.readFile(file, this.dimension, this.worker)) {

                    // process evaluation
                    this.worker.evaluate(analyser, file, detector, container);

                    // stops the processing if needed
                    if (this.stop) return;

                    // update progress bar
                    this.mainClass.updateProgressBar();
                }

                // stop current detector
                detector.stop();
            }

            System.out.println("- File " + file.getName() + " finished.\r\n");
        }

        // finish the evaluation
        this.mainClass.finish();
    }
}