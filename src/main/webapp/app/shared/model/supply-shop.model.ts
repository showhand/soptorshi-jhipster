import { Moment } from 'moment';

export interface ISupplyShop {
    id?: number;
    shopName?: string;
    additionalInformation?: string;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
    supplyZoneZoneName?: string;
    supplyZoneId?: number;
    supplyAreaAreaName?: string;
    supplyAreaId?: number;
    supplySalesRepresentativeSalesRepresentativeName?: string;
    supplySalesRepresentativeId?: number;
    supplyAreaManagerId?: number;
}

export class SupplyShop implements ISupplyShop {
    constructor(
        public id?: number,
        public shopName?: string,
        public additionalInformation?: string,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment,
        public supplyZoneZoneName?: string,
        public supplyZoneId?: number,
        public supplyAreaAreaName?: string,
        public supplyAreaId?: number,
        public supplySalesRepresentativeSalesRepresentativeName?: string,
        public supplySalesRepresentativeId?: number,
        public supplyAreaManagerId?: number
    ) {}
}
