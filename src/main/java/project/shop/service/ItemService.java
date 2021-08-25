package project.shop.service;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop.api.v1.orders.dto.create.OrderCreateRequest;
import project.shop.domain.item.Item;
import project.shop.repository.ItemRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Long saveItem(Item item) {
        return itemRepository.save(item);
    }

    // item 전체 조회
    public List<Item> findItems() {
        return itemRepository.findALl();
    }

    // item 조건 조회
    public List<Item> findItems(String itemName, Integer price, String sign) {
        return itemRepository.findByCondition(itemName, price, sign);
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    @Transactional
    public void update(Long itemId, String itemName, Integer price, Integer stockQuantity) {

        itemRepository.update(itemId, itemName, price, stockQuantity);
    }

    public Long findByName(String name) {

        List<Item> findItems = itemRepository.findByName(name);
        return findItems.stream()
                .map(i -> i.getId())
                .findFirst().get();
    }

    @Transactional
    public void delete(Long itemId) {
        itemRepository.delete(itemId);
    }


}
