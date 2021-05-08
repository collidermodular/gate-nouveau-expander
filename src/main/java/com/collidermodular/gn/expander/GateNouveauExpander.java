package com.collidermodular.gn.expander;

import java.util.LinkedList;

public class GateNouveauExpander {
  private final LinkedList<Packet> queue;

  public GateNouveauExpander() {
    this.queue = new LinkedList<>();
  }

  public void reset() {
    this.queue.clear();
  }

  public void setSimpleStep(int index, boolean enabled, double gateWidth) {
    this.queue.add(new StepPacket(index, StepType.SIMPLE, enabled, gateWidth));
  }

  public void setAccentStep(int index, double gateWidth) {
    this.queue.add(new StepPacket(index, StepType.ACCENT, true, gateWidth));
  }

  public void setTiedSteps(int startIndex, int endIndex, double endGateWidth) {
    for (int i = startIndex; i <= endIndex; i++) {
      if (i == startIndex) {
        this.queue.add(new StepPacket(i, StepType.TIE_HEAD, true, 1.0));
      } else if (i == endIndex) {
        this.queue.add(new StepPacket(i, StepType.TIE_TAIL, true, endGateWidth));
      } else {
        this.queue.add(new StepPacket(i, StepType.TIE_MID, true, 1.0));
      }
    }
  }

  public void setSubdividedSTep(int index, int length, boolean[] microSteps) {
    if (length > 8) { throw new IllegalArgumentException("Length must be 8 or less"); }
    this.queue.add(new StepPacket(index, StepType.SUBDIV, true, 0.0, microSteps, length));
  }

  public boolean hasNext() {
    return this.queue.size() > 0;
  }

  public double nextDouble() {
    Packet packet = this.queue.poll();
    if (packet != null) {
      return packet.toDouble();
    }
    return 0;
  }
}
