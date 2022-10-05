package infokomputer;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;


import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class InfoKomputerClass implements Initializable {

    @FXML
    private MFXTextField computerText;

    @FXML
    private Tooltip computerTooltip;

    @FXML
    private MFXTextField helpdeskText;

    @FXML
    private MFXTextField ipText;

    @FXML
    private Tooltip ipTooltip;

    @FXML
    private Tooltip phoneTooltip;

    @FXML
    private Tooltip userTooltip;

    @FXML
    private MFXTextField usernameText;

    @FXML
    private MFXTextField websiteText;

    @FXML
    private Tooltip websiteTooltip;

    final Clipboard clipboard = Clipboard.getSystemClipboard();
    final ClipboardContent content = new ClipboardContent();


    @FXML
    void copyToClipboard(MouseEvent event){

        String imageID = event.getSource().toString();
        Window window = ((Node) event.getTarget()).getScene().getWindow();
        if(imageID.contains("computerCopy")){
            content.putString(computerText.getText());
            computerTooltip.show(window);
        }

        if(imageID.contains("userCopy")){
            content.putString(usernameText.getText());
            userTooltip.show(window);
        }

        if(imageID.contains("ipCopy")){
            content.putString(ipText.getText());
            ipTooltip.show(window);
        }

        if(imageID.contains("helpdeskCopy")){
            content.putString(helpdeskText.getText());
            phoneTooltip.show(window);
        }

        if(imageID.contains("websiteCopy")){
            content.putString(websiteText.getText());
            websiteTooltip.show(window);
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
            String result;
            while ((output = output_reader.readLine()) != null) {
                result = output.replace("IPv4 Address. . . . . . . . . . . :","");
                textField.setText(result);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
}

    @FXML
    void mouseExited(MouseEvent event) {
        String fieldID = event.getSource().toString();
        if(fieldID.contains("computerField")){
           computerTooltip.hide();
        }

        if(fieldID.contains("userField")){
            userTooltip.hide();
        }

        if(fieldID.contains("ipField")){
           ipTooltip.hide();
        }

        if(fieldID.contains("phoneField")){
           phoneTooltip.hide();
        }

        if(fieldID.contains("websiteField")) {
            websiteTooltip.hide();
        }
    }



@Override
public void initialize(URL location,ResourceBundle resources){

        getInformation("echo %COMPUTERNAME%", computerText);
        getInformation("echo %USERNAME%", usernameText);
        getInformation("ipconfig | find \"IPv4 Address\"",ipText);
        helpdeskText.setText("22 290 10 02");
        websiteText.setText("https://helpdesk");

    for (Tooltip tooltip : Arrays.asList(computerTooltip, userTooltip, ipTooltip, phoneTooltip, websiteTooltip)) {
        tooltip.setText("Tekst skopiowano do schowka");
    }

    }

}