import { Moment } from 'moment';

export interface IFinancialAccountYear {
    id?: number;
    startDate?: Moment;
    endDate?: Moment;
    previousYear?: number;
    status?: boolean;
}

export class FinancialAccountYear implements IFinancialAccountYear {
    constructor(
        public id?: number,
        public startDate?: Moment,
        public endDate?: Moment,
        public previousYear?: number,
        public status?: boolean
    ) {
        this.status = this.status || false;
    }
}
