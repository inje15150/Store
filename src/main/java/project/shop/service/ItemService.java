package project.shop.service;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop.api.v1.orders.dto.create.OrderCreateRequest;
import project.shop.domain.item.Item;
import project.shop.repository.ItemRepository;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findALl();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    @Transactional
    public void update(Long itemId, String itemName, Integer price, Integer stockQuantity) {

        itemRepository.update(itemId, itemName, price, stockQuantity);
    }

    public Long findByName(String name) {

        return itemRepository.findByName(name);
    }
}
