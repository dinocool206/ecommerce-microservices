package com.example.inventory.factory;

import com.example.inventory.strategy.InventoryStrategy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class InventoryStrategyFactory {

    private final Map<String, InventoryStrategy> strategies;

    public InventoryStrategyFactory(
            List<InventoryStrategy> strategyList) {

        strategies = strategyList.stream()
                .collect(Collectors.toMap(
                        s -> "EXPIRY",
                        Function.identity()));
    }

    public InventoryStrategy getStrategy(String type) {
        return strategies.get(type);
    }
}
