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

export const enum ProductType {
    FINISHED_PRODUCT = 'FINISHED_PRODUCT',
    BY_PRODUCT = 'BY_PRODUCT'
}

export const enum StockInProcessStatus {
    WAITING_FOR_STOCK_IN_PROCESS = 'WAITING_FOR_STOCK_IN_PROCESS',
    COMPLETED_STOCK_IN_PROCESS = 'COMPLETED_STOCK_IN_PROCESS'
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
    mfgDate?: Moment;
    expiryDate?: Moment;
    typeOfProduct?: ProductType;
    status?: StockInProcessStatus;
    processStartedBy?: string;
    processStartedOn?: Moment;
    stockInBy?: string;
    stockInDate?: Moment;
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
    requisitionsRequisitionNo?: string;
    requisitionsId?: number;
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
        public mfgDate?: Moment,
        public expiryDate?: Moment,
        public typeOfProduct?: ProductType,
        public status?: StockInProcessStatus,
        public processStartedBy?: string,
        public processStartedOn?: Moment,
        public stockInBy?: string,
        public stockInDate?: Moment,
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
        public vendorId?: number,
        public requisitionsRequisitionNo?: string,
        public requisitionsId?: number
    ) {}
}
