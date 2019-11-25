import { Moment } from 'moment';

export interface ISalaryMessages {
    id?: number;
    comments?: any;
    commentedOn?: Moment;
    commenterFullName?: string;
    commenterId?: number;
    monthlySalaryId?: number;
}

export class SalaryMessages implements ISalaryMessages {
    constructor(
        public id?: number,
        public comments?: any,
        public commentedOn?: Moment,
        public commenterFullName?: string,
        public commenterId?: number,
        public monthlySalaryId?: number
    ) {}
}
