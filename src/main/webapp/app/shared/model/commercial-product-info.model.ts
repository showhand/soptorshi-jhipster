import { Moment } from 'moment';

export const enum UnitOfMeasurements {
    PCS = 'PCS',
    KG = 'KG',
    TON = 'TON',
    GRAM = 'GRAM'
}

export interface ICommercialProductInfo {
    id?: number;
    serialNo?: number;
    packagingDescription?: string;
    othersDescription?: string;
    offeredQuantity?: number;
    offeredUnit?: UnitOfMeasurements;
    offeredUnitPrice?: number;
    offeredTotalPrice?: number;
    buyingQuantity?: number;
    buyingUnit?: UnitOfMeasurements;
    buyingUnitPrice?: number;
    buyingTotalPrice?: number;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
    commercialBudgetBudgetNo?: string;
    commercialBudgetId?: number;
    productCategoriesName?: string;
    productCategoriesId?: number;
    productsName?: string;
    productsId?: number;
}

export class CommercialProductInfo implements ICommercialProductInfo {
    constructor(
        public id?: number,
        public serialNo?: number,
        public packagingDescription?: string,
        public othersDescription?: string,
        public offeredQuantity?: number,
        public offeredUnit?: UnitOfMeasurements,
        public offeredUnitPrice?: number,
        public offeredTotalPrice?: number,
        public buyingQuantity?: number,
        public buyingUnit?: UnitOfMeasurements,
        public buyingUnitPrice?: number,
        public buyingTotalPrice?: number,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment,
        public commercialBudgetBudgetNo?: string,
        public commercialBudgetId?: number,
        public productCategoriesName?: string,
        public productCategoriesId?: number,
        public productsName?: string,
        public productsId?: number
    ) {}
}
