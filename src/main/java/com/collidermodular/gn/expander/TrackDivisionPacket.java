package com.collidermodular.gn.expander;

public class TrackDivisionPacket extends Packet {
  public final int ppqn;

  public TrackDivisionPacket(int ppqn) {
    this.ppqn = ppqn;
  }

  @Override
  public byte[] toByteArray() {
    byte[] bytes = new byte[8];
    bytes[0] = SettingCommands.TrackDivision;
    bytes[1] = (byte)ppqn;
    return bytes;
  }
}
