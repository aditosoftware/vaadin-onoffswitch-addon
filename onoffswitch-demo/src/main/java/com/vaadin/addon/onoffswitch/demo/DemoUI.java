package com.vaadin.addon.onoffswitch.demo;

import com.vaadin.addon.onoffswitch.OnOffSwitch;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import javax.servlet.annotation.WebServlet;

@Theme("demo")
@Title("OnOffSwitch Add-on Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI {

  @WebServlet(value = "/*", asyncSupported = true)
  @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class)
  public static class Servlet extends VaadinServlet {
  }

  @Override
  protected void init (VaadinRequest request) {

    final OnOffSwitch onOffSwitch = new OnOffSwitch(false);
    onOffSwitch.addValueChangeListener(event -> {
      boolean checked = event.getValue();
      System.out.println("OnOffSwitch checked : " + checked);
    });

    onOffSwitch.addFocusListener(event -> System.out.println("FOCUS"));

    Button toggle = new Button();
    toggle.addClickListener(event -> onOffSwitch.setValue(!onOffSwitch.getValue()));

    final VerticalLayout layout = new VerticalLayout();
    layout.setSizeFull();
    layout.addComponent(onOffSwitch);
    layout.addComponent(toggle);
    layout.setComponentAlignment(onOffSwitch, Alignment.MIDDLE_CENTER);
    setContent(layout);
  }

}
