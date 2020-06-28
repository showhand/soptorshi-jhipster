import { Moment } from 'moment';

export const enum SupplyZoneWiseAccumulationStatus {
    PENDING = 'PENDING',
    APPROVED = 'APPROVED',
    REJECTED = 'REJECTED'
}

export interface ISupplyZoneWiseAccumulation {
    id?: number;
    zoneWiseAccumulationRefNo?: string;
    quantity?: number;
    price?: number;
    status?: SupplyZoneWiseAccumulationStatus;
    remarks?: string;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
    supplyZoneName?: string;
    supplyZoneId?: number;
    supplyZoneManagerId?: number;
    productCategoryName?: string;
    productCategoryId?: number;
    productName?: string;
    productId?: number;
}

export class SupplyZoneWiseAccumulation implements ISupplyZoneWiseAccumulation {
    constructor(
        public id?: number,
        public zoneWiseAccumulationRefNo?: string,
        public quantity?: number,
        public price?: number,
        public status?: SupplyZoneWiseAccumulationStatus,
        public remarks?: string,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment,
        public supplyZoneName?: string,
        public supplyZoneId?: number,
        public supplyZoneManagerId?: number,
        public productCategoryName?: string,
        public productCategoryId?: number,
        public productName?: string,
        public productId?: number
    ) {}
}
