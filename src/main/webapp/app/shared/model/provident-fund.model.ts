import { Moment } from 'moment';

export interface IProvidentFund {
    id?: number;
    startDate?: Moment;
    rate?: number;
    status?: boolean;
    modifiedBy?: number;
    modifiedOn?: Moment;
}

export class ProvidentFund implements IProvidentFund {
    constructor(
        public id?: number,
        public startDate?: Moment,
        public rate?: number,
        public status?: boolean,
        public modifiedBy?: number,
        public modifiedOn?: Moment
    ) {
        this.status = this.status || false;
    }
}
