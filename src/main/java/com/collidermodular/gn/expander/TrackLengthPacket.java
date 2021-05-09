package com.collidermodular.gn.expander;

public class TrackLengthPacket extends Packet {
  public final int length;

  public TrackLengthPacket(int length) {
    this.length = length;
  }

  @Override
  public byte[] toByteArray() {
    byte[] bytes = new byte[8];
    bytes[0] = SettingCommands.TrackLength;
    bytes[1] = (byte)length;
    return bytes;
  }
}
