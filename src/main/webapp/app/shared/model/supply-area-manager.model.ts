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
    supplyZoneZoneName?: string;
    supplyZoneId?: number;
    supplyAreaAreaName?: string;
    supplyAreaId?: number;
    employeeFullName?: string;
    employeeId?: number;
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
        public supplyZoneZoneName?: string,
        public supplyZoneId?: number,
        public supplyAreaAreaName?: string,
        public supplyAreaId?: number,
        public employeeFullName?: string,
        public employeeId?: number
    ) {}
}
