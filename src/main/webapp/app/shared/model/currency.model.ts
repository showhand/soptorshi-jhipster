import { Moment } from 'moment';

export const enum CurrencyFlag {
    BASE = 'BASE',
    OPTIONAL = 'OPTIONAL'
}

export interface ICurrency {
    id?: number;
    description?: string;
    notation?: string;
    flag?: CurrencyFlag;
    modifiedBy?: string;
    modifiedOn?: Moment;
}

export class Currency implements ICurrency {
    constructor(
        public id?: number,
        public description?: string,
        public notation?: string,
        public flag?: CurrencyFlag,
        public modifiedBy?: string,
        public modifiedOn?: Moment
    ) {}
}
