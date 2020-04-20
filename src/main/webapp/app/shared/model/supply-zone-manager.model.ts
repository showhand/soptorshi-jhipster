import { Moment } from 'moment';

export const enum SupplyZoneManagerStatus {
    ACTIVE = 'ACTIVE',
    INACTIVE = 'INACTIVE'
}

export interface ISupplyZoneManager {
    id?: number;
    endDate?: Moment;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
    status?: SupplyZoneManagerStatus;
    supplyZoneZoneName?: string;
    supplyZoneId?: number;
    employeeFullName?: string;
    employeeId?: number;
}

export class SupplyZoneManager implements ISupplyZoneManager {
    constructor(
        public id?: number,
        public endDate?: Moment,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment,
        public status?: SupplyZoneManagerStatus,
        public supplyZoneZoneName?: string,
        public supplyZoneId?: number,
        public employeeFullName?: string,
        public employeeId?: number
    ) {}
}
