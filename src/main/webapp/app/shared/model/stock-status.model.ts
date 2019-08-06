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

export interface IStockStatus {
    id?: number;
    containerTrackingId?: string;
    totalQuantity?: number;
    availableQuantity?: number;
    totalPrice?: number;
    availablePrice?: number;
    stockInBy?: string;
    stockInDate?: Moment;
    unit?: ItemUnit;
    containerCategory?: ContainerCategory;
    expiryDate?: Moment;
    stockInItemId?: number;
    itemCategoriesName?: string;
    itemCategoriesId?: number;
    itemSubCategoriesName?: string;
    itemSubCategoriesId?: number;
    inventoryLocationsName?: string;
    inventoryLocationsId?: number;
    inventorySubLocationsName?: string;
    inventorySubLocationsId?: number;
}

export class StockStatus implements IStockStatus {
    constructor(
        public id?: number,
        public containerTrackingId?: string,
        public totalQuantity?: number,
        public availableQuantity?: number,
        public totalPrice?: number,
        public availablePrice?: number,
        public stockInBy?: string,
        public stockInDate?: Moment,
        public unit?: ItemUnit,
        public containerCategory?: ContainerCategory,
        public expiryDate?: Moment,
        public stockInItemId?: number,
        public itemCategoriesName?: string,
        public itemCategoriesId?: number,
        public itemSubCategoriesName?: string,
        public itemSubCategoriesId?: number,
        public inventoryLocationsName?: string,
        public inventoryLocationsId?: number,
        public inventorySubLocationsName?: string,
        public inventorySubLocationsId?: number
    ) {}
}
