package io.github.arisnguyen.d2s.pattern.behavioral.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandTest {

    private OrderService orderService;
    private OrderCommandInvoker invoker;

    @BeforeEach
    void setUp() {
        orderService = new OrderService();
        invoker = new OrderCommandInvoker();
    }

    @Test
    void testAddItemCommandExecutesAndUpdatesInventory() {
        OrderCommand cmd = new AddItemCommand(orderService, "LAPTOP", 3);
        invoker.executeCommand(cmd);

        assertEquals(3, orderService.getCartQuantity("LAPTOP"));
        assertEquals(7, orderService.getInventoryStock("LAPTOP")); // initial was 10
        assertTrue(invoker.canUndo());
    }

    @Test
    void testUndoAddItemCommandRestoresCartAndInventory() {
        OrderCommand cmd = new AddItemCommand(orderService, "LAPTOP", 2);
        invoker.executeCommand(cmd);

        invoker.undoLastCommand();
        assertEquals(0, orderService.getCartQuantity("LAPTOP"));
        assertEquals(10, orderService.getInventoryStock("LAPTOP"));
        assertFalse(invoker.canUndo());
    }

    @Test
    void testCancelItemCommand() {
        // Pre-add item
        orderService.addItemToCart("PHONE", 5);

        OrderCommand cancelCmd = new CancelItemCommand(orderService, "PHONE", 2);
        invoker.executeCommand(cancelCmd);

        assertEquals(3, orderService.getCartQuantity("PHONE"));
        assertEquals(17, orderService.getInventoryStock("PHONE"));

        invoker.undoLastCommand();
        assertEquals(5, orderService.getCartQuantity("PHONE"));
        assertEquals(15, orderService.getInventoryStock("PHONE"));
    }

    @Test
    void testInsufficientInventoryThrows() {
        OrderCommand excessiveCmd = new AddItemCommand(orderService, "LAPTOP", 15);
        assertThrows(IllegalStateException.class, () -> invoker.executeCommand(excessiveCmd));
    }
}
