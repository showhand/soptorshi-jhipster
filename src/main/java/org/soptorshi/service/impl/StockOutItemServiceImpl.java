package org.soptorshi.service.impl;

import org.soptorshi.domain.StockInItem;
import org.soptorshi.domain.StockStatus;
import org.soptorshi.repository.*;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.StockOutItemService;
import org.soptorshi.domain.StockOutItem;
import org.soptorshi.repository.search.StockOutItemSearchRepository;
import org.soptorshi.service.StockStatusService;
import org.soptorshi.service.dto.StockOutItemDTO;
import org.soptorshi.service.mapper.StockOutItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.soptorshi.web.rest.errors.InternalServerErrorException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing StockOutItem.
 */
@Service
@Transactional
public class StockOutItemServiceImpl implements StockOutItemService {

    private final Logger log = LoggerFactory.getLogger(StockOutItemServiceImpl.class);

    private final StockOutItemRepository stockOutItemRepository;

    private final StockOutItemMapper stockOutItemMapper;

    private final StockOutItemSearchRepository stockOutItemSearchRepository;

    private final StockStatusService stockStatusService;

    private final StockStatusRepository stockStatusRepository;

    private final ItemCategoryRepository itemCategoryRepository;

    private final ItemSubCategoryRepository itemSubCategoryRepository;

    private final InventoryLocationRepository inventoryLocationRepository;

    private final InventorySubLocationRepository inventorySubLocationRepository;

    private final StockInItemRepository stockInItemRepository;

    public StockOutItemServiceImpl(StockOutItemRepository stockOutItemRepository, StockOutItemMapper stockOutItemMapper, StockOutItemSearchRepository stockOutItemSearchRepository,
                                   StockStatusService stockStatusService,
                                   StockStatusRepository stockStatusRepository,
                                   ItemCategoryRepository itemCategoryRepository, ItemSubCategoryRepository itemSubCategoryRepository,
                                   InventoryLocationRepository inventoryLocationRepository, InventorySubLocationRepository inventorySubLocationRepository, StockInItemRepository stockInItemRepository) {
        this.stockOutItemRepository = stockOutItemRepository;
        this.stockOutItemMapper = stockOutItemMapper;
        this.stockOutItemSearchRepository = stockOutItemSearchRepository;
        this.stockStatusService = stockStatusService;
        this.stockStatusRepository = stockStatusRepository;
        this.itemCategoryRepository = itemCategoryRepository;
        this.itemSubCategoryRepository = itemSubCategoryRepository;
        this.inventoryLocationRepository = inventoryLocationRepository;
        this.inventorySubLocationRepository = inventorySubLocationRepository;
        this.stockInItemRepository = stockInItemRepository;
    }

    /**
     * Save a stockOutItem.
     *
     * @param stockOutItemDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StockOutItemDTO save(StockOutItemDTO stockOutItemDTO) {
        log.debug("Request to save StockOutItem : {}", stockOutItemDTO);
        if (stockOutItemDTO.getId() == null) {
            String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().toString() : "";
            Instant currentDateTime = Instant.now();

            StockInItem stockInItem = stockInItemRepository.getByItemCategoriesAndItemSubCategoriesAndInventoryLocationsAndInventorySubLocationsAndContainerTrackingId(
                itemCategoryRepository.getOne(stockOutItemDTO.getItemCategoriesId()),
                itemSubCategoryRepository.getOne(stockOutItemDTO.getItemSubCategoriesId()),
                inventoryLocationRepository.getOne(stockOutItemDTO.getInventoryLocationsId()),
                inventorySubLocationRepository.getOne(stockOutItemDTO.getInventorySubLocationsId()),
                stockOutItemDTO.getContainerTrackingId());

            StockStatus stockStatus = stockStatusRepository.getByItemCategoriesAndItemSubCategoriesAndInventoryLocationsAndInventorySubLocationsAndContainerTrackingId(
                itemCategoryRepository.getOne(stockOutItemDTO.getItemCategoriesId()),
                itemSubCategoryRepository.getOne(stockOutItemDTO.getItemSubCategoriesId()),
                inventoryLocationRepository.getOne(stockOutItemDTO.getInventoryLocationsId()),
                inventorySubLocationRepository.getOne(stockOutItemDTO.getInventorySubLocationsId()),
                stockOutItemDTO.getContainerTrackingId());

            if (stockInItem != null && stockStatus != null) {
                stockOutItemDTO.setStockOutBy(currentUser);
                stockOutItemDTO.setStockOutDate(currentDateTime);
                stockOutItemDTO.setStockInItemsId(stockInItem.getId());
                stockOutItemDTO.setStockStatusesId(stockStatus.getId());
                int response = updateStockStatus(stockOutItemDTO, stockStatus, currentUser, currentDateTime);
                if (response == 1) {
                    StockOutItem stockOutItem = stockOutItemMapper.toEntity(stockOutItemDTO);
                    stockOutItem = stockOutItemRepository.save(stockOutItem);
                    /*stockOutItemSearchRepository.save(stockOutItem);*/
                    return stockOutItemMapper.toDto(stockOutItem);
                }
                throw new InternalServerErrorException("Item can not be stock out.");
            }
            throw new InternalServerErrorException("Illegal operation.");
        }
        throw new InternalServerErrorException("Only insert operation is available.");
    }

    /**
     * Get all the stockOutItems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StockOutItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StockOutItems");
        return stockOutItemRepository.findAll(pageable)
            .map(stockOutItemMapper::toDto);
    }


    /**
     * Get one stockOutItem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StockOutItemDTO> findOne(Long id) {
        log.debug("Request to get StockOutItem : {}", id);
        return stockOutItemRepository.findById(id)
            .map(stockOutItemMapper::toDto);
    }

    /**
     * Delete the stockOutItem by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StockOutItem : {}", id);
        stockOutItemRepository.deleteById(id);
        stockOutItemSearchRepository.deleteById(id);
    }

    /**
     * Search for the stockOutItem corresponding to the query.
     *
     * @param query    the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StockOutItemDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of StockOutItems for query {}", query);
        return stockOutItemSearchRepository.search(queryStringQuery(query), pageable)
            .map(stockOutItemMapper::toDto);
    }

    private int updateStockStatus(StockOutItemDTO stockOutItemDTO,
                                  StockStatus stockStatus, String currentUser, Instant currentDateTime) {
        if (stockStatus != null) {
            if (stockOutItemDTO.getQuantity().compareTo(stockStatus.getAvailableQuantity()) > 0) {
                throw new InternalServerErrorException("Required quantity is greater than available quantity");
            } else {
                stockStatus.setAvailableQuantity(stockStatus.getAvailableQuantity() - stockOutItemDTO.getQuantity());
                stockStatus.setAvailablePrice(stockStatus.getAvailablePrice() - (stockOutItemDTO.getQuantity() * stockStatus.getStockInItems().getStockInProcesses().getUnitPrice()));
                stockStatusRepository.save(stockStatus);
            }
            return 1;
        }
        return  0;
    }
}
