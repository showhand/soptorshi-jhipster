import { Moment } from 'moment';

export const enum SupplySalesRepresentativeStatus {
    ACTIVE = 'ACTIVE',
    INACTIVE = 'INACTIVE'
}

export interface ISupplySalesRepresentative {
    id?: number;
    name?: string;
    contact?: string;
    email?: string;
    additionalInformation?: string;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
    status?: SupplySalesRepresentativeStatus;
    supplyZoneName?: string;
    supplyZoneId?: number;
    supplyAreaName?: string;
    supplyAreaId?: number;
    supplyZoneManagerId?: number;
    supplyAreaManagerId?: number;
}

export class SupplySalesRepresentative implements ISupplySalesRepresentative {
    constructor(
        public id?: number,
        public name?: string,
        public contact?: string,
        public email?: string,
        public additionalInformation?: string,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment,
        public status?: SupplySalesRepresentativeStatus,
        public supplyZoneName?: string,
        public supplyZoneId?: number,
        public supplyAreaName?: string,
        public supplyAreaId?: number,
        public supplyZoneManagerId?: number,
        public supplyAreaManagerId?: number
    ) {}
}
