import javafx.application.*;
import javafx.beans.*;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TextFieldControl extends Application {
  @Override public void start(Stage stage) {
    final TextField textField = new TextField("Phone: ");
    textField.addEventFilter(KeyEvent.ANY, new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
          // block cursor control keys.
          case LEFT:
          case RIGHT:
          case UP:
          case DOWN:
          case PAGE_UP:
          case PAGE_DOWN:
          case HOME:
          case END:
            keyEvent.consume();

          // allow deletion and tab.
          case DELETE:
          case BACK_SPACE:
          case TAB:
            return;
		default:
			break;
        }

        // only allow digits and a few punctuation symbols to be entered.
        if (!"0123456789-() ".contains(keyEvent.getCharacter())) {
          keyEvent.consume();
        }
      }
    });
    
        
    textField.focusedProperty().addListener(new InvalidationListener() {
      @Override public void invalidated(Observable observable) {
        // due to some weirdness JavaFX will auto select the text when the text field
        // receives focus, so instead deselect and position the caret at the end of the field.
        // Another weird thing is that a pulse must be run before the deselection or caret
        // positioning request occurs or it won't take effect, so a runnable seems to suffic to ensure that.
        Platform.runLater(new Runnable() {
          @Override public void run() {
            textField.deselect();
            textField.positionCaret(textField.getText().length());
            textField.deselect();
          }
        });
      }
    });

    VBox layout = new VBox();
    layout.getChildren().setAll(new VBox(textField, new TextField()));
    stage.setScene(new Scene(layout));
    stage.show();

    textField.requestFocus();
  }
  
  public static void main(String[] args) {
	    launch(args);
	  }
}
