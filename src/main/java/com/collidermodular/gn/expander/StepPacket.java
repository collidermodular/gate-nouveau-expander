package com.collidermodular.gn.expander;

public class StepPacket extends Packet {
  public final int index;
  public final StepType type;
  public final boolean enabled;
  public final double gateWidth;
  public final int microStepLength;
  public final boolean[] microSteps;

  public StepPacket(int index, StepType type, boolean enabled, double gateWidth) {
    this(index, type, enabled, gateWidth, new boolean[0], 0);
  }

  public StepPacket(int index, StepType type, boolean enabled, double gateWidth, boolean[] microSteps, int microStepLength) {
    this.index = index;
    this.type = type;
    this.enabled = enabled;
    this.gateWidth = gateWidth;
    this.microSteps = microSteps;
    this.microStepLength = microStepLength;
  }

  public byte[] toByteArray() {
    byte[] bytes = new byte[8];

    // header byte is command. Values 0-127 are implicitly "set step" command
    bytes[0] = (byte)index;
    bytes[1] = twoToOneBytes((byte) type.ordinal(), (byte)(enabled ? 1 : 0));

    byte width = (byte)Math.round(gateWidth / (1/14.0));
    bytes[2] = twoToOneBytes(width, (byte)0);

    if (type == StepType.SUBDIV) {
      bytes[3] = (byte)microStepLength;
      byte subSteps = 0;
      for (int i = 0; i < microSteps.length; i++) {
        if (microSteps[i]) {
          subSteps |= 1 << i;
        }
      }
      bytes[4] = subSteps;
    }

    return bytes;
  }
}
