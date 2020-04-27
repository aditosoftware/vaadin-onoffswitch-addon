package com.vaadin.addon.onoffswitch;

import com.vaadin.addon.onoffswitch.client.OnOffSwitchClientRpc;
import com.vaadin.addon.onoffswitch.client.OnOffSwitchServerRpc;
import com.vaadin.addon.onoffswitch.client.OnOffSwitchState;
import com.vaadin.event.FieldEvents;
import com.vaadin.shared.Registration;
import com.vaadin.ui.AbstractField;

@SuppressWarnings("serial")
public class OnOffSwitch extends AbstractField<Boolean> implements FieldEvents.FocusNotifier {

  // 서버 RPC Receiver 구현
  private OnOffSwitchServerRpc serverRpc = new OnOffSwitchServerRpc() {
    @Override
    public void clicked (boolean checked) {
      if (isReadOnly()) {
        return;
      }
      final Boolean oldValue = getValue(); // 서버 value
      final Boolean newValue = checked;    // 클라이언트측 요청 Value
      // 양쪽 Value가 틀린 경우 Click 상태 값 변경
      if (!newValue.equals(oldValue)) {
        // 서버측 Value 변경
        setValue(newValue);
      }
      // 클라이언트 RPC alert 메서드 호출
      getRpcProxy(OnOffSwitchClientRpc.class).alert(String.valueOf(newValue));
    }
  };

  public OnOffSwitch () {
    registerRpc(serverRpc); // 서버 RPC Receiver 등록

		// Register FocusAndBlur RPC to receive the events.
		registerRpc(new FieldEvents.FocusAndBlurServerRpcDecorator(this, this::fireEvent));
    setValue(Boolean.FALSE);
  }

  public OnOffSwitch (boolean checked) {
    this();
    setValue(checked); // 서버 On/Off Value
  }

  @Override
  protected void doSetValue (Boolean newValue) {
    if (newValue == null) {
      newValue = false;
    }
    // 상태 필드 값을 변경 하여 OnOffSwitchConnector onStateChanged method가 call이 되도록 한다.
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
}

