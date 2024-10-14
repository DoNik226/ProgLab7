package responses;

public class ByteArrayPacketResponse extends BaseResponse {
    private final int responseKey;
    private final int deliveryNumber;
    private final byte[] packet;

    public ByteArrayPacketResponse(int responseKey, int deliveryNumber, byte[] packet) {
        this.responseKey = responseKey;
        this.deliveryNumber = deliveryNumber;
        this.packet = packet;
    }

    public byte[] getPacket() {
        return packet;
    }

    public int getDeliveryNumber() {
        return deliveryNumber;
    }

    public int getResponseKey() {
        return responseKey;
    }
}
