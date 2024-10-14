package responses;

public class IntermediateResponse extends BaseResponse {
    private final int responseKey;
    private final int totalDeliveries;

    public IntermediateResponse(int responseKey, int totalDeliveries) {
        this.responseKey = responseKey;
        this.totalDeliveries = totalDeliveries;
    }

    public int getResponseKey() {
        return responseKey;
    }

    public int getTotalDeliveries() {
        return totalDeliveries;
    }
}
