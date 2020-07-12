import { Moment } from 'moment';

export const enum SupplyAreaManagerStatus {
    ACTIVE = 'ACTIVE',
    INACTIVE = 'INACTIVE'
}

export interface ISupplyAreaManager {
    id?: number;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
    endDate?: Moment;
    status?: SupplyAreaManagerStatus;
    supplyZoneName?: string;
    supplyZoneId?: number;
    supplyAreaName?: string;
    supplyAreaId?: number;
    employeeFullName?: string;
    employeeId?: number;
    supplyZoneManagerId?: number;
}

export class SupplyAreaManager implements ISupplyAreaManager {
    constructor(
        public id?: number,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment,
        public endDate?: Moment,
        public status?: SupplyAreaManagerStatus,
        public supplyZoneName?: string,
        public supplyZoneId?: number,
        public supplyAreaName?: string,
        public supplyAreaId?: number,
        public employeeFullName?: string,
        public employeeId?: number,
        public supplyZoneManagerId?: number
    ) {}
}
