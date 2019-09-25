import { Moment } from 'moment';

export const enum FinancialYearStatus {
    ACTIVE = 'ACTIVE',
    NOT_ACTIVE = 'NOT_ACTIVE'
}

export interface IFinancialAccountYear {
    id?: number;
    startDate?: Moment;
    endDate?: Moment;
    previousStartDate?: Moment;
    previousEndDate?: Moment;
    status?: FinancialYearStatus;
}

export class FinancialAccountYear implements IFinancialAccountYear {
    constructor(
        public id?: number,
        public startDate?: Moment,
        public endDate?: Moment,
        public previousStartDate?: Moment,
        public previousEndDate?: Moment,
        public status?: FinancialYearStatus
    ) {}
}
