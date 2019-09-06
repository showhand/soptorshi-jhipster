import { Moment } from 'moment';

export interface IConversionFactor {
    id?: number;
    covFactor?: number;
    rcovFactor?: number;
    bcovFactor?: number;
    rbcovFactor?: number;
    modifiedBy?: string;
    modifiedOn?: Moment;
    currencyNotation?: string;
    currencyId?: number;
}

export class ConversionFactor implements IConversionFactor {
    constructor(
        public id?: number,
        public covFactor?: number,
        public rcovFactor?: number,
        public bcovFactor?: number,
        public rbcovFactor?: number,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public currencyNotation?: string,
        public currencyId?: number
    ) {}
}
