package com.collidermodular.gn.expander;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StepPacketTest {

  @Test
  void toByteArray() {
    StepPacket packet = new StepPacket(6, StepType.SIMPLE, true, 0.5);
    byte[] bytes = packet.toByteArray();

    assertEquals(6, bytes[0]);
  }
}