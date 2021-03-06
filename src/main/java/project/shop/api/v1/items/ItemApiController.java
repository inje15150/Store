package project.shop.api.v1.items;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.shop.api.v1.converter.ItemParameterMapping;
import project.shop.api.v1.converter.QueryParser;
import project.shop.api.v1.converter.StringToItemParameter;
import project.shop.api.v1.items.dto.create.CreateItemRequest;
import project.shop.api.v1.items.dto.create.CreateItemResponse;
import project.shop.api.v1.items.dto.delete.DeleteItemResponse;
import project.shop.api.v1.items.dto.read.ItemDto;
import project.shop.api.v1.items.dto.read.ItemResult;
import project.shop.api.v1.Errors;
import project.shop.api.v1.items.dto.update.UpdateItemRequest;
import project.shop.api.v1.items.dto.update.UpdateItemResponse;
import project.shop.domain.item.Item;
import project.shop.repository.springdatajpa.SpringJpaItemRepository;
import project.shop.service.ItemService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ItemApiController {

    private final ItemService itemService;
    private final SpringJpaItemRepository itemRepository;

    /**
     * ์ํ ์กฐํ API
     */

    @GetMapping("/api/v1/items")
    public ResponseEntity allItems(@RequestParam @Nullable String query) {

        log.info("query= {}", query);

        if (query == null) {
            List<Item> findItems = itemRepository.findAll();
            List<ItemDto> collect = changeItemDto(findItems);

            return new ResponseEntity(new ItemResult(collect.size(), collect), HttpStatus.OK);
        }

        QueryParser<ItemParameterMapping> q = new QueryParser<>();
        ItemParameterMapping parse = q.parse(query, new ItemParameterMapping());
        String itemName = parse.getItemName();
        Integer price = parse.getPrice();
        String sign = parse.getSign();

        if (sign == null) {
            sign = "=";
        }

//        StringToItemParameter converter = new StringToItemParameter();
//
//        String itemName = converter.convert(query).getItemName();
//        Integer price = converter.convert(query).getPrice();
//        String sign = converter.convert(query).getSign();

        List<Item> findItems = itemService.findItems(itemName, price, sign);
//        List<Item> findItems = itemRepository.findParamQuery(itemName, String.valueOf(price), sign);
        List<ItemDto> collect = changeItemDto(findItems);
        log.info("collects= {}", collect);

        return new ResponseEntity(new ItemResult(collect.size(), collect), HttpStatus.OK);
    }

    private List<ItemDto> changeItemDto(List<Item> items) {
        return items.stream()
                .map(item -> new ItemDto(item.getName(), item.getPrice(), item.getStockQuantity()))
                .collect(Collectors.toList());
    }

    @PostMapping("/api/v1/items/new")
    public ResponseEntity createItem(@RequestBody @Validated CreateItemRequest param, BindingResult bindingResult, HttpServletRequest request) {

        if (param.getPrice() != null && param.getStockQuantity() != null) {
            if (param.getPrice() * param.getStockQuantity() < 10000) {
                bindingResult.addError(new ObjectError("minRegisterPrice", "์ํ ๊ฐ๊ฒฉ * ์ฌ๊ณ?๊ฐ 10,000์ ์ด์์ด์ด์ผ ํฉ๋๋ค."));
            }
        }

        if (bindingResult.hasErrors()) {
            List<String> errorList = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage()).collect(Collectors.toList());
            return new ResponseEntity(new Errors(errorList), HttpStatus.BAD_REQUEST);
        }

        Long itemId = itemRepository.save(saveItem(param)).getId();

        Item findItem = itemRepository.findById(itemId).get();

        return new ResponseEntity(new CreateItemResponse(itemId, findItem.getName(), request.getMethod(), HttpStatus.OK), HttpStatus.OK);
    }

    @Transactional
    @PatchMapping("/api/v1/items/{itemId}/edit")
    public ResponseEntity updateItem(@PathVariable("itemId") Long itemId,
                                     @RequestBody @Validated UpdateItemRequest param, BindingResult bindingResult,
                                     HttpServletRequest request) {

        if (param.getPrice() != null && param.getStockQuantity() != null) {
            if (param.getPrice() * param.getStockQuantity() < 10000) {
                bindingResult.addError(new ObjectError("minRegisterPrice", "์ํ ๊ฐ๊ฒฉ * ์ฌ๊ณ?๊ฐ 10,000์ ์ด์์ด์ด์ผ ํฉ๋๋ค."));
            }
        }

        if (bindingResult.hasErrors()) {
            List<String> errorList = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage()).collect(Collectors.toList());
            return new ResponseEntity(new Errors(errorList), HttpStatus.BAD_REQUEST);
        }

        Long updateItemId = update(itemId, param);

//        itemService.update(itemId, param.getItemName(), param.getPrice(), param.getStockQuantity());

        Item findItem = itemRepository.findById(updateItemId).get();

        return new ResponseEntity(new UpdateItemResponse(itemId, findItem.getName(), findItem.getPrice(), findItem.getStockQuantity(), request.getMethod(), HttpStatus.OK), HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/items/{itemId}/delete")
    public ResponseEntity itemDelete(@PathVariable("itemId") Long itemId, HttpServletRequest request) {
        itemRepository.deleteById(itemId);

        return new ResponseEntity(new DeleteItemResponse(request.getRequestURI(), request.getMethod(), HttpStatus.OK), HttpStatus.OK);
    }

    private Item saveItem(CreateItemRequest param) {
        Item item = new Item();
        item.setName(param.getItemName());
        item.setPrice(param.getPrice());
        item.setStockQuantity(param.getStockQuantity());

        return item;
    }

    private Long update(Long id, UpdateItemRequest request) {
        Item findItem = itemRepository.findById(id).get();
        findItem.setName(request.getItemName());
        findItem.setPrice(request.getPrice());
        findItem.setStockQuantity(request.getStockQuantity());

        return findItem.getId();
    }
}
