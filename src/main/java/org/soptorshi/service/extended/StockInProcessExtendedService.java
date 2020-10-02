package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.StockInProcess;
import org.soptorshi.domain.enumeration.StockInProcessStatus;
import org.soptorshi.repository.StockInProcessRepository;
import org.soptorshi.repository.search.StockInProcessSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.StockInProcessService;
import org.soptorshi.service.dto.StockInItemDTO;
import org.soptorshi.service.dto.StockInProcessDTO;
import org.soptorshi.service.dto.StockStatusDTO;
import org.soptorshi.service.mapper.StockInProcessMapper;
import org.soptorshi.service.mapper.StockStatusMapper;
import org.soptorshi.web.rest.errors.InternalServerErrorException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;

import static java.lang.Double.parseDouble;

@Service
@Transactional
public class StockInProcessExtendedService extends StockInProcessService {

    private final Logger log = LoggerFactory.getLogger(StockInProcessExtendedService.class);

    private final StockInProcessRepository stockInProcessRepository;

    private final StockInProcessMapper stockInProcessMapper;

    private final StockInItemExtendedService stockInItemExtendedService;

    private final StockStatusExtendedService stockStatusExtendedService;

    private final StockStatusMapper stockStatusMapper;

    public StockInProcessExtendedService(StockInProcessRepository stockInProcessRepository, StockInProcessMapper stockInProcessMapper, StockInProcessSearchRepository stockInProcessSearchRepository, StockInItemExtendedService stockInItemExtendedService, StockStatusMapper stockStatusMapper, StockStatusExtendedService stockStatusExtendedService) {
        super(stockInProcessRepository, stockInProcessMapper, stockInProcessSearchRepository);
        this.stockInProcessRepository = stockInProcessRepository;
        this.stockInProcessMapper = stockInProcessMapper;
        this.stockInItemExtendedService = stockInItemExtendedService;
        this.stockStatusMapper = stockStatusMapper;
        this.stockStatusExtendedService = stockStatusExtendedService;
    }

    /**
     * Save a stockInProcess.
     *
     * @param stockInProcessDTO the entity to save
     * @return the persisted entity
     */
    @Transactional
    public StockInProcessDTO save(StockInProcessDTO stockInProcessDTO) {
        log.debug("Request to save StockInProcess : {}", stockInProcessDTO);

        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "";
        Instant currentDateTime = Instant.now();

        if(stockInProcessDTO.getId() == null) {
            stockInProcessDTO.setProcessStartedBy(currentUser);
            stockInProcessDTO.setProcessStartedOn(currentDateTime);
            stockInProcessDTO.setStatus(StockInProcessStatus.WAITING_FOR_STOCK_IN_PROCESS);
            StockInProcess stockInProcess = stockInProcessMapper.toEntity(stockInProcessDTO);
            stockInProcess = stockInProcessRepository.save(stockInProcess);
            return stockInProcessMapper.toDto(stockInProcess);
        }
        else {
            if(stockInItemExtendedService.exists(stockInProcessMapper.toEntity(stockInProcessDTO))) {
                throw new InternalServerErrorException("Duplicate Stock In Process found!! Stock In Process has been completed before for this record.");
            }
            else{
                stockInProcessDTO.setStockInBy(currentUser);
                stockInProcessDTO.setStockInDate(currentDateTime);
                stockInProcessDTO.setStatus(StockInProcessStatus.COMPLETED_STOCK_IN_PROCESS);
                StockInProcess stockInProcess = stockInProcessMapper.toEntity(stockInProcessDTO);
                stockInProcess = stockInProcessRepository.save(stockInProcess);
                StockInProcessDTO result = stockInProcessMapper.toDto(stockInProcess);
                int response = insertInStock(result, currentUser, currentDateTime);
                if (response == 0)
                    throw new InternalServerErrorException("Mismatch in item container or item per container.");
                return result;
            }
        }
    }

    private int insertInStock(StockInProcessDTO stockInProcessDTO, String currentUser, Instant currentDateTime) {
        String[] containerIds = getContainerIds(stockInProcessDTO);
        String[] itemsPerContainer = stockInProcessDTO.getQuantityPerContainer().split(",");
        if (validateRecord(stockInProcessDTO, containerIds, itemsPerContainer)) return 0;

        for (int i = 0; i < stockInProcessDTO.getTotalContainer(); i++) {
            StockInItemDTO stockInItemDTO = getStockInItemDTO(stockInProcessDTO, BigDecimal.valueOf(parseDouble(itemsPerContainer[i].trim())), containerIds[i].trim(), currentUser, currentDateTime);
            StockInItemDTO result = stockInItemExtendedService.save(stockInItemDTO);

            StockStatusDTO stockStatusDTO = getStockStatus(stockInProcessDTO, BigDecimal.valueOf(parseDouble(itemsPerContainer[i].trim())), containerIds[i].trim(), currentUser, currentDateTime, result.getId());
            stockStatusExtendedService.save(stockStatusDTO);
        }
        return 1;
    }

