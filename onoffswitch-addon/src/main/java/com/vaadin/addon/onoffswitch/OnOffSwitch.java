package com.vaadin.addon.onoffswitch;

import com.vaadin.addon.onoffswitch.client.OnOffSwitchServerRpc;
import com.vaadin.addon.onoffswitch.client.OnOffSwitchState;
import com.vaadin.event.FieldEvents;
import com.vaadin.shared.Registration;
import com.vaadin.ui.AbstractField;

import java.util.Objects;

public class OnOffSwitch extends AbstractField<Boolean> implements FieldEvents.FocusNotifier {
  public OnOffSwitch () {
    // Register the Service RPC on the inner class implementation.
    registerRpc(new ServerRpcImpl());

    // Register FocusAndBlur RPC to receive the events.
    registerRpc(new FieldEvents.FocusAndBlurServerRpcDecorator(this, this::fireEvent));

    // Initialize the value with false.
    setValue(false);
  }

  public OnOffSwitch (boolean checked) {
    this();

    // Initialize the value with the given parameter.
    setValue(checked);
  }

  @Override
  protected void doSetValue (Boolean newValue) {
    // Assume the new value should be false if null is given.
    if (newValue == null)
      newValue = false;

    // Update the state with the new value.
    getState().checked = newValue;
  }

  @Override
  public Boolean getValue () {
    return getState().checked;
  }

  @Override
  protected OnOffSwitchState getState () {
    return (OnOffSwitchState) super.getState();
  }

  @Override
  public Registration addFocusListener (FieldEvents.FocusListener listener) {
    return addListener("focus", FieldEvents.FocusEvent.class, listener, FieldEvents.FocusListener.focusMethod);
  }

  private class ServerRpcImpl implements OnOffSwitchServerRpc {
    @Override
    public void clicked (boolean checked) {
      // If the switch is readonly, it's not required to continue.
      if (isReadOnly())
        return;

      // Get the old and new value for comparison.
      Boolean oldValue = getValue();
      Boolean newValue = checked;

      // Update the value if the old and new value differ.
      if (!Objects.equals(newValue, oldValue))
        setValue(newValue);
    }
  }
}

