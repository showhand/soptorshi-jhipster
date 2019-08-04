import { Moment } from 'moment';

export interface IStockStatus {
    id?: number;
    containerTrackingId?: string;
    totalQuantity?: number;
    availableQuantity?: number;
    totalPrice?: number;
    availablePrice?: number;
    stockInBy?: string;
    stockInDate?: Moment;
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
