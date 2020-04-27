package com.vaadin.addon.onoffswitch.client;

import com.vaadin.addon.onoffswitch.OnOffSwitch;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.client.ui.ConnectorFocusAndBlurHandler;
import com.vaadin.shared.ui.Connect;

/**
 * Describes the connector between the the server-side and client-side.
 */
@Connect(OnOffSwitch.class)
public class OnOffSwitchConnector extends AbstractComponentConnector {
  OnOffSwitchServerRpc serverRpc = RpcProxy.create(OnOffSwitchServerRpc.class, this);

  public OnOffSwitchConnector () {
    // Add a click handler which redirects the new value to the server-side.
    getWidget().addClickHandler(event -> serverRpc.clicked(!getWidget().getValue()));
  }

  @Override
  protected void init () {
    super.init();
    ConnectorFocusAndBlurHandler.addHandlers(this);
  }

  @Override
  public OnOffSwitchWidget getWidget () {
    return (OnOffSwitchWidget) super.getWidget();
  }

  @Override
  public OnOffSwitchState getState () {
    return (OnOffSwitchState) super.getState();
  }

  @Override
  public void onStateChanged (StateChangeEvent stateChangeEvent) {
    super.onStateChanged(stateChangeEvent);

    // Update the value of the widget based on the state.
    getWidget().setValue(getState().checked, true);
  }
}
