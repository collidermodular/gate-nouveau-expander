package com.collidermodular.gn.expander;

import java.util.LinkedList;

public class GateNouveauExpander {
  protected final LinkedList<Packet> queue;

  // VoltagePolyJack seems to (incorrectly?) hang on to the last value, so tell the
  // consumer there is a message left and return 0 after queue is empty 🤪
  private boolean returnZero;

  public GateNouveauExpander() {
    this.queue = new LinkedList<>();
  }

  // -- step commands --

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

  public void setSubdividedStep(int index, int length, boolean[] microSteps) {
    if (length > 8) { throw new IllegalArgumentException("Length must be 8 or less"); }
    this.queue.add(new StepPacket(index, StepType.SUBDIV, true, 0.0, microSteps, length));
  }

  // -- setting commands --

  public void setTrackLength(int length) {
    this.queue.add(new TrackLengthPacket(length));
  }

  public void setTrackDivision(int ppqn) {
    this.queue.add(new TrackDivisionPacket(ppqn));
  }

  // -- queue management --

  public void reset() {
    this.queue.clear();
  }
  public int size() { return this.queue.size(); }

  public double nextDouble() {
    if (this.queue.size() == 0) { return 0; }

    Packet packet = this.queue.poll();
    if (packet != null) {
      return packet.toDouble();
    }
    return 0;
  }

}
