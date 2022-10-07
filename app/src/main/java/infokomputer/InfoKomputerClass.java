package infokomputer;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;


import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class InfoKomputerClass implements Initializable {

    @FXML
    private MFXTextField computerText;

    @FXML
    private ImageView gifResult;

    @FXML
    private Text textResult;

    @FXML
    private MFXTextField helpdeskText;

    @FXML
    private MFXTextField ipText;

    @FXML
    private MFXTextField usernameText;

    @FXML
    private MFXTextField websiteText;

    final Clipboard clipboard = Clipboard.getSystemClipboard();
    final ClipboardContent content = new ClipboardContent();

    ProcessService service = new ProcessService();

    void setVisibleAfterCopy(boolean value){
        gifResult.setVisible(value);
        textResult.setVisible(value);
    }

    void copyResultTimer(){
        setVisibleAfterCopy(true);
        if(!service.isRunning()){
            service.start();
        }
        service.setOnSucceeded(e -> {
                    setVisibleAfterCopy(false);
                    service.reset();
                }
        );
    }



    @FXML
    void copyToClipboard(MouseEvent event){

        String imageID = event.getSource().toString();
        if(imageID.contains("computerCopy")){
            content.putString(computerText.getText());
            copyResultTimer();
        }
        if(imageID.contains("userCopy")){
            content.putString(usernameText.getText());
            copyResultTimer();
        }

        if(imageID.contains("ipCopy")){
            content.putString(ipText.getText());
            copyResultTimer();
        }

        if(imageID.contains("helpdeskCopy")){
            content.putString(helpdeskText.getText());
            copyResultTimer();
        }

        if(imageID.contains("websiteCopy")){
            content.putString(websiteText.getText());
            copyResultTimer();
            try {
                Desktop.getDesktop().browse(new URI("https://helpdesk"));
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        clipboard.setContent(content);
    }


    void getInformation(String command, MFXTextField textField) {
        try {
            ProcessBuilder build_test = new ProcessBuilder(
                    "cmd.exe", "/c", command);
            Process p = build_test.start();
            BufferedReader output_reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String output;
            String delchars;
            String delspaces;
            while ((output = output_reader.readLine()) != null) {
                delchars = output.replace("IPv4 Address. . . . . . . . . . . :","");
                delspaces = delchars.replace(" ","");
                textField.setText(delspaces);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
}


@Override
public void initialize(URL location,ResourceBundle resources){

        gifResult.setVisible(false);
        textResult.setVisible(false);
        getInformation("echo %COMPUTERNAME%", computerText);
        getInformation("echo %USERNAME%", usernameText);
        getInformation("ipconfig | find \"IPv4 Address\"",ipText);
        helpdeskText.setText("22 290 10 02");
        websiteText.setText("https://helpdesk");
    }

}