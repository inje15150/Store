package project.shop.web.controller.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import project.shop.domain.item.Album;
import project.shop.domain.item.Book;
import project.shop.domain.item.Item;
import project.shop.domain.item.Movie;
import project.shop.service.ItemService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;

    @GetMapping("items/new")
    public String itemSaveForm(@ModelAttribute ItemForm itemForm) {
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String itemSave(@Validated @ModelAttribute("itemForm") ItemForm itemForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("errors= {}", bindingResult);
            return "items/createItemForm";
        }

        Item item = getItem(itemForm.getItemName(), itemForm.getPrice(), itemForm.getStockQuantity(), itemForm.getSelectItem());

        itemService.saveItem(item);

        return "redirect:/items";
    }

    @GetMapping("/items")
    public String itemList(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);

        return "items/itemList";
    }

    private Item getItem(String itemName, int price, int stockQuantity, String selectItem) {

        log.info("getItem call");

        Item item = new Item();

        item.setName(itemName);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);

        return item;
    }
}
