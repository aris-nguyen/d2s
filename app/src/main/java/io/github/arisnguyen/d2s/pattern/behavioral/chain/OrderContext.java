package io.github.arisnguyen.d2s.pattern.behavioral.chain;

public class OrderContext {
    private final String orderId;
    private final String userToken;
    private final String userRole;
    private final int requestCountPerMinute;
    private final int itemCount;
    private final int availableInventory;

    public OrderContext(String orderId, String userToken, String userRole,
                        int requestCountPerMinute, int itemCount, int availableInventory) {
        this.orderId = orderId;
        this.userToken = userToken;
        this.userRole = userRole;
        this.requestCountPerMinute = requestCountPerMinute;
        this.itemCount = itemCount;
        this.availableInventory = availableInventory;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getUserToken() {
        return userToken;
    }

    public String getUserRole() {
        return userRole;
    }

    public int getRequestCountPerMinute() {
        return requestCountPerMinute;
    }

    public int getItemCount() {
        return itemCount;
    }

    public int getAvailableInventory() {
        return availableInventory;
    }
}
