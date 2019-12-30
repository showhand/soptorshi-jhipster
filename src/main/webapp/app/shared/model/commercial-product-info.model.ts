import { Moment } from 'moment';

export const enum ProductSpecification {
    FILLET = 'FILLET',
    STEAK = 'STEAK',
    CUBE = 'CUBE',
    BLOCK = 'BLOCK',
    BUTTERFLY = 'BUTTERFLY'
}

export const enum UnitOfMeasurements {
    PCS = 'PCS',
    KG = 'KG',
    TON = 'TON',
    GRAM = 'GRAM'
}

export const enum PackColor {
    PLAIN = 'PLAIN',
    PRINT = 'PRINT',
    BOTH = 'BOTH',
    NONE = 'NONE'
}

export interface ICommercialProductInfo {
    id?: number;
    taskNo?: number;
    productSpecification?: ProductSpecification;
    spSize?: string;
    offeredQuantity?: number;
    offeredUnit?: UnitOfMeasurements;
    offeredUnitPrice?: number;
    offeredTotalPrice?: number;
    spSticker?: string;
    spLabel?: string;
    spQtyInPack?: number;
    spQtyInMc?: number;
    ipColor?: PackColor;
    ipSize?: string;
    ipSticker?: string;
    ipLabel?: string;
    ipQtyInMc?: number;
    mcColor?: PackColor;
    mcPly?: string;
    mcSize?: string;
    mcSticker?: string;
    mcLabel?: string;
    cylColor?: string;
    cylSize?: string;
    cylQty?: number;
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
        public taskNo?: number,
        public productSpecification?: ProductSpecification,
        public spSize?: string,
        public offeredQuantity?: number,
        public offeredUnit?: UnitOfMeasurements,
        public offeredUnitPrice?: number,
        public offeredTotalPrice?: number,
        public spSticker?: string,
        public spLabel?: string,
        public spQtyInPack?: number,
        public spQtyInMc?: number,
        public ipColor?: PackColor,
        public ipSize?: string,
        public ipSticker?: string,
        public ipLabel?: string,
        public ipQtyInMc?: number,
        public mcColor?: PackColor,
        public mcPly?: string,
        public mcSize?: string,
        public mcSticker?: string,
        public mcLabel?: string,
        public cylColor?: string,
        public cylSize?: string,
        public cylQty?: number,
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