    private boolean validateRecord(StockInProcessDTO stockInProcessDTO, String[] containerIds, String[] itemsPerContainer) {
        if (!isValidTotalContainer(stockInProcessDTO)) return true;
        if (!isNumberOfContainerAndNumberOfContainerTrackingIdEqual(stockInProcessDTO, containerIds))
            return true;
        if (!isNumberOfContainerAndNumberOfItemsEqual(stockInProcessDTO, itemsPerContainer))
            return true;
        if (isAnyContainerTrackingIdEmpty(containerIds)) return true;
        if (isAnyItemPerContainerEmpty(itemsPerContainer)) return true;
        return isTotalQuantityAndSumOfItemsPerContainerEqual(stockInProcessDTO, itemsPerContainer);
    }

    public boolean isTotalQuantityAndSumOfItemsPerContainerEqual(StockInProcessDTO stockInProcessDTO, String[] itemsPerContainer) {
        BigDecimal totalQuantity = BigDecimal.ZERO;
        for (String itemPerContainer : itemsPerContainer) {
            Double d = new Double(itemPerContainer);
            BigDecimal bigDecimal = new BigDecimal(d);
            totalQuantity = bigDecimal.add(totalQuantity);
        }
        return !totalQuantity.equals(stockInProcessDTO.getTotalQuantity());
    }

    private boolean isAnyItemPerContainerEmpty(String[] itemsPerContainer) {
        for (String itemPerContainer : itemsPerContainer) {
            if (itemPerContainer.trim().isEmpty()) return true;
        }
        return false;
    }

    private boolean isAnyContainerTrackingIdEmpty(String[] containerIds) {
        for (String containerId : containerIds) {
            if (containerId.trim().isEmpty()) return true;
        }
        return false;
    }

    private boolean isNumberOfContainerAndNumberOfContainerTrackingIdEqual(StockInProcessDTO stockInProcessDTO, String[] containerIds) {
        return containerIds.length == stockInProcessDTO.getTotalContainer();
    }

    private boolean isNumberOfContainerAndNumberOfItemsEqual(StockInProcessDTO stockInProcessDTO, String[] itemsPerContainer) {
        return itemsPerContainer.length == stockInProcessDTO.getTotalContainer();
    }

    private String[] getContainerIds(StockInProcessDTO stockInProcessDTO) {
        return Arrays.stream(stockInProcessDTO.getContainerTrackingId().split(",")
        ).distinct().toArray(String[]::new);
    }

    private boolean isValidTotalContainer(StockInProcessDTO stockInProcessDTO) {
        return stockInProcessDTO.getTotalContainer() != null && stockInProcessDTO.getTotalContainer() != 0;
    }


    private StockInItemDTO getStockInItemDTO(StockInProcessDTO stockInProcessDTO, BigDecimal quantity, String containerId, String currentUserId, Instant currentDateTime) {
        StockInItemDTO stockInItemDTO = new StockInItemDTO();
        stockInItemDTO.setProductCategoriesId(stockInProcessDTO.getProductCategoriesId());
        stockInItemDTO.setProductsId(stockInProcessDTO.getProductsId());
        stockInItemDTO.setInventoryLocationsId(stockInProcessDTO.getInventoryLocationsId());
        stockInItemDTO.setInventorySubLocationsId(stockInProcessDTO.getInventorySubLocationsId());
        stockInItemDTO.setQuantity(quantity);
        stockInItemDTO.setUnit(stockInProcessDTO.getUnit());
        stockInItemDTO.setPrice(stockInProcessDTO.getUnitPrice().multiply(quantity));
        stockInItemDTO.setContainerCategory(stockInProcessDTO.getContainerCategory());
        stockInItemDTO.setContainerTrackingId(containerId);
        stockInItemDTO.setMfgDate(stockInProcessDTO.getMfgDate());
        stockInItemDTO.setExpiryDate(stockInProcessDTO.getExpiryDate());
        stockInItemDTO.setStockInBy(currentUserId);
        stockInItemDTO.setStockInDate(currentDateTime);
        stockInItemDTO.setRemarks(stockInProcessDTO.getRemarks());
        stockInItemDTO.setStockInProcessesId(stockInProcessDTO.getId());
        return stockInItemDTO;
    }

    private StockStatusDTO getStockStatus(StockInProcessDTO stockInProcessDTO, BigDecimal quantity, String containerId, String currentUserId, Instant currentDateTime, Long stockInItemId) {
        StockStatusDTO stockStatusDTO = new StockStatusDTO();
        stockStatusDTO.setProductCategoriesId(stockInProcessDTO.getProductCategoriesId());
        stockStatusDTO.setProductsId(stockInProcessDTO.getProductsId());
        stockStatusDTO.setInventoryLocationsId(stockInProcessDTO.getInventoryLocationsId());
        stockStatusDTO.setInventorySubLocationsId(stockInProcessDTO.getInventorySubLocationsId());
        stockStatusDTO.setContainerTrackingId(containerId);
        stockStatusDTO.setTotalQuantity(quantity);
        stockStatusDTO.setAvailableQuantity(quantity);
        stockStatusDTO.setUnit(stockInProcessDTO.getUnit());
        stockStatusDTO.setTotalPrice(stockInProcessDTO.getUnitPrice().multiply(quantity));
        stockStatusDTO.setAvailablePrice(stockInProcessDTO.getUnitPrice().multiply(quantity));
        stockStatusDTO.setStockInBy(currentUserId);
        stockStatusDTO.setStockInDate(currentDateTime);
        stockStatusDTO.setStockInItemId(stockInItemId);
        return stockStatusDTO;
    }
}
