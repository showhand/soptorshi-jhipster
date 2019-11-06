import { Moment } from 'moment';

export interface ISupplyMoneyCollection {
    id?: number;
    totalAmount?: number;
    receivedAmount?: number;
    remarks?: string;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
    supplyZoneZoneName?: string;
    supplyZoneId?: number;
    supplyAreaAreaName?: string;
    supplyAreaId?: number;
    supplyAreaManagerManagerName?: string;
    supplyAreaManagerId?: number;
    supplySalesRepresentativeSalesRepresentativeName?: string;
    supplySalesRepresentativeId?: number;
}

export class SupplyMoneyCollection implements ISupplyMoneyCollection {
    constructor(
        public id?: number,
        public totalAmount?: number,
        public receivedAmount?: number,
        public remarks?: string,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment,
        public supplyZoneZoneName?: string,
        public supplyZoneId?: number,
        public supplyAreaAreaName?: string,
        public supplyAreaId?: number,
        public supplyAreaManagerManagerName?: string,
        public supplyAreaManagerId?: number,
        public supplySalesRepresentativeSalesRepresentativeName?: string,
        public supplySalesRepresentativeId?: number
    ) {}
}
