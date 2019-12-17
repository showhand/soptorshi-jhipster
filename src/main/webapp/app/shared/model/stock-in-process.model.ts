import { Moment } from 'moment';

export const enum UnitOfMeasurements {
    PCS = 'PCS',
    KG = 'KG',
    TON = 'TON',
    GRAM = 'GRAM'
}

export const enum ContainerCategory {
    BOTTLE = 'BOTTLE',
    DRUM = 'DRUM',
    PACKET = 'PACKET',
    SACK = 'SACK',
    OTHERS = 'OTHERS'
}

export interface IStockInProcess {
    id?: number;
    totalQuantity?: number;
    unit?: UnitOfMeasurements;
    unitPrice?: number;
    totalContainer?: number;
    containerCategory?: ContainerCategory;
    containerTrackingId?: string;
    quantityPerContainer?: string;
    expiryDate?: Moment;
    stockInBy?: string;
    stockInDate?: Moment;
    purchaseOrderId?: string;
    remarks?: string;
    productCategoriesName?: string;
    productCategoriesId?: number;
    productsName?: string;
    productsId?: number;
    inventoryLocationsName?: string;
    inventoryLocationsId?: number;
    inventorySubLocationsName?: string;
    inventorySubLocationsId?: number;
    vendorCompanyName?: string;
    vendorId?: number;
}

export class StockInProcess implements IStockInProcess {
    constructor(
        public id?: number,
        public totalQuantity?: number,
        public unit?: UnitOfMeasurements,
        public unitPrice?: number,
        public totalContainer?: number,
        public containerCategory?: ContainerCategory,
        public containerTrackingId?: string,
        public quantityPerContainer?: string,
        public expiryDate?: Moment,
        public stockInBy?: string,
        public stockInDate?: Moment,
        public purchaseOrderId?: string,
        public remarks?: string,
        public productCategoriesName?: string,
        public productCategoriesId?: number,
        public productsName?: string,
        public productsId?: number,
        public inventoryLocationsName?: string,
        public inventoryLocationsId?: number,
        public inventorySubLocationsName?: string,
        public inventorySubLocationsId?: number,
        public vendorCompanyName?: string,
        public vendorId?: number
    ) {}
}
