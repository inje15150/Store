package project.shop.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.shop.domain.item.Item;
import project.shop.repository.ItemRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Test
    void itemSave() {

        Item item = new Item();
        item.setName("itemA");
        item.setPrice(1000);
        item.setStockQuantity(10);

        Item item2 = new Item();
        item.setName("itemB");
        item.setPrice(2000);
        item.setStockQuantity(10);

        itemService.saveItem(item);
        itemService.saveItem(item2);

        List<Item> items = itemService.findItems();

        Assertions.assertThat(items.size()).isEqualTo(2);
    }
}