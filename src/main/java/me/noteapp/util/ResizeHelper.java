package me.noteapp.util;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ResizeHelper {

    public static void addResizeListener(Stage stage) {
        ResizeListener resizeListener = new ResizeListener(stage);
        stage.getScene().addEventHandler(MouseEvent.ANY, resizeListener);
    }

    private static class ResizeListener implements EventHandler<MouseEvent> {
        private final Stage stage;
        private Cursor cursorEvent = Cursor.DEFAULT;
        private final int border = 8;
        private double startX = 0;
        private double startY = 0;
        private double sceneX = 0;
        private double sceneY = 0;

        private boolean moveH;
        private boolean moveV;
        private boolean resizeH;
        private boolean resizeV;

        public ResizeListener(Stage stage) {
            this.stage = stage;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            EventType<? extends MouseEvent> mouseEventType = mouseEvent.getEventType();
            Scene scene = stage.getScene();

            double mouseEventX = mouseEvent.getSceneX();
            double mouseEventY = mouseEvent.getSceneY();
            double sceneWidth = scene.getWidth();
            double sceneHeight = scene.getHeight();

            if (MouseEvent.MOUSE_MOVED.equals(mouseEventType)) {
                if (mouseEventX < border && mouseEventY < border) {
                    cursorEvent = Cursor.NW_RESIZE;
                } else if (mouseEventX < border && mouseEventY > sceneHeight - border) {
                    cursorEvent = Cursor.SW_RESIZE;
                } else if (mouseEventX > sceneWidth - border && mouseEventY < border) {
                    cursorEvent = Cursor.NE_RESIZE;
                } else if (mouseEventX > sceneWidth - border && mouseEventY > sceneHeight - border) {
                    cursorEvent = Cursor.SE_RESIZE;
                } else if (mouseEventX < border) {
                    cursorEvent = Cursor.W_RESIZE;
                } else if (mouseEventX > sceneWidth - border) {
                    cursorEvent = Cursor.E_RESIZE;
                } else if (mouseEventY < border) {
                    cursorEvent = Cursor.N_RESIZE;
                } else if (mouseEventY > sceneHeight - border) {
                    cursorEvent = Cursor.S_RESIZE;
                } else {
                    cursorEvent = Cursor.DEFAULT;
                }
                scene.setCursor(cursorEvent);

            } else if (MouseEvent.MOUSE_PRESSED.equals(mouseEventType)) {
                startX = stage.getWidth() - mouseEventX;
                startY = stage.getHeight() - mouseEventY;
                sceneX = mouseEvent.getSceneX();
                sceneY = mouseEvent.getSceneY();

                moveH = mouseEventX < border;
                moveV = mouseEventY < border;
                resizeH = mouseEventX > sceneWidth - border;
                resizeV = mouseEventY > sceneHeight - border;

            } else if (MouseEvent.MOUSE_DRAGGED.equals(mouseEventType)) {
                if (cursorEvent != Cursor.DEFAULT) {
                    if (moveH) {
                        stage.setX(mouseEvent.getScreenX() - sceneX);
                        stage.setWidth(stage.getWidth() - (mouseEvent.getSceneX() - sceneX));
                    }
                    if (moveV) {
                        stage.setY(mouseEvent.getScreenY() - sceneY);
                        stage.setHeight(stage.getHeight() - (mouseEvent.getSceneY() - sceneY));
                    }
                    if (resizeH) {
                        stage.setWidth(mouseEventX + startX);
                    }
                    if (resizeV) {
                        stage.setHeight(mouseEventY + startY);
                    }
                }
            } else if (MouseEvent.MOUSE_RELEASED.equals(mouseEventType)) {
                moveH = moveV = resizeH = resizeV = false;
            }
        }
    }
}