import { Moment } from 'moment';

export const enum ItemUnit {
    KG = 'KG',
    PCS = 'PCS',
    LITER = 'LITER',
    DOZEN = 'DOZEN'
}

export const enum ContainerCategory {
    BOTTLE = 'BOTTLE',
    DRUM = 'DRUM',
    PACKET = 'PACKET',
    SACK = 'SACK'
}

export interface IStockInItem {
    id?: number;
    quantity?: number;
    unit?: ItemUnit;
    price?: number;
    containerCategory?: ContainerCategory;
    containerTrackingId?: string;
    expiryDate?: Moment;
    stockInBy?: string;
    stockInDate?: Moment;
    purchaseOrderId?: string;
    remarks?: string;
    itemCategoriesName?: string;
    itemCategoriesId?: number;
    itemSubCategoriesName?: string;
    itemSubCategoriesId?: number;
    inventoryLocationsName?: string;
    inventoryLocationsId?: number;
    inventorySubLocationsName?: string;
    inventorySubLocationsId?: number;
    manufacturersName?: string;
    manufacturersId?: number;
    stockInProcessesId?: number;
}

export class StockInItem implements IStockInItem {
    constructor(
        public id?: number,
        public quantity?: number,
        public unit?: ItemUnit,
        public price?: number,
        public containerCategory?: ContainerCategory,
        public containerTrackingId?: string,
        public expiryDate?: Moment,
        public stockInBy?: string,
        public stockInDate?: Moment,
        public purchaseOrderId?: string,
        public remarks?: string,
        public itemCategoriesName?: string,
        public itemCategoriesId?: number,
        public itemSubCategoriesName?: string,
        public itemSubCategoriesId?: number,
        public inventoryLocationsName?: string,
        public inventoryLocationsId?: number,
        public inventorySubLocationsName?: string,
        public inventorySubLocationsId?: number,
        public manufacturersName?: string,
        public manufacturersId?: number,
        public stockInProcessesId?: number
    ) {}
}
