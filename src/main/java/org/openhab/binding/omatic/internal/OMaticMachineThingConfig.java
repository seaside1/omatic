/**
 * Copyright (c) 2010-2023 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.omatic.internal;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

/**
 * The {@link OMaticMachineThingConfig} encapsulates all the configuration options for an instance of the
 * {@link OMaticClientThingHandler}.
 *
 * @author Joseph (Seaside) Hagberg - Initial contribution
 */
@NonNullByDefault
public class OMaticMachineThingConfig {
    private static final long DEFAULT_TIMER_DELAY = 10;
    private long timerDelay = DEFAULT_TIMER_DELAY;
    private String name = "";
    private String powerInputItem = "";
    private String energyInputItem = "";
    private Double activeThreshold = -1.0;
    private Integer idleTime = -1;
    private @Nullable Double staticPower = null;
    private Double cost = 1.0D;
    private String dateFormat = "YYYY-MM-dd HH:mm:ss";
    private long maxRunningTime = 60 * 60 * 24;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getActiveThreshold() {
        return activeThreshold;
    }

    public void setActiveThreshold(Double activeThreshold) {
        this.activeThreshold = activeThreshold;
    }

    public Integer getIdleTime() {
        return idleTime;
    }

    public void setIdleTime(Integer idleTime) {
        this.idleTime = idleTime;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public long getMaxRunningTime() {
        return maxRunningTime;
    }

    public long getTimerDelay() {
        return timerDelay;
    }

    public void setTimerDelay(long timerDelay) {
        this.timerDelay = timerDelay;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public String toString() {
        return "OMaticMachineThingConfig [timerDelay=" + timerDelay + ", name=" + name + ", powerInputItem="
                + powerInputItem + ", energyInputItem=" + energyInputItem + ", activeThreshold=" + activeThreshold
                + ", idleTime=" + idleTime + ", staticPower=" + staticPower + ", cost=" + cost + ", dateFormat="
                + dateFormat + ", maxRunningTime=" + maxRunningTime + "]";
    }

    public String getPowerInputItem() {
        return powerInputItem;
    }

    public void setPowerInputItem(String powerInputItem) {
        this.powerInputItem = powerInputItem;
    }

    public String getEnergyInputItem() {
        return energyInputItem;
    }

    public void setEnergyInputItem(String energyInputItem) {
        this.energyInputItem = energyInputItem;
    }

    public @Nullable Double getStaticPower() {
        return staticPower;
    }

    public void setStaticPower(Double staticPower) {
        this.staticPower = staticPower;
    }
}
