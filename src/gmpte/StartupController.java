/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gmpte;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author mbgm2rm2
 */
public class StartupController implements Initializable, ControllerInterface {

  @FXML
  public VBox driverMenuItem;
  
  @FXML
  public VBox controllerMenuItem;
  
  @FXML
  public VBox passengerMenuItem;
  
  public MainControllerInterface mainController;
  
  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {    
    // driver menu item chose handler
    onDriverMenuItemChoose();
    
    // controller menu item choose handler
    onControllerMenuItemChoose();
    
    // handle passenger interface menu item
    onPassengerMenuItemChoose();
  }  
  
  public void onDriverMenuItemChoose() {
    driverMenuItem.setOnMouseClicked(new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent t) {
        mainController.showLoginPage();
      }
      
    });
  }
  
  public void onControllerMenuItemChoose() {
    controllerMenuItem.setOnMouseClicked(new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent t) {
        mainController.showControllerInterface();
      }
      
    });
  }
  
  public void onPassengerMenuItemChoose() {
    passengerMenuItem.setOnMouseClicked(new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent t) {
        mainController.showPassengerInterface();
      }
      
    });
  }
  
  public void setMainController(MainControllerInterface mainController) {
        this.mainController = mainController;
  }

  @Override
  public void refresh() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
