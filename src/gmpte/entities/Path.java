/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gmpte.entities;

import gmpte.db.RouteDBInfo;
import gmpte.db.ServiceDB;
import gmpte.helpers.ListHelper;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author mbgm2rm2
 */
public class Path {
  
  private final LinkedList<BusStop> fullPath;
  private final LinkedList<BusChange> changes;
  
  public Path(LinkedList<BusStop> fullPath) {
    this.fullPath = fullPath;
    changes = new LinkedList<>();
    
    
    Route currentRoute = chooseRoute(fullPath.peek(), fullPath.get(1));
    System.out.println(fullPath.get(1));
    System.out.println(currentRoute);
    
    BusChange change = new BusChange(currentRoute);
    // add starting bus station
    
    BusStop startBStop = fullPath.poll();
    
    Date lastDate = new Date();
    
    
    System.out.println("Start on "+startBStop+" Route:"+currentRoute.getRouteID());
    change.addBusStation(startBStop);
    
    while(fullPath.peek()!=null) {
      BusStop current = fullPath.poll();
      BusStop next = null;
      
      if(fullPath.size()>0)
        next = fullPath.get(0);
      
      if(next!=null && !next.hasRoute(currentRoute)) {
        change.addBusStation(current);
        // updating change times
        change.setBusStopTimes(ServiceDB.getNearestService(currentRoute, startBStop, lastDate));
        
        // updating last date to the disembarkment time of the last service
        lastDate = change.getDisembarkTime();
        
        // saving the change
        changes.add(change);
        
        // change start bus stop
        startBStop = current;
        
        // choosing new route now
        System.out.println("Choosing common root betwen "+current+current.getRoutesString()+" and "+next+next.getRoutesString());
        currentRoute = chooseRoute(current, next);
        System.out.println("Chose "+currentRoute.getRouteID());
        change = new BusChange(currentRoute);
      }
      
      System.out.println(current+" Route:"+currentRoute.getRouteID());
      
      change.addBusStation(current);
    }
    
    changes.add(change);
    change.setBusStopTimes(ServiceDB.getNearestService(currentRoute, startBStop, lastDate));
  }
 
  
  public LinkedList<BusChange> getFullPath() {
    return changes;
  }
  
  private Route chooseRoute(BusStop current, BusStop nextStation) {
    ArrayList<Route> currentRoutes = current.getRoutes();
    ArrayList<Route> nextStationRoutes = nextStation.getRoutes();
    
    ArrayList<Route> routes = (ArrayList<Route>)ListHelper
                            .<Route>intersect(currentRoutes, nextStationRoutes);
    
    for(Route route : routes) {
      // check if route goes from current to nextStation
      if(RouteDBInfo.doesRouteGoFromTo(current, nextStation, route))
        return route;
    }
    
    
    return null;
  }
}
