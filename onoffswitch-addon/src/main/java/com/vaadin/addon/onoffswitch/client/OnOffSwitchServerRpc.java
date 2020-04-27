package com.vaadin.addon.onoffswitch.client;

import com.vaadin.shared.communication.ServerRpc;

public interface OnOffSwitchServerRpc extends ServerRpc {
  void clicked (boolean checked);
}
