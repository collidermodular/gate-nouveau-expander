package com.collidermodular.gn.expander;

public class StepPacket extends Packet {
  public final int trackIndex;
  public final int patternIndex;
  public final int stepIndex;
  public final StepType type;
  public final boolean enabled;
  public final double gateWidth;
  public final int microStepLength;
  public final boolean[] microSteps;

  public StepPacket(int trackIndex, int patternIndex, int stepIndex, StepType type, boolean enabled, double gateWidth) {
    this(trackIndex, patternIndex, stepIndex, type, enabled, gateWidth, new boolean[0], 0);
  }

  public StepPacket(int trackIndex, int patternIndex, int stepIndex, StepType type, boolean enabled, double gateWidth, boolean[] microSteps, int microStepLength) {
    this.trackIndex = trackIndex;
    this.patternIndex = patternIndex;
    this.stepIndex = stepIndex;
    this.type = type;
    this.enabled = enabled;
    this.gateWidth = gateWidth;
    this.microSteps = microSteps;
    this.microStepLength = microStepLength;
  }

  public byte[] toByteArray() {
    byte[] bytes = new byte[8];

    // header byte is command. Values 0-127 are implicitly "set step" command
    bytes[0] = (byte)stepIndex;

    int transmittedTrackIndex = trackIndex > -1 ? trackIndex : 0;
    int transmittedPatternIndex = patternIndex > -1 ? patternIndex : 0;
    bytes[1] = twoToOneBytes((byte) transmittedTrackIndex, (byte) transmittedPatternIndex);

    byte flags = (byte)(enabled ? 1 : 0);
    if (trackIndex > -1) {  flags |= 1 << 2; }
    if (patternIndex > -1) {  flags |= 1 << 3; }
    bytes[2] = twoToOneBytes((byte) type.ordinal(), flags);

    byte width = (byte)Math.round(gateWidth / (1/14.0));
    bytes[3] = twoToOneBytes(width, (byte)0);

    if (type == StepType.SUBDIV) {
      bytes[4] = (byte)microStepLength;
      byte subSteps = 0;
      for (int i = 0; i < microSteps.length; i++) {
        if (microSteps[i]) {
          subSteps |= 1 << i;
        }
      }
      bytes[5] = subSteps;
    }

    return bytes;
  }
}
