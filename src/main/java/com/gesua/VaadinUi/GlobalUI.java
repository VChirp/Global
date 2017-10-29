package com.gesua.VaadinUi;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.UI;
@SpringUI
@Theme("valo")
public class GlobalUI extends UI {
    HorizontalSplitPanel horizontalSplitPanel = new HorizontalSplitPanel();
    InitUiView ui = new InitUiView();
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        horizontalSplitPanel.addComponent(ui);
        setContent(horizontalSplitPanel);

    }
}
