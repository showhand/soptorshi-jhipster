import { Moment } from 'moment';

export const enum TaxStatus {
    ACTIVE = 'ACTIVE',
    INACTIVE = 'INACTIVE'
}

export interface ITax {
    id?: number;
    amount?: number;
    taxStatus?: TaxStatus;
    modifiedBy?: string;
    modifiedOn?: Moment;
    financialAccountYearId?: number;
    employeeFullName?: string;
    employeeId?: number;
}

export class Tax implements ITax {
    constructor(
        public id?: number,
        public amount?: number,
        public taxStatus?: TaxStatus,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public financialAccountYearId?: number,
        public employeeFullName?: string,
        public employeeId?: number
    ) {}
}
