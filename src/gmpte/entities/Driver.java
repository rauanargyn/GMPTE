/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gmpte.entities;

import java.util.ArrayList;
import java.util.Iterator;
import gmpte.db.DriverInfo;
import gmpte.db.TimetableInfo;

/**
 *
 * @author argyn
 */
public class Driver implements Comparable<Driver> {
    
    private final int driverNumber;
    
    private final int driverID;
    
    private String name;
    
    private int holidaysTaken;
    
    private int hoursThisYear;
    
    private int minutesThisWeek;
    
    private boolean available;
    
    private Shift shift;
    
    public Driver(int driverID, int driverNumber) {
        this.driverID = driverID;
        this.driverNumber = driverNumber;
        available = false;
       
        
        flashShift();
    }
    
    public int getDriverID() {
        return driverID;
    }
    
    public int getDriverNumber() {
        return driverNumber;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setHolidaysTaken(int holidaysTaken) {
        this.holidaysTaken = holidaysTaken;
    }
    
    
    public int getHolidaysTaken() {
        return holidaysTaken;
    }
    
    public void setHoursThisYear(int hoursThisYear) {
        this.hoursThisYear = hoursThisYear;
    }
    
    public int getHoursThisYear() {
        return hoursThisYear;
    }
    
    public void setHoursThisWeek(int minutesThisWeek) {
        this.minutesThisWeek = minutesThisWeek;
    }
    
    public double getMinutesThisWeek() {
        return minutesThisWeek;
    }
    
    public void increaseHoursThisWeek(int minutesIncrease) {
      setHoursThisWeek(minutesThisWeek+minutesIncrease);
    }
    
    public boolean isAvailable() {
        return available;
    }
    
    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    public void flashShift() {
        shift = new Shift();
    }
    
    public void assignToService(Service service) {
      shift.addService(service);
    }
    
    public Shift getShift() {
        return shift;
    }
    
    public void dbUpdateHoursThisWeek() {
        DriverInfo.setHoursThisWeek(driverID, minutesThisWeek);
    }
    
    public boolean isAbleToTakeService(Service service) {
        return shift.isAbleToTakeService(service) && !doesServiceClash(service);
    }
    
    private boolean doesServiceClash(Service service) {
        Iterator<Service> assignedServicesIterator = shift.getAssignedServices().iterator();
        while(assignedServicesIterator.hasNext()) {
            Service assignedService = assignedServicesIterator.next();
            if(TimetableInfo.checkServiceClashes(assignedService, service))
                return true;
        }
        return false;
    }
    
    
    
    @Override
    public int compareTo(Driver other) {
      if(minutesThisWeek > other.minutesThisWeek)
        return -1;
      else if(minutesThisWeek == other.minutesThisWeek)
        return 0;
      return 1;
    }
    
    @Override
    public String toString() {
        return name+"(ID:"+driverID+")";
    }
}
