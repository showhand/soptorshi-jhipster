import { Moment } from 'moment';

export const enum SupplyAreaWiseAccumulationStatus {
    PENDING = 'PENDING',
    FORWARDED = 'FORWARDED',
    APPROVED = 'APPROVED',
    REJECTED = 'REJECTED'
}

export interface ISupplyAreaWiseAccumulation {
    id?: number;
    areaWiseAccumulationRefNo?: string;
    quantity?: number;
    price?: number;
    status?: SupplyAreaWiseAccumulationStatus;
    zoneWiseAccumulationRefNo?: string;
    remarks?: string;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
    supplyZoneName?: string;
    supplyZoneId?: number;
    supplyZoneManagerId?: number;
    supplyAreaName?: string;
    supplyAreaId?: number;
    supplyAreaManagerId?: number;
    productCategoryName?: string;
    productCategoryId?: number;
    productName?: string;
    productId?: number;
}

export class SupplyAreaWiseAccumulation implements ISupplyAreaWiseAccumulation {
    constructor(
        public id?: number,
        public areaWiseAccumulationRefNo?: string,
        public quantity?: number,
        public price?: number,
        public status?: SupplyAreaWiseAccumulationStatus,
        public zoneWiseAccumulationRefNo?: string,
        public remarks?: string,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment,
        public supplyZoneName?: string,
        public supplyZoneId?: number,
        public supplyZoneManagerId?: number,
        public supplyAreaName?: string,
        public supplyAreaId?: number,
        public supplyAreaManagerId?: number,
        public productCategoryName?: string,
        public productCategoryId?: number,
        public productName?: string,
        public productId?: number
    ) {}
}
