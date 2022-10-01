package infokomputer;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class InfoKomputer implements Initializable {

    @FXML
    private MFXTextField computerText;

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


    @FXML
    void copyToClipboard(MouseEvent event){

        String imageID = event.getSource().toString();
        System.out.println(imageID);
        if(imageID.contains("computerCopy")){
            content.putString(computerText.getText());

        }

        if(imageID.contains("userCopy")){
            content.putString(usernameText.getText());
        }

        if(imageID.contains("ipCopy")){
            content.putString(ipText.getText());

        }

        if(imageID.contains("helpdeskCopy")){
            content.putString(helpdeskText.getText());
        }

        if(imageID.contains("websiteCopy")){
            content.putString(websiteText.getText());
            try {
                Desktop.getDesktop().browse(new URI("https://helpdesk"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (URISyntaxException e) {
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
            String result;
            while ((output = output_reader.readLine()) != null) {
                result = output.replace("IPv4 Address. . . . . . . . . . . :","");
                textField.setText(result);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
}


@Override
public void initialize(URL location,ResourceBundle resources){

        getInformation("echo %COMPUTERNAME%", computerText);
        getInformation("echo %USERNAME%", usernameText);
        getInformation("ipconfig | find \"IPv4 Address\"",ipText);
        helpdeskText.setText("22 290 10 02");
        websiteText.setText("https://helpdesk");

    }
}
