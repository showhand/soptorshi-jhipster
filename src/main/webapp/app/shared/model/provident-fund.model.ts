import { Moment } from 'moment';

export const enum ProvidentFundStatus {
    ACTIVE = 'ACTIVE',
    NOT_ACTIVE = 'NOT_ACTIVE'
}

export interface IProvidentFund {
    id?: number;
    startDate?: Moment;
    rate?: number;
    status?: ProvidentFundStatus;
    modifiedBy?: string;
    modifiedOn?: Moment;
    employeeFullName?: string;
    employeeId?: number;
}

export class ProvidentFund implements IProvidentFund {
    constructor(
        public id?: number,
        public startDate?: Moment,
        public rate?: number,
        public status?: ProvidentFundStatus,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public employeeFullName?: string,
        public employeeId?: number
    ) {}
}
