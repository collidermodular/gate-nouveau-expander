package com.collidermodular.gn.expander;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GateNouveauExpanderTest {

  @Test
  void setTiedSteps() {
    GateNouveauExpander expander = new GateNouveauExpander();
    expander.setTiedSteps(3, 5, 0.66);

    StepPacket packet1 = (StepPacket) expander.queue.get(0);
    assertEquals(StepType.TIE_HEAD, packet1.type);

    StepPacket packet2 = (StepPacket) expander.queue.get(1);
    assertEquals(StepType.TIE_MID, packet2.type);

    StepPacket packet3 = (StepPacket) expander.queue.get(2);
    assertEquals(StepType.TIE_TAIL, packet3.type);
    assertEquals(0.66, packet3.gateWidth);
  }
}