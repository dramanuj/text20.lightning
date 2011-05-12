/*
 * VelocityWarperProperties.java
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
package de.dfki.km.text20.lightning.plugins.mousewarp.velocitywarper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.simpleframework.xml.Attribute;

/**
 * stores configurations, write them to a file on exit and tries to load them on startup 
 * 
 * @author Christoph Käding
 *
 */
public class VelocityWarperProperties implements Serializable {

    /** generated serial id */
    private static final long serialVersionUID = -4766853585658130189L;

    @Attribute
    /** angle threshold for mousewarping */
    private int angleThreshold;

    @Attribute
    /** distance threshold for mousewarping */
    private double velocity;

    @Attribute
    /** home radius for mousewarping */
    private int reactionTime;

    /** file where porperties are stored */
    private File propertiesFile;

    /** from properties file readed object */
    private Object object;
    
    /** singleton instance of this properties */
    private static VelocityWarperProperties properties;

    /**
     * creates properties, tries to load property file
     */
    private VelocityWarperProperties() {

        // creates properties file
        this.propertiesFile = new File("./plugins/VelocityWarper/properties.prop");

        // status is used to indicate if the properties object could be readed probably 
        boolean status = false;

        if (this.propertiesFile.exists()) {
            try {

                // read object from file
                ObjectInputStream inputStream = new ObjectInputStream((new FileInputStream(this.propertiesFile)));
                this.object = inputStream.readObject();

                if (this.object instanceof VelocityWarperProperties) {

                    // store readed configurations
                    this.angleThreshold = ((VelocityWarperProperties) this.object).getAngleThreshold();
                    this.velocity = ((VelocityWarperProperties) this.object).getSpeed();
                    this.reactionTime = ((VelocityWarperProperties) this.object).getReactionTime();

                    // reading successful
                    status = true;
                    System.out.println("\r\nVelocityWarper properties file was found.");
                    System.out.println("angle: " + this.angleThreshold + ", velocity: " + this.velocity + ", reaction time: " + this.reactionTime + "\r\n");
                }

                // cleanup
                inputStream.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // if reading was not successful or properties file was not found
        if (!status) {
            this.restoreDefault();
            System.out.println("\r\nVelocityWarper properties file was not found.\r\n");
        }
    }

    /**
     * restores default values
     */
    public void restoreDefault() {
        // set default values
        this.angleThreshold = 10;
        this.velocity = 15;
        this.reactionTime = 15;
    }

    /**
     * creates and returns the singleton instance
     * 
     * @return properties
     */
    public static VelocityWarperProperties getInstance() {
        if(properties == null) {
            properties = new VelocityWarperProperties();
        }
        return properties;
    }
    
    /**
     * @return the angleThreshold
     */
    public int getAngleThreshold() {
        return this.angleThreshold;
    }

    /**
     * @param angleThreshold the angleThreshold to set
     */
    public void setAngleThreshold(int angleThreshold) {
        this.angleThreshold = angleThreshold;
    }

    /**
     * @return the distanceThreshold
     */
    public double getSpeed() {
        return this.velocity;
    }

    /**
     * @param velocity , the minimal movement velocity
     */
    public void setSpeed(double velocity) {
        this.velocity = velocity;
    }

    /**
     * @return the homeRadius
     */
    public int getReactionTime() {
        return this.reactionTime;
    }

    /**
     * @param reactionTime to set
     */
    public void setReactionTime(int reactionTime) {
        this.reactionTime = reactionTime;
    }

    /**
     * write properties to propertiesFile
     */
    public void writeProperties() {
        try {

            // write object
            new File("plugins/VelocityWarper").mkdirs();
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(this.propertiesFile));
            outputStream.writeObject(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
