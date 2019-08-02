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

export interface IStockInProcess {
    id?: number;
    totalQuantity?: number;
    unit?: ItemUnit;
    unitPrice?: number;
    totalContainer?: number;
    containerCategory?: ContainerCategory;
    containerTrackingId?: string;
    itemPerContainer?: string;
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
}

export class StockInProcess implements IStockInProcess {
    constructor(
        public id?: number,
        public totalQuantity?: number,
        public unit?: ItemUnit,
        public unitPrice?: number,
        public totalContainer?: number,
        public containerCategory?: ContainerCategory,
        public containerTrackingId?: string,
        public itemPerContainer?: string,
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
        public manufacturersId?: number
    ) {}
}
