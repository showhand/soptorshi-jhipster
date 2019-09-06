import { Moment } from 'moment';

export interface IPredefinedNarration {
    id?: number;
    narration?: string;
    modifiedBy?: string;
    modifiedOn?: Moment;
    voucherName?: string;
    voucherId?: number;
}

export class PredefinedNarration implements IPredefinedNarration {
    constructor(
        public id?: number,
        public narration?: string,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public voucherName?: string,
        public voucherId?: number
    ) {}
}
