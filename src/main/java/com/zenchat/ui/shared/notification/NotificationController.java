package com.zenchat.ui.shared.notification;

import com.zenchat.ui.framework.component.FxController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class NotificationController implements FxController<NotificationViewModel> {

    @FXML
    private Pane notificationPane;

    @FXML
    private ImageView notificationImage;

    @FXML
    private Text notificationText;

    private NotificationViewModel notificationViewModel;
    private NotificationView notificationView;

    @Override
    public void initModel(NotificationViewModel registrationModel) {
        this.notificationViewModel = registrationModel;
        registrationModel.getNotificationModeObjectProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case ERROR: {
                    notificationText.textProperty().bind(notificationViewModel.getErrorMessage());
                    notificationPane.getStyleClass().add("error");
                    notificationImage.setImage(notificationView.getWarningImage());
                    break;
                }
                case INFORMMATION: {
                    notificationText.textProperty().bind(notificationViewModel.getInformationMessage());
                    notificationPane.getStyleClass().add("info");
                    notificationImage.setImage(notificationView.getInformationImage());
                    break;
                }
            }
        });
    }

    @FXML
    public void closeClicked(ActionEvent event) {
        notificationViewModel.close();
    }


    public void setView(NotificationView notificationView) {
        this.notificationView = notificationView;
    }
}
