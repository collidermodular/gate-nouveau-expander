package com.collidermodular.gn.expander;

import java.nio.ByteBuffer;

public abstract class Packet {
  abstract public byte[] toByteArray();

  public double toDouble() {
    return ByteBuffer.wrap(this.toByteArray()).getDouble();
  }

  protected byte twoToOneBytes(byte byte1, byte byte2) {
    byte out = byte1;
    if ((byte2 & 1) != 0) out |= 16;
    if ((byte2 & 2) != 0) out |= 32;
    if ((byte2 & 4) != 0) out |= 64;
    if ((byte2 & 8) != 0) out |= 128;
    return out;
  }
}
