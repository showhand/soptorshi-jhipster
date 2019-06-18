import { Moment } from 'moment';

export interface IProvidentFund {
    id?: number;
    startDate?: Moment;
    rate?: number;
    status?: boolean;
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
        public status?: boolean,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public employeeFullName?: string,
        public employeeId?: number
    ) {
        this.status = this.status || false;
    }
}
