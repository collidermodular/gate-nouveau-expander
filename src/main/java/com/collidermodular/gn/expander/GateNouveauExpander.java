package com.collidermodular.gn.expander;

import java.util.LinkedList;

public class GateNouveauExpander {
  protected final LinkedList<Packet> queue;

  public GateNouveauExpander() {
    this.queue = new LinkedList<>();
  }

  // -- step commands --

  public void setSimpleStep(int trackIndex, int patternIndex, int stepIndex, boolean enabled, double gateWidth) {
    this.queue.add(new StepPacket(trackIndex, patternIndex, stepIndex, StepType.SIMPLE, enabled, gateWidth));
  }

  public void setSimpleStep(int stepIndex, boolean enabled, double gateWidth) {
    this.setSimpleStep(-1, -1, stepIndex, enabled, gateWidth);
  }

  public void setAccentStep(int trackIndex, int patternIndex, int stepIndex, double gateWidth) {
    this.queue.add(new StepPacket(trackIndex, patternIndex, stepIndex, StepType.ACCENT, true, gateWidth));
  }

  public void setAccentStep(int stepIndex, double gateWidth) {
    this.setAccentStep(-1, -1, stepIndex, gateWidth);
  }

  public void setTiedSteps(int trackIndex, int patternIndex, int startIndex, int endIndex, double endGateWidth) {
    for (int i = startIndex; i <= endIndex; i++) {
      if (i == startIndex) {
        this.queue.add(new StepPacket(trackIndex, patternIndex, i, StepType.TIE_HEAD, true, 1.0));
      } else if (i == endIndex) {
        this.queue.add(new StepPacket(trackIndex, patternIndex, i, StepType.TIE_TAIL, true, endGateWidth));
      } else {
        this.queue.add(new StepPacket(trackIndex, patternIndex, i, StepType.TIE_MID, true, 1.0));
      }
    }
  }

  public void setTiedSteps(int startIndex, int endIndex, double endGateWidth) {
    this.setTiedSteps(-1, -1, startIndex, endIndex, endGateWidth);
  }

  public void setSubdividedStep(int trackIndex, int patternIndex, int stepIndex, int length, boolean[] microSteps) {
    if (length > 8) { throw new IllegalArgumentException("Length must be 8 or less"); }
    this.queue.add(new StepPacket(trackIndex, patternIndex, stepIndex, StepType.SUBDIV, true, 0.0, microSteps, length));
  }

  public void setSubdividedStep(int index, int length, boolean[] microSteps) {
    this.setSubdividedStep(-1, -1, index, length, microSteps);
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
